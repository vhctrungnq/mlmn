package vn.com.vhc.sts.model;

public class Resavail extends Nokia2gStruct {

	public Resavail() {
		FIELD_LIST = "c002092,c002027,c002028,c002034,c002035,c002005,c002004,c002093,c002060,c002059,"
				+ "c002062,c002061,c002070,c002069,c002048_1";
	}

	@Override
	public String toString(String separator) {
		// TODO Auto-generated method stub
		return c002092 + separator + c002027 + separator + c002028 + separator + c002034 + separator + c002035
				+ separator + c002005 + separator + c002004 + separator + c002093 + separator + c002060 + separator
				+ c002059 + separator + c002062 + separator + c002061 + separator + c002070 + separator + c002069
				+ separator + c002048_1;
	}

	@Override
	public String createHeader(String separator) {
		// TODO Auto-generated method stub
		return "c002092" + separator + "c002027" + separator + "c002028" + separator + "c002034" + separator + "c002035"
				+ separator + "c002005" + separator + "c002004" + separator + "c002093" + separator + "c002060"
				+ separator + "c002059" + separator + "c002062" + separator + "c002061" + separator + "c002070"
				+ separator + "c002069" + separator + "c002048_1";
	}

}
