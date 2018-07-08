package dao;

import java.util.List;

import vo.SYS_PARAMETER;

public interface SYS_PARAMETERDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETER
     *
     * @ibatorgenerated Thu Sep 06 10:56:36 ICT 2012
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETER
     *
     * @ibatorgenerated Thu Sep 06 10:56:36 ICT 2012
     */
    void insert(SYS_PARAMETER record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETER
     *
     * @ibatorgenerated Thu Sep 06 10:56:36 ICT 2012
     */
    void insertSelective(SYS_PARAMETER record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETER
     *
     * @ibatorgenerated Thu Sep 06 10:56:36 ICT 2012
     */
    SYS_PARAMETER selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETER
     *
     * @ibatorgenerated Thu Sep 06 10:56:36 ICT 2012
     */
    int updateByPrimaryKeySelective(SYS_PARAMETER record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table SYS_PARAMETER
     *
     * @ibatorgenerated Thu Sep 06 10:56:36 ICT 2012
     */
    int updateByPrimaryKey(SYS_PARAMETER record);

	List<SYS_PARAMETER> getSysParameterByCode(String code);

	List<SYS_PARAMETER> getSystemNotInSysMenu();

	List<SYS_PARAMETER> checkWarningTypeExits(String code, String name);

	List<SYS_PARAMETER> getListTTPACount();

	SYS_PARAMETER getSPByValue(String value);
	
	List<SYS_PARAMETER> getSPByCodeAndName(String code, String name);

	SYS_PARAMETER getValueByCodeName(String pName, String pValue);

	int updateByCodeName(SYS_PARAMETER record);

	List<SYS_PARAMETER> getSysParametersFilter(String code, String name,
			String column, String order);

	List<SYS_PARAMETER> distinctMaThamSo();

	List<SYS_PARAMETER> titleSysParameter();

	List<SYS_PARAMETER> getListHourOnDay();

	List<SYS_PARAMETER> getAlarmType();

	List<SYS_PARAMETER> getStatusFinish();

	List<SYS_PARAMETER> getCausebySystem();

	List<SYS_PARAMETER> getStatusDVTUCTT();

	List<SYS_PARAMETER> getStatusNode();
	
	List<SYS_PARAMETER> getCondition();

	SYS_PARAMETER getTimerLossDip();
	
	// @author Thangpx
	/*List<SYS_PARAMETER> getSPByName(String pName, String pValue);
*/
	List<SYS_PARAMETER> getSPByName(String alarmType, String netWork,
			String typeForm);

	List<SYS_PARAMETER> getVendorList();

	List<SYS_PARAMETER> getGroupQualityList();

	List<SYS_PARAMETER> getProcessFeedbackJob();

	List<SYS_PARAMETER> getStatusOfCentralFb();

	List<SYS_PARAMETER> getIsEnableCableOdfList();

	List<SYS_PARAMETER> getLoaiBaoCaoList();

	List<SYS_PARAMETER> getColorOdfLienTang();

	List<SYS_PARAMETER> getAsImportWarehouseUnit();

	List<SYS_PARAMETER> asImportWarehouseByUnit(String unit);

	List<SYS_PARAMETER> getSameUnitAssets();

	List<SYS_PARAMETER> getListFbIbc();

	List<SYS_PARAMETER> getHomePage(String module); 
	
	List<SYS_PARAMETER> getUserAdmin();

	List<SYS_PARAMETER> titleSysParameterByCode(String typeCode, String string);

	List<SYS_PARAMETER> getModuleWhenLogin(String moduleValue);

	List<SYS_PARAMETER> getEquipmentType();

	List<SYS_PARAMETER> getStatusEquipmentType();

	List<SYS_PARAMETER> getStatusIsoLicenseSoft();

	List<SYS_PARAMETER> getSeverityList();

	List<SYS_PARAMETER> getStatusViewList();

	List<SYS_PARAMETER> getAckStatusList();

	List<SYS_PARAMETER> getVendorForResourceList();

	List<SYS_PARAMETER> getWarningType();

	List<SYS_PARAMETER> getPositionList();

	List<SYS_PARAMETER> getSysParaForBlacklist(String username,
			String alarmType, String alarmId, String alarmName);

	List<SYS_PARAMETER> getNetworkTypeList();

	List<SYS_PARAMETER> getNeTypeList();

	List<SYS_PARAMETER> getRawTableList();

	List<SYS_PARAMETER> getAlarmColumnNameList();

	List<SYS_PARAMETER> getYesNoList();

	List<SYS_PARAMETER> getSearchList();
	
	List<SYS_PARAMETER> getTypeEmailHourList();

	List<SYS_PARAMETER> getInputStatusList();
	
	List<SYS_PARAMETER> getMailLevel();

	List<SYS_PARAMETER> getShiftHeader(String region);

	List<SYS_PARAMETER> getNetworkForOnAir();

	List<SYS_PARAMETER> getFormsLogin();

	List<SYS_PARAMETER> getStatusSendMail();

	List<SYS_PARAMETER> getSmsOtherForSysUsers();

	List<SYS_PARAMETER> getStatusDyTrx();

	List<SYS_PARAMETER> getTypeDyTrx();

	List<SYS_PARAMETER> getTypeDyTrx3g();

	List<SYS_PARAMETER> getAlamNameList(String warningTp);

	List<SYS_PARAMETER> getBrandName();

	List<SYS_PARAMETER> getLLProvider();

	List<SYS_PARAMETER> getLeaseLine(String llNode, String llProvider);

	List<SYS_PARAMETER> getRegionList();
	//kiem tra khu vuc dang ky có ton tai ko
	boolean checkRegionExit(String regionRole);
	// lay các trạng thái UCTT
	List<SYS_PARAMETER> getStatusUCTT();
	// lay danh sach severity cho chuc nang chart
	List<SYS_PARAMETER> getSeverityListChart();

	// Quản lý điện năng: Lấy danh sách loại file upload trong module QLDN
	List<SYS_PARAMETER> getTypeOfImportQLDN(String typeFile);

	// LAY TIEU DE CAC BAO CAO CUA MODULE QLDN
	List<SYS_PARAMETER> gettitleReportQLDN(String code, String type);

	// LAY TIEU DE CHO CAC CHUC NANG XUAT BANG THONG KE HOAT DONG MAY NO
	List<SYS_PARAMETER> gettitleQLDNTTNhienLieu(String code, String type);

}