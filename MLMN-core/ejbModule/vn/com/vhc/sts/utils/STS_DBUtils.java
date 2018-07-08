package vn.com.vhc.sts.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.cp.SmppQueue;
import vn.com.vhc.sts.cp.VRpDyBsc;
import vn.com.vhc.sts.cp.VRpDyCell;
import vn.com.vhc.sts.cp.VRpDyDistrict;
import vn.com.vhc.sts.cp.VRpDyProvince;
import vn.com.vhc.sts.cp.VRpHrBsc;
import vn.com.vhc.sts.cp.VRpHrCell;
import vn.com.vhc.sts.cp.VRpHrDistrict;
import vn.com.vhc.sts.cp.VRpHrProvince;

public class STS_DBUtils {

	public static Connection getConnect() {

		Connection conn = null;

		try {
			InitialContext context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:/remoteDS");
			conn = ds.getConnection();

		} catch (NamingException e) {
		} catch (SQLException e) {
		}

		return conn;
	}

	public static List<SmppQueue> getSmppQueue() {
		Connection conn = null;
		List<SmppQueue> smsList = new ArrayList<SmppQueue>();
		try {
			conn = getConnect();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from SMPP_QUEUE where TINHTRANG = 'Y'");

			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				SmppQueue smppQueue = new SmppQueue();
				smppQueue.setIsdn(rs.getString("ISDN"));
				smppQueue.setCode(rs.getString("CODE"));
				smppQueue.setIssueDatetime(rs.getDate("ISSUE_DATETIME"));
				smppQueue.setServiceType(rs.getString("SERVICE_TYPE"));
				smppQueue.setReplyMessage(rs.getString("REPLY_MESSAGE"));
				smppQueue.setIsdnActive(rs.getString("ISDN_ACTIVE"));
				smppQueue.setReceiveMessage(rs.getString("RECEIVE_MESSAGE"));
				smppQueue.setError(rs.getString("ERROR"));
				smppQueue.setDxl(rs.getInt("DXL"));
				smppQueue.setBlConfirmed(rs.getInt("BL_CONFIRMED"));
				smppQueue.setTinhtrang(rs.getString("TINHTRANG"));
				smppQueue.setNgayKk(rs.getDate("NGAY_KK"));

				smsList.add(smppQueue);
			}
			preStmt.close();

		} catch (SQLException e) {
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
		}

