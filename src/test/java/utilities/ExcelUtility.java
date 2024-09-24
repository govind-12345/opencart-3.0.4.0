package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility 
{
	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	
	String path;
	
	public ExcelUtility (String path) {
		this.path=path;
	}
	
	public int getRowCount(String sheetName) throws IOException {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		int rowcount=sheet.getLastRowNum();
		workbook.close();
		fi.close();
		return rowcount;
	}
	
	public int getCellCount(String sheetName,int rownum) throws IOException {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		int Cellcount=row.getLastCellNum();
		workbook.close();
		fi.close();
		return Cellcount;
	}
	
	public String getCellData(String sheetName,int rownum,int colnum) throws IOException {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
		
		DataFormatter formatter=new DataFormatter();
		String data;
		
		try {
			data=formatter.formatCellValue(cell); //Returns the formatted value of a cell as a String regardless
		}
		catch(Exception e) {
			data="";
		}
		workbook.close();
		fi.close();
		return data;
	}
	
	public void SetCellData(String sheetname, int rownum, int colnum, String data) throws IOException {
	    File xlfile = new File(path);
	    if (!xlfile.exists()) {  // if file doesn't exist, create new file
	        workbook = new XSSFWorkbook();
	        fo = new FileOutputStream(path);
	        workbook.write(fo);
	        workbook.close();
	        fo.close();
	    }

	    fi = new FileInputStream(path);
	    workbook = new XSSFWorkbook(fi);

	    if (workbook.getSheetIndex(sheetname) == -1) {  // if sheet doesn't exist, create it
	        workbook.createSheet(sheetname);
	    }

	    sheet = workbook.getSheet(sheetname);

	    if (sheet.getRow(rownum) == null) {  // if row doesn't exist, create it
	        sheet.createRow(rownum);
	    }

	    row = sheet.getRow(rownum);

	    if (row.getCell(colnum) == null) {  // if cell doesn't exist, create it
	        row.createCell(colnum);
	    }

	    cell = row.getCell(colnum);
	    cell.setCellValue(data);  // Set the cell data

	    // Move FileOutputStream initialization here, outside the if block
	    fo = new FileOutputStream(path);  // Open FileOutputStream to write to the file
	    workbook.write(fo);  // Write to the file

	    workbook.close();
	    fi.close();
	    fo.close();
	}
		
		public void fillGreenColor(String sheetName,int rownum,int colnum) throws IOException {
		fi=new FileInputStream(path);
		workbook=new XSSFWorkbook(fi);
		sheet=workbook.getSheet(sheetName);
		row=sheet.getRow(rownum);
		cell=row.getCell(colnum);
			
		style=workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
		cell.setCellStyle(style);
		fo=new FileOutputStream(path);
		workbook.write(fo);
		workbook.close();
		fi.close();
		fo.close();
	}
		
		public void fillRedColor(String sheetName,int rownum,int colnum) throws IOException {
			fi=new FileInputStream(path);
			workbook=new XSSFWorkbook(fi);
			sheet=workbook.getSheet(sheetName);
			row=sheet.getRow(rownum);
			cell=row.getCell(colnum);
				
			style=workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				
			cell.setCellStyle(style);
			fo=new FileOutputStream(path);
			workbook.write(fo);
			workbook.close();
			fi.close();
			fo.close();
			
		}

}
