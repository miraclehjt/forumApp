package com.chenzl.app.controllers.backend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.helper.StringUtil;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.chenzl.app.bean.backend.forum.QuestionnaireQuestionDetail;
import com.chenzl.app.entity.CctQuestionnaireDetail;
import com.chenzl.app.entity.CctQuestionnaireQuestion;

public class ReadQuestionnaireExcel {
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
	public static final String EMPTY = "";
	public static final String POINT = ".";
	
	public static String getPostfix(String path) {
		if (path == null || EMPTY.equals(path.trim())) {
			return EMPTY;
		}
		if (path.contains(POINT)) {
			return path.substring(path.lastIndexOf(POINT) + 1, path.length());
		}
		return EMPTY;
	}
	
	public static List<QuestionnaireQuestionDetail> readExcel(CommonsMultipartFile multipartFile) throws IOException {
		String path = multipartFile.getOriginalFilename();
		if (StringUtils.isBlank(path)) {
			return new ArrayList<QuestionnaireQuestionDetail>();
		}
		String postfix = getPostfix(path);
		if (!EMPTY.equals(postfix)) {
			if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
				return readXls(multipartFile.getInputStream());
			} else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
				return readXlsx(multipartFile.getInputStream());
			}
		} else {
			System.out.println(path + NOT_EXCEL_FILE);
		}
		
		return new ArrayList<QuestionnaireQuestionDetail>();
	}
		
	/**
	 * Read the Excel 2003-2007
	 * 
	 * @param path
	 *            the path of the Excel
	 * @return
	 * @throws IOException
	 */
	public static List<QuestionnaireQuestionDetail> readXls(InputStream is) throws IOException {

		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell type = null;
		HSSFCell content = null;
		HSSFCell questionCode = null;
		HSSFCell questionContent = null;
		HSSFCell questionScore = null;
		
		QuestionnaireQuestionDetail questionnaireDetail = null;
		CctQuestionnaireDetail cctQuestionnaireDetail = null;
		List<CctQuestionnaireQuestion> cctQuestionnaireQuestionList = new ArrayList<CctQuestionnaireQuestion>();
		
		List<QuestionnaireQuestionDetail> questionnaireDetailList = new ArrayList<QuestionnaireQuestionDetail>();
		
		// Read the Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			sheet = hssfWorkbook.getSheetAt(numSheet);
			if (sheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				row = sheet.getRow(rowNum);
				if (row != null) {
					// 读取问题主体
					type = row.getCell(0);
					content = row.getCell(1);
					if (!StringUtils.isBlank(getValue(content))) {
						cctQuestionnaireDetail = new CctQuestionnaireDetail();
						cctQuestionnaireDetail.setQuestionDetailType(Short.valueOf(getValue(type)));
						cctQuestionnaireDetail.setQuestionDetailContent(getValue(content));
						cctQuestionnaireDetail.setState(Short.valueOf("1"));
						cctQuestionnaireQuestionList  = new ArrayList<CctQuestionnaireQuestion>();
						questionnaireDetail = new QuestionnaireQuestionDetail(cctQuestionnaireDetail, cctQuestionnaireQuestionList);
						questionnaireDetailList.add(questionnaireDetail);
						if ("4".equals(getValue(type))) {
							continue;
						}
					}
					// 读取问题答案
					questionCode = row.getCell(2);
					questionContent = row.getCell(3);
					questionScore = row.getCell(4);
					if (StringUtil.isBlank(getValue(questionCode))) {
						continue;
					}
					CctQuestionnaireQuestion cctQuestionnaireQuestion = new CctQuestionnaireQuestion();
					cctQuestionnaireQuestion.setQuestionCode(getValue(questionCode));
					cctQuestionnaireQuestion.setQuestionContent(getValue(questionContent));
					cctQuestionnaireQuestion.setQuestionScore(getLongValue(getValue(questionScore)));
					cctQuestionnaireQuestion.setState(Short.valueOf("1"));
					cctQuestionnaireQuestionList.add(cctQuestionnaireQuestion);
				}
			}
		}
		
		return questionnaireDetailList;
	}
		
	/**
	 * Read the Excel 2010
	 * 
	 * @param path
	 *            the path of the excel file
	 * @return
	 * @throws IOException
	 */
	public static List<QuestionnaireQuestionDetail> readXlsx(InputStream is) throws IOException {

		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		XSSFSheet sheet = null;
		XSSFRow row =null;
		XSSFCell type = null;
		XSSFCell content = null;
		XSSFCell questionCode = null;
		XSSFCell questionContent = null;
		XSSFCell questionScore = null;
		
		QuestionnaireQuestionDetail questionnaireDetail = null;
		CctQuestionnaireDetail cctQuestionnaireDetail = null;
		List<CctQuestionnaireQuestion> cctQuestionnaireQuestionList = new ArrayList<CctQuestionnaireQuestion>();
		
		List<QuestionnaireQuestionDetail> questionnaireDetailList = new ArrayList<QuestionnaireQuestionDetail>();
		
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			sheet = xssfWorkbook.getSheetAt(numSheet);
			if (sheet == null) {
				continue;
			}
			// Read the Row
			for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
				row = sheet.getRow(rowNum);
				if (row != null) {
					// 读取问题主体
					type = row.getCell(0);
					content = row.getCell(1);
					if (!StringUtils.isBlank(getValue(content))) {
						cctQuestionnaireDetail = new CctQuestionnaireDetail();
						cctQuestionnaireDetail.setQuestionDetailType(Short.valueOf(getValue(type)));
						cctQuestionnaireDetail.setQuestionDetailContent(getValue(content));
						cctQuestionnaireDetail.setState(Short.valueOf("1"));
						cctQuestionnaireQuestionList  = new ArrayList<CctQuestionnaireQuestion>();
						questionnaireDetail = new QuestionnaireQuestionDetail(cctQuestionnaireDetail, cctQuestionnaireQuestionList);
						questionnaireDetailList.add(questionnaireDetail);
						if ("4".equals(getValue(type))) {
							continue;
						}
					}
					// 读取问题答案
					questionCode = row.getCell(2);
					questionContent = row.getCell(3);
					questionScore = row.getCell(4);
					if (StringUtil.isBlank(getValue(questionCode))) {
						continue;
					}
					CctQuestionnaireQuestion cctQuestionnaireQuestion = new CctQuestionnaireQuestion();
					cctQuestionnaireQuestion.setQuestionCode(getValue(questionCode));
					cctQuestionnaireQuestion.setQuestionContent(getValue(questionContent));
					cctQuestionnaireQuestion.setQuestionScore(getLongValue(getValue(questionScore)));
					cctQuestionnaireQuestion.setState(Short.valueOf("1"));
					cctQuestionnaireQuestionList.add(cctQuestionnaireQuestion);
				}
			}
		}
		
		return questionnaireDetailList;
	}
	
	private static Long getLongValue(final String v) {
		if (StringUtils.isBlank(v)){
			return Long.valueOf("0");
		}
		Float f = Float.valueOf(v);
		return Long.valueOf(f.longValue());
	}
	
	@SuppressWarnings("static-access")
	private static String getValue(XSSFCell xssfRow) {
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
	
	public static void main(String[] args) {
		String filepath = "D:\\asiainfo\\cmc\\cscapp\\STAGE4\\调查问卷模板.xlsx";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filepath);
			List<QuestionnaireQuestionDetail> questionnaireDetailList = readXlsx(fis);
			System.out.println(questionnaireDetailList.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
