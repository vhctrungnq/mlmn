package dao;

import java.util.Date;
import java.util.List;

import vo.DetailLostConfig;
import vo.DetailLostConfig3g;
import vo.SYS_PARAMETER;
import vo.VAlRbLossConfig3G;
import vo.V_RB_LOSSCONFIG_3G;

public interface VAlRbLossConfig3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_CONFIG3G
     *
     * @ibatorgenerated Wed Jan 16 15:58:45 ICT 2013
     */
    void insert(VAlRbLossConfig3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_CONFIG3G
     *
     * @ibatorgenerated Wed Jan 16 15:58:45 ICT 2013
     */
    void insertSelective(VAlRbLossConfig3G record);
    
    V_RB_LOSSCONFIG_3G  selectByPrimaryKey(int id);

	public int delete(Date sdate, String rncid, String alarmLevel,
			int fmAlarmId);
	public int deleteByPrimaryKey(Integer id);

	V_RB_LOSSCONFIG_3G selectByMore(Date stime, String rncid, String alarmLevel,
			int alarmID);

	int update(V_RB_LOSSCONFIG_3G vAlRbLossConfig3G);

	List<V_RB_LOSSCONFIG_3G> getLossConfig3GList(String startTime,String endTime, String rncid,
			String alarmLevel, String cellid, int totalTime, int totalTimeEN,
			String dvtTeamProcess, String dvtUserProcess, String causeby,
			String statusKTMCH, String alarmType, String column, int order);
	List<V_RB_LOSSCONFIG_3G> getLossConfig3G(String startTime, String rncid,
			String alarmLevel, String cellid, int totalTime, int totalTimeEN,
			String dvtTeamProcess, String dvtUserProcess, String causeby,
			String statusKTMCH, String alarmType, String column, int order);

	List<SYS_PARAMETER> titleForm(String alarmType,String typeForm);

	List<DetailLostConfig> getDetail(String rncid, String alarmLevel,
			String timer, int order, String column);

	List<SYS_PARAMETER> titleFormDetail();

	int updateStatusViewByPrimaryKey(String netWork, String status,
			String id);

	
}