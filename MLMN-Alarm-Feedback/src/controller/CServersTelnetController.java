/*package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vo.CSeversTelnet;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import dao.CSeversTelnetDAO;
import dao.SYS_PARAMETERDAO;

@Controller
@RequestMapping("/cauhinh/telnet/*")
public class CServersTelnetController extends BaseController{

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private CSeversTelnetDAO cSeversTelnetDAO;
	
	private DateFormat df_full = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

	@RequestMapping("list")
	public ModelAndView list(
			@RequestParam(required = false) String serverName,
			@RequestParam(required = false) String telnetUser,
			@RequestParam(required = false) String alarmId,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String alarmType,
			@RequestParam(required = false) String command,
			@RequestParam(required = false) String node,
			Model model, HttpServletRequest request) {
		
		if (serverName==null) serverName="";
		else serverName = serverName.trim();
		
		if (telnetUser==null) telnetUser="";
		else telnetUser = telnetUser.trim();
		
		if (alarmId==null) alarmId="";
		else alarmId = alarmId.trim();
		
		if (alarmType==null) alarmType="";
		else alarmType = alarmType.trim();
		
		if (type==null) type="";
		else type = type.trim();
		
		if(command == null) command = "";
		else command = command.trim();
				
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		List<CSeversTelnet> serverTelnetList = new ArrayList<CSeversTelnet>();
		
		model.addAttribute("serverName", serverName);
		model.addAttribute("telnetUser", telnetUser);	
		model.addAttribute("command", command);
		model.addAttribute("alarmId", alarmId);		

		List<SYS_PARAMETER> alarmTypeList = sysParameterDao.getSysParameterByCode("TELNET_ALARM_TYPE");
		model.addAttribute("alarmTypeList", alarmTypeList);
		model.addAttribute("alarmType", alarmType);
		
		List<SYS_PARAMETER> nodeList = sysParameterDao.getSysParameterByCode("VENDOR");
		model.addAttribute("nodeList", nodeList);
		model.addAttribute("node", node);
		
		List<SYS_PARAMETER> typeList = sysParameterDao.getSysParameterByCode("TELNET_TYPE");
		model.addAttribute("typeList", typeList);
		model.addAttribute("type", type);
		
		serverTelnetList = cSeversTelnetDAO.getCServersTelnet(node,serverName, telnetUser, alarmId, type, alarmType, command, order, column);
		
		model.addAttribute("cSeversTelnet", serverTelnetList);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ServerTelnet" + dateNow;
		model.addAttribute("exportFileName", exportFileName);

		return new ModelAndView("jsp/cSeversTelnetList");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String serverId, ModelMap model, HttpServletRequest request) {
		
		SYS_PARAMETER titleS= sysParameterDao.getValueByCodeName("TITLE_SERVER_TELNET", "CAU_HINH_TELNET_EDIT");
		if (titleS!=null)
			model.addAttribute("titleF", titleS.getValue());
		else
			model.addAttribute("titleF", "TITLE_SERVER_TELNET.CAU_HINH_TELNET_EDIT");
		CSeversTelnet alarm = new CSeversTelnet();
		if(serverId!=null)
		{
			alarm = cSeversTelnetDAO.selectByPrimaryKey(Integer.parseInt(serverId));
		}
		
		model.addAttribute("description", alarm.getDescription());
		model.addAttribute("serverName", alarm.getServerName());
		model.addAttribute("ipAddress",alarm.getIpAddress());
		model.addAttribute("loginTime", alarm.getLoginTime());
		model.addAttribute("function",alarm.getFunction());
		model.addAttribute("deviceType",alarm.getDeviceType());
		model.addAttribute("telnetUser",alarm.getTelnetUser());
		model.addAttribute("alarmId", alarm.getAlarmName());
		model.addAttribute("telnetPass",alarm.getTelnetPassword());
		model.addAttribute("command",alarm.getCommand());
		model.addAttribute("alarmType",alarm.getAlarmType());
		model.addAttribute("type", alarm.getType());
		model.addAttribute("node", alarm.getNode());
		
		model.addAttribute("cServersTelnet", alarm);		
				
		List<SYS_PARAMETER> alarmTypeList = sysParameterDao.getSysParameterByCode("TELNET_ALARM_TYPE");
		model.addAttribute("alarmTypeList", alarmTypeList);
		
		List<SYS_PARAMETER> nodeList = sysParameterDao.getSysParameterByCode("VENDOR");
		model.addAttribute("nodeList", nodeList);
		
		List<SYS_PARAMETER> typeList = sysParameterDao.getSysParameterByCode("TELNET_TYPE");
		model.addAttribute("typeList", typeList);
		
		return "jsp/cSeversTelnetForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("cServersTelnet") @Valid CSeversTelnet cServersTelnet, BindingResult result,
			@RequestParam(required = false) String serverName,
			@RequestParam(required = false) String ipAddress,
			@RequestParam(required = false) String telnetUser,
			@RequestParam(required = false) String alarmId,
			@RequestParam(required = false) String loginTime,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String function,
			@RequestParam(required = false) String deviceType,
			@RequestParam(required = false) String telnetPass,
			@RequestParam(required = false) String alarmType,
			@RequestParam(required = false) String command,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String node,
			ModelMap model, HttpServletRequest request) throws ParseException {
		
		SYS_PARAMETER titleS= sysParameterDao.getValueByCodeName("TITLE_SERVER_TELNET", "CAU_HINH_TELNET_ADD");
		if (titleS!=null)
			model.addAttribute("titleF", titleS.getValue());
		else
			model.addAttribute("titleF", "TITLE_SERVER_TELNET.CAU_HINH_TELNET_ADD");
		
			model.addAttribute("cServersTelnet", cServersTelnet);	
			
			model.addAttribute("description", description);
			model.addAttribute("serverName", serverName);
			model.addAttribute("ipAddress",ipAddress);
			model.addAttribute("loginTime", loginTime);
			model.addAttribute("function",function);
			model.addAttribute("deviceType",deviceType);
			model.addAttribute("telnetUser",telnetUser);
			model.addAttribute("alarmId", alarmId);
			model.addAttribute("telnetPass",telnetPass);
			model.addAttribute("command", command);

		List<SYS_PARAMETER> alarmTypeList = sysParameterDao.getSysParameterByCode("TELNET_ALARM_TYPE");
		model.addAttribute("alarmTypeList", alarmTypeList);
		model.addAttribute("alarmType", alarmType);
		
		List<SYS_PARAMETER> nodeList = sysParameterDao.getSysParameterByCode("VENDOR");
		model.addAttribute("nodeList", nodeList);
		model.addAttribute("node", node);
		
		List<SYS_PARAMETER> typeList = sysParameterDao.getSysParameterByCode("TELNET_TYPE");
		model.addAttribute("typeList", typeList);
		model.addAttribute("type", type);
		
		if(loginTime == null || loginTime.equals("")){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 0);
			loginTime = df_full.format(cal.getTime());
		}
		
		if (loginTime!=null && DateTools.isValid("yyyy-MM-dd HH-mm-ss", loginTime)==false)
		{
			//model.addAttribute("errorsdate", "*");
			saveMessageKey(request, "loginTime.errorsdate");
			return "jsp/cSeversTelnetForm";
		}
		
		if (result.hasErrors()) {
			saveMessageKey(request, "alarmExtend.errorField");
			 return "jsp/cSeversTelnetForm";
		}
		else
		{
			if (cServersTelnet.getServerId()== null)
			{
					cSeversTelnetDAO.insertSelective(cServersTelnet);
					saveMessageKey(request, "Thêm mới thành công");
			}
					
			else{
					cSeversTelnetDAO.updateCServersTelnet(cServersTelnet);
					saveMessageKey(request, "Cập nhật thành công");	
				}
				
		}
		
		model.addAttribute("description", "");
		model.addAttribute("serverName", "");
		model.addAttribute("serverName", "");
		model.addAttribute("ipAddress","");
		model.addAttribute("loginTime", loginTime);
		model.addAttribute("function","");
		model.addAttribute("deviceType","");
		model.addAttribute("telnetUser","");
		model.addAttribute("alarmId", "");
		model.addAttribute("telnetPass","");
		model.addAttribute("command", "");
		model.addAttribute("node", "");
		model.addAttribute("type", "");
		model.addAttribute("alarmType", "");
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String serverId,
			HttpServletRequest request, Model model) {
		
			try {
				cSeversTelnetDAO.deleteCServersTelnet(Integer.parseInt(serverId));
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
			
			return "redirect:list.htm";
		}
	}
*/