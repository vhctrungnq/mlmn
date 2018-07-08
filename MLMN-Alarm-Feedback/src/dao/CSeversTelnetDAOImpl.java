package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.CSeversTelnet;

public class CSeversTelnetDAOImpl extends SqlMapClientDaoSupport implements CSeversTelnetDAO {
	
	public void insertSelective(CSeversTelnet record) {
        getSqlMapClientTemplate().insert("C_SERVERS_TELNET.ibatorgenerated_insertSelective", record);
    }
	
	@Override
	public CSeversTelnet selectByPrimaryKey(int serverId) {
		CSeversTelnet key = new CSeversTelnet();
        key.setServerId(serverId);
        CSeversTelnet record = (CSeversTelnet) getSqlMapClientTemplate().queryForObject("C_SERVERS_TELNET.selectByPrimaryKey", key);
        return record;
	}
	
	@Override
	public int deleteCServersTelnet(Integer serverId) {
		CSeversTelnet key = new CSeversTelnet();
        key.setServerId(serverId);
        int rows = getSqlMapClientTemplate().delete("C_SERVERS_TELNET.deleteCServersTelnet", key);
        return rows;
	}
    
	@Override
	public int updateCServersTelnet(CSeversTelnet record){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_NODE", record.getNode());
		parms.put("P_SERVER_ID", record.getServerId());
		parms.put("P_SERVER_NAME", record.getServerName());
		parms.put("P_IP_ADDRESS", record.getIpAddress());
		parms.put("P_ALARM_ID", record.getAlarmName());
		parms.put("P_LOGIN_TIME", record.getLoginTime());
		parms.put("P_TYPE", record.getType());
		parms.put("P_COMMAND", record.getCommand());
		parms.put("P_FUNCTION", record.getFunction());
		parms.put("P_DEVICE_TYPE", record.getDeviceType());
		parms.put("P_TELNET_NAME", record.getTelnetUser());
		parms.put("P_TELNET_PASS", record.getTelnetPassword());
		parms.put("P_ALARM_TYPE", record.getAlarmType());
		
        int rows = getSqlMapClientTemplate().update("C_SERVERS_TELNET.updateCServersTelnet", parms);
        return rows;
	}
    
    
	@SuppressWarnings("unchecked")
	@Override
	public List<CSeversTelnet> getCServersTelnet(String node,String serverName, String telnetUser, String alarmId, 
    		String type, String alarmType, String command, int order, String column){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("P_NODE", node);
		map.put("P_SERVER_NAME", serverName);
		map.put("P_TELNET_USER", telnetUser);
		map.put("P_ALARM_ID", alarmId);
		map.put("P_TYPE", type);
		map.put("P_ALARM_TYPE", alarmType);
		map.put("P_COMMAND", command);		
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		
		map.put("P_DATA", null);
		
		return getSqlMapClientTemplate().queryForList("C_SERVERS_TELNET.getCServersTelnet", map);
	
	}
}