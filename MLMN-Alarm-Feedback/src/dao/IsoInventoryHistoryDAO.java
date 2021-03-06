package dao;

import java.util.List;

import vo.IsoInventoryHistory;

public interface IsoInventoryHistoryDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    void insert(IsoInventoryHistory record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    void insertSelective(IsoInventoryHistory record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    IsoInventoryHistory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    int updateByPrimaryKeySelective(IsoInventoryHistory record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ISO_INVENTORY_HISTORY
     *
     * @ibatorgenerated Thu Oct 31 17:45:27 ICT 2013
     */
    int updateByPrimaryKey(IsoInventoryHistory record);

	List<IsoInventoryHistory> getInventoryTrackHistoryFilter(String startDate,
			String endDate, String oldNe, String ne, String productCode, String productName, String locationName, String seriNo, String status,
			Integer startRecord, Integer endRecord, String sortfield,
			String sortorder, String strWhere);
}