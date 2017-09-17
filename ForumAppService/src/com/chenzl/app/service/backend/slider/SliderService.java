package com.chenzl.app.service.backend.slider;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chenzl.app.entity.SysSlider;
import com.chenzl.app.mappers.SysSliderMapper;
import com.chenzl.app.service.BaseService;
import com.chenzl.app.service.CommonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class SliderService  extends BaseService<SysSlider>{
	@Autowired
	private SysSliderMapper sysSliderMapper;
	
	@Autowired
	CommonService commonService;
	/**
	 * 查询所有轮播
	 * @param params
	 * @return
	 */
	public Page<SysSlider> getAllInnerSlider(final Map<String, Object> params){
		Integer page = (Integer) params.get("page"); // 页码
		Integer rows = (Integer) params.get("rows"); // 每页显示行
		PageHelper.startPage(page.intValue(), rows.intValue());
		
		Page<SysSlider> pagelist = this.sysSliderMapper.getInnerSlidersForPage(params);
		
		return pagelist;
	}
	
	/**
	 * 获取APP首页轮播
	 * @return
	 */
	public List<SysSlider> getInnerHomeSlider() {
	/*	SysSlider slider = new SysSlider();
		slider.setState("1");*/
		return this.sysSliderMapper.getInnerHomeSlider();
	}
	/**
	 * 根据轮播ID获取轮播对象
	 * @param sliderId
	 * @return
	 */
	public SysSlider getInnerSliderById(int sliderId) {
		return this.sysSliderMapper.selectByPrimaryKey(sliderId);
	}
	
	/**
	 * 创建轮播
	 * @param name
	 * @param sliderImg
	 * @param sliderLink
	 * @param state
	 * @param user
	 * @param date
	 * @param sortId
	 * @throws Exception
	 */
	public void createInnerSlider(String name,String sliderImg,String sliderLink,String state,String user,Date date,String sortId) throws Exception {
		SysSlider slider = new SysSlider();
		slider.setCreateUser(user);
		slider.setCreateDate(date);
		slider.setSliderImg(sliderImg);
		slider.setSliderLink(sliderLink);
		slider.setSliderName(name);
		slider.setUpdateUser(user);
		slider.setUpdateDate(date);
		slider.setState(state);
		slider.setSortId(sortId);
		
		this.sysSliderMapper.insertSelective(slider);
	}
	
	/**
	 * 更新轮播
	 * @param sliderId
	 * @param name
	 * @param sliderImg
	 * @param sliderLink
	 * @param state
	 * @param staff
	 * @param date
	 * @param sortId
	 */
	public void updateInnerSlider(int sliderId,String name,String sliderImg,String sliderLink,String state,String user,Date date,String sortId) {
		SysSlider slider = new SysSlider();
		slider.setSliderId(sliderId);
		slider.setUpdateUser(user);
		slider.setUpdateDate(date);
		if (!StringUtils.isBlank(sliderImg)) {
			slider.setSliderImg(sliderImg);
		}
		slider.setSliderLink(sliderLink);
		slider.setSliderName(name);
		slider.setState(state);
		slider.setSortId(sortId);
		
		this.sysSliderMapper.updateByPrimaryKeySelective(slider);
	}
	
	/**
	 * 删除内部轮播
	 * @param sliderId
	 */
	public void deleteInnerSlider(int sliderId) {
		this.sysSliderMapper.deleteByPrimaryKey(sliderId);
	}
	
	/**
	 * 获取当前最大值
	 * @return
	 */
	public Long getInnerMaxSortId() {
		return this.sysSliderMapper.getInnerMaxSortId();
	}

}
