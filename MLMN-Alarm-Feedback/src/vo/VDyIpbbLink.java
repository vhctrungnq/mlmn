package vo;

import java.io.Serializable;
import java.util.Date;

public class VDyIpbbLink implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.DAY
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Date day;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.DIRECTION
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private String direction;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.LINK
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float link;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.BAND_WIDTH
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float bandWidth;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.IN_KBIT_SECOND
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float inKbitSecond;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.IN_UTILIZATION
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float inUtilization;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.OUT_KBIT_SECOND
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float outKbitSecond;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.OUT_UTILIZATION
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float outUtilization;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.DELAY_MAX
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float delayMax;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.DELAY_AVG
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float delayAvg;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.JITTER_AVG
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float jitterAvg;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.MAX_OVER_THRESHOLD_50
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float maxOverThreshold50;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.MAX_OVER_THRESHOLD_10
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float maxOverThreshold10;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.PACKET_LOSS
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float packetLoss;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.MOS
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float mos;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column V_DY_IPBB_LINK.LOSS_CONNECTION_DUR
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private Float lossConnectionDur;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table V_DY_IPBB_LINK
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.DAY
     *
     * @return the value of V_DY_IPBB_LINK.DAY
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Date getDay() {
        return day;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.DAY
     *
     * @param day the value for V_DY_IPBB_LINK.DAY
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setDay(Date day) {
        this.day = day;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.DIRECTION
     *
     * @return the value of V_DY_IPBB_LINK.DIRECTION
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public String getDirection() {
        return direction;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.DIRECTION
     *
     * @param direction the value for V_DY_IPBB_LINK.DIRECTION
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.LINK
     *
     * @return the value of V_DY_IPBB_LINK.LINK
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getLink() {
        return link;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.LINK
     *
     * @param link the value for V_DY_IPBB_LINK.LINK
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setLink(Float link) {
        this.link = link;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.BAND_WIDTH
     *
     * @return the value of V_DY_IPBB_LINK.BAND_WIDTH
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getBandWidth() {
        return bandWidth;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.BAND_WIDTH
     *
     * @param bandWidth the value for V_DY_IPBB_LINK.BAND_WIDTH
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setBandWidth(Float bandWidth) {
        this.bandWidth = bandWidth;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.IN_KBIT_SECOND
     *
     * @return the value of V_DY_IPBB_LINK.IN_KBIT_SECOND
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getInKbitSecond() {
        return inKbitSecond;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.IN_KBIT_SECOND
     *
     * @param inKbitSecond the value for V_DY_IPBB_LINK.IN_KBIT_SECOND
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setInKbitSecond(Float inKbitSecond) {
        this.inKbitSecond = inKbitSecond;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.IN_UTILIZATION
     *
     * @return the value of V_DY_IPBB_LINK.IN_UTILIZATION
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getInUtilization() {
        return inUtilization;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.IN_UTILIZATION
     *
     * @param inUtilization the value for V_DY_IPBB_LINK.IN_UTILIZATION
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setInUtilization(Float inUtilization) {
        this.inUtilization = inUtilization;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.OUT_KBIT_SECOND
     *
     * @return the value of V_DY_IPBB_LINK.OUT_KBIT_SECOND
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getOutKbitSecond() {
        return outKbitSecond;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.OUT_KBIT_SECOND
     *
     * @param outKbitSecond the value for V_DY_IPBB_LINK.OUT_KBIT_SECOND
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setOutKbitSecond(Float outKbitSecond) {
        this.outKbitSecond = outKbitSecond;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.OUT_UTILIZATION
     *
     * @return the value of V_DY_IPBB_LINK.OUT_UTILIZATION
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getOutUtilization() {
        return outUtilization;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.OUT_UTILIZATION
     *
     * @param outUtilization the value for V_DY_IPBB_LINK.OUT_UTILIZATION
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setOutUtilization(Float outUtilization) {
        this.outUtilization = outUtilization;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.DELAY_MAX
     *
     * @return the value of V_DY_IPBB_LINK.DELAY_MAX
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getDelayMax() {
        return delayMax;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.DELAY_MAX
     *
     * @param delayMax the value for V_DY_IPBB_LINK.DELAY_MAX
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setDelayMax(Float delayMax) {
        this.delayMax = delayMax;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.DELAY_AVG
     *
     * @return the value of V_DY_IPBB_LINK.DELAY_AVG
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getDelayAvg() {
        return delayAvg;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.DELAY_AVG
     *
     * @param delayAvg the value for V_DY_IPBB_LINK.DELAY_AVG
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setDelayAvg(Float delayAvg) {
        this.delayAvg = delayAvg;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.JITTER_AVG
     *
     * @return the value of V_DY_IPBB_LINK.JITTER_AVG
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getJitterAvg() {
        return jitterAvg;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.JITTER_AVG
     *
     * @param jitterAvg the value for V_DY_IPBB_LINK.JITTER_AVG
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setJitterAvg(Float jitterAvg) {
        this.jitterAvg = jitterAvg;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.MAX_OVER_THRESHOLD_50
     *
     * @return the value of V_DY_IPBB_LINK.MAX_OVER_THRESHOLD_50
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getMaxOverThreshold50() {
        return maxOverThreshold50;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.MAX_OVER_THRESHOLD_50
     *
     * @param maxOverThreshold50 the value for V_DY_IPBB_LINK.MAX_OVER_THRESHOLD_50
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setMaxOverThreshold50(Float maxOverThreshold50) {
        this.maxOverThreshold50 = maxOverThreshold50;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.MAX_OVER_THRESHOLD_10
     *
     * @return the value of V_DY_IPBB_LINK.MAX_OVER_THRESHOLD_10
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getMaxOverThreshold10() {
        return maxOverThreshold10;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.MAX_OVER_THRESHOLD_10
     *
     * @param maxOverThreshold10 the value for V_DY_IPBB_LINK.MAX_OVER_THRESHOLD_10
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setMaxOverThreshold10(Float maxOverThreshold10) {
        this.maxOverThreshold10 = maxOverThreshold10;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.PACKET_LOSS
     *
     * @return the value of V_DY_IPBB_LINK.PACKET_LOSS
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getPacketLoss() {
        return packetLoss;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.PACKET_LOSS
     *
     * @param packetLoss the value for V_DY_IPBB_LINK.PACKET_LOSS
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setPacketLoss(Float packetLoss) {
        this.packetLoss = packetLoss;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.MOS
     *
     * @return the value of V_DY_IPBB_LINK.MOS
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getMos() {
        return mos;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.MOS
     *
     * @param mos the value for V_DY_IPBB_LINK.MOS
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setMos(Float mos) {
        this.mos = mos;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column V_DY_IPBB_LINK.LOSS_CONNECTION_DUR
     *
     * @return the value of V_DY_IPBB_LINK.LOSS_CONNECTION_DUR
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public Float getLossConnectionDur() {
        return lossConnectionDur;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column V_DY_IPBB_LINK.LOSS_CONNECTION_DUR
     *
     * @param lossConnectionDur the value for V_DY_IPBB_LINK.LOSS_CONNECTION_DUR
     *
     * @ibatorgenerated Fri Mar 11 09:25:28 ICT 2016
     */
    public void setLossConnectionDur(Float lossConnectionDur) {
        this.lossConnectionDur = lossConnectionDur;
    }
    private Integer week;
    private Integer month;
    private Integer year;

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}