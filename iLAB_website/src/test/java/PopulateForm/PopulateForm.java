package PopulateForm;
import PopulateForm_POM.PopulateForm_POM;
import com.relevantcodes.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import testCases.Driver;
import utility.Constant;
import utility.WebDr;

public class PopulateForm extends WebDr {

    public PopulateForm(WebDriver wdriver, ExtentTest test) throws Exception {
        this.wdriver = wdriver;
        this.test = test;
    }
public boolean fn_populateForm() throws Exception {
        boolean blnActivate_Reg = false;
        setExcelFile(Constant.Path_TestData + Driver.file_TestData, "Configuration");
        PopulateForm_POM.PopulateForm_PageObjects();
        String sName = getValue("Name");
        String sEmail = getValue("Email");
        String sExpect = "";

        try {

            click("lblCareerTab","Click Career tab");
            Thread.sleep(300);
            click("lblSouthAfrica","Click South Africa");
            Thread.sleep(300);
            click("lblJob","Click first job");
            Thread.sleep(300);
            click("btnApply","Click apply button");
            Thread.sleep(300);
            setText("txtName",sName,"Set name");
            Thread.sleep(100);
            setText("txtEmail",sEmail,"Set email");
            Thread.sleep(100);
            setText("txtPhone",getPhoneNumber(),"Set phone number");
            Thread.sleep(100);
            click("btnSendApplication","Click send application");
            Thread.sleep(100);
            sExpect = "You need to upload at least one file.";
            validateString(sExpect,getText("lblUploadValidation","get error message"),"Error message validation");

            blnActivate_Reg = true;
        } catch (Exception e) {
            WriteStep(wdriver, "Execution in Activate Trips Flow", "Execution of Activate Trips Flow", e.toString(), "FAIL");
        }
        return blnActivate_Reg;
    }
}
