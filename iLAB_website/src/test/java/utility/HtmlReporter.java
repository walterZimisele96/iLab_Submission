package utility;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import testCases.Driver;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HtmlReporter extends ExcelUtils
{
    public static String htmlReport, captureScreen, apEnv, appCycle, appUrl, executionType, ScreenFolder ;
    public String testCase_Itr, testCase_ID, testCase_Name, dirPath, screenDirPath;
    public int IterationCnt, step_no, tc_no;
    public ExtentReports extent;
    public ExtentTest test;

    public void setUpReportFolder(String preferbrowser) throws Exception
    {
        setExcelFile(Constant.Path_TestData + Driver.file_TestData,"Configuration");
        htmlReport=getCellData(2,1);
        captureScreen=getCellData(3,1);
        apEnv=getCellData(4,1);
        appCycle=getCellData(5,1);
        appUrl=getCellData(6,1);
        executionType=getCellData(7,1);
        tc_no=0;
        DateFormat dateFormat = new SimpleDateFormat ("ddMMyyyyHHmmss");
        Date date = new Date();
        String NowDateTime = dateFormat.format(date);
        dirPath=Driver.userDir + "/TestResults/" + "Result_" +  preferbrowser + "_" + NowDateTime;
    }
    public void startTestCase(String testCaseName, String testDescription, String Browser) throws Exception {
        ScreenFolder = testCase_Name + "-" + testCase_ID + "-" + testCase_Itr;
        screenDirPath = dirPath + "/Screenshots/" + ScreenFolder;
        System.setProperty("ScreeshotPath_"+Browser, screenDirPath);
        String sScenarioName = testCaseName.split("-")[0];
        try {
            Files.createDirectories (Paths.get (screenDirPath));
            test = extent.startTest(testCaseName, testDescription);
            test.assignCategory(sScenarioName);
        } catch (Exception e) {
            System.out.println ("Exception in HtmlReporter.startTestCase " + e.toString ());
            throw e;
        }
    }
    public void startResult(String browser) throws Exception{
        try{
            extent = new ExtentReports(dirPath+ "/result_"+browser+".html", false);
            extent.addSystemInfo ("Browser",browser);
            extent.loadConfig(new File(Constant.Path_Plugin + "/extent-config.xml"));
        }
        catch (Exception e){
            System.out.println ("Exception in HtmlReporter.startResult " + e.toString () );
            throw e;
        }
    }

    public HtmlReporter WriteStep(WebDriver driver, String description, String expected, String actual, String Status ) throws Exception
    {
        String snapPath = null;
        try
        {
            setExcelFile(Constant.Path_TestData + Driver.file_TestData, "Configuration");
            String ScreenShotCapture = getCellData(2,1);
            switch (ScreenShotCapture) {
                case "On Every Step" : snapPath = takeSnap(driver,description);break;
                case "On Error": if (Status.toUpperCase().equals("FAIL")) {
                    snapPath = takeSnap(driver,description);
                }break;
                case "Never": snapPath = "";break;
            }
            String ScreenshotPath = "./Screenshots/" + ScreenFolder + "/" + snapPath + ".png";
            if (snapPath.equals ("")){
                if(Status.toUpperCase().equals("PASS")){
                    test.log(LogStatus.PASS,"Desc: " + description +   " -||- Expected: " + expected + " -||- Actual: " + actual );
                }else if(Status.toUpperCase().equals("FAIL")){
                    test.log(LogStatus.FAIL," Desc: " +description + " -||- Expected: " + expected+ " -||- Actual: " + actual);
                }else if(Status.toUpperCase().equals("FATAL")){
                    test.log(LogStatus.FATAL, " Desc: " +description + " -||- Expected: " + expected+ " -||- Actual: " + actual);
                }else if(Status.toUpperCase().equals("DONE")){
                    test.log(LogStatus.INFO, " Desc: " +description + " -||- Expected: " + expected+ " -||- Actual: " + actual);
                }
            }
            else{
                if(Status.toUpperCase().equals("PASS")){
                    test.log(LogStatus.PASS,"Desc: " + description +   " -||- Expected: " + expected + " -||- Actual: " + actual + test.addScreenCapture(ScreenshotPath));
                }else if(Status.toUpperCase().equals("FAIL")){
                    test.log(LogStatus.FAIL," Desc: " +description + " -||- Expected: " + expected+ " -||- Actual: " + actual+ test.addScreenCapture(ScreenshotPath));
                }else if(Status.toUpperCase().equals("FATAL")){
                    test.log(LogStatus.FATAL, " Desc: " +description + " -||- Expected: " + expected+ " -||- Actual: " + actual+ test.addScreenCapture(ScreenshotPath));
                }else if(Status.toUpperCase().equals("DONE")){
                    test.log(LogStatus.INFO, " Desc: " +description + " -||- Expected: " + expected+ " -||- Actual: " + actual+ test.addScreenCapture(ScreenshotPath));
                }
            }

            System.out.println("Desc: " + description +  " Expected: " + expected + " Actual: " + actual + " - Status: " + Status );

        }
        catch (Exception e)
        {
            System.out.println ("Exception in HtmlReporter.WriteStep " + e.toString () );
            throw e;
        }
        return this;

    }

    public String takeSnap(WebDriver driver, String Description) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat ("ddMMyyyyHHmmss");
        Date date = new Date();
        String NowDateTime = dateFormat.format(date);

        String strGetScreenshotPath = "";
        String ImageFilePath = "";
        try {
            String browserName  = driver.toString();
            if(browserName.contains("Chrome")){
                strGetScreenshotPath = System.getProperty("ScreeshotPath_Chrome");
            }else if(browserName.contains("Firefox")) {
                strGetScreenshotPath = System.getProperty("ScreeshotPath_Firefox");
            }

            ImageFilePath = strGetScreenshotPath + "/" + Description.trim() + "_" + NowDateTime + ".png";

                File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(ImageFilePath));
        } catch (Exception e) {
            System.out.println("Error in TakeSnap" + e.toString());
        }
        return Description.trim() + "_" + NowDateTime;

    }
    @AfterClass
    public void endTestcase() {
        extent.endTest(test);
    }
    @AfterClass
    public void endTestReport() throws Exception {
        extent.flush();
    }
}
