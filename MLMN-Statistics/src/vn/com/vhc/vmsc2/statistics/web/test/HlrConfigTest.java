package vn.com.vhc.vmsc2.statistics.web.test;

import com.thoughtworks.selenium.*;
public class HlrConfigTest extends SeleneseTestCase{
	
	public void setUp() throws Exception {
		setUp("http://localhost:8080/", "*chrome", 5555);
	}
	
	public void testHlrList() throws Exception {
		selenium.open("/VMSC2-Statistics/config/hlr/list.htm");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách HLR"));
		verifyTrue(selenium.isTextPresent("4 items found, displaying all items.1"));
		selenium.select("vendor", "label=ALCATEL");
		selenium.click("//input[@name='filter']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("ALCATEL"));
		verifyTrue(selenium.isTextPresent("2 items found, displaying all items.1"));
		selenium.select("vendor", "label=HUAWEI");
		selenium.click("//input[@name='filter']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("HUAWEI"));
		verifyTrue(selenium.isTextPresent("2 items found, displaying all items.1"));
		selenium.select("vendor", "label=ERICSSON");
		selenium.click("//input[@name='filter']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách HLR"));
		verifyTrue(selenium.isTextPresent("Nothing found to display."));
		selenium.select("vendor", "label=Tất cả");
		selenium.click("//input[@name='filter']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("4 items found, displaying all items.1"));
		verifyTrue(selenium.isTextPresent("ALCATEL"));
		verifyTrue(selenium.isTextPresent("HUAWEI"));
		selenium.type("hlrid", "213");
		selenium.click("//input[@name='filter']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyEquals("213", selenium.getValue("hlrid"));
		verifyTrue(selenium.isTextPresent("213"));
	}

	public void testHlrEdit() throws Exception {
		selenium.open("/VMSC2-Statistics/config/hlr/list.htm");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("4 items found, displaying all items.1"));
		selenium.click("link=Sửa");
		selenium.waitForPageToLoad("30000");
		selenium.type("softwareVersion", "45");
		verifyEquals("vmsc2-statistics: HLR Form", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("HLR"));
		verifyTrue(selenium.isTextPresent("213"));
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("HLR cập nhật thành công."));
		verifyTrue(selenium.isTextPresent("4 items found, displaying all items.1"));
		verifyTrue(selenium.isTextPresent("45"));
		selenium.click("link=Sửa");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR Form", selenium.getTitle());
		selenium.click("//input[@value='Cancel']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
	}

	public void testHlrNewEdit() throws Exception {
		selenium.open("/VMSC2-Statistics/config/hlr/list.htm");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách HLR"));
		verifyTrue(selenium.isTextPresent("4 items found, displaying all items.1"));
		verifyEquals("", selenium.getValue("hlrid"));
		verifyTrue(selenium.isTextPresent("HHCM01E"));
		selenium.click("link=Thêm mới");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR Form", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("HLR"));
		verifyEquals("", selenium.getValue("hlrid"));
		selenium.type("hlrid", "ghhh");
		selenium.select("vendor", "label=ERICSSON");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("HLR khởi tạo thành công."));
		verifyTrue(selenium.isTextPresent("5 items found, displaying all items.1"));
		verifyTrue(selenium.isTextPresent("ghhh"));
		verifyTrue(selenium.isTextPresent("ERICSSON"));
		selenium.click("link=Thêm mới");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR Form", selenium.getTitle());
		selenium.type("hlrid", "ghgh");
		selenium.click("//input[@value='Cancel']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("5 items found, displaying all items.1"));
		selenium.click("link=Thêm mới");
		selenium.waitForPageToLoad("30000");
		selenium.type("hlrid", "56");
		selenium.type("hardwareVersion", "3");
		selenium.click("save");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách HLR"));
		verifyTrue(selenium.isTextPresent("HLR đã tồn tại"));
		verifyTrue(selenium.isTextPresent("5 items found, displaying all items.1"));
	}

	public void testHlrDel() throws Exception {
		selenium.open("/VMSC2-Statistics/config/hlr/list.htm");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách HLR"));
		verifyTrue(selenium.isTextPresent("5 items found, displaying all items.1"));
		verifyTrue(selenium.isTextPresent("ghhh"));
		selenium.chooseCancelOnNextConfirmation();
		selenium.click("//table[@id='hlr']/tbody/tr[5]/td[6]/a[2]");
		assertTrue(selenium.getConfirmation().matches("Bạn có chắc muốn xóa[\\s\\S]$"));
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		selenium.click("//table[@id='hlr']/tbody/tr[5]/td[6]/a[2]");
		assertTrue(selenium.getConfirmation().matches("Bạn có chắc muốn xóa[\\s\\S]$"));
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách HLR"));
		verifyTrue(selenium.isTextPresent("HLR đã xóa."));
		verifyTrue(selenium.isTextPresent("4 items found, displaying all items.1"));
	}

	public void testHlrUpload() throws Exception {
		selenium.open("/VMSC2-Statistics/config/hlr/list.htm");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách HLR"));
		verifyTrue(selenium.isTextPresent("4 items found, displaying all items.1"));
		selenium.click("link=Upload file");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: Upload HlR", selenium.getTitle());
		selenium.type("file", "C:\\Users\\SL400\\Downloads\\HlrList.csv");
		selenium.click("//input[@value='Quay lại']");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("4 items found, displaying all items.1"));
		selenium.click("link=Upload file");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: Upload HlR", selenium.getTitle());
		selenium.type("file", "C:\\Users\\SL400\\Downloads\\HlrList.csv");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách HLR"));
		verifyTrue(selenium.isTextPresent("Upload thành công!"));
		verifyTrue(selenium.isTextPresent("4 items found, displaying all items.1"));
		verifyTrue(selenium.isTextPresent("213"));
		verifyTrue(selenium.isTextPresent("56"));
		verifyTrue(selenium.isTextPresent("HHCE"));
		verifyTrue(selenium.isTextPresent("HHCM01E"));
		selenium.click("link=Upload file");
		selenium.waitForPageToLoad("30000");
		selenium.type("file", "C:\\Users\\SL400\\Downloads\\hlr.csv");
		verifyEquals("vmsc2-statistics: Upload HlR", selenium.getTitle());
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Upload thành công!"));
		verifyTrue(selenium.isTextPresent("6 items found, displaying all items.1"));
		verifyTrue(selenium.isTextPresent("2143"));
		verifyTrue(selenium.isTextPresent("562"));
		verifyTrue(selenium.isTextPresent("213"));
		verifyTrue(selenium.isTextPresent("56"));
		verifyTrue(selenium.isTextPresent("HHCE"));
		verifyTrue(selenium.isTextPresent("HHCM01E"));
	}

}
