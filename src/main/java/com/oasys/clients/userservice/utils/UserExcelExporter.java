package com.oasys.clients.userservice.utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.oasys.uppcl_user__master_management.entity.BankBranchDetailsEntity;

@Component
public class UserExcelExporter {
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<BankBranchDetailsEntity> listUsers;
     
    public UserExcelExporter(List<BankBranchDetailsEntity> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("IFSC_Code");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        
        createCell(row, 0, "ID", style);      
        createCell(row, 1, "Bank ID", style);       
        createCell(row, 2, "State Code", style);    
        createCell(row, 3, "District Name", style);
        createCell(row, 4, "IFSC Code", style);
        createCell(row, 5, "Branch Name", style);
        createCell(row, 6, "Bank Name", style);
        createCell(row, 7, "State Name", style);
        createCell(row, 8, "Created By", style);
        createCell(row, 9, "Created Date", style);
        createCell(row, 10, "Modified Date", style);
        createCell(row, 11, "Modified By", style);
        createCell(row, 12, "Status", style);
        createCell(row, 13, "STD Code", style);
        createCell(row, 14, "Landline Number", style);
        createCell(row, 15, "Bank Name ID", style);
        createCell(row, 16, "Branch Contact Number", style);
        createCell(row, 17, "Address", style);
        createCell(row, 18, "Remarks", style);
        
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (BankBranchDetailsEntity list : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, list.getId().toString(), style);
            createCell(row, columnCount++, list.getBankId(), style);
            createCell(row, columnCount++, list.getStateCode(), style);
            createCell(row, columnCount++, list.getDistrictName(), style);
            createCell(row, columnCount++, list.getIfscCode(), style);
            createCell(row, columnCount++, list.getBranchName(), style);
            createCell(row, columnCount++, list.getBankName(), style);
            createCell(row, columnCount++, list.getStateName(), style);
            createCell(row, columnCount++, list.getCreatedBy(), style);
            createCell(row, columnCount++, list.getCreatedDate().toString(), style);
            createCell(row, columnCount++, list.getModifiedDate().toString(), style);
            createCell(row, columnCount++, list.getModifiedBy(), style);
            createCell(row, columnCount++, list.getStatus(), style);
            createCell(row, columnCount++, list.getStdCode(), style);
            createCell(row, columnCount++, list.getLandlineNumber(), style);
            createCell(row, columnCount++, list.getBankNameId().getId().toString(), style);
            createCell(row, columnCount++, list.getBranchContactNumber(), style);
            createCell(row, columnCount++, list.getAddress(), style);
            createCell(row, columnCount++, list.getRemarks(), style);
             
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
    
	public String getByColumnName(Workbook wb, Sheet sheet, String ColumnName, int rowNum)
			throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {
		DataFormatter dataFormatter = new DataFormatter();

		Row row = sheet.getRow(0);
		String cellValue = null;
		short lastcolumnused = row.getLastCellNum();

		for (int i = 0; i < lastcolumnused; i++) {
			if (row.getCell(i).getStringCellValue().equalsIgnoreCase(ColumnName)) {
				row = sheet.getRow(rowNum);
				Cell column = row.getCell(i);
				if (Objects.nonNull(column)) {
					cellValue = dataFormatter.formatCellValue(column);
					cellValue = StringUtils.isNotBlank(cellValue) ? cellValue : null;
				}
				break;
			}
		}
		return cellValue;
	}

}
