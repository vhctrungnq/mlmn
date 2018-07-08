package dao;

import java.util.List;

import vo.CSeversTelnet;

public interface CSeversTelnetDAO {
	
	void insertSelective(CSeversTelnet record);
	
	CSeversTelnet selectByPrimaryKey(int parseInt);
	
	int deleteCServersTelnet(Integer serverId);
    
	int updateCServersTelnet(CSeversTelnet record);
    
    List<CSeversTelnet> getCServersTelnet(String node,String serverName, String telnetUser, String alarmId, 
    		String type, String alarmType, String command, int order, String column);
}