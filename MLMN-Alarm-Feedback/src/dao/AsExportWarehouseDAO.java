package dao;

import java.util.List;

import vo.AsExportWarehouse;
import vo.AsImportWarehouse;
import vo.SYS_PARAMETER;
import vo.alarm.utils.AsExportFilter;

public interface AsExportWarehouseDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_EXPORT_WAREHOUSE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_EXPORT_WAREHOUSE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    void insert(AsExportWarehouse record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_EXPORT_WAREHOUSE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    void insertSelective(AsExportWarehouse record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_EXPORT_WAREHOUSE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    AsExportWarehouse selectByPrimaryKey(String id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_EXPORT_WAREHOUSE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    int updateByPrimaryKeySelective(AsExportWarehouse record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_EXPORT_WAREHOUSE
     *
     * @ibatorgenerated Tue Apr 23 17:22:40 ICT 2013
     */
    int updateByPrimaryKey(AsExportWarehouse record);
    
    List<AsExportWarehouse> inPhieu(String sdate, String edate,
    		String productCode, String assetsName,String serialNo,String vendor,
    		String boPhanSd,String donViSd,String sophieu,String usersEx);
			
    List<AsExportWarehouse> getAsExportWarehouse(AsExportFilter filter, int order, String column);  
    List<String> getProductCode();//Lay tu nhap kho tai san  
    List<String> getMataisan(String term);// Lay ma danh muc tai san tu nhap kho tai san   
    AsImportWarehouse getAllByProCode(String productCode);// Lay product_code, product_name, unit
    List<AsImportWarehouse> getSeriNoByProCode(String productCode);// Lay seria_no theo product_code
    AsImportWarehouse getAmountByProCode(String productCode,String importDate);//Tong so luong tai san nhap kho
    AsExportWarehouse getAmountExByProCode(String productCode, String serial);//Tong so luong tai san xuat kho va Tong so luong tai san tra lai
    List<SYS_PARAMETER> titleForm(String typeForm);
}