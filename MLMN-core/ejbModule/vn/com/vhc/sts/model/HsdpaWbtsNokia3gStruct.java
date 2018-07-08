package vn.com.vhc.sts.model;

public class HsdpaWbtsNokia3gStruct extends Nokia3gStruct {

	

	public HsdpaWbtsNokia3gStruct() {
		FIELD_LIST = "M5000C129,M5000C126,M5000C71,M5000C70,M5000C127,M5000C72,M5000C128,M5000C69,M5000C74,M5000C73";
		// 25-6-2018 TRUNGNQ THEM CAC COUNTER DE TINH CHI SO DL, UL THROUGHPUT 3G
		FIELD_LIST += ",M5000C324,M5000C325,M5000C323,M5000C322,M5000C321,M5000C320";
	}

	public String toString(String separator) {
		return M5000C129 + separator + M5000C126 + separator + M5000C71 + separator + M5000C70 + separator + M5000C127 + separator + M5000C72 + separator
				+ M5000C128 + separator + M5000C69 + separator + M5000C74 + separator + M5000C73 + separator
				+ M5000C324 + separator + M5000C325 + separator + M5000C323 + separator + M5000C322 + separator
				+ M5000C321 + separator + M5000C320;

	}

	public String createHeader(String separator) {
		return "M5000C129" + separator + "M5000C126" + separator + "M5000C71" + separator + "M5000C70" + separator + "M5000C127" + separator + "M5000C72"
				+ separator + "M5000C128" + separator + "M5000C69" + separator + "M5000C74" + separator + "M5000C73"
				+ separator + "M5000C324" + separator + "M5000C325" + separator + "M5000C323" + separator + "M5000C322"
				+ separator + "M5000C321" + separator + "M5000C320";
	}


}
