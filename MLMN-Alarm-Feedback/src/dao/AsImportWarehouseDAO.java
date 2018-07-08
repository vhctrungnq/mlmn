package dao;

import java.util.List;

import vo.AsExportWarehouse;
import vo.AsImportWarehouse;
import vo.AssetsManagements;

public interface AsImportWarehouseDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    void insert(AsImportWarehouse record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    void insertSelective(AsImportWarehouse record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    AsImportWarehouse selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    int updateByPrimaryKeySelective(AsImportWarehouse record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed Apr 24 14:59:34 ICT 2013
     */
    int updateByPrimaryKey(AsImportWarehouse record);
    
    int updateByProductAndSerial(AsImportWarehouse record);

	List<AsImportWarehouse> getAsImportWarehouseFilter(String asTypesId, String productCode, String serialNo, String productName, 
			String importDateFrom, String importDateTo, String ject, String vendor, String column, String order);

	List<AsImportWarehouse> testAsImportWarehouse(String productCode,
			String serialNo);
	
	List<AsImportWarehouse> getNSX(String productCode, String serialNo);
	
	List<AsImportWarehouse> updateByProCodeSerNo(AsImportWarehouse record);

	AssetsManagements getAsManagementByProCode(String productCode);

	List<AsImportWarehouse> updateProName(String productCode,
			String productName, String unit, String modifiedBy);

	List<AsExportWarehouse> getAsExtByCodeSerial(String productCode,
			String serial);

	List<AsImportWarehouse> getAsImpByCodeSerial(String productCode,
			String serial, String id);

	List<AsImportWarehouse> listSeriByProCode(String productCode);

	int getAmountDontUse(String id);
}