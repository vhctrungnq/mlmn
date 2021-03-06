package dao;

import java.util.List;

import vo.QldnThongTinMayNo;

public interface QldnThongTinMayNoDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_THONG_TIN_MAY_NO
     *
     * @ibatorgenerated Mon Jul 03 14:28:31 ICT 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_THONG_TIN_MAY_NO
     *
     * @ibatorgenerated Mon Jul 03 14:28:31 ICT 2017
     */
    void insert(QldnThongTinMayNo record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_THONG_TIN_MAY_NO
     *
     * @ibatorgenerated Mon Jul 03 14:28:31 ICT 2017
     */
    void insertSelective(QldnThongTinMayNo record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_THONG_TIN_MAY_NO
     *
     * @ibatorgenerated Mon Jul 03 14:28:31 ICT 2017
     */
    QldnThongTinMayNo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_THONG_TIN_MAY_NO
     *
     * @ibatorgenerated Mon Jul 03 14:28:31 ICT 2017
     */
    int updateByPrimaryKeySelective(QldnThongTinMayNo record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_THONG_TIN_MAY_NO
     *
     * @ibatorgenerated Mon Jul 03 14:28:31 ICT 2017
     */
    int updateByPrimaryKey(QldnThongTinMayNo record);

    // kiem tra thong tin may no da ton tai chua
	QldnThongTinMayNo checkThongTinMayNo(String idTram, String idDtxd);

	List<QldnThongTinMayNo> filter(String idTram, String idDtxd, String tinh, String toVt, String nhom,
			String loaiThanhToan, String tenXhhVtt, String hieuMay, String congSuat, String dinhMuc, String ats);
	
	int getMaxId();

	List<QldnThongTinMayNo> getThongKeMayNo(String type);
	
	List<QldnThongTinMayNo> getThongTinMayNoList(String idTram, String idDtxd, String loaiThanhToan, String tenTram,
			String tinh, String to, String nhom, String tenXhhVtt, String hieuMayNo, String congSuat,
			String dinhMuc, String loaiNhienLieu, String ats);
}