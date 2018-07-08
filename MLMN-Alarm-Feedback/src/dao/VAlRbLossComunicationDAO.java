package dao;

import java.util.List;

import vo.SYS_PARAMETER;
import vo.VAlRbLossComunication;

public interface VAlRbLossComunicationDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_COMUNICATION
     *
     * @ibatorgenerated Thu Dec 13 14:46:06 ICT 2012
     */
    void insert(VAlRbLossComunication record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_COMUNICATION
     *
     * @ibatorgenerated Thu Dec 13 14:46:06 ICT 2012
     */
    void insertSelective(VAlRbLossComunication record);

	/*List<VAlRbLossComunication> getAllComunicationInDay(String day, String bscid,
			String dip, String teamProcess, int totalTimeN, int order,
			String column);*/

	VAlRbLossComunication selectByID(int parseInt);
	public int deleteByPrimaryKey(Integer id);

	int update(VAlRbLossComunication vAlRbLossComunication);

	List<VAlRbLossComunication> getAllComunicationInDay(String day,
			String bscid, String dip, String teamProcess,
			String dvtUserProcess, int totalTimeFN, int totalTimeEN,
			String statusKT, int order, String column);

	List<SYS_PARAMETER> titleLossCommunication(String typeForm);
}