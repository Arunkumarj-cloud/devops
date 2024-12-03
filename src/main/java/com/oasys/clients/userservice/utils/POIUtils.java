package com.oasys.clients.userservice.utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class POIUtils {
	public static String getDataByColumnName(Workbook wb, Sheet sheet, String ColumnName, int rowNum)
			throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {

		Row row = sheet.getRow(0);
		String cellValue = null;
		short lastcolumnused = row.getLastCellNum();

		int colnum = 0;
		for (int i = 0; i < lastcolumnused; i++) {
			if (row.getCell(i).getStringCellValue().equalsIgnoreCase(ColumnName)) {
				colnum = i;
				break;
			}
		}
		row = sheet.getRow(rowNum);
		Cell column = row.getCell(colnum);
		cellValue = column.getStringCellValue();
		return cellValue;
	}
	
	public static String getByColumnName(Workbook wb, Sheet sheet, String ColumnName, int rowNum)
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
