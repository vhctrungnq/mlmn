package dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.QLDNReportTTTram;
import vo.QldnTramTTDien;

public class QldnTramTTDienDAOImpl extends SqlMapClientDaoSupport implements QldnTramTTDienDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_DIEN
     *
     * @ibatorgenerated Tue Jul 04 16:52:56 ICT 2017
     */
    public QldnTramTTDienDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_DIEN
     *
     * @ibatorgenerated Tue Jul 04 16:52:56 ICT 2017
     */
    public int deleteByPrimaryKey(Integer id) {
        QldnTramTTDien key = new QldnTramTTDien();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("QLDN_TRAM_TT_DIEN.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_DIEN
     *
     * @ibatorgenerated Tue Jul 04 16:52:56 ICT 2017
     */
    public void insert(QldnTramTTDien record) {
        getSqlMapClientTemplate().insert("QLDN_TRAM_TT_DIEN.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_DIEN
     *
     * @ibatorgenerated Tue Jul 04 16:52:56 ICT 2017
     */
    public void insertSelective(QldnTramTTDien record) {
        getSqlMapClientTemplate().insert("QLDN_TRAM_TT_DIEN.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_DIEN
     *
     * @ibatorgenerated Tue Jul 04 16:52:56 ICT 2017
     */
    public QldnTramTTDien selectByPrimaryKey(Integer id) {
        QldnTramTTDien key = new QldnTramTTDien();
        key.setId(id);
        QldnTramTTDien record = (QldnTramTTDien) getSqlMapClientTemplate().queryForObject("QLDN_TRAM_TT_DIEN.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_DIEN
     *
     * @ibatorgenerated Tue Jul 04 16:52:56 ICT 2017
     */
    public int updateByPrimaryKeySelective(QldnTramTTDien record) {
        int rows = getSqlMapClientTemplate().update("QLDN_TRAM_TT_DIEN.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table QLDN_TRAM_TT_DIEN
     *
     * @ibatorgenerated Tue Jul 04 16:52:56 ICT 2017
     */
    public int updateByPrimaryKey(QldnTramTTDien record) {
        int rows = getSqlMapClientTemplate().update("QLDN_TRAM_TT_DIEN.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

	@Override
	public QldnTramTTDien selectByKey(String makh, Integer thangQuyTt,
			Integer namTt) {
		
		QldnTramTTDien key = new QldnTramTTDien();
        key.setMakh(makh);
        key.setThangQuyTt(thangQuyTt);
        key.setNamTt(namTt);
        
        QldnTramTTDien record = (QldnTramTTDien) getSqlMapClientTemplate().queryForObject("QLDN_TRAM_TT_DIEN.selectByKey", key);
        return record;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<QldnTramTTDien> getTramTTDienList(String ngayNhapF,
			String ngayNhapT, String idTram, String loaitram, String tinh,
			String huyen, String daiVT, String tgttTq, String thangQuyTt,
			String namTt, String loaiDG, String status,String ngayTttw,String type,String username) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("P_NGAY_NHAP_FROM", ngayNhapF);
		map.put("P_NGAY_NHAP_TO", ngayNhapT);
		map.put("P_ID_TRAM", idTram);
		map.put("P_LOAITRAM", loaitram);
		map.put("P_TINH", tinh);
		map.put("P_HUYEN", huyen);
		map.put("P_DAIVT", daiVT);
		map.put("P_TGTT_TQ", tgttTq);
		map.put("P_THANG_QUY_TT", thangQuyTt);
		map.put("P_NAM_TT", namTt);
		map.put("P_DG_LOAI", loaiDG);
		map.put("P_STATUS", status);
		map.put("P_NGAY_TTTW", ngayTttw);
		map.put("P_TYPE", type);
		map.put("P_USERNAME", username);
		map.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getTramTTDienList", map);
	}
	
    @SuppressWarnings("unchecked")
	@Override
	public QldnTramTTDien getTramTTDien(String idTram,String tgttTqTK, String thangQuyTtTK, String namTtTK  ) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("P_ID_TRAM", idTram);
		map.put("P_TGTTTQ", tgttTqTK);
		map.put("P_Thangquytt", thangQuyTtTK);      
		map.put("P_NAMTT", namTtTK);    
		map.put("p_data", null);
		List<QldnTramTTDien> dataList=  getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getTramTTDien", map);

		QldnTramTTDien record = new QldnTramTTDien();
		if (dataList.size()>0)
		{
			record = dataList.get(0);
		}
	    return record;
	}
    
    /*Tong hop so luong tram thanh toan dien theo tung loai chi tieu*/
    @SuppressWarnings("unchecked")
	@Override
	public List<QLDNReportTTTram> getReportTTTram(String type) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("P_TYPE", type);  
		map.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getReportTTTram", map);
	}
    /*Thong ke thong tin tram theo tinh, nha cung cap.ANHCTV:15/09/2017*/
    @SuppressWarnings("unchecked")
	@Override
	public List<QLDNReportTTTram> getReportTTTramTinhNhaCC() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getReportTTTramTinhNhaCC", map);
	}
    
    /*bao cao số lượng trạm chưa thanh toán tiền điện  theo tháng
	Thống kê chi phí điện theo tháng
	Theo dõi thanh toán điện theo tháng
	@type: CHUA_TT,DA_TT,CHI_PHI_TT,CHENH_LECH_TRAM*/
    @SuppressWarnings("unchecked")
	@Override
	public List<String> getReportThanhToanDien(String type, String region, String province, String tgttTq,
			 String thangQuyTtF, String namTtF, String thangQuyTtTo, String namTtTo) {
		Map<String, String> map = new HashMap<String, String>();
    	map.put("P_TYPE", type);  
    	map.put("P_REGION", region);  
    	map.put("P_PROVINCE", province);  
    	map.put("P_TGTTTQ", tgttTq);  
    	map.put("P_THANGQUY_F", thangQuyTtF);  
    	map.put("P_NAM_F", namTtF);  
    	map.put("P_THANGQUY_T", thangQuyTtTo);  
    	map.put("P_NAM_T", namTtTo);  
		map.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getReportThanhToanDien", map);
	}

    /*Thống kê từng tháng số tiền thanh toán so với tháng trước
	Thống kê từng tháng điện năng tiêu thụ so với định mức điện
	@type: THANG,DINH_MUC*/
    @SuppressWarnings("unchecked")
	@Override
	public List<QldnTramTTDien> getChenhLechDienList(String region, String province, String tgttTq, String thangQuy,
			String nam, String tyLe, String type) {
    	
				Map<String, String> map = new HashMap<String, String>();
		    	map.put("P_REGION", region);  
		    	map.put("P_PROVINCE", province);  
		    	map.put("P_TGTTTQ", tgttTq);  
		    	map.put("P_THANGQUY", thangQuy);  
		    	map.put("P_NAM", nam);  
		    	map.put("P_TYLE", tyLe);  
		    	map.put("P_TYPE", type);  
				map.put("p_data", null);
				return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getChenhLechDienList", map);
	}
    
    /*thong tin chi tiet chenh lech dien cua tram trong tung tinh @type: THANG,DINH_MUC*/
    @SuppressWarnings("unchecked")
	@Override
	public List<QldnTramTTDien> getChenhLechDienDetail(String region, String province, String tgttTq, String thangQuy,
			String nam, String tyLe, String type) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("P_REGION", region);  
    	map.put("P_PROVINCE", province);  
    	map.put("P_TGTTTQ", tgttTq);  
    	map.put("P_THANGQUY", thangQuy);  
    	map.put("P_NAM", nam);  
    	map.put("P_TYLE", tyLe);  
    	map.put("P_TYPE", type);  
		map.put("p_data", null);
		if (type.equalsIgnoreCase("THANG")) {
			return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getChenhLechDienThangDetail", map);
		}
		else
		{
			return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getChenhLechDienDinhMucDetail", map);
		}
	}
    
  //DoaiNH lay danh sach tong hop thanh toan dien theo thang, ngay 04-10-2017
  	@SuppressWarnings("unchecked")
  	@Override
  	public List<QldnTramTTDien> getTongHopTTDienList(String region,String tinh, String chuKyTT,String thangQuyTt,String nam,String type) {
  	    // TODO Auto-generated method stub
  	    Map<String, String> parms = new HashMap<String, String>();
  	    parms.put("P_REGION", region);
  	    parms.put("P_PROVINCE", tinh);
  	    parms.put("P_TGTTTQ", chuKyTT);
  	    parms.put("P_THANGQUY", thangQuyTt);
  	    parms.put("P_NAM", nam);
  	    parms.put("P_TYPE", type);
  	    parms.put("P_Data", null);
  	    return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getTomgHopTTDienList", parms);
  	}
  	//DoaiNH lay danh sach tong hop thanh toan dien tạm ứng theo tháng, ngay 05-10-2017
  	@SuppressWarnings("unchecked")
  	@Override
  	public List<QldnTramTTDien> getTongHopTTDienTWList(String region, String tinh, String chuKyTT,
  		String thangQuyTt, String nam, String type) {
  	    // TODO Auto-generated method stub
  	    Map<String, String> parms = new HashMap<String, String>();
  	    parms.put("P_REGION", region);
  	    parms.put("P_PROVINCE", tinh);
  	    parms.put("P_TGTTTQ", chuKyTT);
  	    parms.put("P_THANGQUY", thangQuyTt);
  	    parms.put("P_NAM", nam);
  	    parms.put("P_TYPE", type);
  	    parms.put("P_Data", null);
  	    return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getTomgHopTTDienTWList", parms);
  	}
  	
  	 @SuppressWarnings("unchecked")
 	@Override
 	public List<QldnTramTTDien> getTkTtDnTheoMucListAll(String month, String year, String tinh, Integer dinhmuc1,Integer dinhmuc2, String type ) {
 		Map<String, Object> parms = new HashMap<String, Object>();
 		
 		parms.put("P_TYPE", type);
 		parms.put("P_MONTH", month);	
 		parms.put("P_YEAR", year);	
 		parms.put("P_TINH", tinh);
 		parms.put("P_DM1", dinhmuc1);
 		parms.put("P_DM2", dinhmuc2);
 		parms.put("P_DATA", null);
 			
 		return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getTkTtDnTheoMucListAll", parms);
  	 }
  	 @SuppressWarnings("unchecked")
	@Override
	public QldnTramTTDien checkExit(String makh, String tuNgay, String denNgay) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("P_MAKH", makh);
		map.put("P_TU_NGAY", tuNgay);
		map.put("P_DEN_NGAY", denNgay);      
		map.put("p_data", null);
		List<QldnTramTTDien> dataList=  getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.checkExit", map);

		QldnTramTTDien record = new QldnTramTTDien();
		if (dataList.size()>0)
		{
			record = dataList.get(0);
		}
	    return record;
	}
  // thong ke chech lech dien nang tieu thu cua tram giua cac thang
	 @SuppressWarnings("unchecked")
	@Override
	public List<String> getReportChenhLechTram(String daiVT,  String tgttTq, String thangQuyTtF,
			String namTtF, String thangQuyTtTo, String namTtTo) {
		Map<String, String> map = new HashMap<String, String>();
    	map.put("P_DAIVT", daiVT);  
    	map.put("P_TGTTTQ", tgttTq);  
    	map.put("P_THANGQUY_F", thangQuyTtF);  
    	map.put("P_NAM_F", namTtF);  
    	map.put("P_THANGQUY_T", thangQuyTtTo);  
    	map.put("P_NAM_T", namTtTo);  
		map.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getReportChenhLechTram", map);
	}

	@Override
	public int updateDVTXuLy(String tram, String tgttTq, String thangQuyTt, String namTt, String nguyenNhan, String kqToKt) {
		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_ID_TRAM", tram);
   		parms.put("P_TGTTTQ", tgttTq);
   		parms.put("P_THANGQUY", thangQuyTt);
   		parms.put("P_NAM", namTt);
       	parms.put("P_NGUYEN_NHAN", nguyenNhan);
   		parms.put("P_KQ_TO_KT", kqToKt);
           int rows = getSqlMapClientTemplate().update("QLDN_TRAM_TT_DIEN.updateDVTXuLy", parms);
           return rows;
	}
	
	 @SuppressWarnings("unchecked")
		@Override
		public List<QldnTramTTDien> getBaoCaoKetQuaKtListAll(String month, String year,String nhom,String nguong) {
			Map<String, Object> map = new HashMap<String, Object>();
	    	map.put("P_MONTH", month);  
	    	map.put("P_YEAR", year);  
	    	map.put("P_NHOM", nhom);  
	    	map.put("P_NGUONG", nguong);   
			map.put("p_data", null);
			return getSqlMapClientTemplate().queryForList("QLDN_TRAM_TT_DIEN.getBaoCaoKetQuaKtListAll", map);
		}


}