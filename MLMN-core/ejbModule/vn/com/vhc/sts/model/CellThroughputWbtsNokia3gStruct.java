package vn.com.vhc.sts.model;

public class CellThroughputWbtsNokia3gStruct extends Nokia3gStruct {
	public CellThroughputWbtsNokia3gStruct() {
		FIELD_LIST = "M5002C3,M5002C2,M5002C4,M5002C128,M5002C129";
	}

	@Override
	public String toString(String separator) {
		// TODO Auto-generated method stub
		return M5002C3 + separator + M5002C2 + separator + M5002C4 + separator + M5002C128 + separator + M5002C129;
	}

	@Override
	public String createHeader(String separator) {
		// TODO Auto-generated method stub
		return "M5002C3" + separator + "M5002C2" + separator + "M5002C4" + separator + "M5002C128" + separator + "M5002C129";

	}

}
