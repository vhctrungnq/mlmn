package vn.com.vhc.sts.model;

public class CellThrputNokia3gStruct extends Nokia3gStruct {

	public CellThrputNokia3gStruct() {
		FIELD_LIST = "M1023C8,M1023C10,M1023C23,M1023C7,M1023C6";
	}

	@Override
	public String toString(String separator) {
		// TODO Auto-generated method stub
		return M1023C8 + separator + M1023C10 + separator + M1023C23 + separator + M1023C7 + separator + M1023C6;
	}

	@Override
	public String createHeader(String separator) {
		// TODO Auto-generated method stub
		return "M1023C8" + separator + "M1023C10" + separator + "M1023C23" + separator + "M1023C7" + separator + "M1023C6";

	}

}