		return smsList;
	}

	public static void updateSmppQueueStatus(SmppQueue smppQueue) {
		// TODO Auto-generated method stub

	}	

	public static void insertSms(String isdn, String content) {
		Connection conn = null;
		try {
			conn = getConnect();
			PreparedStatement preStmt = conn
					.prepareStatement("INSERT INTO HLR_SEND_DIRECT(ISDN, MESSAGE) values (?,?)");
			preStmt.setString(1, isdn);
			preStmt.setString(2, content);
			preStmt.execute();
			preStmt.close();

		} catch (SQLException e) {
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public static VRpDyBsc getDyBscRp(String bscid, Date day) {

		Connection conn = null;
		VRpDyBsc vRpDyBsc = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_DY_BSC where bscid = ? and day = ?");
			preStmt.setString(1, bscid);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));

			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				vRpDyBsc = new VRpDyBsc();
				vRpDyBsc.setDay(rs.getDate("DAY"));
				vRpDyBsc.setRegion(rs.getString("REGION"));
				vRpDyBsc.setBscid(rs.getString("BSCID"));
				vRpDyBsc.settTraf(rs.getFloat("T_TRAF"));
				vRpDyBsc.setHalfrate(rs.getFloat("HALFRATE"));
				vRpDyBsc.settDrpr(rs.getFloat("T_DRPR"));
				vRpDyBsc.settBlkr(rs.getFloat("T_BLKR"));
				vRpDyBsc.setCssr(rs.getFloat("CSSR"));
				vRpDyBsc.setsDrpr(rs.getFloat("S_DRPR"));
				vRpDyBsc.setsBlkr(rs.getFloat("S_BLKR"));
			}
			preStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpDyBsc;
	}

	public static VRpHrBsc getHrBscRp(String bscid, Date day, int hour) {

		Connection conn = null;
		VRpHrBsc vRpHrBsc = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_HR_BSC where bscid = ? and day = ? and hour = ?");
			preStmt.setString(1, bscid);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));
			preStmt.setInt(3, hour);
			
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				vRpHrBsc = new VRpHrBsc();
				vRpHrBsc.setDay(rs.getDate("DAY"));
				vRpHrBsc.setHour(rs.getInt("HOUR"));
				vRpHrBsc.setRegion(rs.getString("REGION"));
				vRpHrBsc.setBscid(rs.getString("BSCID"));
				vRpHrBsc.settTraf(rs.getFloat("T_TRAF"));
				vRpHrBsc.setHalfrate(rs.getFloat("HALFRATE"));
				vRpHrBsc.settDrpr(rs.getFloat("T_DRPR"));
				vRpHrBsc.settBlkr(rs.getFloat("T_BLKR"));
				vRpHrBsc.setCssr(rs.getFloat("CSSR"));
				vRpHrBsc.setsDrpr(rs.getFloat("S_DRPR"));
				vRpHrBsc.setsBlkr(rs.getFloat("S_BLKR"));
			}
			preStmt.close();

		} catch (SQLException e) {
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpHrBsc;
	}
	
	public static VRpDyCell getDyCellRp(String cellid, Date day) {

		Connection conn = null;
		VRpDyCell vRpDyCell = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_DY_CELL where cellid = ? and day = ?");
			preStmt.setString(1, cellid);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));

			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				vRpDyCell = new VRpDyCell();
				vRpDyCell.setDay(rs.getDate("DAY"));
				vRpDyCell.setRegion(rs.getString("REGION"));
				vRpDyCell.setBscid(rs.getString("BSCID"));
				vRpDyCell.settDef(rs.getInt("T_DEF"));
				vRpDyCell.settAvail(rs.getBigDecimal("T_AVAIL"));
				vRpDyCell.settTraf(rs.getFloat("T_TRAF"));
				vRpDyCell.setHaftratePercent(rs.getFloat("HAFTRATE_PERCENT"));
				vRpDyCell.settDrpr(rs.getFloat("T_DRPR"));
				vRpDyCell.settBlkr(rs.getFloat("T_BLKR"));
				vRpDyCell.setCssr(rs.getFloat("CSSR"));
				vRpDyCell.setsDrpr(rs.getFloat("S_DRPR"));
				vRpDyCell.setsBlkr(rs.getFloat("S_BLKR"));
			}
			preStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpDyCell;
	}

	public static VRpHrCell getHrCellRp(String cellid, Date day, int hour) {

		Connection conn = null;
		VRpHrCell vRpHrCell = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_HR_CELL where cellid = ? and day = ? and hour = ?");
			preStmt.setString(1, cellid);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));
			preStmt.setInt(3, hour);
			
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				vRpHrCell = new VRpHrCell();
				vRpHrCell.setDay(rs.getDate("DAY"));
				vRpHrCell.setHour(rs.getInt("HOUR"));
				vRpHrCell.setRegion(rs.getString("REGION"));
				vRpHrCell.setBscid(rs.getString("BSCID"));
				vRpHrCell.settDef(rs.getInt("T_DEF"));
				vRpHrCell.settAvail(rs.getBigDecimal("T_AVAIL"));
				vRpHrCell.settTraf(rs.getFloat("T_TRAF"));
				vRpHrCell.setHaftratePercent(rs.getFloat("HAFTRATE_PERCENT"));
				vRpHrCell.settDrpr(rs.getFloat("T_DRPR"));
				vRpHrCell.settBlkr(rs.getFloat("T_BLKR"));
				vRpHrCell.setCssr(rs.getFloat("CSSR"));
				vRpHrCell.setsDrpr(rs.getFloat("S_DRPR"));
				vRpHrCell.setsBlkr(rs.getFloat("S_BLKR"));
			}
			preStmt.close();

		} catch (SQLException e) {
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpHrCell;
	}
	
	public static VRpDyProvince getDyProvinceRp(String province, Date day) {

		Connection conn = null;
		VRpDyProvince vRpDyProvince = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_DY_PROVINCE where province = ? and day = ?");
			preStmt.setString(1, province);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));

			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				vRpDyProvince = new VRpDyProvince();
				vRpDyProvince.setDay(rs.getDate("DAY"));
				vRpDyProvince.setRegion(rs.getString("REGION"));
				vRpDyProvince.setProvince(rs.getString("Province"));
				vRpDyProvince.settTraf(rs.getFloat("T_TRAF"));
				vRpDyProvince.setHaftratePercent(rs.getFloat("HAFTRATE_PERCENT"));
				vRpDyProvince.settDrpr(rs.getFloat("T_DRPR"));
				vRpDyProvince.settBlkr(rs.getFloat("T_BLKR"));
				vRpDyProvince.setCssr(rs.getFloat("CSSR"));
				vRpDyProvince.setsDrpr(rs.getFloat("S_DRPR"));
				vRpDyProvince.setsBlkr(rs.getFloat("S_BLKR"));
			}
			preStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpDyProvince;
	}

	public static VRpHrProvince getHrProvinceRp(String province, Date day, int hour) {

		Connection conn = null;
		VRpHrProvince vRpHrProvince = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_HR_PROVINCE where province = ? and day = ? and hour = ?");
			preStmt.setString(1, province);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));
			preStmt.setInt(3, hour);
			
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				vRpHrProvince = new VRpHrProvince();
				vRpHrProvince.setDay(rs.getDate("DAY"));
				vRpHrProvince.setHour(rs.getInt("HOUR"));
				vRpHrProvince.setRegion(rs.getString("REGION"));
				vRpHrProvince.setProvince(rs.getString("PROVINCE"));
				vRpHrProvince.settTraf(rs.getFloat("T_TRAF"));
				vRpHrProvince.setHaftratePercent(rs.getFloat("HAFTRATE_PERCENT"));
				vRpHrProvince.settDrpr(rs.getFloat("T_DRPR"));
				vRpHrProvince.settBlkr(rs.getFloat("T_BLKR"));
				vRpHrProvince.setCssr(rs.getFloat("CSSR"));
				vRpHrProvince.setsDrpr(rs.getFloat("S_DRPR"));
				vRpHrProvince.setsBlkr(rs.getFloat("S_BLKR"));
			}
			preStmt.close();

		} catch (SQLException e) {
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpHrProvince;
	}
	
	public static VRpDyDistrict getDyDistrictRp(String district, Date day) {

		Connection conn = null;
		VRpDyDistrict vRpDyDistrict = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_DY_DISTRICT where district = ? and day = ?");
			preStmt.setString(1, district);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));

			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				vRpDyDistrict = new VRpDyDistrict();
				vRpDyDistrict.setDay(rs.getDate("DAY"));
				vRpDyDistrict.setRegion(rs.getString("REGION"));
				vRpDyDistrict.setDistrict(rs.getString("district"));
				vRpDyDistrict.settTraf(rs.getFloat("T_TRAF"));
				vRpDyDistrict.setHaftratePercent(rs.getFloat("HAFTRATE_PERCENT"));
				vRpDyDistrict.settDrpr(rs.getFloat("T_DRPR"));
				vRpDyDistrict.settBlkr(rs.getFloat("T_BLKR"));
				vRpDyDistrict.setCssr(rs.getFloat("CSSR"));
				vRpDyDistrict.setsDrpr(rs.getFloat("S_DRPR"));
				vRpDyDistrict.setsBlkr(rs.getFloat("S_BLKR"));
			}
			preStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpDyDistrict;
	}

	public static VRpHrDistrict getHrDistrictRp(String district, Date day, int hour) {

		Connection conn = null;
		VRpHrDistrict vRpHrDistrict = null;
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_HR_PROVINCE where district = ? and day = ? and hour = ?");
			preStmt.setString(1, district);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));
			preStmt.setInt(3, hour);
			
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				vRpHrDistrict = new VRpHrDistrict();
				vRpHrDistrict.setDay(rs.getDate("DAY"));
				vRpHrDistrict.setHour(rs.getInt("HOUR"));
				vRpHrDistrict.setRegion(rs.getString("REGION"));
				vRpHrDistrict.setDistrict(rs.getString("District"));
				vRpHrDistrict.settTraf(rs.getFloat("T_TRAF"));
				vRpHrDistrict.setHaftratePercent(rs.getFloat("HAFTRATE_PERCENT"));
				vRpHrDistrict.settDrpr(rs.getFloat("T_DRPR"));
				vRpHrDistrict.settBlkr(rs.getFloat("T_BLKR"));
				vRpHrDistrict.setCssr(rs.getFloat("CSSR"));
				vRpHrDistrict.setsDrpr(rs.getFloat("S_DRPR"));
				vRpHrDistrict.setsBlkr(rs.getFloat("S_BLKR"));
			}
			preStmt.close();

		} catch (SQLException e) {
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpHrDistrict;
	}
	
	public static List<VRpDyCell> getDyCellCssr(String bscid, Date day, Float cssr) {

		Connection conn = null;
		List<VRpDyCell> vRpDyCellList = new ArrayList<VRpDyCell>();
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_DY_CELL where bscid = ? and day = ? and CSSR <= ?");
			preStmt.setString(1, bscid);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));
			preStmt.setFloat(3, cssr);
			
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				VRpDyCell vRpDyCell = new VRpDyCell();
				vRpDyCell.setCellid(rs.getString("CELLID"));
			}
			preStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpDyCellList;
	}
	
	public static List<VRpDyCell> getDyCellTdrop(String bscid, Date day, Float tdrop) {

		Connection conn = null;
		List<VRpDyCell> vRpDyCellList = new ArrayList<VRpDyCell>();
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_DY_CELL where bscid = ? and day = ? and T_DRPR >= ?");
			preStmt.setString(1, bscid);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));
			preStmt.setFloat(3, tdrop);
			
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				VRpDyCell vRpDyCell = new VRpDyCell();
				vRpDyCell.setCellid(rs.getString("CELLID"));
			}
			preStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpDyCellList;
	}
	
	public static List<VRpDyCell> getDyCellTblk(String bscid, Date day, Float tblk) {

		Connection conn = null;
		List<VRpDyCell> vRpDyCellList = new ArrayList<VRpDyCell>();
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_DY_CELL where bscid = ? and day = ? and T_BLKR >= ?");
			preStmt.setString(1, bscid);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));
			preStmt.setFloat(3, tblk);
			
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				VRpDyCell vRpDyCell = new VRpDyCell();
				vRpDyCell.setCellid(rs.getString("CELLID"));
			}
			preStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpDyCellList;
	}
	
	public static List<VRpDyCell> getDyCellSdrop(String bscid, Date day, Float sdrop) {

		Connection conn = null;
		List<VRpDyCell> vRpDyCellList = new ArrayList<VRpDyCell>();
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_DY_CELL where bscid = ? and day = ? and S_DRPR >= ?");
			preStmt.setString(1, bscid);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));
			preStmt.setFloat(3, sdrop);
			
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				VRpDyCell vRpDyCell = new VRpDyCell();
				vRpDyCell.setCellid(rs.getString("CELLID"));
			}
			preStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpDyCellList;
	}
	
	public static List<VRpDyCell> getDyCellSblk(String bscid, Date day, Float sblk) {

		Connection conn = null;
		List<VRpDyCell> vRpDyCellList = new ArrayList<VRpDyCell>();
		try {
			conn = STS_Global.DATA_SOURCE.getConnection();
			PreparedStatement preStmt = conn
					.prepareStatement("select * from V_RP_DY_CELL where bscid = ? and day = ? and S_BLKR >= ?");
			preStmt.setString(1, bscid);
			long t = day.getTime();
			preStmt.setDate(2, new java.sql.Date(t));
			preStmt.setFloat(3, sblk);
			
			ResultSet rs = preStmt.executeQuery();
			while (rs.next()) {
				VRpDyCell vRpDyCell = new VRpDyCell();
				vRpDyCell.setCellid(rs.getString("CELLID"));
			}
			preStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		return vRpDyCellList;
	}
}
