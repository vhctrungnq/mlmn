package vo.alarm.utils;

public class PhoneNumberTools {

	public static String getDauSo(String phoneNumber) {
		
		if (phoneNumber.length() >= 7 )
			return phoneNumber.substring(0, 6);
		return phoneNumber;
	}
}
