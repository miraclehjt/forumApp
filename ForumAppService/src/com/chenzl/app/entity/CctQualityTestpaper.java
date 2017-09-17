package com.chenzl.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * null
 * @author 
 * @version 1.0  2016-09-12
 */
@Table(name = "cct_quality_testpaper")
public class CctQualityTestpaper implements Serializable {
	
	/**
	 * @Fields serialVersionUID:TODO
	 */
	private static final long serialVersionUID = 3405127851877493019L;

	@Id
    private String testpaperId;

    private String testpaperName;

    private String gameId;

    private Integer totalNum;

    private Date testTime;

    private String testpaperFlag;

    private String testpaperStatus;

    private Date testEndtime;

    private String remarks;

    private BigDecimal testHour;

    private String testpaperSrc;

    private String appraiseFlag;
    
    private String staffId;
    
    private String testTimeStr;
    
    private String testEndtimeStr;
    
    private Long lastTime;

    public String getTestpaperId() {
        return testpaperId;
    }

    public void setTestpaperId(String testpaperId) {
        this.testpaperId = testpaperId == null ? null : testpaperId.trim();
    }

    public String getTestpaperName() {
        return testpaperName;
    }

    public void setTestpaperName(String testpaperName) {
        this.testpaperName = testpaperName == null ? null : testpaperName.trim();
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId == null ? null : gameId.trim();
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public String getTestpaperFlag() {
        return testpaperFlag;
    }

    public void setTestpaperFlag(String testpaperFlag) {
        this.testpaperFlag = testpaperFlag == null ? null : testpaperFlag.trim();
    }

    public String getTestpaperStatus() {
        return testpaperStatus;
    }

    public void setTestpaperStatus(String testpaperStatus) {
        this.testpaperStatus = testpaperStatus == null ? null : testpaperStatus.trim();
    }

    public Date getTestEndtime() {
        return testEndtime;
    }

    public void setTestEndtime(Date testEndtime) {
        this.testEndtime = testEndtime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public BigDecimal getTestHour() {
        return testHour;
    }

    public void setTestHour(BigDecimal testHour) {
        this.testHour = testHour;
    }

    public String getTestpaperSrc() {
        return testpaperSrc;
    }

    public void setTestpaperSrc(String testpaperSrc) {
        this.testpaperSrc = testpaperSrc == null ? null : testpaperSrc.trim();
    }

    public String getAppraiseFlag() {
        return appraiseFlag;
    }

    public void setAppraiseFlag(String appraiseFlag) {
        this.appraiseFlag = appraiseFlag == null ? null : appraiseFlag.trim();
    }

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId == null ? null : staffId.trim();
	}

	public String getTestTimeStr() {
		return testTimeStr;
	}

	public void setTestTimeStr(String testTimeStr) {
		this.testTimeStr = testTimeStr;
	}

	public String getTestEndtimeStr() {
		return testEndtimeStr;
	}

	public void setTestEndtimeStr(String testEndtimeStr) {
		this.testEndtimeStr = testEndtimeStr;
	}

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}
}