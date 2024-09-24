package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	//DataProder 1
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path="./testdata/LoginTestData.xlsx";
		
		ExcelUtility xlutil=new ExcelUtility(path);  //creating an object for xlUtility
		int totalrows=xlutil.getRowCount("sheet1");
		int totalcols=xlutil.getCellCount("sheet1",1);
		
		String logindata[][]=new String[totalrows][totalcols];  //created for two dimension Array which can store login data
		
		for(int i=1;i<=totalrows;i++) {
			for(int j=0;j<totalcols;j++) {
				logindata[i-1][j]=xlutil.getCellData("sheet1", i, j);  // 1,0
			}
		}
		return logindata;  //returning two dimension Array
	}
	
	
	//Dataprovider2
	
	//Dataprovider3
	
	//Dataprovider4

}
