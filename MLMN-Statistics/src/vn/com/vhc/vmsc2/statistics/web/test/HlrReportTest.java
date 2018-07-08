package vn.com.vhc.vmsc2.statistics.web.test;

import com.thoughtworks.selenium.*;

public class HlrReportTest extends SeleneseTestCase{
	
	public void setUp() throws Exception {
		setUp("http://localhost:8080/", "*chrome", 5555);
	}
	public void testHlrReport() throws Exception {
		selenium.open("/VMSC2-Statistics/config/hlr/list.htm");
		verifyEquals("vmsc2-statistics: HLR List", selenium.getTitle());
		selenium.click("//table[@id='hlr']/tbody/tr[4]/td[6]/a[3]");
		selenium.waitForPageToLoad("300000");
		verifyEquals("vmsc2-statistics: Hlr Daily Report", selenium.getTitle());
		selenium.click("//img[@alt='...']");
		selenium.click("//div[@id='ui-datepicker-div']/div/a[1]/span");
		selenium.click("link=17");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: Hlr Daily Report", selenium.getTitle());
		verifyEquals("17/09/2010", selenium.getValue("day"));
		verifyTrue(selenium.isTextPresent("Number of total requests for a MAP operation"));
		verifyTrue(selenium.isTextPresent("Number of successful MAP operation executed"));
		verifyTrue(selenium.isTextPresent("159942516"));
		verifyTrue(selenium.isTextPresent("159909878"));
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/ul/li[1]/a/span");
		selenium.waitForPageToLoad("30000");
		selenium.click("//img[@alt='...']");
		selenium.click("//div[@id='ui-datepicker-div']/div/a[1]/span");
		selenium.click("link=17");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: Hlr Hourly Report", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Successful MAP operation executed rate(%)"));
		verifyTrue(selenium.isTextPresent("%"));
		selenium.type("hour", "11");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: Hlr Hourly Report", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Báo cáo giờ"));
		verifyEquals("17/09/2010", selenium.getValue("day"));
		verifyEquals("11", selenium.getValue("hour"));
		verifyTrue(selenium.isTextPresent("Successful MAP operation executed rate(%)"));
		verifyTrue(selenium.isTextPresent("99.99 %"));
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/ul/li[3]/a/span");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: Hlr Weekly Report", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Báo cáo tuần"));
		verifyEquals("2", selenium.getValue("week"));
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/ul/li[4]/a/span");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: Hlr Monthly Report", selenium.getTitle());
		verifyEquals("2", selenium.getValue("month"));
		verifyTrue(selenium.isTextPresent("Successful MAP operation executed rate(%)"));
		verifyTrue(selenium.isTextPresent("%"));
	}
}
