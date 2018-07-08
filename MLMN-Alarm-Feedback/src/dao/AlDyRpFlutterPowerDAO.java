package dao;

import java.util.List;

import vo.AlDyRpFlutterPower;
import vo.AlRpFlutter3Day;
import vo.SYS_PARAMETER;
import vo.WarningDipSame;

public interface AlDyRpFlutterPowerDAO {
	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table AL_DY_RP_FLUTTER_POWER
	 * 
	 * @ibatorgenerated Tue Dec 04 14:55:42 ICT 2012
	 */
	void insert(AlDyRpFlutterPower record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table AL_DY_RP_FLUTTER_POWER
	 * 
	 * @ibatorgenerated Tue Dec 04 14:55:42 ICT 2012
	 */
	void insertSelective(AlDyRpFlutterPower record);

	/*List<AlDyRpFlutterPower> getVAlDyRpFlutterPowerFilter(String startDate, String endDate,
			String bscid, String site, String teamProcess, int order, String column);
*/
	int updateByPrimaryKeySelective(AlDyRpFlutterPower record);

	int deleteByPrimaryKey(Integer id);

	AlDyRpFlutterPower selectByPrimaryKey(int parseInt);
	
	/*------------------Chập chờn 3 ngày liên tiếp-----------------------*/
	
	List<AlRpFlutter3Day>getAllFlutter3Day(String day, String bscid, String site, String teamProcess);

	List<WarningDipSame> getAlarmSameSystem(String bscID, String device,
			String day);

	List<AlDyRpFlutterPower> getVAlDyRpFlutterPowerFilter(String startDate,
			String endDate, String bscid, String site,
			String teamProcess, int totalTimeEN, int totalTimeEN2, int itemSl,
			int itemSlE, int order2, String column);

	List<SYS_PARAMETER> titleFlutter3Day();

	List<SYS_PARAMETER> titleFlutterPower(String typeForm);

}