package dao;

import java.util.List;

import vo.VAssetsImportWarehouse;

public interface VAssetsImportWarehouseDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_ASSETS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed May 22 16:21:26 ICT 2013
     */
    void insert(VAssetsImportWarehouse record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_ASSETS_IMPORT_WAREHOUSE
     *
     * @ibatorgenerated Wed May 22 16:21:26 ICT 2013
     */
    void insertSelective(VAssetsImportWarehouse record);
    
    void insert();
    
    List<VAssetsImportWarehouse> getDataById(String id);
    
    List<VAssetsImportWarehouse> getList(String asTypesId, String productCode, String colunm, int order);
}