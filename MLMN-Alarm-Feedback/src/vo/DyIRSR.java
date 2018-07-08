package vo;

import java.util.Date;

public class DyIRSR {

	private Date day;
    private String ne;
    private String site;
    private String network;
    private String groups;
    private String district;
    private String province;
    private String team;
    private String dept;
    private Integer DUARATION;
    private Integer T_TRAFF;
    public Date getDay() {
        return day;
    }

    public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Integer getDUARATION() {
		return DUARATION;
	}

	public void setDUARATION(Integer dUARATION) {
		DUARATION = dUARATION;
	}

	public Integer getT_TRAFF() {
		return T_TRAFF;
	}

	public void setT_TRAFF(Integer t_TRAFF) {
		T_TRAFF = t_TRAFF;
	}

	public void setDay(Date day) {
        this.day = day;
    }

    public String getNe() {
        return ne;
    }

    public void setNe(String ne) {
        this.ne = ne;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
    
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    
    private Integer IRSR_TOTAL_2G;
    
    private Integer IRSR_BAD_2G;
    private Integer IRSR_GOOD_2G;
    private Double RATE_BAD_2G;
    private Double RATE_GOOD_2G;
    private Integer IRSR_TOTAL_3G;
    private Integer IRSR_BAD_3G;
    private Integer IRSR_GOOD_3G;
    private Double RATE_BAD_3G;
    private Double RATE_GOOD_3G;
    private Integer IRSR_TOTAL;
    private Integer IRSR_BAD;
    private Integer IRSR_GOOD;
    public Integer getIRSR_TOTAL_2G() {
		return IRSR_TOTAL_2G;
	}

	public void setIRSR_TOTAL_2G(Integer iRSR_TOTAL_2G) {
		IRSR_TOTAL_2G = iRSR_TOTAL_2G;
	}

	public Integer getIRSR_BAD_2G() {
		return IRSR_BAD_2G;
	}

	public void setIRSR_BAD_2G(Integer iRSR_BAD_2G) {
		IRSR_BAD_2G = iRSR_BAD_2G;
	}

	public Integer getIRSR_GOOD_2G() {
		return IRSR_GOOD_2G;
	}

	public void setIRSR_GOOD_2G(Integer iRSR_GOOD_2G) {
		IRSR_GOOD_2G = iRSR_GOOD_2G;
	}

	public Double getRATE_BAD_2G() {
		return RATE_BAD_2G;
	}

	public void setRATE_BAD_2G(Double rATE_BAD_2G) {
		RATE_BAD_2G = rATE_BAD_2G;
	}

	public Double getRATE_GOOD_2G() {
		return RATE_GOOD_2G;
	}

	public void setRATE_GOOD_2G(Double rATE_GOOD_2G) {
		RATE_GOOD_2G = rATE_GOOD_2G;
	}

	public Integer getIRSR_TOTAL_3G() {
		return IRSR_TOTAL_3G;
	}

	public void setIRSR_TOTAL_3G(Integer iRSR_TOTAL_3G) {
		IRSR_TOTAL_3G = iRSR_TOTAL_3G;
	}

	public Integer getIRSR_BAD_3G() {
		return IRSR_BAD_3G;
	}

	public void setIRSR_BAD_3G(Integer iRSR_BAD_3G) {
		IRSR_BAD_3G = iRSR_BAD_3G;
	}

	public Integer getIRSR_GOOD_3G() {
		return IRSR_GOOD_3G;
	}

	public void setIRSR_GOOD_3G(Integer iRSR_GOOD_3G) {
		IRSR_GOOD_3G = iRSR_GOOD_3G;
	}

	public Double getRATE_BAD_3G() {
		return RATE_BAD_3G;
	}

	public void setRATE_BAD_3G(Double rATE_BAD_3G) {
		RATE_BAD_3G = rATE_BAD_3G;
	}

	public Double getRATE_GOOD_3G() {
		return RATE_GOOD_3G;
	}

	public void setRATE_GOOD_3G(Double rATE_GOOD_3G) {
		RATE_GOOD_3G = rATE_GOOD_3G;
	}

	public Integer getIRSR_TOTAL() {
		return IRSR_TOTAL;
	}

	public void setIRSR_TOTAL(Integer iRSR_TOTAL) {
		IRSR_TOTAL = iRSR_TOTAL;
	}

	public Integer getIRSR_BAD() {
		return IRSR_BAD;
	}

	public void setIRSR_BAD(Integer iRSR_BAD) {
		IRSR_BAD = iRSR_BAD;
	}

	public Integer getIRSR_GOOD() {
		return IRSR_GOOD;
	}

	public void setIRSR_GOOD(Integer iRSR_GOOD) {
		IRSR_GOOD = iRSR_GOOD;
	}

	public Double getRATE_BAD() {
		return RATE_BAD;
	}

	public void setRATE_BAD(Double rATE_BAD) {
		RATE_BAD = rATE_BAD;
	}

	public Double getRATE_GOOD() {
		return RATE_GOOD;
	}

	public void setRATE_GOOD(Double rATE_GOOD) {
		RATE_GOOD = rATE_GOOD;
	}

	private Double RATE_BAD;
    private Double RATE_GOOD;

    private Integer IRSR_BAD_SUM;
    private Integer IRSR_GOOD_SUM;
    private Double RATE_BAD_SUM;
    public Integer getIRSR_BAD_SUM() {
		return IRSR_BAD_SUM;
	}

	public void setIRSR_BAD_SUM(Integer iRSR_BAD_SUM) {
		IRSR_BAD_SUM = iRSR_BAD_SUM;
	}

	public Integer getIRSR_GOOD_SUM() {
		return IRSR_GOOD_SUM;
	}

	public void setIRSR_GOOD_SUM(Integer iRSR_GOOD_SUM) {
		IRSR_GOOD_SUM = iRSR_GOOD_SUM;
	}

	public Double getRATE_BAD_SUM() {
		return RATE_BAD_SUM;
	}

	public void setRATE_BAD_SUM(Double rATE_BAD_SUM) {
		RATE_BAD_SUM = rATE_BAD_SUM;
	}

	public Double getRATE_GOOD_SUM() {
		return RATE_GOOD_SUM;
	}

	public void setRATE_GOOD_SUM(Double rATE_GOOD_SUM) {
		RATE_GOOD_SUM = rATE_GOOD_SUM;
	}

	public Double getIRSR_TOTAL_SUM() {
		return IRSR_TOTAL_SUM;
	}

	public void setIRSR_TOTAL_SUM(Double iRSR_TOTAL_SUM) {
		IRSR_TOTAL_SUM = iRSR_TOTAL_SUM;
	}


	private Double RATE_GOOD_SUM;
    private Double IRSR_TOTAL_SUM;
}
