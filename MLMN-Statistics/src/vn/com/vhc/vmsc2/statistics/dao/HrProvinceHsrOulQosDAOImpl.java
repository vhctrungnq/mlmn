package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.HrProvinceHsrOulQos;

public class HrProvinceHsrOulQosDAOImpl extends SqlMapClientDaoSupport implements HrProvinceHsrOulQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE_HSR_OUL_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:07:37 ICT 2011
     */
    public HrProvinceHsrOulQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE_HSR_OUL_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:07:37 ICT 2011
     */
    public void insert(HrProvinceHsrOulQos record) {
        getSqlMapClientTemplate().insert("V_RP_HR_PROVINCE_HSR_OUL_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_PROVINCE_HSR_OUL_QOS
     *
     * @ibatorgenerated Wed Feb 23 10:07:37 ICT 2011
     */
    public void insertSelective(HrProvinceHsrOulQos record) {
        getSqlMapClientTemplate().insert("V_RP_HR_PROVINCE_HSR_OUL_QOS.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
	public List<HrProvinceHsrOulQos> filter(String startHour, String endHour, Date day, String province, String region) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("province", province);
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("day", day);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_PROVINCE_HSR_OUL_QOS.filter2", map);
	}
}