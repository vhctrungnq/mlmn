package vn.com.vhc.sts.model;

public class SoftHandoverNokia3gStruct extends Nokia3gStruct {

	public SoftHandoverNokia3gStruct() {
		FIELD_LIST = "M1007C33,M1007C16,M1007C15,M1007C32,M1007C11,M1007C28,M1007C28,M1007C36,M1007C11,M1007C37,M1007C11,M1007C36";
	}

	@Override
	public String toString(String separator) {
		// TODO Auto-generated method stub
		return M1007C33 + separator + M1007C16 + separator + M1007C15 + separator + M1007C32 + separator + M1007C11 + separator + M1007C28 + separator
				+ M1007C28 + separator + M1007C36 + separator + M1007C11 + separator + M1007C37 + separator + M1007C11 + separator + M1007C36;
	}

	@Override
	public String createHeader(String separator) {
		// TODO Auto-generated method stub
		return "M1007C33" + separator + "M1007C16" + separator + "M1007C15" + separator + "M1007C32" + separator + "M1007C11" + separator + "M1007C28"
				+ separator + "M1007C28" + separator + "M1007C36" + separator + "M1007C11" + separator + "M1007C37" + separator + "M1007C11" + separator
				+ "M1007C36";
	}

}
