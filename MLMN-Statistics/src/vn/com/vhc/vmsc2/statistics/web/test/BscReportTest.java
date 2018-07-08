package vn.com.vhc.vmsc2.statistics.web.test;

import com.thoughtworks.selenium.*;

public class BscReportTest extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://localhost:8080/", "*chrome", 5555);
	}
	public void testBscReport() throws Exception {
		selenium.open("/VMSC2-Statistics/config/bsc/list.htm");
		verifyEquals("vmsc2-statistics: bsc list", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách BSC"));
		selenium.click("//table[@id='bsc']/tbody/tr[3]/td[6]/a[3]");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: bsc day report", selenium.getTitle());
		selenium.click("//img[@alt='...']");
		selenium.click("//div[@id='ui-datepicker-div']/div/a[1]/span");
		selenium.click("link=17");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: bsc day report", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("BSC Daily Report"));
		verifyEquals("17/09/2010", selenium.getValue("day"));
		verifyTrue(selenium.isTextPresent("BSG011E"));
		verifyTrue(selenium.isTextPresent("CSSR (%)"));
		verifyTrue(selenium.isTextPresent("98.91%"));
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/div/table[1]/tbody/tr/td[2]/img[1]");
		selenium.click("//div[@id='ui-datepicker-div']/div/a[1]/span");
		selenium.click("link=17");
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/div/table[1]/tbody/tr/td[2]/img[2]");
		selenium.click("//div[@id='ui-datepicker-div']/div/a[1]/span");
		selenium.click("link=21");
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/div/table[1]/tbody/tr/td[2]/input[3]");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: bsc day report", selenium.getTitle());
		verifyEquals("17/09/2010", selenium.getValue("startDate"));
		verifyEquals("21/09/2010", selenium.getValue("endDate"));
		verifyTrue(selenium.isTextPresent("dcrs"));
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/ul/li[1]/a/span");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: BSC Hourly Report", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("BSC Hourly Report"));
		selenium.click("//img[@alt='...']");
		selenium.click("//div[@id='ui-datepicker-div']/div/a[1]/span");
		selenium.click("link=17");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: BSC Hourly Report", selenium.getTitle());
		verifyEquals("17/09/2010", selenium.getValue("day"));
		verifyEquals("10", selenium.getValue("hour"));
		verifyTrue(selenium.isTextPresent("BSG011E"));
		verifyTrue(selenium.isTextPresent("No. of SDCCH Seizure Attempts"));
		verifyTrue(selenium.isTextPresent("544714"));
		selenium.type("hour", "11");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: BSC Hourly Report", selenium.getTitle());
		verifyEquals("11", selenium.getValue("hour"));
		verifyTrue(selenium.isTextPresent("No. of SDCCH Seizure Attempts"));
		verifyTrue(selenium.isTextPresent("542078"));
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/ul/li[3]/a/span");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: BSC Weekly Report", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("BSC Weekly Report"));
		verifyEquals("2", selenium.getValue("week"));
		verifyEquals("2010", selenium.getValue("year"));
		selenium.type("week", "39");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: BSC Weekly Report", selenium.getTitle());
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/ul/li[4]/a/span");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: BSC Monthly Report", selenium.getTitle());
		verifyEquals("2", selenium.getValue("month"));
		verifyEquals("2010", selenium.getValue("year"));
		verifyTrue(selenium.isTextPresent("BSG011E"));
	}
}
