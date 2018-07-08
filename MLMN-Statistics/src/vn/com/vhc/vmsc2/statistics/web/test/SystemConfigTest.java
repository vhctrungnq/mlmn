package vn.com.vhc.vmsc2.statistics.web.test;

import com.thoughtworks.selenium.SeleneseTestCase;

public class SystemConfigTest extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://localhost:8080/", "*chrome", 5555);
	}

	public void testList() throws Exception {
		selenium.open("/VMSC2-Statistics/config/systemConfig/list.htm");
		verifyEquals("vmsc2-statistics: system config list", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Tham số hệ thống"));
		verifyTrue(selenium.isTextPresent("44 items found, displaying all items."));
		selenium.type("paramName", "download.delay.second");
		selenium.click("//input[@name='filter']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("One item found."));
		verifyEquals("download.delay.second", selenium.getTable("systemConfig.1.0"));
	}
	
	public void testEdit() throws Exception {	
		selenium.open("/VMSC2-Statistics/config/systemConfig/list.htm");
		verifyEquals("vmsc2-statistics: system config list", selenium.getTitle());
		selenium.click("//table[@id='systemConfig']/tbody/tr[4]/td[5]/a");
		selenium.waitForPageToLoad("30000");
		selenium.type("paramValue", "60");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: system config list", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("SystemConfig đã được cập nhật thành công."));
		verifyEquals("download.delay.second", selenium.getTable("systemConfig.4.0"));
		verifyEquals("60", selenium.getTable("systemConfig.4.1"));
	}
}
