package dao;

import java.util.Date;
import java.util.List;

import vo.QldnTramTTDien;
import vo.QldnTramTTNhienLieu;

public interface QldnTramTTNhienLieuDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_NHIEN_LIEU
     *
     * @ibatorgenerated Mon Jul 03 14:29:15 ICT 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_NHIEN_LIEU
     *
     * @ibatorgenerated Mon Jul 03 14:29:15 ICT 2017
     */
    void insert(QldnTramTTNhienLieu record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_NHIEN_LIEU
     *
     * @ibatorgenerated Mon Jul 03 14:29:15 ICT 2017
     */
    void insertSelective(QldnTramTTNhienLieu record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_NHIEN_LIEU
     *
     * @ibatorgenerated Mon Jul 03 14:29:15 ICT 2017
     */
    QldnTramTTNhienLieu selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_NHIEN_LIEU
     *
     * @ibatorgenerated Mon Jul 03 14:29:15 ICT 2017
     */
    int updateByPrimaryKeySelective(QldnTramTTNhienLieu record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_NHIEN_LIEU
     *
     * @ibatorgenerated Mon Jul 03 14:29:15 ICT 2017
     */
    int updateByPrimaryKey(QldnTramTTNhienLieu record);
// kiem tra tram da thanh toan nhien lieu chua
	QldnTramTTNhienLieu selectByKey(String idTram, Date tgBatDau,Date ngayChayMf);

	List<QldnTramTTNhienLieu> getData(String loaiThanhToan, String sdate, String edate);
	int getMaxId();

	List<QldnTramTTNhienLieu> getTkTtNhienLieuTinhListAll(String sdate, String edate, String tinh );
	List<QldnTramTTNhienLieu> getTkTtNhienLieuNhieuTinhListAll(String sdate, String edate, String tinh );
	List<QldnTramTTNhienLieu> getTkTheoDonViXHHVTTListAll(String sdate, String edate, String tinh );
	List<QldnTramTTNhienLieu> getTkTtTheoMucListAll(String month, String year, String tinh, Integer dinhmuc1,Integer dinhmuc2, String type );

	/*xuat bang ke hoat dong chay may no. AnhCTV. 15/10/2107*/
	List<QldnTramTTNhienLieu> getTramTTNhienLieuList(String ngayChayMfF, String ngayChayMfT, String idTram,
			String loaiNhienLieu, String tinh, String tenXhhVtt, String type,String username);
	
}