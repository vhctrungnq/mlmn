package dao;

import java.util.List;

import vo.CableParameter;
import vo.SYS_PARAMETER;

public interface CableParameterDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    void insert(CableParameter record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    void insertSelective(CableParameter record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    CableParameter selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    int updateByPrimaryKeySelective(CableParameter record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table CABLE_PARAMETER
     *
     * @ibatorgenerated Sat Jan 12 10:31:02 ICT 2013
     */
    int updateByPrimaryKey(CableParameter record);

	List<CableParameter> getCabParameterValue(String focus, String term);

	List<SYS_PARAMETER> titleForm(String typeForm);

	List<CableParameter> distinctMaThamSo();

	Object getSysParametersFilter(String code, String name, String column,
			String string);
}