package dao;

import java.util.Date;
import java.util.List;

import vo.SYS_PARAMETER;
import vo.VAlRbLossAntenna;

public interface VAlRbLossAntennaDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_ANTENNA
     *
     * @ibatorgenerated Wed Dec 19 16:42:49 ICT 2012
     */
    void insert(VAlRbLossAntenna record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_ANTENNA
     *
     * @ibatorgenerated Wed Dec 19 16:42:49 ICT 2012
     */
    void insertSelective(VAlRbLossAntenna record);

	int delete(Date stime, String rncid, String alarmLevel, int alarmID);

	/*List<VAlRbLossAntenna> getLossAntennaList(String startTime, String rncid,
			String alarmLevel, int totalTime, String dvtTeamProcess,
			String sector, String antenError, String statusKTMCH,
			String column, int order);*/

	VAlRbLossAntenna selectByMore(Date stime, String rncid, String alarmLevel,
			int alarmID);

	int update(VAlRbLossAntenna vAlRbLossAntenna);

	List<VAlRbLossAntenna> getLossAntennaList(String startTime, String rncid,
			String alarmLevel, int totalTime, int totalTimeE,
			String dvtTeamProcess, String dvtUserProcess, String sector,
			String antenError, String statusKTMCH, String column, int order);

	VAlRbLossAntenna selectByID(int id);

	List<SYS_PARAMETER> titleAntenna(String  typeForm);
}