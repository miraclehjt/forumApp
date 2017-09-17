package com.chenzl.app.service.forum;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenzl.app.entity.CctForumPostStar;
import com.chenzl.app.mappers.CctForumPostStarMapper;
import com.chenzl.app.mappers.common.CommonMapper;
import com.chenzl.app.service.CommonService;



@Service
public class ForumPostStarService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ForumPostStarService.class);

	@Autowired
	private CommonMapper commonMapper;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CctForumPostStarMapper cctForumPostStarMapper;
	
	/**
	 * 操作顶贴点赞
	 * @param postId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public void operateForumPostStar(final Integer postId, final String userId) throws Exception {
		if (!isForumPostStared(postId, userId)) {
			createCctForumPostStar(postId, userId);
		} else {
			deleteCctForumPostStar(postId, userId);
		}
	}
	
	/**
	 * 判断是否已顶贴点赞
	 * @param postId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Boolean isForumPostStared(final Integer postId, final String userId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postId", postId);
		params.put("userId", userId);
		List<CctForumPostStar> cctForumPostStars = cctForumPostStarMapper.getCctForumPostStarByMap(params);
		if (cctForumPostStars != null && cctForumPostStars.size() > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	/**
	 * 新增问题点赞记录
	 * @param postId 问题ID
	 * @param staffId 点赞工号
	 * @return
	 * @throws Exception
	 */
	public int createCctForumPostStar(final Integer postId, final String userId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postId", postId);
		params.put("userId", userId);
		List<CctForumPostStar> cctForumPostStars = cctForumPostStarMapper.getCctForumPostStarByMap(params);
		if (cctForumPostStars != null && cctForumPostStars.size() > 0) {
			return 1;
		}
		CctForumPostStar cctForumPostStar = new CctForumPostStar();
		cctForumPostStar.setPostId(postId);
		cctForumPostStar.setUserId(userId);
		cctForumPostStar.setCreateUser(userId);
		Timestamp timestamp = commonMapper.selectSysDate();
		cctForumPostStar.setCreateDate(timestamp);
		int i = cctForumPostStarMapper.insertSelective(cctForumPostStar);
		cctForumPostStarMapper.updateCctForumPostStaNumAdd(postId);
		return i;
	}
	
	/**
	 * 取消删除点赞记录
	 * @param postId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int deleteCctForumPostStar(final Integer postId, final String userId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("postId", postId);
		params.put("userId", userId);
		int i = cctForumPostStarMapper.deleteCctForumPostStarByMap(params);
		cctForumPostStarMapper.updateCctForumPostStaNumSubtract(postId);
		return i;
	}
}
