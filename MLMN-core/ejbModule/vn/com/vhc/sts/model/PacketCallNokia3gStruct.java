package vn.com.vhc.sts.model;

public class PacketCallNokia3gStruct extends Nokia3gStruct {
	public PacketCallNokia3gStruct() {
		FIELD_LIST = "M1022C8,M1022C7,M1022C183,M1022C31,M1022C195,M1022C32,M1022C210,M1022C68,M1022C67,M1022C62,"
				+ "M1022C61,M1022C213,M1022C204,M1022C62,M1022C68,M1022C61,M1022C210,M1022C49,M1022C67,M1022C213,M1022C49";
	}

	@Override
	public String toString(String separator) {
		// TODO Auto-generated method stub
		return M1022C8 + separator + M1022C7 + separator + M1022C183 + separator + M1022C31 + separator + M1022C195 + separator + M1022C32 + separator
				+ M1022C210 + separator + M1022C68 + separator + M1022C67 + separator + M1022C62 + separator + M1022C61 + separator + M1022C213 + separator
				+ M1022C204 + separator + M1022C62 + separator + M1022C68 + separator + M1022C61 + separator + M1022C210 + separator + M1022C49 + separator
				+ M1022C67 + separator + M1022C213 + separator + M1022C49;
	}

	@Override
	public String createHeader(String separator) {
		// TODO Auto-generated method stub
		return "M1022C8" + separator + "M1022C7" + separator + "M1022C183" + separator + "M1022C31" + separator + "M1022C195" + separator + "M1022C32"
				+ separator + "M1022C210" + separator + "M1022C68" + separator + "M1022C67" + separator + "M1022C62" + separator + "M1022C61" + separator
				+ "M1022C213" + separator + "M1022C204" + separator + "M1022C62" + separator + "M1022C68" + separator + "M1022C61" + separator + "M1022C210"
				+ separator + "M1022C49" + separator + "M1022C67" + separator + "M1022C213" + separator + "M1022C49";
	}

}
