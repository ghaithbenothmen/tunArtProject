package Controllers;

import Entites.Concours;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Cell;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportToExcel {
    public static void exportToExcel(List<Concours> concours, File file) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Concours");
        CellStyle headerStyle = createHeaderStyle(workbook);

        int row = 0;
        Row headerRow = sheet.createRow(row++);
        createStyledCell(headerRow, 0, "Name", headerStyle);
        createStyledCell(headerRow, 1, "Type", headerStyle);
        createStyledCell(headerRow, 2, "Pool Price", headerStyle);
        createStyledCell(headerRow, 3, "Date", headerStyle);
        createStyledCell(headerRow, 4, "Link", headerStyle);

        for (Concours r : concours) {
            Row fillRow = sheet.createRow(row++);
            createCell(fillRow, 0, r.getNom());
            createCell(fillRow, 1, r.getType().toString());
            createCell(fillRow, 2, String.valueOf(r.getPrix()));
            createCell(fillRow, 3, r.getDate().toString());
            createCell(fillRow, 4, r.getLien());
        }

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
        } catch (IOException ex) {
            Logger.getLogger(ExportToExcel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            workbook.close();
        }
    }

    private static void createCell(Row row, int column, String value) {
        Cell cell = row.createCell(column, CellType.STRING);
        cell.setCellValue(value);
    }

    private static void createStyledCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column, CellType.STRING);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private static CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();




        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        return style;
    }
}