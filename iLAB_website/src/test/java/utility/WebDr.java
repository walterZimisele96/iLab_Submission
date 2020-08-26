package utility;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import testCases.Driver;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class WebDr extends HtmlReporter {

    public static Map<String, String> page_Objects = new HashMap<String, String>();
    public WebDriver wdriver;
    public static String os;

    public void openApplication(String URL, String preferBrowser) throws Exception {

        try {
            DesiredCapabilities oCap = null;
            ChromeOptions options = new ChromeOptions();
            switch (preferBrowser) {
                case "Chrome":
                    System.setProperty("webdriver.chrome.driver", Constant.Chrome_Driver);
                    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                    chromePrefs.put("profile.default_content_settings.popups", 0);
                    chromePrefs.put("download.default_directory", Constant.Download);
                    options.setExperimentalOption("prefs", chromePrefs);
                    DesiredCapabilities cap = DesiredCapabilities.chrome();
                    cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    cap.setCapability(ChromeOptions.CAPABILITY, options);
                    wdriver = new ChromeDriver(cap);
                    wdriver.manage().deleteAllCookies();
                    break;
                case "Firefox":
                    System.setProperty("webdriver.gecko.driver", Constant.Firefox_Driver);
                    oCap = DesiredCapabilities.firefox();
                    oCap.setBrowserName("firefox");
                    wdriver = new FirefoxDriver(oCap);
                    break;
                default:
                    break;
            }

                wdriver.get(URL);
                wdriver.manage().window().maximize();
                wdriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                wdriver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("Failed to create wdriver object for " + preferBrowser + " in Method openApplication in WebDr. Error : " + e.toString());
            throw e;
        }
    }
    public String getValue(String fieldName) {
        try {
            return Driver.dictionary.get(fieldName).trim();
        } catch (Exception e) {
            System.out.println("Exception in WebDr.getValue : " + e.toString());
            throw e;
        }
    }
    public WebElement getElement(String str) throws Exception {
        try {
            String desc = page_Objects.get(str);
            String[] a = desc.split("\\|");
            WebDriverWait wait = new WebDriverWait(wdriver, 30);
            switch (a[0]) {
                case "XPATH":
                    return wdriver.findElement(By.xpath(a[1]));
                default:
                    System.out.println("Function getElement cannot return object for " + str);
                    break;
            }
        } catch (Exception e) {
            System.out.println(os + "-" + e.toString());
            throw e;
        }
        return null;
    }
    public void setText(String elementName, String textToSet, String description) throws Exception {

        try {
            if (textToSet != null) {
                WebElement elmn = getElement(elementName);
                WebDriverWait wait = new WebDriverWait(wdriver, 30);
                if (textToSet == "BLANK") {
                    textToSet = "";
                }
                elmn.clear();
                elmn.sendKeys(textToSet);
                WriteStep(wdriver, description, "Enter Text", "Entered - " + textToSet, "PASS");

            }
        } catch (Exception e) {
            System.out.println("Exception in WebDr.ControlKeyStroke - " + e);
            WriteStep(wdriver, "Object not visible - " + description, "Enter Text", "Not Entered - " + textToSet, "FAIL");
            throw e;
        }

    }

    public void click(String elementName, String description) throws Exception {
        try {
            WebElement elmn = getElement(elementName);

            {
                elmn.click();
                WriteStep(wdriver, description, "Click", "Clicked", "PASS");
            }
        } catch (Exception e) {
            WriteStep(wdriver, "Object not visible - " + description, elementName + ": Click", elementName + ": Not Clicked", "FAIL");
            System.out.println("Exception in WebDr.click - " + elementName + " - " + e.toString());
            throw e;
        }
    }
    //******************************************************************************************************************************************
    public void validateString(String strExpected, String strActual, String description) throws Exception {
        try {

            if (strExpected.toUpperCase().equals("NOT BLANK")) {

                if (strActual.length() == 0) {
                    WriteStep(wdriver, description, strExpected, strActual, "FAIL");
                } else {
                    WriteStep(wdriver, description, strExpected, strActual, "PASS");
                }

            } else if (strExpected.toUpperCase().equals("OPTIONAL")) {

                WriteStep(wdriver, description, strExpected, strActual, "PASS");

            } else {
                if (strExpected.toString().equalsIgnoreCase(strActual.toString())) {
                    WriteStep(wdriver, description, strExpected, strActual, "PASS");
                } else {
                    WriteStep(wdriver, description, strExpected, strActual, "FAIL");
                }
            }

        } catch (Exception e) {
            WriteStep(wdriver, description, "The actual text should match the actual", "Object not displayed", "FAIL");
            System.out.println("Exception in WebDr.setText - " + e);
        }
    }
    //***********************************************************************************************************************************************************************************************************************************************************************/
    public String getPhoneNumber() throws Exception {
        String number ="";
        try {
            Random rand = new Random();
            long drand = (long)(rand. nextDouble()*1000000000L);
            String phone = String.valueOf(drand);
             number = phone.replaceFirst("(\\d{2})(\\d{3})(\\d+)", "$1 $2 $3");
        } catch (Exception e) {
            WriteStep(wdriver, "description", "The actual text should match the actual", "Object not displayed", "FAIL");
            System.out.println("Exception in WebDr.setText - " + e);
        }
        return  + 0+number;
    }
    public String getText(String elementName, String description) throws Exception {
        String textValue = "";
        try {
            if (textValue != null) {
                WebElement elmn = getElement(elementName);
                if (elmn.isDisplayed()) {
                    textValue = elmn.getText();

                } else {
                    WriteStep(wdriver,"Object not visible - " + description, "Get value for " + description, "Object Not visible.", "FAIL");
                }
            }
        } catch (Exception e) {
            WriteStep(wdriver,"Object not visible - " + description, "Get value for " + description, "Object Not visible.", "FAIL");
            System.out.println("Exeption in WebDr.getROPropertyValue - " + e);
            throw e;
        }
        return textValue;
    }
}


