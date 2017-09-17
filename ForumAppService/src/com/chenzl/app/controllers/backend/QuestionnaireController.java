package com.chenzl.app.controllers.backend;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.chenzl.app.bean.backend.forum.QuestionnaireQuestion;
import com.chenzl.app.bean.backend.forum.QuestionnaireQuestionDetail;
import com.chenzl.app.bean.backend.forum.QuestionnaireQuestionDetailUpload;
import com.chenzl.app.bean.backend.forum.Questionnaire;
import com.chenzl.app.bean.app.QuestionnaireAnswerCount;
import com.chenzl.app.bean.app.QuestionnaireDetail;
import com.chenzl.app.entity.CctQualityTestpaper;
import com.chenzl.app.entity.CctQuestionnaire;
import com.chenzl.app.entity.CctQuestionnaireDetail;
import com.chenzl.app.entity.CctQuestionnaireQuestion;
import com.chenzl.app.entity.CctQuestionnaireStaff;
import com.chenzl.app.exception.BusinessException;
import com.chenzl.app.service.backend.forum.QuestionnaireService;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("manage/questionnaire")
public class QuestionnaireController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(QuestionnaireController.class);

	@Autowired
	private QuestionnaireService questionnaireService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 查询调查问卷信息 分页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getQuestionnaireList", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getQuestionnaireList(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("QuestionnaireController.getCctQuestionnaireList...");
		String questionnaireName = request.getParameter("questionnaireName");
		String questionnaireType = request.getParameter("questionnaireType");
		String staffId = request.getParameter("staffId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		JSONObject returnObject = new JSONObject();
		int iPage = 0;
		int iRows = 0;
		if (!StringUtils.isBlank(page)) {
			iPage = Integer.parseInt(page);
		} else {
			iPage = 1;
		}
		if (!StringUtils.isBlank(rows)) {
			iRows = Integer.parseInt(rows);
		} else {
			iRows = 10;
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", Integer.valueOf(iPage));
			params.put("rows", Integer.valueOf(iRows));
			if (!StringUtils.isBlank(questionnaireName)) {
				params.put("questionnaireName", questionnaireName);
			}
			if (!StringUtils.isBlank(questionnaireType)) {
				params.put("questionnaireType", Short.valueOf(questionnaireType));
			}
			if (!StringUtils.isBlank(staffId) && staffId.indexOf("SCKF") == -1) {
				params.put("areaId", areaId);
			}
			params.put("state", Short.valueOf("1")); // 状态 [1] 正常
			Page<CctQuestionnaire> pageList = questionnaireService
					.queryCctQuestionnaireForPage(params);
			returnObject.put("rows", pageList);
			returnObject.put("total", pageList.getTotal());
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 新增调查问卷
	 * 
	 * @param request
	 * @param response
	 * @param questionnaire
	 * @throws Exception
	 */
	@RequestMapping(value = "saveQuestionnaire", method = RequestMethod.POST)
	public void saveQuestionnaire(HttpServletRequest request,
			HttpServletResponse response, Questionnaire questionnaire)
			throws Exception {
		LOGGER.debug("QuestionnaireController.saveQuestionnaire...");

		CctQuestionnaire cctQuestionnaire = new CctQuestionnaire();

		if (!StringUtils.isBlank(questionnaire.getQuestionnaireId())) {
			cctQuestionnaire.setQuestionnaireId(Long.valueOf(questionnaire.getQuestionnaireId()));
		}

		cctQuestionnaire.setQuestionnaireName(questionnaire.getQuestionnaireName());
		cctQuestionnaire.setQuestionnaireType(Short.valueOf(questionnaire.getQuestionnaireType()));
		cctQuestionnaire.setQuestionnaireState(Short.valueOf(questionnaire.getQuestionnaireState()));
		
		Timestamp endDate = new Timestamp((sdf.parse(questionnaire.getEndDate())).getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		endDate = new Timestamp(calendar.getTimeInMillis());
		cctQuestionnaire.setEndDate(endDate);

		if (StringUtils.isBlank(questionnaire.getQuestionnaireId())) {
			cctQuestionnaire.setState(Short.valueOf("1")); // 状态 [1] 正常
			cctQuestionnaire.setCreateStaff(questionnaire.getStaffId());
			cctQuestionnaire.setUpdateStaff(questionnaire.getStaffId());
			questionnaireService.createCctQuestionnaire(cctQuestionnaire);
		} else {
			cctQuestionnaire.setUpdateStaff(questionnaire.getStaffId());
			questionnaireService.updateCctQuestionnaire(cctQuestionnaire);
		}

		response.sendRedirect(request.getContextPath()
				+ "/manage/page/questionnaire.do?dicName=forum");
	}

	/**
	 * 根据ID查询调查问卷
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getQuestionnaireById", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getQuestionnaireById(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("QuestionnaireController.getQuestionnaireById...");
		String questionnaireId = request.getParameter("questionnaireId");
		JSONObject returnObject = new JSONObject();
		try {
			CctQuestionnaire cctQuestionnaire = questionnaireService
					.queryCctQuestionnaireById(Long.valueOf(questionnaireId));
			returnObject.put("data", cctQuestionnaire);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 根据ID删除调查问卷
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteQuestionnaire", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteQuestionnaire(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("QuestionnaireController.deleteQuestionnaire...");
		String questionnaireId = request.getParameter("questionnaireId");
		String staffId = request.getParameter("staffId");
		JSONObject returnObject = new JSONObject();
		try {
			int i = questionnaireService.deleteCctQuestionnaire(
					Long.valueOf(questionnaireId), staffId);
			if (i == 1) {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "删除成功");
			} else {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "删除失败");
			}
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 查询调查问卷明细信息 分页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getQuestionnaireDetailList", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getQuestionnaireDetailList(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("QuestionnaireController.getQuestionnaireDetailList...");
		String questionnaireId = request.getParameter("questionnaireId");
		String questionDetailContent = request
				.getParameter("questionDetailContent");
		String staffId = request.getParameter("staffId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		JSONObject returnObject = new JSONObject();
		int iPage = 0;
		int iRows = 0;
		if (!StringUtils.isBlank(page)) {
			iPage = Integer.parseInt(page);
		} else {
			iPage = 1;
		}
		if (!StringUtils.isBlank(rows)) {
			iRows = Integer.parseInt(rows);
		} else {
			iRows = 10;
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", Integer.valueOf(iPage));
			params.put("rows", Integer.valueOf(iRows));
			if (!StringUtils.isBlank(questionnaireId)) {
				params.put("questionnaireId", Long.valueOf(questionnaireId));
			}
			if (!StringUtils.isBlank(questionDetailContent)) {
				params.put("questionDetailContent", questionDetailContent);
			}
			if (!StringUtils.isBlank(staffId) && staffId.indexOf("SCKF") == -1) {
				params.put("areaId", areaId);
			}
			params.put("state", Short.valueOf("1")); // 状态 [1] 正常
			Page<CctQuestionnaireDetail> pageList = questionnaireService
					.queryCctQuestionnaireDetailForPage(params);
			returnObject.put("rows", pageList);
			returnObject.put("total", pageList.getTotal());
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 新增调查问卷明细
	 * 
	 * @param request
	 * @param response
	 * @param questionnaireDetail
	 * @throws Exception
	 */
	@RequestMapping(value = "saveQuestionnaireDetail", method = RequestMethod.POST)
	public void saveQuestionnaireDetail(HttpServletRequest request,
			HttpServletResponse response,
			QuestionnaireDetail questionnaireDetail) throws Exception {
		LOGGER.debug("QuestionnaireController.saveQuestionnaireDetail...");

		CctQuestionnaireDetail cctQuestionnaireDetail = new CctQuestionnaireDetail();

		if (!StringUtils
				.isBlank(questionnaireDetail.getQuestionnaireDetailId())) {
			cctQuestionnaireDetail.setQuestionnaireDetailId(Long
					.valueOf(questionnaireDetail.getQuestionnaireDetailId()));
		}

		cctQuestionnaireDetail.setQuestionnaireId(Long
				.valueOf(questionnaireDetail.getQuestionnaireId()));
		cctQuestionnaireDetail.setQuestionDetailType(Short
				.valueOf(questionnaireDetail.getQuestionDetailType()));
		cctQuestionnaireDetail.setQuestionDetailContent(questionnaireDetail
				.getQuestionDetailContent());

		if (StringUtils.isBlank(questionnaireDetail.getQuestionnaireDetailId())) {
			cctQuestionnaireDetail.setState(Short.valueOf("1")); // 状态 [1] 正常
			cctQuestionnaireDetail.setCreateStaff(questionnaireDetail
					.getStaffId());
			cctQuestionnaireDetail.setUpdateStaff(questionnaireDetail
					.getStaffId());
			questionnaireService
					.createCctQuestionnaireDetail(cctQuestionnaireDetail);
		} else {
			cctQuestionnaireDetail.setUpdateStaff(questionnaireDetail
					.getStaffId());
			questionnaireService
					.updateCctQuestionnaireDetail(cctQuestionnaireDetail);
		}

		response.sendRedirect(request.getContextPath()
				+ "/manage/page/questionnaire_detail.do?dicName=forum");
	}

	/**
	 * 根据ID查询调查问卷明细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getQuestionnaireDetailById", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getQuestionnaireDetailById(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("QuestionnaireController.getQuestionnaireDetailById...");
		String questionnaireDetailId = request
				.getParameter("questionnaireDetailId");
		JSONObject returnObject = new JSONObject();
		try {
			CctQuestionnaireDetail cctQuestionnaireDetail = questionnaireService
					.queryCctQuestionnaireDetailById(Long
							.valueOf(questionnaireDetailId));
			returnObject.put("data", cctQuestionnaireDetail);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 根据ID删除调查问卷明细
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteQuestionnaireDetail", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteQuestionnaireDetail(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("QuestionnaireController.deleteQuestionnaireDetail...");
		String questionnaireDetailId = request
				.getParameter("questionnaireDetailId");
		String staffId = request.getParameter("staffId");
		JSONObject returnObject = new JSONObject();
		try {
			int i = questionnaireService.deleteCctQuestionnaireDetail(
					Long.valueOf(questionnaireDetailId), staffId);
			if (i == 1) {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "删除成功");
			} else {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "删除失败");
			}
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 查询调查问卷明细信息 分页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getQuestionnaireQuestionList", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getQuestionnaireQuestionList(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("QuestionnaireController.getQuestionnaireQuestionList...");
		String questionnaireDetailId = request
				.getParameter("questionnaireDetailId");
		String staffId = request.getParameter("staffId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		// String page = request.getParameter("page");
		// String rows = request.getParameter("rows");
		// int iPage = 0;
		// int iRows = 0;
		// if (!StringUtils.isBlank(page)) {
		// iPage = Integer.parseInt(page);
		// } else {
		// iPage = 1;
		// }
		// if (!StringUtils.isBlank(rows)) {
		// iRows = Integer.parseInt(rows);
		// } else {
		// iRows = 10;
		// }
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			// params.put("page", Integer.valueOf(iPage));
			// params.put("rows", Integer.valueOf(iRows));
			if (!StringUtils.isBlank(questionnaireDetailId)) {
				params.put("questionnaireDetailId",
						Long.valueOf(questionnaireDetailId));
			}
			if (!StringUtils.isBlank(staffId) && staffId.indexOf("SCKF") == -1) {
				params.put("areaId", areaId);
			}
			params.put("state", Short.valueOf("1")); // 状态 [1] 正常
			List<CctQuestionnaireQuestion> pageList = questionnaireService
					.queryCctQuestionnaireQuestionList(params);
			returnObject.put("rows", pageList);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 新增调查问卷明细选项
	 * 
	 * @param request
	 * @param response
	 * @param questionnaireDetail
	 * @throws Exception
	 */
	@RequestMapping(value = "saveQuestionnaireQuestion", method = RequestMethod.POST)
	public void saveQuestionnaireQuestion(HttpServletRequest request,
			HttpServletResponse response,
			QuestionnaireQuestion questionnaireQuestion) throws Exception {
		LOGGER.debug("QuestionnaireController.saveQuestionnaireQuestion...");

		CctQuestionnaireQuestion cctQuestionnaireQuestion = new CctQuestionnaireQuestion();

		if (!StringUtils.isBlank(questionnaireQuestion
				.getQuestionnaireQuestionId())) {
			cctQuestionnaireQuestion
					.setQuestionnaireDetailId(Long
							.valueOf(questionnaireQuestion
									.getQuestionnaireQuestionId()));
		}

		cctQuestionnaireQuestion.setQuestionnaireDetailId(Long
				.valueOf(questionnaireQuestion.getQuestionnaireDetailId()));
		cctQuestionnaireQuestion.setQuestionCode(questionnaireQuestion
				.getQuestionCode());
		cctQuestionnaireQuestion.setQuestionContent(questionnaireQuestion
				.getQuestionContent());

		if (StringUtils.isBlank(questionnaireQuestion
				.getQuestionnaireQuestionId())) {
			cctQuestionnaireQuestion.setState(Short.valueOf("1")); // 状态 [1] 正常
			cctQuestionnaireQuestion.setCreateStaff(questionnaireQuestion
					.getStaffId());
			cctQuestionnaireQuestion.setUpdateStaff(questionnaireQuestion
					.getStaffId());
			questionnaireService
					.createCctQuestionnaireQuestion(cctQuestionnaireQuestion);
		} else {
			cctQuestionnaireQuestion.setUpdateStaff(questionnaireQuestion
					.getStaffId());
			questionnaireService
					.updateCctQuestionnaireQuestion(cctQuestionnaireQuestion);
		}

		response.sendRedirect(request.getContextPath()
				+ "/manage/page/questionnaire_question.do?dicName=forum");
	}

	/**
	 * 新增、修改、删除 调查问卷明细选项
	 * 
	 * @param request
	 * @param response
	 * @param questionnaireDetail
	 * @throws Exception
	 */
	@RequestMapping(value = "saveQuestionnaireQuestions", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> saveQuestionnaireQuestions(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.debug("QuestionnaireController.saveQuestionnaireQuestions...");
		String staffId = request.getParameter("staffId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");

		String inserted = request.getParameter("inserted");
		LOGGER.debug("inserted -> " + inserted);
		String updated = request.getParameter("updated");
		LOGGER.debug("updated -> " + updated);
		String deleted = request.getParameter("deleted");
		LOGGER.debug("deleted -> " + deleted);

		questionnaireService.operateQuestionnaireQuestions(inserted, updated, deleted, staffId);

		JSONObject returnObject = new JSONObject();
		ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
				returnObject, org.springframework.http.HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 上传文件的方式创建问卷
	 * @param request
	 * @param response
	 * @param questionnaireDetailUpload
	 * @throws Exception
	 */
	@RequestMapping(value = "uploadQuestionnaireQuestions", method = RequestMethod.POST)
	public void uploadQuestionnaireQuestions(HttpServletRequest request, HttpServletResponse response,
			QuestionnaireQuestionDetailUpload questionnaireDetailUpload) throws Exception {
		LOGGER.debug("QuestionnaireController.uploadQuestionnaireQuestions...");
		Long questionnaireId = questionnaireDetailUpload.getQuestionnaireId();
		String staffId = questionnaireDetailUpload.getStaffId();
		String areaId = questionnaireDetailUpload.getAreaId();
		String sessionId = questionnaireDetailUpload.getSessionId();
		CommonsMultipartFile excelFile = questionnaireDetailUpload.getExcelFile()[0];
		List<QuestionnaireQuestionDetail> questionnaireDetailList = ReadQuestionnaireExcel.readExcel(excelFile);
		System.out.println(questionnaireDetailList.size());
		questionnaireService.createQuestionnaireQuestionDetails(questionnaireDetailList, questionnaireId, staffId);
		response.sendRedirect(request.getContextPath() + "/manage/page/questionnaire_detail.do?dicName=forum");
	}

	/**
	 * 查询调查问卷人员列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getQuestionnaireStaffList", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getQuestionnaireStaffList(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("CertificationController.getQuestionnaireStaffList...");
		String questionnaireId = request.getParameter("questionnaireId");
		String testStaff = request.getParameter("testStaff");
		String staffId = request.getParameter("staffId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		JSONObject returnObject = new JSONObject();
		int iPage = 0;
		int iRows = 0;
		if (!StringUtils.isBlank(page)) {
			iPage = Integer.parseInt(page);
		} else {
			iPage = 1;
		}
		if (!StringUtils.isBlank(rows)) {
			iRows = Integer.parseInt(rows);
		} else {
			iRows = 10;
		}
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", Integer.valueOf(iPage));
			params.put("rows", Integer.valueOf(iRows));
			if (!StringUtils.isBlank(questionnaireId)) {
				params.put("questionnaireId", questionnaireId);
			}
			if (!StringUtils.isBlank(testStaff)) {
				params.put("testStaff", testStaff);
			}
			Page<CctQuestionnaireStaff> pageList = questionnaireService
					.queryCctQuestionnaireStaffForPage(params);
			returnObject.put("rows", pageList);
			returnObject.put("total", pageList.getTotal());
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 添加调查问卷人员 批量
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "createQuestionnaireStaffs", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> createQuestionnaireStaffs(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("QuestionnaireController.createQuestionnaireStaffs...");
		String questionnaireId = request.getParameter("questionnaireId");
		String staffIds = request.getParameter("staffIds");
		String staffId = request.getParameter("staffId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		try {
			String[] staffArray = staffIds.split(",");
			List<String> staffIdList = Arrays.asList(staffArray);
			questionnaireService.createQuestionnaireStaffList(
					Long.valueOf(questionnaireId), staffIdList, staffId);
			returnObject.put("retCode", "1");
			returnObject.put("retMsg", "添加成功");
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 删除调查问卷人员 批量
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteQuestionnaireStaffs", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteQuestionnaireStaffs(
			HttpServletRequest request) throws Exception {
		LOGGER.debug("CertificationController.deleteQuestionnaireStaffs...");
		String questionnaireStaffIds = request
				.getParameter("questionnaireStaffIds");
		String staffId = request.getParameter("staffId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		try {
			String[] ids = questionnaireStaffIds.split(",");
			List<String> questionnaireStaffIdList = Arrays.asList(ids);
			Boolean b = questionnaireService.deleteQuestionnaireStaffList(questionnaireStaffIdList);
			if (b) {
				returnObject.put("retCode", "1");
				returnObject.put("retMsg", "删除成功！");
			} else {
				returnObject.put("retCode", "0");
				returnObject.put("retMsg", "删除失败！");
			}
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (BusinessException be) {
			returnObject.put("retCode", "0");
			returnObject.put("retMsg", be.getMessage());
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 查询调查问卷明细信息 分页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getQuestionnaireDetailListForResult", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getQuestionnaireDetailListForResult(
			HttpServletRequest request) throws Exception {
		String questionnaireId = request.getParameter("questionnaireId");
		String staffId = request.getParameter("staffId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("questionnaireId", questionnaireId);
			params.put("state", Short.valueOf("1"));
			List<CctQuestionnaireDetail> cctQuestionnaireDetailList = questionnaireService.queryCctQuestionnaireDetailList(params);
			returnObject.put("data", cctQuestionnaireDetailList);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 获取投票统计数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getQuestionnaireAnswerCount", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getQuestionnaireAnswerCount(HttpServletRequest request) throws Exception {
		LOGGER.debug("QuestionnaireController.getQuestionnaireAnswerCount...");
		String questionnaireDetailId = request.getParameter("questionnaireDetailId");
		String staffId = request.getParameter("staffId");
		String areaId = request.getParameter("areaId");
		String sessionId = request.getParameter("sessionId");
		JSONObject returnObject = new JSONObject();
		try {
			Long answerCount = questionnaireService.queryQuestionnaireAnswerCountByQuestionnaireDetailId(Long.valueOf(questionnaireDetailId));
			Long noAnswerCount = questionnaireService.queryQuestionnaireAnswerNoVote(Long.valueOf(questionnaireDetailId));
			List<QuestionnaireAnswerCount> answerCountList = questionnaireService.getQuestionnaireAnswerCountListByQuestionnaireDetailId(Long.valueOf(questionnaireDetailId));
			returnObject.put("answerCount", answerCount);
			returnObject.put("noAnswerCount", noAnswerCount);
			returnObject.put("answerCountList", answerCountList);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			LOGGER.error("getQuestionnaireAnswerCount", e);
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(new JSONObject(), org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 导出调查投票结果
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "exportQuestionnaireDetail", method = RequestMethod.GET)
	public void exportQuestionnaireDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.debug("QuestionnaireController.exportQuestionnaireDetail...");
		String questionnaireId = request.getParameter("questionnaireId");
		
		CctQuestionnaire cctQuestionnaire = questionnaireService.queryCctQuestionnaireById(Long.valueOf(questionnaireId));
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		String questionnaireName = cctQuestionnaire.getQuestionnaireName();
		questionnaireName = URLEncoder.encode(questionnaireName, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="+ questionnaireName + "_" + questionnaireId+".xls");
		
		Workbook exportWorkBook = questionnaireService.getCctQuestionnaireResult(Long.valueOf(questionnaireId));
		OutputStream os = response.getOutputStream();
		exportWorkBook.write(os);
		os.close();
		response.flushBuffer();
		exportWorkBook.close();
	}
}
