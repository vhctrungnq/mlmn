package vn.com.vhc.sts.cp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import vn.com.vhc.sts.utils.STS_DBUtils;

public class CpProcess implements Runnable {
	private SmppQueue smppQueue;
	private DateFormat df = new SimpleDateFormat("yyyyMMdd");
	private DateFormat dff = new SimpleDateFormat("yyyyMMdd.HH");

	public CpProcess(SmppQueue smppQueue) {
		this.smppQueue = smppQueue;
	}

	public void run() {
		Map<String, String> map = parseMsg(smppQueue.getReceiveMessage());

		String content = "";

		if (map.containsKey("BSC")) {
			if (map.containsKey("NGAY")) {
				try {
					Date day = df.parse(map.get("NGAY"));
					VRpDyBsc vRpDyBsc = STS_DBUtils.getDyBscRp(map.get("BSC"), day);
					content = map.get("BSC") + " " + map.get("NGAY") + " " + vRpDyBsc.gettTraf() + " " + vRpDyBsc.getHalfrate() + " " + vRpDyBsc.gettDrpr()
							+ " " + vRpDyBsc.gettBlkr() + " " + vRpDyBsc.getsDrpr() + " " + vRpDyBsc.getsBlkr() + " " + vRpDyBsc.getCssr();
				} catch (ParseException e) {
				}
			} else if (map.containsKey("GIO")) {
				try {
					Date date = dff.parse(map.get("GIO"));
					VRpHrBsc vRpHrBsc = STS_DBUtils.getHrBscRp(map.get("BSC"), date, date.getHours());
					content = map.get("BSC") + " " + map.get("GIO") + " " + vRpHrBsc.gettTraf() + " " + vRpHrBsc.getHalfrate() + " " + vRpHrBsc.gettDrpr()
							+ " " + vRpHrBsc.gettBlkr() + " " + vRpHrBsc.getsDrpr() + " " + vRpHrBsc.getsBlkr() + " " + vRpHrBsc.getCssr();
				} catch (ParseException e) {
				}
			}
		} else if (map.containsKey("CELL")) {
			if (map.containsKey("NGAY")) {
				try {
					Date day = df.parse(map.get("NGAY"));
					VRpDyCell vRpDyCell = STS_DBUtils.getDyCellRp(map.get("CELL"), day);
					content = map.get("CELL") + " " + map.get("NGAY") + " " + vRpDyCell.gettDef() + " " + vRpDyCell.gettAvail() + " " + vRpDyCell.gettTraf()
							+ " " + vRpDyCell.getHaftratePercent() + " " + vRpDyCell.gettDrpr() + " " + vRpDyCell.gettBlkr() + " " + vRpDyCell.getsDrpr() + " "
							+ vRpDyCell.getsBlkr() + " " + vRpDyCell.getCssr();
				} catch (ParseException e) {
				}
			} else if (map.containsKey("GIO")) {
				try {
					Date date = dff.parse(map.get("GIO"));
					VRpHrCell vRpHrCell = STS_DBUtils.getHrCellRp(map.get("CELL"), date, date.getHours());
					content = map.get("CELL") + " " + map.get("GIO") + " " + vRpHrCell.gettDef() + " " + vRpHrCell.gettAvail() + " " + vRpHrCell.gettTraf()
							+ " " + vRpHrCell.getHaftratePercent() + " " + vRpHrCell.gettDrpr() + " " + vRpHrCell.gettBlkr() + " " + vRpHrCell.getsDrpr() + " "
							+ vRpHrCell.getsBlkr() + " " + vRpHrCell.getCssr();
				} catch (ParseException e) {
				}
			}
		} else if (map.containsKey("PROVINCE")) {
			if (map.containsKey("NGAY")) {
				try {
					Date day = df.parse(map.get("NGAY"));
					VRpDyProvince vRpDyProvince = STS_DBUtils.getDyProvinceRp(map.get("PROVINCE"), day);
					content = map.get("PROVINCE") + " " + map.get("NGAY") + " " + vRpDyProvince.gettTraf() + " " + vRpDyProvince.getHaftratePercent() + " "
							+ vRpDyProvince.gettDrpr() + " " + vRpDyProvince.gettBlkr() + " " + vRpDyProvince.getsDrpr() + " " + vRpDyProvince.getsBlkr() + " "
							+ vRpDyProvince.getCssr();
				} catch (ParseException e) {
				}
			} else if (map.containsKey("GIO")) {
				try {
					Date date = dff.parse(map.get("GIO"));
					VRpHrProvince vRpHrProvince = STS_DBUtils.getHrProvinceRp(map.get("PROVINCE"), date, date.getHours());
					content = map.get("PROVINCE") + " " + map.get("GIO") + " " + vRpHrProvince.gettTraf() + " " + vRpHrProvince.getHaftratePercent() + " "
							+ vRpHrProvince.gettDrpr() + " " + vRpHrProvince.gettBlkr() + " " + vRpHrProvince.getsDrpr() + " " + vRpHrProvince.getsBlkr() + " "
							+ vRpHrProvince.getCssr();
				} catch (ParseException e) {
				}
			}
		} else if (map.containsKey("DISTRICT")) {
			if (map.containsKey("NGAY")) {
				try {
					Date day = df.parse(map.get("NGAY"));
					VRpDyDistrict vRpDyDistrict = STS_DBUtils.getDyDistrictRp(map.get("DISTRICT"), day);
					content = map.get("DISTRICT") + " " + map.get("NGAY") + " " + vRpDyDistrict.gettTraf() + " " + vRpDyDistrict.getHaftratePercent() + " "
							+ vRpDyDistrict.gettDrpr() + " " + vRpDyDistrict.gettBlkr() + " " + vRpDyDistrict.getsDrpr() + " " + vRpDyDistrict.getsBlkr() + " "
							+ vRpDyDistrict.getCssr();
				} catch (ParseException e) {
				}
			} else if (map.containsKey("GIO")) {
				try {
					Date date = dff.parse(map.get("GIO"));
					VRpHrDistrict vRpHrDistrict = STS_DBUtils.getHrDistrictRp(map.get("DISTRICT"), date, date.getHours());
					content = map.get("DISTRICT") + " " + map.get("GIO") + " " + vRpHrDistrict.gettTraf() + " " + vRpHrDistrict.getHaftratePercent() + " "
							+ vRpHrDistrict.gettDrpr() + " " + vRpHrDistrict.gettBlkr() + " " + vRpHrDistrict.getsDrpr() + " " + vRpHrDistrict.getsBlkr() + " "
							+ vRpHrDistrict.getCssr();
				} catch (ParseException e) {
				}
			}
		} else if (map.containsKey("KPI")) {
			try {
				Date day = df.parse(map.get("NGAY"));
				Float threshold = Float.parseFloat(map.get("THRESHOLD"));

				if (map.get("KPI").equalsIgnoreCase("CSSR")) {
					List<VRpDyCell> dyCellList = STS_DBUtils.getDyCellCssr(map.get("BSCID"), day, threshold);
					for (VRpDyCell vRpDyCell : dyCellList) {
						content += vRpDyCell.getCellid() + " ";
					}
				} else if (map.get("KPI").equalsIgnoreCase("TCHDROP")) {
					List<VRpDyCell> dyCellList = STS_DBUtils.getDyCellTdrop(map.get("BSCID"), day, threshold);
					for (VRpDyCell vRpDyCell : dyCellList) {
						content += vRpDyCell.getCellid() + " ";
					}
				} else if (map.get("KPI").equalsIgnoreCase("TCHBLOCK")) {
					List<VRpDyCell> dyCellList = STS_DBUtils.getDyCellTblk(map.get("BSCID"), day, threshold);
					for (VRpDyCell vRpDyCell : dyCellList) {
						content += vRpDyCell.getCellid() + " ";
					}
				} else if (map.get("KPI").equalsIgnoreCase("SDDROP")) {
					List<VRpDyCell> dyCellList = STS_DBUtils.getDyCellSdrop(map.get("BSCID"), day, threshold);
					for (VRpDyCell vRpDyCell : dyCellList) {
						content += vRpDyCell.getCellid() + " ";
					}
				} else if (map.get("KPI").equalsIgnoreCase("SDBLOCK")) {
					List<VRpDyCell> dyCellList = STS_DBUtils.getDyCellSblk(map.get("BSCID"), day, threshold);
					for (VRpDyCell vRpDyCell : dyCellList) {
						content += vRpDyCell.getCellid() + " ";
					}
				}
			} catch (ParseException e) {
			}
		} else {
			content = "syntax error";
		}

		STS_DBUtils.insertSms(smppQueue.getIsdn(), content);

	}

	public Map<String, String> parseMsg(String message) {
		Map<String, String> map = new HashMap<String, String>();

		if (message.indexOf(":") != -1) {
			StringTokenizer st = new StringTokenizer(message);
			while (st.hasMoreElements()) {
				String s = st.nextToken().trim();

				String[] results = s.split(":");
				map.put(results[0], results[1]);
			}
		} else {
			String[] results = message.split(" ");

			map.put("KPI", results[0]);
			map.put("BSCID", results[1]);
			map.put("NGAY", results[2]);
			map.put("THRESHOLD", results[3]);
		}

		return map;
	}
}
