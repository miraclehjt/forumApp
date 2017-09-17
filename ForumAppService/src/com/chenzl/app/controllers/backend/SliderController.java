package com.chenzl.app.controllers.backend;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chenzl.app.bean.backend.slider.SliderWrapper;
import com.chenzl.app.common.util.Pinyin4jUtil;
import com.chenzl.app.common.util.UploadUtils;
import com.chenzl.app.common.util.file.rsmanager.ResClientImpl;
import com.chenzl.app.entity.SysSlider;
import com.chenzl.app.service.backend.slider.SliderService;
import com.github.pagehelper.Page;

/**
 * 轮播图
 * @author chenzl
 */
@Controller
@RequestMapping("manages/slider")
public class SliderController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SliderController.class);

	@Autowired
	private SliderService sliderService;

	@Autowired
	private ResClientImpl resClient;

	/**
	 * 获取轮播图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getAllInnerSlider", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getAllInnerSlider(HttpServletRequest request) {

		LOGGER.debug("SliderController.getAllSlider...");
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
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

		JSONObject returnObject = new JSONObject();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("page", Integer.valueOf(iPage));
			params.put("rows", Integer.valueOf(iRows));

			Page<SysSlider> pageList = this.sliderService.getAllInnerSlider(params);

			returnObject.put("rows", pageList);
			returnObject.put("total", pageList.getTotal());

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}

	/**
	 * 获取app首页轮播
	 * @return
	 */
	@RequestMapping(value = "getInnerHomeSlider", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getInnerHomeSlider() {

		LOGGER.debug("SliderController.getAllSlider...");

		JSONObject returnObject = new JSONObject();
		try {
			List<SysSlider> list = this.sliderService.getInnerHomeSlider();
			returnObject.put("data", list);

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	
	/**
	 * 根据id获取slider
	 */
	@RequestMapping(value = "getInnerSliderById", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getInnerSliderById(
			@RequestParam("sliderId") int sliderId) {
		LOGGER.debug("SliderController.getInnerSliderById...");

		JSONObject returnObject = new JSONObject();
		try {

			SysSlider slider = this.sliderService.getInnerSliderById(sliderId);
			returnObject.put("data", slider);

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	}
	/**
	 * 增加/更新轮播
	 */
	@RequestMapping(value = "saveInnerSlider", method = RequestMethod.POST)
	public String saveInnerSlider(HttpServletRequest request,
			HttpServletResponse response, SliderWrapper slider) {
		LOGGER.debug("SliderController.saveInnerSlider...");

		try {

			String iconUrl = "";
			
			if (slider.getIconFile() != null && slider.getIconFile().length > 0
					&& slider.getIconFile()[0].getSize() > 0) {
				InputStream inputStream = slider.getIconFile()[0]
						.getInputStream();

				BufferedImage sourceImg = ImageIO.read(inputStream);

				System.out.println(sourceImg.getWidth());
				System.out.println(sourceImg.getHeight());

				if (sourceImg.getWidth() != 640 || sourceImg.getHeight() != 320) {
					request.setAttribute("msg", "请上传640px*320px大小的图片");

					return "slider/innerSlider";

				}
				
				String fileDate = UploadUtils.getFileDate(new Timestamp(
						new Date().getTime()));
				String fileDateTime = UploadUtils
						.getFileDateTime(new Timestamp(new Date().getTime()));
				String fileName = fileDateTime
						+ slider.getIconFile()[0].getOriginalFilename();
				fileName = Pinyin4jUtil.getPingYin(fileName);//把中文文件名换成拼音
				String uploadFilePath = UploadUtils.createFilePath(
						UploadUtils.WEB_SITE, UploadUtils.FILE_TYPE_JPG,
						fileDate, fileName);
				String uploadPath = resClient.put(uploadFilePath,
						slider.getIconFile()[0].getBytes());
				uploadPath = UploadUtils.getFileUrlPath(uploadPath);
				iconUrl = uploadPath;
			}
			
			String name = slider.getSliderName();
			String link = slider.getSliderLink();
			String userId = slider.getUserId();
			String state = slider.getState();
			
			if (StringUtils.isBlank(slider.getSliderId())) {
				String sortId = slider.getSortId();
				this.sliderService.createInnerSlider(name, iconUrl, link, state, userId, new Date(),sortId);
			} else {
				int sliderId =Integer.valueOf(slider.getSliderId());
				String sortId = slider.getSortId();
				this.sliderService.updateInnerSlider(sliderId, name, iconUrl, link, state, userId, new Date(),sortId);
			}
			
			return "slider/innerSlider";

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("添加/更新轮播failed");

			request.setAttribute("msg", "请上传640px*320px大小的图片");

			return "slider/innerSlider";

		}


	}

	
	/*
	 * 删除slider
	 */
	@RequestMapping(value = "deleteInnerSlider", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteInnerSlider(
			@RequestParam("sliderId") Integer sliderId) {
		System.out.println(sliderId);
		if (null == sliderId) {
			LOGGER.debug("deleteSlider参数错误！sliderId = " + sliderId);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		try {
			this.sliderService.deleteInnerSlider(sliderId);

			JSONObject returnJsonObject = new JSONObject();
			returnJsonObject.put("errorCode", "200");
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnJsonObject, HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("删除横幅失败");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}

	}
	
	/**
	 * 添加一个轮播图
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getInnerMaxSortId", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getInnerMaxSortId(HttpServletRequest request) {
		
		LOGGER.debug("SliderController.getInnerMaxSortId...");
		
		JSONObject returnObject = new JSONObject();
		
		
		try {
			
			Long maxSortId = this.sliderService.getInnerMaxSortId();
			
			if (maxSortId == null) {
				maxSortId = Long.valueOf("0");
			}
			
			returnObject.put("maxSortId", maxSortId);

			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					returnObject, org.springframework.http.HttpStatus.OK);
			return responseEntity;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ResponseEntity<JSONObject> responseEntity = new ResponseEntity<JSONObject>(
					new JSONObject(),
					org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
			return responseEntity;
		}
	
	}

}
