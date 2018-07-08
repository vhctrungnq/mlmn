package vn.com.vhc.sts.model;

public class Service extends Nokia2gStruct {

	public Service() {
		FIELD_LIST = "c057020,c057032";
	}
	
	@Override
	public String toString(String separator) {
		// TODO Auto-generated method stub
		return c057020 + separator + c057032;
	}

	@Override
	public String createHeader(String separator) {
		// TODO Auto-generated method stub
		return "c057020" + separator + "c057032";
	}

}
