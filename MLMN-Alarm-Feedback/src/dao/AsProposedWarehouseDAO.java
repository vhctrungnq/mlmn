package dao;

import java.util.List;

import vo.AsProposedWarehouse;
/*
Nguoi tao: Anhnt
Ngay tao: 11/06/2013
Muc dich: Khai bao phuong thuc delete, insert, update, select
*/
public interface AsProposedWarehouseDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    void insert(AsProposedWarehouse record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    void insertSelective(AsProposedWarehouse record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    AsProposedWarehouse selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    int updateByPrimaryKeySelective(AsProposedWarehouse record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AS_PROPOSED_WAREHOUSE
     *
     * @ibatorgenerated Tue Jun 11 12:33:07 ICT 2013
     */
    int updateByPrimaryKey(AsProposedWarehouse record); 
    
    List<AsProposedWarehouse> proposedWarehouseList(String user, String day, String status, String column, int order);
    // update thong tin vao bang AS_PROPOSED_WAREHOUSE
    int update(AsProposedWarehouse record);

	List<AsProposedWarehouse> getXetDuyetFilter(String boPhanSd,
			String donViSd, String nguoiSd, String ngayXuatFrom,
			String ngayXuatTo, String createdBy, String column, String order);

	int getCountCreatedBy(String boPhanSd, String donViSd, String nguoiSd,
			String ngayXuatFrom, String ngayXuatTo, String createdBy);

	int getCountAmount(String id, String amount);

	int getAmountTwoId(String id1, String id2);
}