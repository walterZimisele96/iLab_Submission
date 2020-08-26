package testCases;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import utility.Constant;
import utility.WebDr;

public class testFlow extends WebDr {

    public testFlow(ExtentTest test, ExtentReports extent) {
        this.test = test;
        this.extent = extent;
    }

    public void executeTC(String fn_name,String preferBrowser) throws Exception
    {
        try {
            switch (fn_name) {
                case "populate_form" :
                    populate_form(preferBrowser);break;
                default:break;
            }
        } catch (Exception e) {
            wdriver.quit();
        }
    }

    public void populate_form(String preferBrowser) throws Exception{
        setExcelFile(Constant.Path_TestData + Driver.file_TestData,"Configuration");
        String sURL=getCellData(5,1);
        openApplication(sURL, preferBrowser);
        if (new PopulateForm.PopulateForm(wdriver,test).fn_populateForm()){

        }

    }
}