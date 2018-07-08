package vn.com.vhc.sts.model;

public class Traffic extends Nokia2gStruct {

	public Traffic() {
		FIELD_LIST = "c001231,c001099,c001232,c001225,c001226,c001009,c001098,c001003,c001004,c001037,"
				+ "c001038,c001039,c001075,c001076,c001036,c001035,c001078,c001079,c001001,c001007,c001006,"
				+ "c001000,c001193,c001202,c001192,c001010,c001026,c001043,c001008";
	}

	@Override
	public String toString(String separator) {
		// TODO Auto-generated method stub
		return c001231 + separator + c001099 + separator + c001232 + separator + c001225 + separator + c001226
				+ separator + c001009 + separator + c001098 + separator + c001003 + separator + c001004 + separator
				+ c001037 + separator + c001038 + separator + c001039 + separator + c001075 + separator + c001076
				+ separator + c001036 + separator + c001035 + separator + c001078 + separator + c001079 + separator
				+ c001001 + separator + c001007 + separator + c001006 + separator + c001000 + separator + c001193
				+ separator + c001202 + separator + c001192 + separator + c001010 + separator + c001026 + separator
				+ c001043 + separator + c001008;
	}

	@Override
	public String createHeader(String separator) {
		// TODO Auto-generated method stub
		return "c001231" + separator + "c001099" + separator + "c001232" + separator + "c001225" + separator + "c001226"
				+ separator + "c001009" + separator + "c001098" + separator + "c001003" + separator + "c001004"
				+ separator + "c001037" + separator + "c001038" + separator + "c001039" + separator + "c001075"
				+ separator + "c001076" + separator + "c001036" + separator + "c001035" + separator + "c001078"
				+ separator + "c001079" + separator + "c001001" + separator + "c001007" + separator + "c001006"
				+ separator + "c001000" + separator + "c001193" + separator + "c001202" + separator + "c001192"
				+ separator + "c001010" + separator + "c001026" + separator + "c001043" + separator + "c001008";
	}

}
