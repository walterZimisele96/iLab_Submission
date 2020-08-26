package testCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utility.Launcher;

import java.util.HashMap;
import java.util.Map;

public class Driver extends Launcher {
    public static String userDir, file_TestData;
    public static Map<String, String> dictionary = new HashMap<String, String>();
    public static void main (String args[]) throws Exception
    {
        Driver ob=new Driver();
        ob.mainDriver("Chrome");
    }

    @Test
    @Parameters("Browser")
    public void mainDriver(String testngBrowser) throws Exception
    {

        try{
            userDir = System.getProperty("user.dir");
            file_TestData= "MasterData.xlsx";
            InvokeLauncher(testngBrowser);
        }
        catch (Exception e){
            System.out.println ("Exception in Driver.mainDriver" + e.toString ());

        }
    }
}
