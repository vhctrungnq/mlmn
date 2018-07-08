package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.ServerDAO;
import vn.com.vhc.vmsc2.statistics.dao.ServerDetailDAO;
import vn.com.vhc.vmsc2.statistics.domain.Server;
import vn.com.vhc.vmsc2.statistics.domain.ServerDetail;



@Controller
@RequestMapping("/system/ftpServer/*")
public class ServerController extends BaseController {
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

	@RequestMapping("list")
	public ModelAndView list(Model model) {
		List<Server> servers = serverDao.getAll();
		model.addAttribute("serverList", servers);
		return new ModelAndView("serverList");
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer serverId, ModelMap model, HttpServletResponse response) {

		Server server = (serverId == null) ? new Server() : serverDao.selectByPrimaryKey(serverId);
		model.addAttribute(server);
		
		if(serverId!=null){
			List<ServerDetail> serverDetailList = serverDetailDao.getByServerId(serverId);
			model.addAttribute(serverDetailList);
		}
		
		return "serverForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@Valid Server server, BindingResult result, HttpServletRequest request,Model model, HttpServletResponse response) {
		if (!result.hasErrors())
		{
			Server oldServer= serverDao.exitsServer(server.getServerName());
			if (server.getServerId() == null) {
				if (oldServer==null)
				{
					serverDao.insert(server);
					saveMessage(request, "Server được tạo thành công");
				}
				else
				{
					saveMessage(request, "Server đã tồn tại");
					model.addAttribute(server);
					return "serverForm";
				}
	
			} else {
				if (oldServer!=null && ! oldServer.getServerId().equals(server.getServerId()))
				{
					saveMessage(request, "Server đã tồn tại");
					model.addAttribute(server);
					return "serverForm";
				}
				serverDao.updateByPrimaryKey(server);
				saveMessage(request, "Server được cập nhật thành công");
			}
	
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute(server);
			return "serverForm";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) int serverId, HttpServletRequest request, ModelMap model) {
		try
		{
			serverDao.deleteByPrimaryKey(serverId);
			saveMessage(request, "Server đã được xóa");
		}catch(Exception e)
			{
			saveMessage(request, "Bạn không được phép bản ghi đã tồn tại Server Detail");
			}
		return "redirect:list.htm";
	}
}
