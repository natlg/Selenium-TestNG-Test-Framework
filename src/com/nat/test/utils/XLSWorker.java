package com.nat.test.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.nat.test.pages.SearchPage;

/**
 * Class provides work with excel files
 */
public class XLSWorker {

	private String path;
	private FileInputStream fis = null;
	private FileOutputStream fos = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	/**
	 * Class constructor
	 * 
	 * @param path
	 *            Path to the excel file
	 */
	public XLSWorker(String path) {

		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Didn't find file " + path);
		}
	}

	/**
	 * Read data in the TestData sheet. For the correct work there must be empty
	 * row between different test case data in the file
	 * 
	 * @param testCase
	 *            Test Case name as it's in the file
	 * @return Two dimensional array with data from 2 columns to the right of
	 *         Test Case name
	 */
	public String[][] getDataForTest(String testCase) throws Exception {

		// Get sheet
		String sheetName = "TestData";
		int index = workbook.getSheetIndex(sheetName);
		XSSFSheet sheet = workbook.getSheetAt(index);

		// Find row num with test case
		int rowNum = -1;
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row.getCell(0).getStringCellValue().equals(testCase)) {
				rowNum = i;
				break;
			}
		}
		if (rowNum == -1) {
			throw new IllegalArgumentException(
					"Can't find test case with the name " + testCase);
		}

		// Fill list with data till empty cell (array)
		List<String[]> data = new ArrayList<>();
		for (int i = rowNum; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row.getCell(1).getStringCellValue().isEmpty()) {
				break;
			}

			String[] rowData = new String[2];
			rowData[0] = row.getCell(1).getStringCellValue();
			rowData[1] = row.getCell(2).getStringCellValue();
			data.add(rowData);
		}

		// Move list to array
		String[][] array = new String[data.size()][];
		for (int i = 0; i < data.size(); i++) {
			array[i] = data.get(i);
		}
		return array;
	}

	/**
	 * Read data in the cell
	 * 
	 * @param sheetName
	 *            Sheet name
	 * @param colName
	 *            Column name
	 * @param rowNum
	 *            Row number
	 * @return Data in the cell
	 */
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim()
						.equals(colName.trim()))
					colNum = i;
			}
			if (colNum == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";
			return cell.getStringCellValue();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Read data in the cell
	 * 
	 * @param sheetName
	 *            Sheet name
	 * @param colNum
	 *            Column number
	 * @param rowNum
	 *            Row number
	 * @return Data in the cell
	 */
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null) {
				return "";
			}
			return cell.getStringCellValue();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Set data to the cell
	 * 
	 * @param sheetName
	 *            Sheet name
	 * @param colName
	 *            Column name
	 * @param rowNum
	 *            Row number
	 * @param data
	 *            Data
	 * @return True if set the data successfully
	 */
	public boolean setCellData(String sheetName, String colName, int rowNum,
			String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);
			fos = new FileOutputStream(path);
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Set data to the cell
	 * 
	 * @param sheetName
	 *            Sheet name
	 * @param testCase
	 *            Test case
	 * @param data
	 *            Data used in the test
	 * @param result
	 *            Result
	 * @return True if set the data successfully
	 */
	public boolean setCellData(String sheetName, String testCase, String data,
			String result) {
		try {
			// Get sheet
			int index = workbook.getSheetIndex(sheetName);
			XSSFSheet sheet = workbook.getSheetAt(index);

			// Find row num with test case
			int rowNum = -1;
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row.getCell(0).getStringCellValue().equals(testCase)) {
					rowNum = i;
					break;
				}
			}
			if (rowNum == -1) {
				throw new IllegalArgumentException(
						"Can't find test case with the name " + testCase);
			}

			// Find row num with data and save test result
			for (int i = rowNum; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				if (row.getCell(1).getStringCellValue().isEmpty()) {
					break;
				}
				if (row.getCell(1).getStringCellValue().equalsIgnoreCase(data)) {
					cell = row.getCell(3);
					cell.setCellValue(result);
				}
			}

			fos = new FileOutputStream(path);
			workbook.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Didn't find file " + path);
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Can't write data!");
			return false;
		}
		return true;
	}
}
