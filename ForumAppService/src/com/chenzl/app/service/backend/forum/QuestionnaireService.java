package com.chenzl.app.service.backend.forum;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenzl.app.bean.app.QuestionnaireAnswerCount;
import com.chenzl.app.bean.app.QuestionnaireAnswerQuestion;
import com.chenzl.app.bean.app.QuestionnaireDetailBean;
import com.chenzl.app.bean.app.QuestionnaireQuestionBean;
import com.chenzl.app.bean.backend.forum.QuestionnaireQuestion;
import com.chenzl.app.bean.backend.forum.QuestionnaireQuestionDetail;
import com.chenzl.app.entity.CctQuestionnaire;
import com.chenzl.app.entity.CctQuestionnaireAnswer;
import com.chenzl.app.entity.CctQuestionnaireDetail;
import com.chenzl.app.entity.CctQuestionnaireQuestion;
import com.chenzl.app.entity.CctQuestionnaireStaff;
import com.chenzl.app.exception.BusinessException;
import com.chenzl.app.mappers.backend.fourm.CctQuestionnaireAnswerMapper;
import com.chenzl.app.mappers.backend.fourm.CctQuestionnaireDetailMapper;
import com.chenzl.app.mappers.backend.fourm.CctQuestionnaireMapper;
import com.chenzl.app.mappers.backend.fourm.CctQuestionnaireQuestionMapper;
import com.chenzl.app.mappers.backend.fourm.CctQuestionnaireStaffMapper;
import com.chenzl.app.mappers.common.CommonMapper;
import com.chenzl.app.service.CommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class QuestionnaireService {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireService.class);
	
	private static final BigDecimal B_100 = new BigDecimal("100");
	
	@Autowired
	private CommonMapper commonMapper;

	@Autowired
	private CommonService commonService;
	
	/**
	 * 调查问卷Dao
	 */
	@Autowired
	private CctQuestionnaireMapper cctQuestionnaireMapper;
	
	/**
	 * 调查问卷明细Dao
	 */
	@Autowired
	private CctQuestionnaireDetailMapper cctQuestionnaireDetailMapper;
	
	/**
	 * 调查问卷选项明细Dao
	 */
	@Autowired
	private CctQuestionnaireQuestionMapper cctQuestionnaireQuestionMapper;
	
	/**
	 * 调查问卷参与调查人员
	 */
	@Autowired
	private CctQuestionnaireStaffMapper cctQuestionnaireStaffMapper;
	
	/**
	 * 调查问卷答案明细
	 */
	@Autowired
	private CctQuestionnaireAnswerMapper cctQuestionnaireAnswerMapper;
	
	/**
	 * 查询调查问卷信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<CctQuestionnaire> queryCctQuestionnaire(final Map<String, Object> params) throws Exception {
		List<CctQuestionnaire> pageList = cctQuestionnaireMapper.getCctQuestionnaireForPage(params);
		return pageList;
	}
	
	/**
	 * 查询调查问卷信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<CctQuestionnaire> queryCctQuestionnaireForPage(final Map<String, Object> params) throws Exception {
		Integer page = (Integer) params.get("page"); // 页码
		Integer rows = (Integer) params.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue());
		Page<CctQuestionnaire> pageList = cctQuestionnaireMapper.getCctQuestionnaireForPage(params);
		return pageList;
	}
	
	/**
	 * 增加调查问卷
	 * @param cctQuestionnaire
	 * @return
	 * @throws Exception
	 */
	public int createCctQuestionnaire(final CctQuestionnaire cctQuestionnaire) throws Exception {
		String questionnaireId = commonService.generatePrimaryKeyBySequence("CCT_QUESTIONNAIRE$SEQ");
		Timestamp timestamp = commonMapper.selectSysDate();
		cctQuestionnaire.setQuestionnaireId(Long.valueOf(questionnaireId));
		cctQuestionnaire.setCreateDate(timestamp);
		cctQuestionnaire.setUpdateDate(timestamp);
		return cctQuestionnaireMapper.insertSelective(cctQuestionnaire);
	}
	
	/**
	 * 根据ID查询调查问卷
	 * @param questionnaireId
	 * @return
	 * @throws Exception
	 */
	public CctQuestionnaire queryCctQuestionnaireById(final Long questionnaireId) throws Exception {
		CctQuestionnaire cctQuestionnaire = cctQuestionnaireMapper.getCctQuestionnaireById(questionnaireId);
		return cctQuestionnaire;
	}
	
	/**
	 * 修改调查问卷
	 * @param cctQuestionnaire
	 * @return
	 * @throws Exception
	 */
	public int updateCctQuestionnaire(final CctQuestionnaire cctQuestionnaire) throws Exception {
		Timestamp timestamp = commonMapper.selectSysDate();
		cctQuestionnaire.setUpdateDate(timestamp);
		return cctQuestionnaireMapper.updateByPrimaryKeySelective(cctQuestionnaire);
	}
	
	/**
	 * 删除调查问卷
	 * @param questionnaireId
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	public int deleteCctQuestionnaire(final Long questionnaireId, final String staffId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Timestamp timestamp = commonMapper.selectSysDate();
		params.put("questionnaireId", questionnaireId);
		params.put("state", Short.valueOf("0"));
		params.put("updateStaff", staffId);
		params.put("updateDate", timestamp);
		int i = cctQuestionnaireMapper.updateQuestionnaireStateById(params);
		return i;
	}
	
	/**
	 * 查询调查问卷明细
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<CctQuestionnaireDetail> queryCctQuestionnaireDetailList(final Map<String, Object> params) throws Exception {
		List<CctQuestionnaireDetail> pageList = cctQuestionnaireDetailMapper.getCctQuestionnaireDetailList(params);
		return pageList;
	}
	
	/**
	 * 查询调查问卷明细 分页
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<CctQuestionnaireDetail> queryCctQuestionnaireDetailForPage(final Map<String, Object> params) throws Exception {
		Integer page = (Integer) params.get("page"); // 页码
		Integer rows = (Integer) params.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue());
		Page<CctQuestionnaireDetail> pageList = cctQuestionnaireDetailMapper.getCctQuestionnaireDetailForPage(params);
		return pageList;
	}
	
	/**
	 * 新增调查问卷明细
	 * @param cctQuestionnaireDetail
	 * @return
	 * @throws Exception
	 */
	public Long createCctQuestionnaireDetail(final CctQuestionnaireDetail cctQuestionnaireDetail) throws Exception {
		String questionnaireDetailId = commonService.generatePrimaryKeyBySequence("CCT_QUESTIONNAIRE_DETAIL$SEQ");
		Timestamp timestamp = commonMapper.selectSysDate();
		cctQuestionnaireDetail.setQuestionnaireDetailId(Long.valueOf(questionnaireDetailId));
		cctQuestionnaireDetail.setCreateDate(timestamp);
		cctQuestionnaireDetail.setUpdateDate(timestamp);
		cctQuestionnaireDetailMapper.insertSelective(cctQuestionnaireDetail);
		return cctQuestionnaireDetail.getQuestionnaireDetailId();
	}
	
	/**
	 * 根据ID查询问卷明细信息
	 * @param questionnaireDetailId
	 * @return
	 * @throws Exception
	 */
	public CctQuestionnaireDetail queryCctQuestionnaireDetailById(final Long questionnaireDetailId) throws Exception {
		CctQuestionnaireDetail cctQuestionnaireDetail = cctQuestionnaireDetailMapper.getCctQuestionnaireDetailById(questionnaireDetailId);
		return cctQuestionnaireDetail;
	}
	
	/**
	 * 更新查询问卷明细信息
	 * @param cctQuestionnaireDetail
	 * @return
	 * @throws Exception
	 */
	public int updateCctQuestionnaireDetail(final CctQuestionnaireDetail cctQuestionnaireDetail) throws Exception {
		Timestamp timestamp = commonMapper.selectSysDate();
		cctQuestionnaireDetail.setUpdateDate(timestamp);
		return cctQuestionnaireDetailMapper.updateByPrimaryKeySelective(cctQuestionnaireDetail);
	}
	
	/**
	 * 删除调查问卷明细信息
	 * @param questionnaireDetailId
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	public int deleteCctQuestionnaireDetail(final Long questionnaireDetailId, final String staffId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Timestamp timestamp = commonMapper.selectSysDate();
		params.put("questionnaireDetailId", questionnaireDetailId);
		params.put("state", Short.valueOf("0"));
		params.put("updateStaff", staffId);
		params.put("updateDate", timestamp);
		int i = cctQuestionnaireDetailMapper.updateQuestionnaireDetailStateById(params);
		return i;
	}
	
	/**
	 * 查询调查问卷明细选项
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<CctQuestionnaireQuestion> queryCctQuestionnaireQuestionList(final Map<String, Object> params) throws Exception {
		List<CctQuestionnaireQuestion> pageList = cctQuestionnaireQuestionMapper.getQuestionnaireQuestionForPage(params);
		return pageList;
	}
	
	/**
	 * 查询调查问卷明细选项
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<CctQuestionnaireQuestion> queryCctQuestionnaireQuestionForPage(final Map<String, Object> params) throws Exception {
		Integer page = (Integer) params.get("page"); // 页码
		Integer rows = (Integer) params.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue());
		Page<CctQuestionnaireQuestion> pageList = cctQuestionnaireQuestionMapper.getQuestionnaireQuestionForPage(params);
		return pageList;
	}
	
	/**
	 * 新增调查问卷明细选项
	 * @param cctQuestionnaireQuestion
	 * @return
	 * @throws Exception
	 */
	public int createCctQuestionnaireQuestion(final CctQuestionnaireQuestion cctQuestionnaireQuestion) throws Exception {
		String questionnaireQuestionId = commonService.generatePrimaryKeyBySequence("CCT_QUESTIONNAIRE_QUESTION$SEQ");
		Timestamp timestamp = commonMapper.selectSysDate();
		cctQuestionnaireQuestion.setQuestionnaireQuestionId(Long.valueOf(questionnaireQuestionId));
		cctQuestionnaireQuestion.setCreateDate(timestamp);
		cctQuestionnaireQuestion.setUpdateDate(timestamp);
		return cctQuestionnaireQuestionMapper.insertSelective(cctQuestionnaireQuestion);
	}
	
	/**
	 * 根据ID查询调查问卷明细选项
	 * @param questionnaireQuestionId
	 * @return
	 * @throws Exception
	 */
	public CctQuestionnaireQuestion queryCctQuestionnaireQuestionById(final Long questionnaireQuestionId) throws Exception {
		CctQuestionnaireQuestion cctQuestionnaireQuestion = cctQuestionnaireQuestionMapper.getQuestionnaireQuestionById(questionnaireQuestionId);
		return cctQuestionnaireQuestion;
	}
	
	/**
	 * 更新调查问卷明细选项
	 * @param cctQuestionnaireQuestion
	 * @return
	 * @throws Exception
	 */
	public int updateCctQuestionnaireQuestion(final CctQuestionnaireQuestion cctQuestionnaireQuestion) throws Exception {
		Timestamp timestamp = commonMapper.selectSysDate();
		cctQuestionnaireQuestion.setUpdateDate(timestamp);
		return cctQuestionnaireQuestionMapper.updateByPrimaryKeySelective(cctQuestionnaireQuestion);
	}
	
	/**
	 * 删除调查问卷明细选项
	 * @param questionnaireQuestionId
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	public int deleteCctQuestionnaireQuestion(final Long questionnaireQuestionId, final String staffId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Timestamp timestamp = commonMapper.selectSysDate();
		params.put("questionnaireQuestionId", questionnaireQuestionId);
		params.put("state", Short.valueOf("0"));
		params.put("updateStaff", staffId);
		params.put("updateDate", timestamp);
		int i = cctQuestionnaireQuestionMapper.updateQuestionnaireQuestionStateById(params);
		return i;
	}
	
	/**
	 * 问卷调查选项明细
	 * @param inserted
	 * @param updated
	 * @param deleted
	 * @param staffId
	 * @throws Exception
	 */
	public void operateQuestionnaireQuestions(final String inserted, final String updated, final String deleted, final String staffId) throws Exception {
		if (!StringUtils.isBlank(inserted)) {
			insertQuestionnaireQuestions(JSONArray.fromObject(inserted), staffId);
		}
		if (!StringUtils.isBlank(updated)) {
			updateQuestionnaireQuestions(JSONArray.fromObject(updated), staffId);
		}
		if (!StringUtils.isBlank(deleted)) {
			deleteQuestionnaireQuestions(JSONArray.fromObject(deleted), staffId);
		}
	}
	
	/**
	 * 新增调查问卷选项明细
	 * @param insertedArray
	 */
	public void insertQuestionnaireQuestions(JSONArray insertedArray, final String staffId) throws Exception {
		CctQuestionnaireQuestion cctQuestionnaireQuestion = null; 
		JSONObject obj = null;
		String questionScore = null;
		for(int i = 0; i < insertedArray.size(); i++) {
			obj = insertedArray.getJSONObject(i);
			try {
				questionScore = obj.getString("questionScore");
				if (StringUtils.isBlank(questionScore)){
					questionScore = "0";
				}
			} catch (JSONException ignore) {
				questionScore = "0";
			}
			cctQuestionnaireQuestion = new CctQuestionnaireQuestion();
			cctQuestionnaireQuestion.setQuestionnaireDetailId(Long.valueOf(obj.getString("questionnaireDetailId")));
			cctQuestionnaireQuestion.setQuestionCode(obj.getString("questionCode"));
			cctQuestionnaireQuestion.setQuestionContent(obj.getString("questionContent"));
			cctQuestionnaireQuestion.setQuestionScore(Long.valueOf(questionScore));
			cctQuestionnaireQuestion.setState(Short.valueOf("1"));
			cctQuestionnaireQuestion.setCreateStaff(staffId);
			cctQuestionnaireQuestion.setUpdateStaff(staffId);
			createCctQuestionnaireQuestion(cctQuestionnaireQuestion);
		}
	}
	
	/**
	 * 更新调查问卷选项明细
	 * @param updatedArray
	 */
	public void updateQuestionnaireQuestions(JSONArray updatedArray, final String staffId) throws Exception {
		CctQuestionnaireQuestion cctQuestionnaireQuestion = null;
		JSONObject obj = null;
		String questionScore = null;
		for(int i = 0; i < updatedArray.size(); i++) {
			obj = updatedArray.getJSONObject(i);
			try {
				questionScore = obj.getString("questionScore");
				if (StringUtils.isBlank(questionScore)){
					questionScore = "0";
				}
			} catch (JSONException ignore) {
				questionScore = "0";
			}
			cctQuestionnaireQuestion = new CctQuestionnaireQuestion();
			cctQuestionnaireQuestion.setQuestionnaireQuestionId(Long.valueOf(obj.getString("questionnaireQuestionId")));
			cctQuestionnaireQuestion.setQuestionnaireDetailId(Long.valueOf(obj.getString("questionnaireDetailId")));
			cctQuestionnaireQuestion.setQuestionCode(obj.getString("questionCode"));
			cctQuestionnaireQuestion.setQuestionContent(obj.getString("questionContent"));
			cctQuestionnaireQuestion.setQuestionScore(Long.valueOf(questionScore));
			cctQuestionnaireQuestion.setUpdateStaff(staffId);
			updateCctQuestionnaireQuestion(cctQuestionnaireQuestion);
		}
	}
	
	/**
	 * 删除调查问卷选项明细
	 * @param deletedArray
	 */
	public void deleteQuestionnaireQuestions(JSONArray deletedArray, final String staffId) throws Exception {
		JSONObject obj = null;
		for(int i = 0; i < deletedArray.size(); i++) {
			obj = deletedArray.getJSONObject(i);
			deleteCctQuestionnaireQuestion(Long.valueOf(obj.getString("questionnaireQuestionId")), staffId);
		}
	}
	
	/**
	 * 查询调查问卷人员信息 分页
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<CctQuestionnaireStaff> queryCctQuestionnaireStaffForPage(final Map<String, Object> params) throws Exception {
		Integer page = (Integer) params.get("page"); // 页码
		Integer rows = (Integer) params.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue());
		Page<CctQuestionnaireStaff> pageList = cctQuestionnaireStaffMapper.queryCctQuestionnaireStaffForPage(params);
		return pageList;
	}
	
	/**
	 * 添加调查问卷人员 批量
	 * @param questionnaireId
	 * @param staffList
	 * @param createStaff
	 * @throws Exception
	 */
	public void createQuestionnaireStaffList(final Long questionnaireId, final List<String> staffIdList, final String createStaff) throws Exception {
		for (String staffId : staffIdList) {
			if (!StringUtils.isBlank(staffId)) {
				createQuestionnaireStaff(questionnaireId, staffId, createStaff);
			}
		}
	}
	
	/**
	 * 添加调查问卷人员
	 * @param questionnaireId
	 * @param staffId
	 * @param createStaff
	 * @return
	 * @throws Exception
	 */
	public int createQuestionnaireStaff(final Long questionnaireId, final String staffId, final String createStaff) throws Exception {
		// 添加岗位认证人员信息
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionnaireId", questionnaireId);
		params.put("staffId", staffId);
		CctQuestionnaireStaff questionnaireStaff = cctQuestionnaireStaffMapper.getCctQuestionnaireStaffByMap(params);
		if (questionnaireStaff != null) {
			return 0;
		}
		
		questionnaireStaff = new CctQuestionnaireStaff();
		String questionnaireStaffId = commonService.generatePrimaryKeyBySequence("CCT_QUESTIONNAIRE_STAFF$SEQ");
		questionnaireStaff.setQuestionnaireStaffId(Long.valueOf(questionnaireStaffId));
		questionnaireStaff.setQuestionnaireId(questionnaireId);
		questionnaireStaff.setStaffId(staffId);
		questionnaireStaff.setFlag(Short.valueOf("0"));
		questionnaireStaff.setCreateStaff(createStaff);
		Timestamp createDate = commonMapper.selectSysDate();
		questionnaireStaff.setCreateDate(createDate);
		int i = cctQuestionnaireStaffMapper.insertSelective(questionnaireStaff);
	
		return i;
	}
	
	/**
	 * 删除调查问卷人员
	 * @param questionnaireStaffId
	 * @return
	 * @throws Exception
	 */
	public int deleteQuestionnaireStaff(final Long questionnaireStaffId) throws Exception {
		CctQuestionnaireStaff cctQuestionnaireStaff = cctQuestionnaireStaffMapper.getCctQuestionnaireById(questionnaireStaffId);
		int i = 0;
		if (cctQuestionnaireStaff != null 
				&& cctQuestionnaireStaff.getFlag() != null
				&& cctQuestionnaireStaff.getFlag().intValue() == 1) {
			i = 0;
			throw new BusinessException("员工已完成调查，不能删除！");
		} else {
			i = cctQuestionnaireStaffMapper.deleteByPrimaryKey(questionnaireStaffId);
		}
		return i;
	}
	
	/**
	 * 删除调查问卷人员 批量
	 * @param questionnaireStaffIdList
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteQuestionnaireStaffList(final List<String> questionnaireStaffIdList) throws Exception {
		try {
			for (String questionnaireStaffId : questionnaireStaffIdList) {
				deleteQuestionnaireStaff(Long.valueOf(questionnaireStaffId));
			}
		} catch (BusinessException be) {
			throw be;
		}
		return Boolean.TRUE;
	}
	
	/**
	 * 查询调查问卷信息 App端使用
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<CctQuestionnaire> getAppCctQuestionnaireForPage(final Map<String, Object> params) throws Exception {
		Integer pageNum = (Integer) params.get("pageNum"); // 页码
		Integer pageSize = (Integer) params.get("pageSize"); // 每页显示行
		PageHelper.startPage(pageNum, pageSize);
		Page<CctQuestionnaire> pageList = cctQuestionnaireMapper.getAppCctQuestionnaireForPage(params);
		return pageList;
	}
	
	/**
	 * 查询我参与的问卷调查信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Page<CctQuestionnaire> getAppMyCctQuestionnaireForPage(final Map<String, Object> params) throws Exception {
		Integer pageNum = (Integer) params.get("pageNum"); // 页码
		Integer pageSize = (Integer) params.get("pageSize"); // 每页显示行
		PageHelper.startPage(pageNum, pageSize);
		Page<CctQuestionnaire> pageList = cctQuestionnaireMapper.getMyQuestionnaireForPage(params);
		return pageList;
	}
	
	/**
	 * 调查问卷试题信息 App端使用
	 * @param questionnaireId
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	public List<QuestionnaireDetailBean> getAppCctQuestionnaireDetailList(final Long questionnaireId, final String staffId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionnaireId", questionnaireId);
		params.put("staffId", staffId);
		List<QuestionnaireDetailBean> cctQuestionnaireDetailList = cctQuestionnaireDetailMapper.getAppCctQuestionnaireDetail(params);
		setQuestionnaireQuestionList(cctQuestionnaireDetailList);
		return cctQuestionnaireDetailList;
	}
	
	/**
	 * 设置调查问卷明细
	 * @param cctQuestionnaireDetailList
	 * @return
	 * @throws Exception
	 */
	private List<QuestionnaireDetailBean> setQuestionnaireQuestionList(final List<QuestionnaireDetailBean> cctQuestionnaireDetailList) throws Exception {
		QuestionnaireDetailBean questionnaireDetailBean = null;
		List<QuestionnaireQuestionBean> questionnaireQuestionBeanList = null;
		for (int i = 0; i < cctQuestionnaireDetailList.size(); i++) {
			questionnaireDetailBean = cctQuestionnaireDetailList.get(i);
			questionnaireDetailBean.setQuestionnaireDetailNo(String.valueOf(i + 1));
			questionnaireQuestionBeanList = queryQuestionnaireQuestions(questionnaireDetailBean.getQuestionnaireDetailId());
			questionnaireDetailBean.setQuestionList(questionnaireQuestionBeanList);
		}
		return cctQuestionnaireDetailList;
	}
	
	/**
	 * 根据问卷明细查询问卷明细选项
	 * @param questionnaireDetailId
	 * @return
	 * @throws Exception
	 */
	private List<QuestionnaireQuestionBean> queryQuestionnaireQuestions(final Long questionnaireDetailId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionnaireDetailId", questionnaireDetailId);
		List<QuestionnaireQuestionBean> questionnaireQuestionBeanList = cctQuestionnaireQuestionMapper.getAppCctQuestionnaireQuestion(params);
		return questionnaireQuestionBeanList;
	}
	
	/**
	 * 更新员工调查问卷状态
	 * @param questionnaireId
	 * @param staffId
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public int updateQuestionnaireStaffFlag(final Long questionnaireId, final String staffId, final Short flag) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionnaireId", questionnaireId);
		params.put("staffId", staffId);
		params.put("flag", flag);
		int i = cctQuestionnaireStaffMapper.updateQuestionnaireStaffFlag(params);
		return i;
	}
	
	/**
	 * 保存调查问卷答案
	 * @param questionnaireId
	 * @param staffId
	 * @param questionnaireQuestionListJson
	 */
	public void saveQuestionnaireAnswers(final Long questionnaireId, final String staffId, final String questionnaireQuestionListJson) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionnaireId", questionnaireId);
		params.put("staffId", staffId);
		CctQuestionnaireStaff cctQuestionnaireStaff = cctQuestionnaireStaffMapper.getCctQuestionnaireStaffByMap(params);
		List<QuestionnaireQuestion> questionList = new ArrayList<QuestionnaireQuestion>();
		JSONObject json = JSONObject.fromObject(questionnaireQuestionListJson);
		String key = null;
		JSONArray questions = null;
		for (Iterator it = json.keys(); it.hasNext();) {
			key = (String) it.next();
			questions = json.getJSONArray(key);
			questionList.addAll(createQuestionList(questions));
		}
		saveCctQuestionnaireAnswerList(cctQuestionnaireStaff.getQuestionnaireStaffId(), questionnaireId, staffId, questionList);
		updateQuestionnaireStaffFlag(questionnaireId, staffId, Short.valueOf("1"));
	}
	
	private List<QuestionnaireQuestion> createQuestionList(final JSONArray questions) throws Exception {
		final List<QuestionnaireQuestion> questionList = new ArrayList<QuestionnaireQuestion>();
		JSONObject object = null;
		QuestionnaireQuestion questionnaireQuestion = null;
		CctQuestionnaireQuestion cctQuestionnaireQuestion = null;
		String questionnaireQuestionId = null;
		String questionnaireDetailId = null;
		String questionDetailType = null;
		String questionContent = null;
		for (Iterator it = questions.listIterator(); it.hasNext();) {
			object = (JSONObject) it.next();
			questionDetailType = object.getString("questionDetailType");
			if (!"4".equals(questionDetailType)) {
				questionnaireQuestionId = object.getString("questionnaireQuestionId");
			}
			questionnaireDetailId = object.getString("questionnaireDetailId");
			questionContent = object.getString("questionContent");
			questionnaireQuestion = new QuestionnaireQuestion(questionnaireQuestionId, questionnaireDetailId, questionDetailType, questionContent);
			if (!"4".equals(questionDetailType)) {
				cctQuestionnaireQuestion = cctQuestionnaireQuestionMapper.getQuestionnaireQuestionById(Long.valueOf(questionnaireQuestionId));
				questionnaireQuestion.setQuestionCode(cctQuestionnaireQuestion.getQuestionCode());
				questionnaireQuestion.setQuestionContent(cctQuestionnaireQuestion.getQuestionContent());
				questionnaireQuestion.setQuestionScore(cctQuestionnaireQuestion.getQuestionScore());
			}
			questionList.add(questionnaireQuestion);
		}
		return questionList;
	}
	
	public void createQuestionnaireQuestionDetails (List<QuestionnaireQuestionDetail> questionnaireDetailList, Long questionnaireId, String staffId) throws Exception {
		for (QuestionnaireQuestionDetail questionnaireQuestionDetail : questionnaireDetailList) {
			createQuestionnaireDetail(questionnaireQuestionDetail, questionnaireId, staffId);
		}
	}
	
	private void createQuestionnaireDetail(QuestionnaireQuestionDetail questionnaireQuestionDetail, Long questionnaireId, String staffId) throws Exception {
		CctQuestionnaireDetail cctQuestionnaireDetail = questionnaireQuestionDetail.getCctQuestionnaireDetail();
		cctQuestionnaireDetail.setQuestionnaireId(questionnaireId);
		cctQuestionnaireDetail.setCreateStaff(staffId);
		cctQuestionnaireDetail.setUpdateStaff(staffId);
		Long questionnaireDetailId = createCctQuestionnaireDetail(cctQuestionnaireDetail);
		List<CctQuestionnaireQuestion> cctQuestionnaireQuestionList = questionnaireQuestionDetail.getCctQuestionnaireQuestionList();
		for (CctQuestionnaireQuestion cctQuestionnaireQuestion : cctQuestionnaireQuestionList) {
			createQuestionnaireQuestion(cctQuestionnaireQuestion, questionnaireDetailId, staffId);
		}
	}
	
	private void createQuestionnaireQuestion(CctQuestionnaireQuestion cctQuestionnaireQuestion, Long questionnaireDetailId, String staffId) throws Exception {
		cctQuestionnaireQuestion.setQuestionnaireDetailId(questionnaireDetailId);
		cctQuestionnaireQuestion.setCreateStaff(staffId);
		cctQuestionnaireQuestion.setUpdateStaff(staffId);
		createCctQuestionnaireQuestion(cctQuestionnaireQuestion);
	}
	
	/**
	 * 保存员工调查问卷答案 列表
	 * @param questionnaireStaffId
	 * @param questionnaireId
	 * @param staffId
	 * @param questionList
	 * @throws Exception
	 */
	public void saveCctQuestionnaireAnswerList(
			final Long questionnaireStaffId,
			final Long questionnaireId,
			final String staffId,
			final List<QuestionnaireQuestion> questionList) throws Exception {
		for (QuestionnaireQuestion question : questionList) {
			if ("5".equals(question.getQuestionDetailType())) { // 评分
				saveCctQuestionnaireAnswer(
						questionnaireStaffId,
						questionnaireId,
						Long.valueOf(question.getQuestionnaireDetailId()),
						Long.valueOf(question.getQuestionnaireQuestionId()),
						question.getQuestionCode(),
						question.getQuestionContent(),
						question.getQuestionScore(),
						staffId);
				continue;
			} else if ("4".equals(question.getQuestionDetailType())) { // 问答
				saveCctQuestionnaireAnswer(
						questionnaireStaffId,
						questionnaireId,
						Long.valueOf(question.getQuestionnaireDetailId()),
						null,
						question.getQuestionCode(),
						question.getQuestionContent(),
						Long.valueOf("0"),
						staffId);
				continue;
			} else { // 单选、多选、判断
				saveCctQuestionnaireAnswer(
						questionnaireStaffId,
						questionnaireId,
						Long.valueOf(question.getQuestionnaireDetailId()),
						Long.valueOf(question.getQuestionnaireQuestionId()),
						question.getQuestionCode(),
						question.getQuestionContent(),
						Long.valueOf("0"),
						staffId);
				continue;
			}
		}
	}
	
	/**
	 * 保存员工调查问卷答案
	 * @param questionnaireStaffId
	 * @param questionnaireId
	 * @param questionnaireDetailId
	 * @param questionnaireQuestionId
	 * @param questionContent
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	public int saveCctQuestionnaireAnswer(
			final Long questionnaireStaffId,
			final Long questionnaireId,
			final Long questionnaireDetailId,
			final Long questionnaireQuestionId,
			final String questionCode,
			final String questionContent,
			final Long questionnaireSccore,
			final String staffId) throws Exception {
		int i = createCctQuestionnaireAnswer(questionnaireStaffId, questionnaireId, questionnaireDetailId, questionnaireQuestionId, questionCode, questionContent, questionnaireSccore, staffId);
		return i;
	}
	
	/**
	 * 新增员工调查问卷答案
	 * @param questionnaireStaffId
	 * @param questionnaireId
	 * @param questionnaireDetailId
	 * @param questionId
	 * @param questionContent
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	public int createCctQuestionnaireAnswer(
			final Long questionnaireStaffId,
			final Long questionnaireId,
			final Long questionnaireDetailId,
			final Long questionnaireQuestionId,
			final String questionCode,
			final String questionContent,
			final Long questionScore,
			final String staffId) throws Exception {
		CctQuestionnaireAnswer cctQuestionnaireAnswer = new CctQuestionnaireAnswer();
		String questionnaireAnswerId = commonService.generatePrimaryKeyBySequence("CCT_QUESTIONNAIRE_ANSWER$SEQ");
		cctQuestionnaireAnswer.setQuestionnaireAnswerId(Long.valueOf(questionnaireAnswerId));
		cctQuestionnaireAnswer.setQuestionnaireStaffId(questionnaireStaffId);
		cctQuestionnaireAnswer.setQuestionnaireId(questionnaireId);
		cctQuestionnaireAnswer.setQuestionnaireDetailId(questionnaireDetailId);
		cctQuestionnaireAnswer.setQuestionnaireQuestionId(questionnaireQuestionId);
		cctQuestionnaireAnswer.setQuestionnaireCode(questionCode);
		cctQuestionnaireAnswer.setQuestionnaireAnswer(questionContent);
		cctQuestionnaireAnswer.setQuestionnaireScore(questionScore);
		cctQuestionnaireAnswer.setCreateStaff(staffId);
		Timestamp timestamp = commonMapper.selectSysDate();
		cctQuestionnaireAnswer.setCreateDate(timestamp);
		int i = cctQuestionnaireAnswerMapper.insertSelective(cctQuestionnaireAnswer);
		return i; 
	}
	
	/**
	 * 更新员工调查问卷答案
	 * @param questionnaireStaffId
	 * @param questionnaireId
	 * @param questionnaireDetailId
	 * @param questionId
	 * @param questionContent
	 * @param staffId
	 * @return
	 * @throws Exception
	 */
	public int updateCctQuestionnaireAnswer(
			final Long questionnaireStaffId,
			final Long questionnaireId,
			final Long questionnaireDetailId,
			final Long questionnaireQuestionId,
			final String questionCode,
			final String questionContent,
			final Long questionScore,
			final String staffId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionnaireStaffId", questionnaireStaffId);
		params.put("questionnaireId", questionnaireId);
		params.put("questionnaireDetailId", questionnaireDetailId);
		CctQuestionnaireAnswer cctQuestionnaireAnswer = queryCctQuestionnaireAnswerByMap(params);
		int i = 0;
		if (cctQuestionnaireAnswer != null) {
			cctQuestionnaireAnswer.setQuestionnaireQuestionId(questionnaireQuestionId);
			cctQuestionnaireAnswer.setQuestionnaireCode(questionCode);
			cctQuestionnaireAnswer.setQuestionnaireAnswer(questionContent);
			cctQuestionnaireAnswer.setQuestionnaireScore(questionScore);
			i = cctQuestionnaireAnswerMapper.updateByPrimaryKeySelective(cctQuestionnaireAnswer);
		}
		return i;
	}
	
	/**
	 * 根据Map条件查询 调查问卷答案明细
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public CctQuestionnaireAnswer queryCctQuestionnaireAnswerByMap(final Map<String, Object> params) throws Exception {
		return cctQuestionnaireAnswerMapper.getCctQuestionnaireAnswerByMap(params);
	}
	
	/**
	 * 统计投票总人数
	 * @param questionnaireDetailId 投票选项ID
	 * @return
	 * @throws Exception
	 */
	public Long queryQuestionnaireAnswerCountByQuestionnaireDetailId(final Long questionnaireDetailId) throws Exception {
		return cctQuestionnaireAnswerMapper.getQuestionnaireAnswerCountByQuestionnaireDetailId(questionnaireDetailId);
	}
	
	/**
	 * 统计未投票人数
	 * @param questionnaireDetailId
	 * @return
	 * @throws Exception
	 */
	public Long queryQuestionnaireAnswerNoVote(final Long questionnaireDetailId) throws Exception {
		return cctQuestionnaireAnswerMapper.queryQuestionnaireAnswerNoVote(questionnaireDetailId);
	}
	
	/**
	 * 统计投票明细
	 * @param questionnaireDetailId 投票选项ID
	 * @return
	 * @throws Exception
	 */
	public List<QuestionnaireAnswerCount> getQuestionnaireAnswerCountListByQuestionnaireDetailId(final Long questionnaireDetailId) throws Exception {
		return cctQuestionnaireAnswerMapper.getQuestionnaireAnswerCountListByQuestionnaireDetailId(questionnaireDetailId);
	}
	
	/**
	 * 问答回答明细
	 * @param questionnaireDetailId
	 * @return
	 * @throws Exception
	 */
	public List<QuestionnaireAnswerQuestion> getQuestionnaireAnswerQuestionListByQuestionnaireDetailId(final Long questionnaireDetailId) throws Exception {
		return cctQuestionnaireAnswerMapper.getQuestionnaireAnswerQuestionListByQuestionnaireDetailId(questionnaireDetailId);
	}
	
	/**
	 * 
	 * @param questionnaireId
	 * @return
	 * @throws Exception
	 */
	public List<QuestionnaireDetailBean> queryCctQuestionnaireDetailListForExport(final Map<String, Object> params) throws Exception {
		return cctQuestionnaireDetailMapper.getCctQuestionnaireDetailListForExport(params);
	}
	
	/**
	 * 获取调查投票导出数据表
	 * @param questionnaireId
	 * @return
	 * @throws Exception
	 */
	public Workbook getCctQuestionnaireResult(final Long questionnaireId) throws Exception {
		CctQuestionnaire cctQuestionnaire = cctQuestionnaireMapper.getCctQuestionnaireById(questionnaireId);
		Short questionnaireType = cctQuestionnaire.getQuestionnaireType();
		
		Workbook exportWorkBook = new HSSFWorkbook();
		
		createAnswerCountSheet(cctQuestionnaire.getQuestionnaireId(), cctQuestionnaire.getQuestionnaireName(), exportWorkBook);
		
		if (questionnaireType.intValue() == 1) {
			// 问答题Sheet
			createAnswerQuestionSheet(cctQuestionnaire.getQuestionnaireId(), cctQuestionnaire.getQuestionnaireName(), exportWorkBook);
			
			// 评分题Sheet
			createAnswerVoteSheet(cctQuestionnaire.getQuestionnaireId(), cctQuestionnaire.getQuestionnaireName(), exportWorkBook);
		}
		
		return exportWorkBook;
	}
	
	/**
	 * 创建选项问题Sheet
	 * @param questionnaireId
	 * @param questionnaireName
	 * @param workBook
	 * @throws Exception
	 */
	private void createAnswerCountSheet(final Long questionnaireId, final String questionnaireName, Workbook workBook) throws Exception {
		Sheet exportSheet = workBook.createSheet("选择");
		
		Row titleRow = null;
		titleRow = exportSheet.createRow(0);
		titleRow.createCell(0).setCellValue("序号");
		titleRow.createCell(1).setCellValue("问题描述");
		titleRow.createCell(2).setCellValue("问题选项");
		titleRow.createCell(3).setCellValue("选项内容");
		titleRow.createCell(4).setCellValue("选择数量");
		titleRow.createCell(5).setCellValue("选择比例");
		
		CellStyle cellStyle = workBook.createCellStyle();   
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中  
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直 
		
		int i = 1;
		Row contentRow = null;
		CellRangeAddress cellNumRangeAddress = null;
		CellRangeAddress cellRangeAddress = null;
		Cell currentNumCell = null;
		Cell currentCell = null;
		int firstRow = 0;
		int lastRow = 0;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionnaireId", questionnaireId); // 问卷调查统计
		params.put("notQuestionDetailType", "true"); // 不包括问答题
		List<QuestionnaireDetailBean> questionnaireDetailBeanList = queryCctQuestionnaireDetailListForExport(params);
		Long answerCountSize = new Long("0");
		List<QuestionnaireAnswerCount> questionnaireAnswerCountList = null;
		QuestionnaireAnswerCount questionnaireAnswerCount = null;
		int num = 1;
		for (QuestionnaireDetailBean questionnaireDetailBean : questionnaireDetailBeanList) {
			answerCountSize = queryQuestionnaireAnswerCountByQuestionnaireDetailId(questionnaireDetailBean.getQuestionnaireDetailId());
			questionnaireAnswerCountList = getQuestionnaireAnswerCountListByQuestionnaireDetailId(questionnaireDetailBean.getQuestionnaireDetailId());
			for (int j = 0; j < questionnaireAnswerCountList.size(); j++) {
				if (j == 0) {
					firstRow = i;
				}
				if (j == (questionnaireAnswerCountList.size() - 1)) {
					lastRow  = i;
				}
				questionnaireAnswerCount = questionnaireAnswerCountList.get(j);
				contentRow = exportSheet.createRow(i);
				contentRow.createCell(0).setCellValue(i);
				contentRow.createCell(1).setCellValue(questionnaireDetailBean.getQuestionDetailContent());
				contentRow.createCell(2).setCellValue(questionnaireAnswerCount.getQuestionCode());
				contentRow.createCell(3).setCellValue(questionnaireAnswerCount.getQuestionContent());
				contentRow.createCell(4).setCellValue(questionnaireAnswerCount.getAnswerCount());
				contentRow.createCell(5).setCellValue(getCountPercentage(questionnaireAnswerCount.getAnswerCount(), answerCountSize));
				i++;
			}
			cellNumRangeAddress = new CellRangeAddress(firstRow, lastRow, 0, 0);
			cellRangeAddress = new CellRangeAddress(firstRow, lastRow, 1, 1);
			if ((lastRow - firstRow != 0)) {
				
				exportSheet.addMergedRegion(cellNumRangeAddress);
				currentNumCell = exportSheet.getRow(firstRow).getCell(0);
				currentNumCell.setCellStyle(cellStyle);
				currentNumCell.setCellValue(num++);
				
				exportSheet.addMergedRegion(cellRangeAddress);
				currentCell = exportSheet.getRow(firstRow).getCell(1);
				currentCell.setCellStyle(cellStyle);
				
			}
		}
	}
	
	/**
	 * 获取选项百分比
	 * @param answerCount
	 * @param answerCountSize
	 * @return
	 * @throws Exception
	 */
	private String getCountPercentage(Long answerCount, Long answerCountSize) throws Exception {
		if (answerCount == 0) {
			return "0.0%";
		}
		if (answerCountSize == 0) {
			return "0.0%";
		}
		BigDecimal b_answerCount = new BigDecimal(answerCount);
		BigDecimal b_answerCountSize = new BigDecimal(answerCountSize);
		BigDecimal b_percentage = B_100.multiply(b_answerCount.divide(b_answerCountSize, 4, RoundingMode.FLOOR));
		DecimalFormat df = new DecimalFormat("#.##");
		String s_percentage = df.format(b_percentage);
		return s_percentage + "%";
	}
	
	/**
	 * 创建问答问题Sheet
	 * @param questionnaireId
	 * @param questionnaireName
	 * @param workBook
	 * @throws Exception
	 */
	private void createAnswerQuestionSheet(final Long questionnaireId, final String questionnaireName, Workbook workBook) throws Exception {
		Sheet exportSheet = workBook.createSheet("问答");
		
		Row titleRow = null;
		titleRow = exportSheet.createRow(0);
		titleRow.createCell(0).setCellValue("序号");
		titleRow.createCell(1).setCellValue("问题描述");
		titleRow.createCell(2).setCellValue("问题回答");
		titleRow.createCell(3).setCellValue("员工工号");
		titleRow.createCell(4).setCellValue("员工姓名");
		titleRow.createCell(5).setCellValue("联系电话");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionnaireId", questionnaireId); // 问卷调查统计
		params.put("questionDetailType", Integer.valueOf("4")); // 只限问答题
		List<QuestionnaireDetailBean> questionnaireDetailBeanList = queryCctQuestionnaireDetailListForExport(params);
		List<QuestionnaireAnswerQuestion> questionnaireAnswerQuestionList = null;
		
		int i = 1;
		Row contentRow = null;
		for (QuestionnaireDetailBean questionnaireDetailBean : questionnaireDetailBeanList) {
			questionnaireAnswerQuestionList = getQuestionnaireAnswerQuestionListByQuestionnaireDetailId(questionnaireDetailBean.getQuestionnaireDetailId());
			for (QuestionnaireAnswerQuestion questionnaireAnswerQuestion : questionnaireAnswerQuestionList) {
				contentRow = exportSheet.createRow(i);
				contentRow.createCell(0).setCellValue(i);
				contentRow.createCell(1).setCellValue(questionnaireAnswerQuestion.getQuestionDetailContent());
				contentRow.createCell(2).setCellValue(questionnaireAnswerQuestion.getQuestionnaireAnswer());
				contentRow.createCell(3).setCellValue(questionnaireAnswerQuestion.getStaffId());
				contentRow.createCell(4).setCellValue(questionnaireAnswerQuestion.getStaffName());
				contentRow.createCell(5).setCellValue(questionnaireAnswerQuestion.getStaffTel());
				i++;
			}
		}
	}
	
	/**
	 * 创建投票问题Sheet
	 * @param questionnaireId
	 * @param questionnaireName
	 * @param workBook
	 * @throws Exception
	 */
	private void createAnswerVoteSheet(final Long questionnaireId, final String questionnaireName, Workbook workBook) throws Exception {
		Sheet exportSheet = workBook.createSheet("评分");
		
		Row titleRow = null;
		titleRow = exportSheet.createRow(0);
		titleRow.createCell(0).setCellValue("序号");
		titleRow.createCell(1).setCellValue("问题描述");
		titleRow.createCell(2).setCellValue("问题选项");
		titleRow.createCell(3).setCellValue("选项内容");
		titleRow.createCell(4).setCellValue("选项分数");
		titleRow.createCell(5).setCellValue("选项数量");
		titleRow.createCell(6).setCellValue("选项总数");
		titleRow.createCell(7).setCellValue("选项评分（平均分）");
		
		CellStyle cellStyle = workBook.createCellStyle();   
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中  
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直 
		
		int i = 1;
		Row contentRow = null;
		CellRangeAddress cellNumRangeAddress = null;
		CellRangeAddress cellRangeAddress = null;
		Cell currentNumCell = null;
		Cell currentCell = null;
		int firstRow = 0;
		int lastRow = 0;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("questionnaireId", questionnaireId); // 问卷调查统计
		params.put("questionDetailType", Integer.valueOf("5")); // 只限投票题
		List<QuestionnaireDetailBean> questionnaireDetailBeanList = queryCctQuestionnaireDetailListForExport(params);
		List<QuestionnaireAnswerCount> questionnaireAnswerCountList = null;
		QuestionnaireAnswerCount questionnaireAnswerCount = null;
		int answerTotalScore = 0;
		int answerTotalVote = 0;
		int num = 1;
		for (QuestionnaireDetailBean questionnaireDetailBean : questionnaireDetailBeanList) {
			questionnaireAnswerCountList = getQuestionnaireAnswerCountListByQuestionnaireDetailId(questionnaireDetailBean.getQuestionnaireDetailId());
			answerTotalScore = 0;
			answerTotalVote = 0;
			for (int j = 0; j < questionnaireAnswerCountList.size(); j++) {
				if (j == 0) {
					firstRow = i;
				}
				if (j == (questionnaireAnswerCountList.size() - 1)) {
					lastRow  = i;
				}
				questionnaireAnswerCount = questionnaireAnswerCountList.get(j);
				contentRow = exportSheet.createRow(i);
				contentRow.createCell(0).setCellValue(i);
				contentRow.createCell(1).setCellValue(questionnaireDetailBean.getQuestionDetailContent());
				contentRow.createCell(2).setCellValue(questionnaireAnswerCount.getQuestionCode());
				contentRow.createCell(3).setCellValue(questionnaireAnswerCount.getQuestionContent());
				contentRow.createCell(4).setCellValue(questionnaireAnswerCount.getQuestionScore());
				contentRow.createCell(5).setCellValue(questionnaireAnswerCount.getAnswerCount());
				
				answerTotalScore += (questionnaireAnswerCount.getQuestionScore() * questionnaireAnswerCount.getAnswerCount());
				answerTotalVote += questionnaireAnswerCount.getAnswerCount();
				i++;
			}
			contentRow = exportSheet.getRow(firstRow);
			contentRow.createCell(6).setCellValue(answerTotalVote);
			contentRow.createCell(7).setCellValue(scoreAverage(answerTotalScore, answerTotalVote).floatValue());
			
			// 合并问题单元格
			cellNumRangeAddress = new CellRangeAddress(firstRow, lastRow, 0, 0);
			cellRangeAddress = new CellRangeAddress(firstRow, lastRow, 1, 1);
			if ((lastRow - firstRow != 0)) {
				
				exportSheet.addMergedRegion(cellNumRangeAddress);
				currentNumCell = exportSheet.getRow(firstRow).getCell(0);
				currentNumCell.setCellStyle(cellStyle);
				currentNumCell.setCellValue(num++);
				
				exportSheet.addMergedRegion(cellRangeAddress);
				currentCell = exportSheet.getRow(firstRow).getCell(1);
				currentCell.setCellStyle(cellStyle);
			}
			
			// 合并得分单元格
			cellRangeAddress = new CellRangeAddress(firstRow, lastRow, 6, 6);
			if ((lastRow - firstRow != 0)) {
				exportSheet.addMergedRegion(cellRangeAddress);
				currentCell = exportSheet.getRow(firstRow).getCell(6);
				currentCell.setCellStyle(cellStyle);
			}
			
			// 合并得分单元格
			cellRangeAddress = new CellRangeAddress(firstRow, lastRow, 7, 7);
			if ((lastRow - firstRow != 0)) {
				exportSheet.addMergedRegion(cellRangeAddress);
				currentCell = exportSheet.getRow(firstRow).getCell(7);
				currentCell.setCellStyle(cellStyle);
			}
		}
	}
	
	private BigDecimal scoreAverage(int score, int vote) throws Exception {
		if (score == 0 || vote == 0) {
			return new BigDecimal("0");
		}
		BigDecimal s = new BigDecimal(score);
		BigDecimal v = new BigDecimal(vote);
		return s.divide(v, RoundingMode.FLOOR);
	}
}