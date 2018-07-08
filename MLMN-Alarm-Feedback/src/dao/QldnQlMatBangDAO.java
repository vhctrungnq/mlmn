package dao;

import java.util.List;

import vo.QldnPhanBoChiPhi;
import vo.QldnQlMatBang;

public interface QldnQlMatBangDAO {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_QL_MAT_BANG
     *
     * @ibatorgenerated Mon Jul 03 14:28:15 ICT 2017
     */
    void insert(QldnQlMatBang record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_QL_MAT_BANG
     *
     * @ibatorgenerated Mon Jul 03 14:28:15 ICT 2017
     */
    void insertSelective(QldnQlMatBang record);

	QldnQlMatBang selectByKey(String maTram, String idHopdong);

	int updateByPrimaryKey(QldnQlMatBang record);
	
	public int deleteByPrimaryKey(Integer id);
	
	List<QldnQlMatBang> gettkSlTramQLVaTTListAll(Integer year, String tinh);
	List<QldnQlMatBang> gettkTtMatBangTheoDvXHHListAll(Integer year, String tinh);
	
	List<QldnQlMatBang> gettkTtMatBangTheoDvKhacListAll(Integer year, String tinh);
	
	List<QldnQlMatBang> gettkChiTietTheoDvXHHListAll(Integer year, String tinh);
	
	List<QldnQlMatBang> gettkChiTietTheoDvVNPTListAll(Integer year, String tinh);
	
	List<QldnQlMatBang> gettkChiPhiXHHTtListAll(Integer year, String tinh);
	
	List<QldnQlMatBang> getTTMatBangList(String maTram, String idHopdong, String sohd, String tinh, String huyen,
			String htSohuu, String dvSohuu, String chuthehd, String dongiathangNovat,
			String hdNgayketthuc, String ngaytinhtien);

		QldnQlMatBang selectByPrimaryKey(int id);
}