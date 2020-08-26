package PopulateForm_POM;

import utility.WebDr;

import java.util.HashMap;
import java.util.Map;

public class PopulateForm_POM {

    public static void PopulateForm_PageObjects() {
        Map<String, String> My_Page_Obejcts = new HashMap<String, String>();
        /*****
         *
         *
         */
        My_Page_Obejcts.put("lblCareerTab","XPATH|//*[@id='menu-item-1373']/a[text()='CAREERS']");
        My_Page_Obejcts.put("lblSouthAfrica","XPATH|//div[@class='vc_btn3-container vc_btn3-left']//*[text()='South Africa']");
        My_Page_Obejcts.put("lblJob","XPATH|(//div[@class='wpjb-line-major'])[1]");
        My_Page_Obejcts.put("btnApply","XPATH|//a[@class='wpjb-button wpjb-form-toggle wpjb-form-job-apply']");
        My_Page_Obejcts.put("txtName","XPATH|//input[@id='applicant_name']");
        My_Page_Obejcts.put("txtEmail","XPATH|//input[@id='email']");
        My_Page_Obejcts.put("txtPhone","XPATH|//input[@id='phone']");
        My_Page_Obejcts.put("btnSendApplication","XPATH|//input[@id='wpjb_submit']");
        My_Page_Obejcts.put("lblUploadValidation","XPATH|//ul[@class='wpjb-errors']");
        WebDr.page_Objects=My_Page_Obejcts;
    }
}
