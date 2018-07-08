package dao;

import java.util.List;

import vo.IsoInventory;

public interface IsoInventoryDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    void insert(IsoInventory record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    void insertSelective(IsoInventory record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    IsoInventory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    int updateByPrimaryKeySelective(IsoInventory record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY
     *
     * @ibatorgenerated Thu Oct 31 16:53:09 ICT 2013
     */
    int updateByPrimaryKey(IsoInventory record);

	int updateByMutiKey(IsoInventory record);

	List<IsoInventory> getIsoEquipmentFilter(String deptCode, String team, String subTeam, String province, String district, String neType, String ne, String neGroup, 
			String locationName, String vendor,
			String productCode, String productName, String seriNo, String inputStatus, String status, String location,
			Integer startRecord, Integer endRecord,
			String sortfield, String sortorder, String strWhere);

	int countIsoEquipmentFilter(String deptCode, String team, String subTeam, String province, String district, String neType, String ne, String neGroup, 
			String locationName, String vendor,
			String productCode, String productName, String seriNo, String inputStatus, String status, String location, String strWhere);

	int insertAndResult(IsoInventory record);

	/*List<IsoInventory> getIsoEquipmentSyn(String deptCode, String provinceCode,
			String neType);*/

	List<IsoInventory> getInventoryTrackNewFilter(String startDate,
			String endDate, String oldNe, String ne, String productCode, String productName, String locationName, String seriNo, String status,
			Integer startRecord, Integer endRecord, String sortfield,
			String sortorder, String strWhere);

	int countInventoryTrackFilter(String startDate, String endDate, String oldNe, String ne, 
			String productCode, String productName, String locationName, String seriNo, String status, String strWhere);

	List<IsoInventory> getUpdateIsoInventory(String productCode,
			String seriNo, String id);

	List<IsoInventory> getReportCardDuPhong(String deptCode, String team, String subTeam, String province, String district,
			String productName,
			String productCode, String neType,
			Integer startRecord, Integer endRecord, String sortfield,
			String sortorder, String strWhere);
	int countReportCardDuPhong(String deptCode, String team, String subTeam, String province, String district,
			String productName,
			String productCode, String neType, String strWhere);
	
	
	
	List<IsoInventory> getIsoInventoryHome(String startDate, String endDate);

	List<IsoInventory> getIsoInventoryHomeVendor(String startDate,
			String endDate);

	List<IsoInventory> getReportGeneralList(String deptCode, String team, String subTeam, String province, String district,
			String locationName, String neType, String location,
			Integer startRecord, Integer endRecord, String sortfield, String sortorder, String strWhere);

	int countReportGeneralList(String deptCode, String team, String subTeam, String province, String district,
    		String locationName, String neType, String location, String strWhere);

	List<IsoInventory> getLocationNameList();

	List<IsoInventory> getTeamList();
	List<IsoInventory> getLocationList();
	List<IsoInventory> getSubTeamList();

	List<IsoInventory> getNeGroupList();

	int insertInfoForIsoInventory();

	List<IsoInventory> getIsoInventoryTemp(String status, String user);

	int getDataForIsoInventory(String status, String user);

	int deleteDataIsoInventoryTemp(String user);

	List<IsoInventory> getIsoEquipmentSyn(String deptCode, String team,
			String subTeam, String neType);

	List<IsoInventory> getReportToOptima(String productName,
			String productCode, String neType, String vendor);
}