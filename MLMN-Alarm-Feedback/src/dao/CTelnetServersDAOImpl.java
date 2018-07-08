package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.CTelnetServers;
import vo.SYS_PARAMETER;
import vo.alarm.utils.CauhinhTelnetFilter;

public class CTelnetServersDAOImpl extends SqlMapClientDaoSupport implements CTelnetServersDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_TELNET_SERVERS
     *
     * @ibatorgenerated Wed Nov 13 14:32:42 ICT 2013
     */
    public CTelnetServersDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_TELNET_SERVERS
     *
     * @ibatorgenerated Wed Nov 13 14:32:42 ICT 2013
     */
    public int deleteByPrimaryKey(String ipAddress, String module, String ne) {
        CTelnetServers key = new CTelnetServers();
        key.setIpAddress(ipAddress);
        key.setModule(module);
        key.setNe(ne);
        int rows = getSqlMapClientTemplate().delete("C_TELNET_SERVERS.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_TELNET_SERVERS
     *
     * @ibatorgenerated Wed Nov 13 14:32:42 ICT 2013
     */
    public void insert(CTelnetServers record) {
        getSqlMapClientTemplate().insert("C_TELNET_SERVERS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_TELNET_SERVERS
     *
     * @ibatorgenerated Wed Nov 13 14:32:42 ICT 2013
     */
    public void insertSelective(CTelnetServers record) {
        getSqlMapClientTemplate().insert("C_TELNET_SERVERS.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_TELNET_SERVERS
     *
     * @ibatorgenerated Wed Nov 13 14:32:42 ICT 2013
     */
    public CTelnetServers selectByPrimaryKey(String ipAddress, String module, String ne) {
        CTelnetServers key = new CTelnetServers();
        key.setIpAddress(ipAddress);
        key.setModule(module);
        key.setNe(ne);
        CTelnetServers record = (CTelnetServers) getSqlMapClientTemplate().queryForObject("C_TELNET_SERVERS.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_TELNET_SERVERS
     *
     * @ibatorgenerated Wed Nov 13 14:32:42 ICT 2013
     */
    public int updateByPrimaryKeySelective(CTelnetServers record) {
        int rows = getSqlMapClientTemplate().update("C_TELNET_SERVERS.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table C_TELNET_SERVERS
     *
     * @ibatorgenerated Wed Nov 13 14:32:42 ICT 2013
     */
    public int updateByPrimaryKey(CTelnetServers record) {
        int rows = getSqlMapClientTemplate().update("C_TELNET_SERVERS.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleForm(String typeForm) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_FORM", typeForm);	
		parms.put("P_DATA", null);	
		return getSqlMapClientTemplate().queryForList("C_TELNET_SERVERS.titleForm", parms);
	}
	/*Lay danh sach chi tiet cua giam SAT HE THONG  (13/11/2013 VanAnhCT)
				@param p_DATA: */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<CTelnetServers> getTelnetServerDetail() {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);	
		return getSqlMapClientTemplate().queryForList("C_TELNET_SERVERS.getTelnetServerDetail", parms);
	}
	/*Lay danh sach tong hop cua giam sat HE THONG ALARM  (13/11/2013 VanAnhCT)
	@param P_DAY: NGAY TELNET
	@param P_HOUR_FROM: GIO TELNET
	@param P_HOUR_TO: GIO TELNET
	@param P_TYPE: alarm,KPI,Invent
	@param p_DATA: */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<CTelnetServers> getTelnetServerType(String day, String hourF,
			String hourT, String type) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DAY", day);	
		parms.put("P_HOUR_FROM", hourF);
		parms.put("P_HOUR_TO", hourT);
		parms.put("P_TYPE", type);
		parms.put("P_DATA", null);	
		return getSqlMapClientTemplate().queryForList("C_TELNET_SERVERS.getTelnetServerType", parms);
	}
	
	/*@Author: anhnt
	update : 24/12/2013*/
	@SuppressWarnings("unchecked")
	@Override
	public List<CTelnetServers> getTelnetServerList(CauhinhTelnetFilter filter, int order, String column) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("p_vendor", filter.getVendor());	
		parms.put("p_ne", filter.getNe());
		parms.put("p_ip", filter.getIpAddress());
		parms.put("p_telnetUser", filter.getTelnetUser());
		parms.put("p_telnetType", filter.getTelnetType());
		parms.put("P_COLUMN", column);
		parms.put("P_ORDER", order);
		
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("C_TELNET_SERVERS.getTelnetServerList", parms);
	}
	
	public int deleteById(int id) {
        CTelnetServers key = new CTelnetServers();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("C_TELNET_SERVERS.ibatorgenerated_deleteById", key);
        return rows;
    }
	
	public CTelnetServers selectById(int id) {
        CTelnetServers key = new CTelnetServers();
        key.setId(id); 
        CTelnetServers record = (CTelnetServers) getSqlMapClientTemplate().queryForObject("C_TELNET_SERVERS.ibatorgenerated_selectById", key);
        return record;
    }
	
	public void insertSelectiveById(CTelnetServers record) {
        getSqlMapClientTemplate().insert("C_TELNET_SERVERS.ibatorgenerated_insertSelectiveById", record);
    }
	
	public int updateByIdSelective(CTelnetServers record) {
        int rows = getSqlMapClientTemplate().update("C_TELNET_SERVERS.ibatorgenerated_updateByIdSelective", record);
        return rows;
    }
}