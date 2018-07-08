package dao;

import java.util.List;

import vo.QldnDonViThuHuong;
import vo.QldnPhanBoChiPhi;

public interface QldnDonViThuHuongDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_DON_VI_THU_HUONG
     *
     * @ibatorgenerated Mon Jul 03 14:27:39 ICT 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_DON_VI_THU_HUONG
     *
     * @ibatorgenerated Mon Jul 03 14:27:39 ICT 2017
     */
    void insert(QldnDonViThuHuong record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_DON_VI_THU_HUONG
     *
     * @ibatorgenerated Mon Jul 03 14:27:39 ICT 2017
     */
    void insertSelective(QldnDonViThuHuong record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_DON_VI_THU_HUONG
     *
     * @ibatorgenerated Mon Jul 03 14:27:39 ICT 2017
     */
    QldnDonViThuHuong selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_DON_VI_THU_HUONG
     *
     * @ibatorgenerated Mon Jul 03 14:27:39 ICT 2017
     */
    int updateByPrimaryKeySelective(QldnDonViThuHuong record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_DON_VI_THU_HUONG
     *
     * @ibatorgenerated Mon Jul 03 14:27:39 ICT 2017
     */
    int updateByPrimaryKey(QldnDonViThuHuong record);

    // kiem tra don vi thu huong da ton tai chua
	QldnDonViThuHuong checkDVThuHuong(String tenDv);

	// lay danh sach don vi thu huong
	List<QldnDonViThuHuong> getDonViThuHuong();
	

	List<QldnDonViThuHuong> getList(String tenDv, String maSoThue, String soTaiKhoan, String tenNganHang,
		String ghiChu);


}