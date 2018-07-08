package vn.com.vhc.vmsc2.statistics.domain;

import java.util.Date;

/**
 * @author galaxy
 * @createDate 9:50:32 AM
 * IPBBLinkTruyenDan.java
 *
 */
public class IPBBLinkTruyenDan {
	private int year;
	private int month;
	private int quarter;
	private int week;
	private Date day;
	private String link;
	private String direction;
	private Float bandWidth;
	private Float inKbitSecond;
	private Float inUtilization;
	private Float outKbitSecond;
	private Float outUtilization;
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * @return the quarter
	 */
	public int getQuarter() {
		return quarter;
	}
	/**
	 * @param quarter the quarter to set
	 */
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	/**
	 * @return the week
	 */
	public int getWeek() {
		return week;
	}
	/**
	 * @param week the week to set
	 */
	public void setWeek(int week) {
		this.week = week;
	}
	/**
	 * @return the day
	 */
	public Date getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(Date day) {
		this.day = day;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}
	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}
	/**
	 * @return the bandWidth
	 */
	public Float getBandWidth() {
		return bandWidth;
	}
	/**
	 * @param bandWidth the bandWidth to set
	 */
	public void setBandWidth(Float bandWidth) {
		this.bandWidth = bandWidth;
	}
	/**
	 * @return the inKbitSecond
	 */
	public Float getInKbitSecond() {
		return inKbitSecond;
	}
	/**
	 * @param inKbitSecond the inKbitSecond to set
	 */
	public void setInKbitSecond(Float inKbitSecond) {
		this.inKbitSecond = inKbitSecond;
	}
	/**
	 * @return the inUtilization
	 */
	public Float getInUtilization() {
		return inUtilization;
	}
	/**
	 * @param inUtilization the inUtilization to set
	 */
	public void setInUtilization(Float inUtilization) {
		this.inUtilization = inUtilization;
	}
	/**
	 * @return the outKbitSecond
	 */
	public Float getOutKbitSecond() {
		return outKbitSecond;
	}
	/**
	 * @param outKbitSecond the outKbitSecond to set
	 */
	public void setOutKbitSecond(Float outKbitSecond) {
		this.outKbitSecond = outKbitSecond;
	}
	/**
	 * @return the outUtilization
	 */
	public Float getOutUtilization() {
		return outUtilization;
	}
	/**
	 * @param outUtilization the outUtilization to set
	 */
	public void setOutUtilization(Float outUtilization) {
		this.outUtilization = outUtilization;
	}

}
