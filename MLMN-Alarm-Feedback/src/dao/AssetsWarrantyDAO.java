package dao;

import java.util.List;

import vo.AssetsManagements;
import vo.AssetsWarranty;
import vo.MDepartment;
import vo.SYS_PARAMETER;

public interface AssetsWarrantyDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    void insert(AssetsWarranty record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    void insertSelective(AssetsWarranty record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    AssetsWarranty selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    int updateByPrimaryKeySelective(AssetsWarranty record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ASSETS_WARRANTY
     *
     * @ibatorgenerated Mon Apr 22 10:35:56 ICT 2013
     */
    int updateByPrimaryKey(AssetsWarranty record);

	List<AssetsWarranty> getAssetsWarrantyFilter(String asTypesId, String productCode, String serialNo, String sentDateFrom, String sentDateTo, String deliveryNo, String csr, String vendor, String column, String order);

	List<SYS_PARAMETER> getDepartmentList();

	List<AssetsManagements> getAssetsManagementsList(String asTypesId);

	List<AssetsWarranty> testAssetsWarranty(String productCode, String serialNo);

	void updateByProCodeSerNo(AssetsWarranty record);

	List<MDepartment> getDepartmentDetailList();

	List<SYS_PARAMETER> getRejectAssetsWarranty();
	
	
}