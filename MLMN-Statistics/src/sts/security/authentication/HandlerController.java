package sts.security.authentication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import vn.com.vhc.vmsc2.statistics.dao.SysLogDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysMenuDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.SysLog;
import vn.com.vhc.vmsc2.statistics.domain.SysMenu;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.utils.AlarmSetting;
/**
 * Get menu html
 * @author Mr. QuangBD
 */
@Component
public class HandlerController extends HandlerInterceptorAdapter {

	@Autowired
	private SysUsersDAO sysUsersDao ;
	@Autowired
	private SysMenuDAO sysMenuDao;
	@Autowired 
	private SysLogDAO sysLogDAO;
	
	private static Logger logger = Logger.getLogger(HandlerController.class);
	private DateFormat df_time = new SimpleDateFormat("HH:mm:ss");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<SysUsers> user = sysUsersDao.getUserByUsername(username);
		boolean loginFlag = false ;
		
		try {
			loginFlag = (Boolean) RequestContextHolder.currentRequestAttributes().getAttribute("loginFlag", RequestAttributes.SCOPE_SESSION);
			
		} catch (Exception ex) {
			try {
				if(user.size() > 0){
					SysLog sysLog = new SysLog();
					
					sysLog.setUserId("STS");
					sysLog.setUserName(user.get(0).getUsername());
					sysLog.setSessionId((long)0);
					sysLog.setLastAction("Logon");
					sysLog.setLogonDay(new Date());
					sysLog.setLogonTime(df_time.format(new Date()));
					sysLog.setLogType("LOGON-LOGOUT");
					sysLog.setIpAddress(request.getRemoteAddr());
					sysLog.setHost(request.getRemoteHost());
					
					sysLogDAO.insert(sysLog);
					
					logger.info("INSERT LOG SUCCESS! Username: " + username + ", ip: " + sysLog.getIpAddress());
				
				}
				loginFlag = true;
				RequestContextHolder.currentRequestAttributes().setAttribute("loginFlag", loginFlag, RequestAttributes.SCOPE_SESSION);
				
			} catch (Exception e) {
				
			}
		}
		
		String requestURI = request.getRequestURI().toString();
		
		String system = "";
		if(RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION) != null)
			system = RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION).toString();
		if (system.equals("") && AlarmSetting._MODULE != null) {
			system = AlarmSetting._MODULE;
		}
		
		if (requestURI.indexOf("home.htm") >= 0 
				|| requestURI.indexOf("ajax") >= 0 
				|| requestURI.indexOf("form/phan-anh") >= 0 
				|| requestURI.indexOf("loginDefault") >= 0 
				|| requestURI.indexOf("temp") >= 0 
				|| requestURI.indexOf("map/list.htm") >= 0 
				|| requestURI.indexOf("help/list.htm") >= 0 
				|| requestURI.indexOf("report/bieu-do-giam-sat/") >= 0
				|| sysMenuDao.checkPermissionRequest(username, requestURI)) {
				
			
			
			return super.preHandle(request, response, handler);
		}
		
		logger.warn("Permission Request: " + username + " - " + requestURI);
		
		response.sendRedirect("VMSC6-Alarm-Feedback/loginDefault.htm");
		
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView model) throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String system = "";
		boolean globalDataFlag = false;
		try {
			if (RequestContextHolder.currentRequestAttributes().getAttribute("globalData", RequestAttributes.SCOPE_SESSION) != null) {
				globalDataFlag = true;
			}
			
			if(RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION) != null)
				system = RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION).toString();
			
		} catch(Exception ex) {
			logger.info("Chua ghi log globalData");
		}
		
		if (system.equals("") && AlarmSetting._MODULE != null) {
			system = AlarmSetting._MODULE;
		}
		
		// chua ghi log || load module moi
		if (!globalDataFlag || (AlarmSetting._MODULE!=null && !system.equals(AlarmSetting._MODULE))) {
			logger.info("Chua ghi log globalData");
			try{
				if (model != null && (request.getRequestURI().toString().indexOf("form/phan-anh") < 0)) {
					
					SysUsers user = sysUsersDao.selectByPrimaryKey(username);
					
					List<SysMenu> menuList = sysMenuDao.getMenuUserHtml(user.getUsername(), system);
					Map<String, Object> globalData = new HashMap<String, Object>();
					
					globalData.put("username", username);
					model.addObject("globalUser", user);
					
					String menuItem = getMenu(menuList);
					
			        globalData.put("menuItem", menuItem);
			        
			        model.addObject("globalData", globalData);
			        
			       
			        
			        RequestContextHolder.currentRequestAttributes().setAttribute("globalUser", user, RequestAttributes.SCOPE_SESSION);
			        RequestContextHolder.currentRequestAttributes().setAttribute("globalData", globalData, RequestAttributes.SCOPE_SESSION);
			   }
			}
			catch(Exception e){}
		}
		
		// luu lai module xu ly
		AlarmSetting._MODULE = system;
		
		super.postHandle(request, response, handler, model);
		
	}
	
	/**
	 * Get menu html
	 * @author Mr. ThangPX
	 * @param menuList
	 * @return menuTag (String menuTag is converted)
	 */
	private String getMenu(List<SysMenu> menuList)
	{
	
		String menuTag = "";
		menuTag = displayChildren(menuList,1, 1,menuTag);
		menuTag = menuTag.substring(4, menuTag.length()-5);
	    return menuTag;
	}
	/**
	 * Get menu childrend html
	 * @param menuList, level, parent, subMenu
	 * @return  menuTag
	 */
	private String displayChildren(List<SysMenu> menuList, int level, int parent, String subMenu)
	{
	    subMenu = "<ul>";
		List<SysMenu> subNodeList = null;
		subNodeList = getSubNode(menuList, parent);
		for(int i = 0; i < subNodeList.size(); i++)
		{
			int node = subNodeList.get(i).getId();
			if(checkParent(node, menuList) == false)
			{
				subMenu += "<li><a href='"+subNodeList.get(i).getAppCode()+"'>" +subNodeList.get(i).getMenuName()+ "</a></li>";
			}
			else
			{
				subMenu += "<li><a href='"+subNodeList.get(i).getAppCode()+"'>" +subNodeList.get(i).getMenuName()+ "</a>";
				subMenu += displayChildren(menuList, level + 1,subNodeList.get(i).getId(), subMenu);
				subMenu += "</li>";
			}
		}
		subMenu += "</ul>"; 
		return subMenu;
	}
	/**
	 * Check parentNode
	 * @param parent, menuList
	 * @return  menuTag
	 */
	private boolean checkParent(int node, List<SysMenu> menuList)
	{
		boolean flag = false;
		for(int i = 0 ; i < menuList.size(); i++)
		{
			
			int b = menuList.get(i).getIdHas();
			
			if(node== b)
			{
				flag = true;
				break;
			}
		}
		return flag;
	}
	/**
	 * Get subNode
	 * @param menuList, parent
	 * @return  menuTag
	 */
	private List<SysMenu> getSubNode(List<SysMenu> menuList, int parent)
	{
		List<SysMenu> listTmp = new ArrayList<SysMenu>();
		SysMenu sysmenu = null;
		for(int i = 0; i < menuList.size(); i++)
		{
			if(menuList.get(i).getIdHas()== parent)
			{
				sysmenu = menuList.get(i);
				listTmp.add(sysmenu);
			}
		}
		return listTmp;
	}
	
}