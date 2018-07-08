package vn.com.vhc.vmsc2.statistics.web.test;

import com.thoughtworks.selenium.*;

public class BscConfigTest extends SeleneseTestCase{
	
	public void setUp() throws Exception {
		setUp("http://localhost:8080/", "*chrome", 5555);
	}
	
	public void testBscForm() throws Exception {
		selenium.open("/VMSC2-Statistics/config/bsc/list.htm");
		verifyEquals("vmsc2-statistics: bsc list", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách BSC"));
		selenium.click("//input[@name='filter']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("34 items found, displaying all items.1"));
		selenium.select("vendor", "label=HUAWEI");
		selenium.click("//input[@name='filter']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("2 items found, displaying all items.1"));
		selenium.select("vendor", "label=ERICSSON");
		selenium.click("//input[@name='filter']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("31 items found, displaying all items.1"));
		selenium.select("vendor", "label=Tất cả");
		selenium.type("bscid", "123");
		selenium.click("//input[@name='filter']");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Nothing found to display"));
	}
	
	public void testBscEdit() throws Exception {
		selenium.open("/VMSC2-Statistics/config/bsc/list.htm");
		selenium.click("link=Sửa");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: bsc form", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("BSC"));
		selenium.type("trau", "1231");
		selenium.type("trx", "2");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: bsc list", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách BSC"));
		verifyTrue(selenium.isTextPresent("BSC đã cập nhật thành công."));
		verifyTrue(selenium.isTextPresent("34 items found, displaying all items.1"));
	}

	public void testBscNewEdit() throws Exception {
		selenium.open("/VMSC2-Statistics/config/bsc/list.htm");
		selenium.click("link=Thêm mới");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: bsc form", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("BSC"));
		selenium.type("bscid", "123");
		selenium.type("trau", "12");
		selenium.type("trx", "123");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: bsc list", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách BSC"));
		verifyTrue(selenium.isTextPresent("BSC khởi tạo thành công."));
		verifyTrue(selenium.isTextPresent("35 items found, displaying all items.1"));
		selenium.click("link=Thêm mới");
		selenium.waitForPageToLoad("30000");
		selenium.type("bscid", "123");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: bsc list", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách BSC"));
		verifyTrue(selenium.isTextPresent("BSC đã tồn tại"));
		verifyTrue(selenium.isTextPresent("35 items found, displaying all items."));
	}
	
	public void testBscUpload() throws Exception {
		selenium.open("/VMSC2-Statistics/config/bsc/list.htm");
		verifyEquals("vmsc2-statistics: bsc list", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách BSC"));
		verifyTrue(selenium.isTextPresent("35 items found, displaying all items.1"));
		selenium.click("link=Upload file");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: upload bsc", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Upload cấu hình BSC"));
		selenium.type("file", "C:\\Users\\SL400\\Downloads\\BscList.csv");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: bsc list", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách BSC"));
		verifyTrue(selenium.isTextPresent("Upload thành công!"));
		verifyTrue(selenium.isTextPresent("35 items found, displaying all items.1"));
		selenium.click("link=Upload file");
		selenium.waitForPageToLoad("30000");
		selenium.type("file", "C:\\Users\\SL400\\Downloads\\bsc.csv");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: bsc list", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách BSC"));
		verifyTrue(selenium.isTextPresent("Upload thành công!"));
		verifyTrue(selenium.isTextPresent("37 items found, displaying all items.1"));
	}
}
