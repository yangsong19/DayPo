package com.xinlab.blueapple.contenttool.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;



public class PoiReadXls2 {
	public static void main(String[] args) {  
		File f = new File("c:\\a.xls"); 
		InputStream input = null;
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
        try {  
            input = new FileInputStream(f);  
            fs = new POIFSFileSystem(input);  
            wb = new HSSFWorkbook(fs);  
            HSSFSheet sheet = wb.getSheetAt(0);  
            Iterator rows = sheet.rowIterator();  
            while (rows.hasNext()) {  
                HSSFRow row = (HSSFRow) rows.next();  
                // System.out.print("行：" + row.getRowNum() + " ");  
                Iterator cells = row.cellIterator();  
                while (cells.hasNext()) {  
                    HSSFCell cell = (HSSFCell) cells.next();  
                    // System.out.println("列：" + cell.getCellNum());  
                    switch (cell.getCellType()) {  
                    case HSSFCell.CELL_TYPE_NUMERIC: // 数字  
                        System.out.print(cell.getNumericCellValue() + " N ");  
                        break;  
                    case HSSFCell.CELL_TYPE_STRING: // 字符串  
                    	if("".equals(cell.getStringCellValue()) || null == cell.getStringCellValue()){
                    		System.out.print(" empty string ");
                    	}else{
                    		System.out.print(cell.getStringCellValue() + " S ");  
                    	}
                        break;  
                    case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean  
                        System.out.print(cell.getBooleanCellValue() + " B ");  
                        break;  
                    case HSSFCell.CELL_TYPE_FORMULA: // 公式  
                        System.out.print(cell.getCellFormula() + " F ");  
                        break;  
                    case HSSFCell.CELL_TYPE_BLANK: // 空值  
                        System.out.print(" blank ");  
                        break;  
                    case HSSFCell.CELL_TYPE_ERROR: // 故障  
                        System.out.print(" error ");  
                        break;  
                    default:  
                        System.out.print("未知类型   ");  
                        break;  
                    }  
                }  
                System.out.println();  
            }  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        } finally{
        	if(input != null){
        		try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
    }  
}
