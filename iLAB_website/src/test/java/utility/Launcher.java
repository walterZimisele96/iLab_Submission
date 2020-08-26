package utility;

import org.testng.Reporter;
import testCases.*;
import java.util.*;

public class Launcher extends HtmlReporter {

    public void InvokeLauncher(String preferBrowser) throws Exception
    {
        try {
            setUpReportFolder(preferBrowser);
            startResult(preferBrowser);
            int z1 = 1;
            setExcelFile(Constant.Path_TestData + Driver.file_TestData, "Driver");
            int tc_row = 1;
            String tc_id = "";
            String tc_name = "";
            String fn_name = "";
            while (z1 == 1) {

                setExcelFile(Constant.Path_TestData + Driver.file_TestData, "Driver");
                String s_Flag = getCellData(tc_row, 3);
                if (s_Flag.equals("Yes"))
                {
                    tc_id = getCellData(tc_row, 0);
                    tc_name = getCellData(tc_row, 1);
                    fn_name = getCellData(tc_row, 2);

                    Map<String, String> dictionary1 = getCurrentTestData(tc_id);

                    int k;
                    for (k = 1; k <= itr_size; k++) {
                        testCase_Itr = "" + k;
                        testCase_ID = tc_id;
                        testCase_Name = tc_name;

                        Driver.dictionary = getCurrentTestData(tc_id);
                        //*****************Driver Setup
                        if (iteration_Flag.equals("Yes")) {
//                            String sAge = Driver.dictionary.get("Age");
                            System.out.println( " Iteration Number " + k);
                            String sTestCaseName = Driver.dictionary.get("TestCase_Name");
                            step_no = 0;
                            tc_no++;
                            setExcelFile(Constant.Path_TestData + Driver.file_TestData, "Driver");
                            startTestCase(sTestCaseName, "TC_Description: " + tc_name + " executed in " + preferBrowser, preferBrowser);
                            setExcelFile(Constant.Path_TestData + Driver.file_TestData, "Configuration");
                            new testFlow(test, extent).executeTC(fn_name, preferBrowser);
                            endTestcase();
                            Reporter.log("<h2>TC - " + tc_name + " (ID - " + tc_id + ") - Ends( Iteration - " + k + ")</h2>");
                        }
                        if (k == itr_size) {
                            data_row = 0;
                        } else {
                            data_row++;
                        }
                    }

                    tc_row++;
                } else if (getCellData(tc_row, 3).length() < 1) {
                    z1 = 0;
                } else {
                    tc_row++;
                }
            }
            endTestReport();
        }catch (Exception e) {

        }
    }
}