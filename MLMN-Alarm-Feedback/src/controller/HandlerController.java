package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import vo.AlShift;
import vo.SysLog;
import vo.SysMenu;
import vo.SysUsers;
import vo.alarm.utils.AlarmSetting;

import dao.AlShiftDAO;
import dao.SysLogDAO;
import dao.SysMenuDAO;
import dao.SysUsersDAO;
/**
 * Function        : Ghi log nguoi dang nhap, load menu khi dang nhap thanh cong
 * Created By      :
 * Create Date     :
 * Modified By     : BUIQUANG
 * Modify Date     : 26/11/2013
 * @author BUIQUANG
 * Description     :
 */
@Component
public class HandlerController extends HandlerInterceptorAdapter {

	@Autowired
	private SysUsersDAO sysUsersDao;
	@Autowired
	private SysMenuDAO sysMenuDao;
	@Autowired 
	private AlShiftDAO alShiftDAO;
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
		boolean loginFlag = false;
		
		try {
			loginFlag = (Boolean) RequestContextHolder.currentRequestAttributes().getAttribute("loginFlag", RequestAttributes.SCOPE_SESSION);
			
		} catch (Exception ex) {
			try {
				if(user.size() > 0){
					SysLog sysLog = new SysLog();
					
					sysLog.setUserId(AlarmSetting._MODULE);
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
				|| requestURI.indexOf("ban-do-tram") >= 0
				|| sysMenuDao.checkPermissionRequest(username, requestURI)) {
				
			return super.preHandle(request, response, handler);
		}
		
		logger.warn("Permission Request: " + username + " - " + requestURI);
		
		response.sendRedirect("mlmn/loginDefault.htm");
		
		return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		String system = "";
		boolean globalDataFlag = false;
		/*try {*/
			if (RequestContextHolder.currentRequestAttributes().getAttribute("globalData", RequestAttributes.SCOPE_SESSION) != null) {
				globalDataFlag = true;
			}
			
			if(RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION) != null)
				system = RequestContextHolder.currentRequestAttributes().getAttribute("module", RequestAttributes.SCOPE_SESSION).toString();
			
		/*} catch(Exception ex) {
			logger.info("Chua ghi log globalData");
		}*/
		
		if (system.equals("") && AlarmSetting._MODULE != null) {
			system = AlarmSetting._MODULE;
		}
		
		// chua ghi log || load module moi
		if (!globalDataFlag || (AlarmSetting._MODULE!=null && !system.equals(AlarmSetting._MODULE))) {
			logger.info("Chua ghi log globalData");
			try{
				//SysUsers user = sysUsersDao.selectByPrimaryKey(username);
				SysUsers user = sysUsersDao.selectByUsename(username); //AnhCTV.16/11/2015
				
				if (model != null && (request.getRequestURI().toString().indexOf("form/phan-anh") < 0)&&user!=null) {
					
					List<SysMenu> menuList = sysMenuDao.getMenuUserHtml(user.getUsername(), system);
					Map<String, Object> globalData = new HashMap<String, Object>();
					
					globalData.put("username", username);
					model.addObject("globalUser", user);
					
					String menuItem = getMenu(menuList);
					
			        globalData.put("menuItem", menuItem);
			        
			        model.addObject("globalData", globalData);
			        
			        // Ca trực
			        AlShift shift = alShiftDAO.getCaTrucGanNhat(user.getRegion());
			    //    System.out.println("ca truc:"+shift.getGiaoUsername());
			        model.addObject("globalCaTruc", shift);
			        
			        RequestContextHolder.currentRequestAttributes().setAttribute("globalUser", user, RequestAttributes.SCOPE_SESSION);
			        RequestContextHolder.currentRequestAttributes().setAttribute("globalData", globalData, RequestAttributes.SCOPE_SESSION);
			        RequestContextHolder.currentRequestAttributes().setAttribute("globalCaTruc", shift, RequestAttributes.SCOPE_SESSION);
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
	 * @param menuList
	 * @return
	 */
	private String getMenu(List<SysMenu> menuList){
		
		String v_tem = "<li>";
		String v_menu = "";
		int v_level_1 = 0;
	      
		String v_appCode_2 = null;
		int v_level_2 = 0;
		String v_m_name_2 = null;
		
		for (SysMenu item: menuList) {
			if (v_level_1 == 0) {
				v_level_1   = item.getLevel();
	            v_level_2   = item.getLevel();
	            
	            v_m_name_2  = item.getMenuName();
	            
	            v_appCode_2 = item.getAppCode();
			} else {
				v_level_1   = v_level_2;
	            
	            v_level_2   = item.getLevel();
	            v_appCode_2 = item.getAppCode();
	            v_m_name_2  = item.getMenuName();
	            
	            if (v_level_2 > v_level_1) {
	                v_tem   = "<ul style='width: 200px;'><li>";
	            }
	            else if (v_level_2 == v_level_1) {
	                v_tem   = "</li><li>";
	            }
	            else {
	                v_tem   = "";
	                for (int i=v_level_2; i<v_level_1; i++) {
	                	v_tem   += "</li></ul>";
	                }
	                v_tem   += "</li><li>";
	            }
			}
			
			v_menu      += v_tem + "<a href=" + v_appCode_2 + ">" + v_m_name_2 + "</a>";
		}
		
		v_tem = "";
		for (int i=1; i<=v_level_2;i++) {
			if (i<v_level_2) {
				v_tem   += "</li></ul>";
			} else if (i==v_level_2) {
				v_tem   += "</li>";
			}
		}
  
		v_menu    += v_tem;
		
		return v_menu;
	}
	
}