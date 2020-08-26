package utility;

import org.apache.poi.xssf.usermodel.*;
import testCases.Driver;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {
    static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook = null;
    private static XSSFCell Cell;

    public static int curr_row=0;
    public static int data_row=0;
    public static int itr_size=1;

    public static String iteration_Flag = "Yes";

    //This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
    public void setExcelFile(String Path,String SheetName) throws Exception
    {

        try {
            // Open the Excel file
            FileInputStream ExcelFile = new FileInputStream(Path);
            // Access the required test data sheet
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(SheetName);
        }
        catch (Exception e)
        {
            throw (e);
        }
    }
    //This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num
    public String getCellData(int RowNum, int ColNum) throws Exception
    {

        try{
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.getStringCellValue();
            return CellData;
        }
        catch (Exception e)
        {
            return"";
        }
    }
    public Map<String, String> getCurrentTestData(String tc_id) throws Exception
    {
        try {

            if(data_row==0)
            {
                setExcelFile(Constant.Path_TestData + Driver.file_TestData,"TestData");

                int j=1;
                int i=0;

                while(j==1)
                {
                    String col1Value = getCellData(i,0);
                    if(col1Value.equals(tc_id))
                    {
                        curr_row=i;
                        int s_size=0;
                        int k=1;
                        int z=i+1; //i will pe field name row, z is field data row

                        while(k==1)
                        {

                            int x_size=getCellData(z,0).length() ;
                            if(x_size != 0)
                            {
                                itr_size=s_size;
                                j=0;
                                k=0;
                            }
                            s_size++;
                            z++;
                        }
                    }
                    i++;
                }
                data_row=curr_row+1;
            }
            Map<String, String> dictionary = new HashMap<String, String> ();
            setExcelFile(Constant.Path_TestData + Driver.file_TestData,"TestData");
            int k=1;
            int i=1;
            while(k==1)
            {
                if(i==2){
                    int iter_flag = getCellData(curr_row,i).length();
                    String interationFlagText = getCellData(curr_row,i) ;
                    if(iter_flag != 0 && interationFlagText.equalsIgnoreCase("TC_ExecutionFlag")){
                        iteration_Flag = getCellData(data_row,i);
                    }else{
                        iteration_Flag = "Yes";
                    }
                    String key=getCellData(curr_row,i);
                    String value="" + getCellData(data_row, i);
                    dictionary.put(key, value);
                    i++;
                }else{
                    int x_size=getCellData(curr_row,i).length();
                    if(x_size!=0)
                    {
                        String key=getCellData(curr_row,i);
                        String value="" + getCellData(data_row, i);
                        dictionary.put(key, value);
                        i++;
                    }
                    else
                    {
                        k=0;
                    }

                }

            }
            return dictionary;
        } catch (Exception e)
        {
            System.out.println("error while reading config - " + e );
            return null;
        }

    }

}
