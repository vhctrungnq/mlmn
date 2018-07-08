package vn.com.vhc.vmsc2.statistics.web.mtcl.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HDistrictCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrMtclDistrictDAO;

@Controller
@RequestMapping("/report/mtcl/hr/*")
public class HrMtclDistrictController {
	
	@Autowired
	private HDistrictCodeDAO hDistrictCodeDAO;	
	@Autowired 
	private HrMtclDistrictDAO hrMtclDistrictDAO;
	
	@RequestMapping("list")
	public String list(@RequestParam(required = false) Date day, @RequestParam(required = false) Integer hour,
			@RequestParam(required = false) String province, @RequestParam(required = false) String district) {
		
		if (day == null) {
			day = new Date();
		}
		
		if (hour == null) {
			hour = 0;
		}
		
		return "mtcl/hrList";
	}
	
}
