package dao;

import java.util.List;

import vo.AlDyRpDip3G;
import vo.AlDyRpDip3G3Day;
import vo.SYS_PARAMETER;
import vo.WarningDipSame;

public interface AlDyRpDip3GDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_DIP_3G
     *
     * @ibatorgenerated Wed Dec 19 16:36:01 ICT 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_DIP_3G
     *
     * @ibatorgenerated Wed Dec 19 16:36:01 ICT 2012
     */
    void insert(AlDyRpDip3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_DIP_3G
     *
     * @ibatorgenerated Wed Dec 19 16:36:01 ICT 2012
     */
    void insertSelective(AlDyRpDip3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_DIP_3G
     *
     * @ibatorgenerated Wed Dec 19 16:36:01 ICT 2012
     */
    AlDyRpDip3G selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_DIP_3G
     *
     * @ibatorgenerated Wed Dec 19 16:36:01 ICT 2012
     */
    int updateByPrimaryKeySelective(AlDyRpDip3G record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_DIP_3G
     *
     * @ibatorgenerated Wed Dec 19 16:36:01 ICT 2012
     */
    int updateByPrimaryKey(AlDyRpDip3G record);

	int update(AlDyRpDip3G alDyRpDip3G);

	List<AlDyRpDip3G3Day> getAllDip3G3Day(String day, String rnc, String nodeb,
			String dvtTeamProcess);
	List<AlDyRpDip3G> getLossDip3GList(String sday,String eday, String rnc, String nodeb,
			String dvtTeamProcess,
			String dvtUserProcess, int itemSl, int itemSlE, String transType,
			String column, int order);

	List<WarningDipSame> getAlarmSameSystem(String rnc, String device,
			String day);

	List<SYS_PARAMETER> titleLossDip(String typeForm);

	List<SYS_PARAMETER> titleLossDip3G(String typeForm);

}