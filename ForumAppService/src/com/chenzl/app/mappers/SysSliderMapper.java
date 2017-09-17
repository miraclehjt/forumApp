package com.chenzl.app.mappers;

import com.chenzl.app.entity.SysSlider;
import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SysSliderMapper  extends Mapper <SysSlider> {
	
     public List<SysSlider> getInnerHomeSlider();
	
	public Page<SysSlider> getInnerSlidersForPage(Map<String, Object> params);
	
	public Long getInnerMaxSortId();
	
	
}