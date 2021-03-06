package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellMovement;

public class VRpDyCellMovementDAOImpl extends SqlMapClientDaoSupport implements VRpDyCellMovementDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_MOVEMENT
     *
     * @ibatorgenerated Fri Feb 21 10:44:27 ICT 2014
     */
    public VRpDyCellMovementDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_MOVEMENT
     *
     * @ibatorgenerated Fri Feb 21 10:44:27 ICT 2014
     */
    public void insert(VRpDyCellMovement record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_MOVEMENT.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_CELL_MOVEMENT
     *
     * @ibatorgenerated Fri Feb 21 10:44:27 ICT 2014
     */
    public void insertSelective(VRpDyCellMovement record) {
        getSqlMapClientTemplate().insert("V_RP_DY_CELL_MOVEMENT.ibatorgenerated_insertSelective", record);
    }
    public List<VRpDyCellMovement> getTrafficMovementHr(String bscid,String cellid,String startDate,String startHour, String endHour)
    {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_BSCID", 		bscid);
    	parms.put("P_CELLID", 		cellid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_START_HOUR", 		startHour);
    	parms.put("P_END_HOUR", 		endHour);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyCellMovement> record = getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_MOVEMENT.getTrafficMovementHr", parms);
        return record;
	}
    public List<VRpDyCellMovement> getTrafficMovementDy(String bscid,String cellid,String startDate, String endDate){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_BSCID", 		bscid);
    	parms.put("P_CELLID", 		cellid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyCellMovement> record = getSqlMapClientTemplate().queryForList("V_RP_DY_CELL_MOVEMENT.getTrafficMovementDy", parms);
        return record;
	}
}