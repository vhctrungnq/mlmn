package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.vhc.vmsc2.statistics.dao.DownloadLogDAO;
import vn.com.vhc.vmsc2.statistics.domain.DownloadLog;
import vn.com.vhc.vmsc2.statistics.web.filter.TimeLog;


@Controller
public class DownloadLogController extends BaseController {

	private DownloadLogDAO downloadLogDao;

	@Autowired
	public void setDownloadLogDao(DownloadLogDAO DownloadLogDao) {
		this.downloadLogDao = DownloadLogDao;
	}

	@RequestMapping("/log/download")
	public String filter(@ModelAttribute("filter") TimeLog filter, Model model) {
		List<DownloadLog> downloadLogs = downloadLogDao.filter(filter);
		model.addAttribute("downloadLogList", downloadLogs);
		return "downloadLogList";
	}
}
