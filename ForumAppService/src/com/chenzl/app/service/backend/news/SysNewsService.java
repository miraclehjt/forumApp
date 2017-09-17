package com.chenzl.app.service.backend.news;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenzl.app.entity.SysNews;
import com.chenzl.app.mappers.backend.news.SysNewsMapper;
import com.chenzl.app.mappers.common.CommonMapper;
import com.chenzl.app.service.BaseService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class SysNewsService extends BaseService<SysNews> {

	@Autowired
	private SysNewsMapper sysNewsMapper;

	@Autowired
	private CommonMapper commonMapper;

	/**
	 * 查询所有的新闻列表
	 * 
	 * @param params
	 * @return
	 */
	public Page<SysNews> queryNewsListAll(final Map<String, Object> params) {
		Integer page = (Integer) params.get("page"); // 页码
		Integer rows = (Integer) params.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue());
		Page<SysNews> pagelist = this.sysNewsMapper.queryNewsListForPage(params);
		return pagelist;
	}

	// 添加新闻
	public int addNews(final SysNews sysNews) throws Exception {
		Timestamp timestamp = commonMapper.selectSysDate();
		sysNews.setCreateDate(timestamp);
		sysNews.setEndDate(timestamp);
		return sysNewsMapper.insertSelective(sysNews);
	}

	// 更新新闻
	public int updateNews(SysNews sysNews) throws Exception {
		Timestamp timestamp = commonMapper.selectSysDate();
		// sysKb.setCreateDate(timestamp);
		sysNews.setEndDate(timestamp);
		return sysNewsMapper.updateByPrimaryKeySelective(sysNews);
	}

	/**
	 * 根据Id查询新闻
	 * 
	 * @param newsId
	 * @return
	 * @throws Exception
	 */
	public SysNews queryNewsById(Integer newsId) throws Exception {
		SysNews sysNews = sysNewsMapper.getNewsListById(newsId);
		return sysNews;
	}

	/**
	 * 删除新闻
	 * 
	 * @param newsId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int deleteNews(Integer newsId, String userId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		Timestamp timestamp = commonMapper.selectSysDate();
		params.put("newsId", newsId);
		params.put("state", 0);
		params.put("updateUser", userId);
		params.put("endDate", timestamp);
		int i = sysNewsMapper.updateNewsStateById(params);
		return i;
	}
}
