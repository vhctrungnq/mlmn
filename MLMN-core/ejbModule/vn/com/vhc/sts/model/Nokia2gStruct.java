package vn.com.vhc.sts.model;

public abstract class Nokia2gStruct {
	public abstract String toString(String separator);
	public abstract String createHeader(String separator);
	
	public static final Nokia2gStruct getModel(String measurementType) {
		if (measurementType.equals("TRAFFIC")) {
			return new Traffic();
		} else if (measurementType.equals("HO")) {
			return new Ho();
		} else if (measurementType.equals("RESAVAIL")) {
			return new Resavail();
		} else {
			return new Service();
		}
	}
	
	public final String getFieldList() {
		return FIELD_LIST;
	}
	public String FIELD_LIST;
	public String c004000 = "";
	public String c004149 = "";
	public String c004010 = "";
	public String c004202 = "";
	public String c004044 = "";
	public String c004057 = "";
	public String c004074 = "";
	public String c004004 = "";
	public String c004158 = "";
	public String c004014 = "";
	public String c004203 = "";
	public String c004204 = "";
	public String c004097 = "";
	public String c004101 = "";
	public String c004098 = "";
	public String c002092 = "";
	public String c002027 = "";
	public String c002028 = "";
	public String c002034 = "";
	public String c002035 = "";
	public String c002005 = "";
	public String c002004 = "";
	public String c002093 = "";
	public String c002060 = "";
	public String c002059 = "";
	public String c002062 = "";
	public String c002061 = "";
	public String c002070 = "";
	public String c002069 = "";
	public String c002048_1 = "";
	public String c057020 = "";
	public String c057032 = "";
	public String c001231 = "";
	public String c001099 = "";
	public String c001232 = "";
	public String c001225 = "";
	public String c001226 = "";
	public String c001009 = "";
	public String c001098 = "";
	public String c001003 = "";
	public String c001004 = "";
	public String c001037 = "";
	public String c001038 = "";
	public String c001039 = "";
	public String c001075 = "";
	public String c001076 = "";
	public String c001036 = "";
	public String c001035 = "";
	public String c001078 = "";
	public String c001079 = "";
	public String c001001 = "";
	public String c001007 = "";
	public String c001006 = "";
	public String c001000 = "";
	public String c001193 = "";
	public String c001202 = "";
	public String c001192 = "";
	public String c001010 = "";
	public String c001026 = "";
	public String c001043 = "";
	public String c001008 = "";
	public String getC004000() {
		return c004000;
	}
	public void setC004000(String c004000) {
		this.c004000 = c004000;
	}
	public String getC004149() {
		return c004149;
	}
	public void setC004149(String c004149) {
		this.c004149 = c004149;
	}
	public String getC004010() {
		return c004010;
	}
	public void setC004010(String c004010) {
		this.c004010 = c004010;
	}
	public String getC004202() {
		return c004202;
	}
	public void setC004202(String c004202) {
		this.c004202 = c004202;
	}
	public String getC004044() {
		return c004044;
	}
	public void setC004044(String c004044) {
		this.c004044 = c004044;
	}
	public String getC004057() {
		return c004057;
	}
	public void setC004057(String c004057) {
		this.c004057 = c004057;
	}
	public String getC004074() {
		return c004074;
	}
	public void setC004074(String c004074) {
		this.c004074 = c004074;
	}
	public String getC004004() {
		return c004004;
	}
	public void setC004004(String c004004) {
		this.c004004 = c004004;
	}
	public String getC004158() {
		return c004158;
	}
	public void setC004158(String c004158) {
		this.c004158 = c004158;
	}
	public String getC004014() {
		return c004014;
	}
	public void setC004014(String c004014) {
		this.c004014 = c004014;
	}
	public String getC004203() {
		return c004203;
	}
	public void setC004203(String c004203) {
		this.c004203 = c004203;
	}
	public String getC004204() {
		return c004204;
	}
	public void setC004204(String c004204) {
		this.c004204 = c004204;
	}
	public String getC004097() {
		return c004097;
	}
	public void setC004097(String c004097) {
		this.c004097 = c004097;
	}
	public String getC004101() {
		return c004101;
	}
	public void setC004101(String c004101) {
		this.c004101 = c004101;
	}
	public String getC004098() {
		return c004098;
	}
	public void setC004098(String c004098) {
		this.c004098 = c004098;
	}
	public String getC002092() {
		return c002092;
	}
	public void setC002092(String c002092) {
		this.c002092 = c002092;
	}
	public String getC002027() {
		return c002027;
	}
	public void setC002027(String c002027) {
		this.c002027 = c002027;
	}
	public String getC002028() {
		return c002028;
	}
	public void setC002028(String c002028) {
		this.c002028 = c002028;
	}
	public String getC002034() {
		return c002034;
	}
	public void setC002034(String c002034) {
		this.c002034 = c002034;
	}
	public String getC002035() {
		return c002035;
	}
	public void setC002035(String c002035) {
		this.c002035 = c002035;
	}
	public String getC002005() {
		return c002005;
	}
	public void setC002005(String c002005) {
		this.c002005 = c002005;
	}
	public String getC002004() {
		return c002004;
	}
	public void setC002004(String c002004) {
		this.c002004 = c002004;
	}
	public String getC002093() {
		return c002093;
	}
	public void setC002093(String c002093) {
		this.c002093 = c002093;
	}
	public String getC002060() {
		return c002060;
	}
	public void setC002060(String c002060) {
		this.c002060 = c002060;
	}
	public String getC002059() {
		return c002059;
	}
	public void setC002059(String c002059) {
		this.c002059 = c002059;
	}
	public String getC002062() {
		return c002062;
	}
	public void setC002062(String c002062) {
		this.c002062 = c002062;
	}
	public String getC002061() {
		return c002061;
	}
	public void setC002061(String c002061) {
		this.c002061 = c002061;
	}
	public String getC002070() {
		return c002070;
	}
	public void setC002070(String c002070) {
		this.c002070 = c002070;
	}
	public String getC002069() {
		return c002069;
	}
	public void setC002069(String c002069) {
		this.c002069 = c002069;
	}
	public String getC002048_1() {
		return c002048_1;
	}
	public void setC002048_1(String c002048_1) {
		this.c002048_1 = c002048_1;
	}
	public String getC057020() {
		return c057020;
	}
	public void setC057020(String c057020) {
		this.c057020 = c057020;
	}
	public String getC057032() {
		return c057032;
	}
	public void setC057032(String c057032) {
		this.c057032 = c057032;
	}
	public String getC001231() {
		return c001231;
	}
	public void setC001231(String c001231) {
		this.c001231 = c001231;
	}
	public String getC001099() {
		return c001099;
	}
	public void setC001099(String c001099) {
		this.c001099 = c001099;
	}
	public String getC001232() {
		return c001232;
	}
	public void setC001232(String c001232) {
		this.c001232 = c001232;
	}
	public String getC001225() {
		return c001225;
	}
	public void setC001225(String c001225) {
		this.c001225 = c001225;
	}
	public String getC001226() {
		return c001226;
	}
	public void setC001226(String c001226) {
		this.c001226 = c001226;
	}
	public String getC001009() {
		return c001009;
	}
	public void setC001009(String c001009) {
		this.c001009 = c001009;
	}
	public String getC001098() {
		return c001098;
	}
	public void setC001098(String c001098) {
		this.c001098 = c001098;
	}
	public String getC001003() {
		return c001003;
	}
	public void setC001003(String c001003) {
		this.c001003 = c001003;
	}
	public String getC001004() {
		return c001004;
	}
	public void setC001004(String c001004) {
		this.c001004 = c001004;
	}
	public String getC001037() {
		return c001037;
	}
	public void setC001037(String c001037) {
		this.c001037 = c001037;
	}
	public String getC001038() {
		return c001038;
	}
	public void setC001038(String c001038) {
		this.c001038 = c001038;
	}
	public String getC001039() {
		return c001039;
	}
	public void setC001039(String c001039) {
		this.c001039 = c001039;
	}
	public String getC001075() {
		return c001075;
	}
	public void setC001075(String c001075) {
		this.c001075 = c001075;
	}
	public String getC001076() {
		return c001076;
	}
	public void setC001076(String c001076) {
		this.c001076 = c001076;
	}
	public String getC001036() {
		return c001036;
	}
	public void setC001036(String c001036) {
		this.c001036 = c001036;
	}
	public String getC001035() {
		return c001035;
	}
	public void setC001035(String c001035) {
		this.c001035 = c001035;
	}
	public String getC001078() {
		return c001078;
	}
	public void setC001078(String c001078) {
		this.c001078 = c001078;
	}
	public String getC001079() {
		return c001079;
	}
	public void setC001079(String c001079) {
		this.c001079 = c001079;
	}
	public String getC001001() {
		return c001001;
	}
	public void setC001001(String c001001) {
		this.c001001 = c001001;
	}
	public String getC001007() {
		return c001007;
	}
	public void setC001007(String c001007) {
		this.c001007 = c001007;
	}
	public String getC001006() {
		return c001006;
	}
	public void setC001006(String c001006) {
		this.c001006 = c001006;
	}
	public String getC001000() {
		return c001000;
	}
	public void setC001000(String c001000) {
		this.c001000 = c001000;
	}
	public String getC001193() {
		return c001193;
	}
	public void setC001193(String c001193) {
		this.c001193 = c001193;
	}
	public String getC001202() {
		return c001202;
	}
	public void setC001202(String c001202) {
		this.c001202 = c001202;
	}
	public String getC001192() {
		return c001192;
	}
	public void setC001192(String c001192) {
		this.c001192 = c001192;
	}
	public String getC001010() {
		return c001010;
	}
	public void setC001010(String c001010) {
		this.c001010 = c001010;
	}
	public String getC001026() {
		return c001026;
	}
	public void setC001026(String c001026) {
		this.c001026 = c001026;
	}
	public String getC001043() {
		return c001043;
	}
	public void setC001043(String c001043) {
		this.c001043 = c001043;
	}
	public String getC001008() {
		return c001008;
	}
	public void setC001008(String c001008) {
		this.c001008 = c001008;
	}

}
