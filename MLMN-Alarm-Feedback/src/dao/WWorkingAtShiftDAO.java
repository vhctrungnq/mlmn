package dao;


import java.util.Date;
import java.util.List;

import vo.SYS_PARAMETER;
import vo.WWorkingAtShift;

public interface WWorkingAtShiftDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table W_WORKING_AT_SHIFT
     *
     * @ibatorgenerated Fri Mar 28 15:37:47 ICT 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table W_WORKING_AT_SHIFT
     *
     * @ibatorgenerated Fri Mar 28 15:37:47 ICT 2014
     */
    void insert(WWorkingAtShift record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table W_WORKING_AT_SHIFT
     *
     * @ibatorgenerated Fri Mar 28 15:37:47 ICT 2014
     */
    void insertSelective(WWorkingAtShift record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table W_WORKING_AT_SHIFT
     *
     * @ibatorgenerated Fri Mar 28 15:37:47 ICT 2014
     */
    WWorkingAtShift selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table W_WORKING_AT_SHIFT
     *
     * @ibatorgenerated Fri Mar 28 15:37:47 ICT 2014
     */
    int updateByPrimaryKeySelective(WWorkingAtShift record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table W_WORKING_AT_SHIFT
     *
     * @ibatorgenerated Fri Mar 28 15:37:47 ICT 2014
     */
    int updateByPrimaryKey(WWorkingAtShift record);
    
    
    List<SYS_PARAMETER> titleForm(String typeFuntion, String typeForm);

	/*List<WWorkingAtShift> getListFilter(String typeFuntion, String ngayTK,
			String ngayTKTo, String userDelivered, String workName);
*/
	List<String> getWorkFilterCondition(String getWorkFilterCondition,
			String type);

	List<WWorkingAtShift> getListFilter(String type, String ngayTK,
			String ngayTKTo, String userDelivered, String workName, String caTK,String region);
/*them moi cong viec lam trong ca*/
	int addWorkInShift(WWorkingAtShift workShift, Date nhanNgayTruc,
			String nhanCaTruc, String nhanUsername, String nhanCaVien,String maPhong,String username);
}