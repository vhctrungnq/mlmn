package vn.com.vhc.vmsc2.statistics.web.test;

import com.thoughtworks.selenium.*;

public class StpReportTest extends SeleneseTestCase{
	
	public void setUp() throws Exception {
		setUp("http://localhost:8080/", "*chrome", 5555);
	}
	public void testStpReport() throws Exception {
		selenium.open("/VMSC2-Statistics/config/stp/list.htm");
		verifyEquals("vmsc2-statistics: STP List", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Danh sách STP"));
		selenium.click("//table[@id='stp']/tbody/tr[7]/td[12]/a[3]");
		selenium.waitForPageToLoad("300000");
		verifyEquals("vmsc2-statistics: Stp Daily Report", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("STP Daily Report"));
		verifyTrue(selenium.isTextPresent("Báo cáo chi tiết của STPHC4E"));
		selenium.click("//img[@alt='...']");
		selenium.click("//div[@id='ui-datepicker-div']/div/a[1]/span");
		selenium.click("link=17");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: Stp Daily Report", selenium.getTitle());
		verifyEquals("17/09/2010", selenium.getValue("day"));
		verifyTrue(selenium.isTextPresent("Duration of unavailability of association for traffic (number of seconds)"));
		verifyTrue(selenium.isTextPresent("62948"));
		verifyTrue(selenium.isTextPresent("Duration of inaccessibility of destination (number of seconds)"));
		verifyTrue(selenium.isTextPresent("2237"));
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/ul/li[1]/a/span");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("STP Hourly Report"));
		verifyEquals("vmsc2-statistics: Stp Hourly Report", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Báo cáo giờ"));
		verifyTrue(selenium.isTextPresent("Báo cáo chi tiết của STPHC4E"));
		selenium.click("//img[@alt='...']");
		selenium.click("//div[@id='ui-datepicker-div']/div/a[1]/span");
		selenium.click("link=17");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("Duration of unavailability of association for traffic (number of seconds)"));
		verifyTrue(selenium.isTextPresent("3600"));
		verifyTrue(selenium.isTextPresent("Sigtrans utilization RPB-E (%)"));
		verifyTrue(selenium.isTextPresent("2057928508 %"));
		selenium.type("hour", "12");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("17/09/2010", selenium.getValue("day"));
		verifyEquals("12", selenium.getValue("hour"));
		verifyEquals("vmsc2-statistics: Stp Hourly Report", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("Duration of unavailability of association for traffic (number of seconds)"));
		verifyTrue(selenium.isTextPresent("3600"));
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/ul/li[3]/a/span");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: Stp Weekly Report", selenium.getTitle());
		verifyEquals("43", selenium.getValue("week"));
		verifyEquals("2010", selenium.getValue("year"));
		selenium.type("week", "39");
		selenium.click("submit");
		selenium.waitForPageToLoad("30000");
		verifyEquals("39", selenium.getValue("week"));
		verifyTrue(selenium.isTextPresent("Báo cáo tuần"));
		verifyTrue(selenium.isTextPresent("Báo cáo chi tiết của STPHC4E"));
		selenium.click("//div[@id='content']/table/tbody/tr[2]/td/ul/li[4]/a/span");
		selenium.waitForPageToLoad("30000");
		verifyEquals("vmsc2-statistics: Stp Monthly Report", selenium.getTitle());
		verifyTrue(selenium.isTextPresent("STP Monthly Report"));
		selenium.click("month");
		verifyEquals("9", selenium.getValue("month"));
		selenium.click("year");
		verifyEquals("2010", selenium.getValue("year"));
	}
}
