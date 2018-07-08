package vn.com.vhc.vmsc2.statistics.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.ServerDAO;
import vn.com.vhc.vmsc2.statistics.dao.ServerDetailDAO;
import vn.com.vhc.vmsc2.statistics.domain.Server;
import vn.com.vhc.vmsc2.statistics.domain.ServerDetail;


@Controller
@RequestMapping("/system/ftpServerDetail/*")
public class ServerDetailController extends BaseController {
	private ServerDAO serverDao;
	private ServerDetailDAO serverDetailDao;

	@Autowired
	public void setServerDao(ServerDAO serverDao) {
		this.serverDao = serverDao;
	}

	@Autowired
	public void setServerDetailDao(ServerDetailDAO serverDetailDao) {
		this.serverDetailDao = serverDetailDao;
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = true) int serverId, @RequestParam(required = false) String baseDir, ModelMap model) {

		ServerDetail serverDetail = (baseDir == null) ? new ServerDetail() : serverDetailDao.selectByPrimaryKey(baseDir, serverId);
		model.addAttribute(serverDetail);

		Server server = serverDao.selectByPrimaryKey(serverId);
		model.addAttribute(server);

		return "serverDetailForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = true) int srvId, @Valid ServerDetail serverDetail,BindingResult result, HttpServletRequest request, Model model) {
		if (!result.hasErrors())
		{
			ServerDetail oldServerDetail=serverDetailDao.selectByPrimaryKey(serverDetail.getBaseDir(), serverDetail.getServerId());
			if (serverDetail.getServerId() == null && serverDetail.getBaseDir() == null) {
				if(oldServerDetail==null)
				{
					serverDetail.setServerId(srvId);
					serverDetailDao.insert(serverDetail);
					saveMessage(request, "Tạo ServerDetail thành công.");
				}
				else
				{
					saveMessage(request, "ServerDetail đã tồn tại");
					model.addAttribute(serverDetail);
					return "serverDetailForm";
				}
			} else {
				if (oldServerDetail!=null && !oldServerDetail.getServerId().equals(serverDetail.getServerId()) && !oldServerDetail.getBaseDir().equals(serverDetail.getBaseDir()))
				{
					saveMessage(request, "ServerDetail đã tồn tại");
					model.addAttribute(serverDetail);
					return "serverDetailForm";
				}
				serverDetailDao.updateByPrimaryKey(serverDetail);
				saveMessage(request, "Update server thành công.");
			}
			return "redirect:/system/ftpServer/list.htm?id=" + srvId;
		}
		else
		{
			model.addAttribute(serverDetail);
			return "serverDetailForm";
		}
	}


	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) int serverId, @RequestParam(required = true) String baseDir, HttpServletRequest request) {

		serverDetailDao.deleteByPrimaryKey(baseDir, serverId);
		saveMessage(request, "Xóa ServerDetail thành công.");

		return "redirect:/system/ftpServer/form.htm?id=" + serverId;
	}
}
