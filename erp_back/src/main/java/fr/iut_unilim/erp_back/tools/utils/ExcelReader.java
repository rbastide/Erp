package fr.iut_unilim.erp_back.tools.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ExcelReader {

    public static void readExcelFile(String filePath) {
        try (FileInputStream fis = new FileInputStream(new File(filePath))) {

            Workbook workbook = new XSSFWorkbook(fis);


            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                System.out.println("Reading Sheet: " + sheet.getSheetName());


                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    if (row.getRowNum() <= 1) continue;


                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case STRING:
                                System.out.print(cell.getStringCellValue() + "\t");
                                break;
                            case NUMERIC:
                                System.out.print(cell.getNumericCellValue() + "\t");
                                break;
                            case BOOLEAN:
                                System.out.print(cell.getBooleanCellValue() + "\t");
                                break;
                            default:
                                System.out.print("\t");
                                break;
                        }
                    }
                    System.out.println();
                }
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
