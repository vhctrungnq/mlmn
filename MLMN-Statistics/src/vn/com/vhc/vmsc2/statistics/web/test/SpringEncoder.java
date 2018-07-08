package vn.com.vhc.vmsc2.statistics.web.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class SpringEncoder {
	@Test
	public void testSpringEncoder() {
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String hashedPass = encoder.encodePassword("opal", null);

		assertEquals("22b5c9accc6e1ba628cedc63a72d57f8", hashedPass);
	}

}
