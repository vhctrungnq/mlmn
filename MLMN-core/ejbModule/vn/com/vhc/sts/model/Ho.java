package vn.com.vhc.sts.model;

public class Ho extends Nokia2gStruct {

	public Ho() {
		FIELD_LIST = "c004000,c004149,c004010,c004202,c004044,c004057,c004074,c004004,"
				+ "c004158,c004014,c004203,c004204,c004097,c004101,c004098";
	}

	@Override
	public String toString(String separator) {
		// TODO Auto-generated method stub
		return c004000 + separator + c004149 + separator + c004010 + separator + c004202 + separator + c004044
				+ separator + c004057 + separator + c004074 + separator + c004004 + separator + c004158 + separator
				+ c004014 + separator + c004203 + separator + c004204 + separator + c004097 + separator + c004101
				+ separator + c004098;

	}

	@Override
	public String createHeader(String separator) {
		// TODO Auto-generated method stub
		return "c004000" + separator + "c004149" + separator + "c004010" + separator + "c004202" + separator + "c004044"
		+ separator + "c004057" + separator + "c004074" + separator + "c004004" + separator + "c004158"
		+ separator + "c004014" + separator + "c004203" + separator + "c004204" + separator + "c004097"
		+ separator + "c004101" + separator + "c004098";
	}

}
