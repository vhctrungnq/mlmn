package controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vo.SYS_PARAMETER;

import controller.BaseController;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;

/**
 * Function        : Doi mat khau
 * Created By      : BUIQUANG
 * Create Date     :
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/admin/doi-mat-khau/*")
public class AdminDoiMatKhauController extends BaseController {

	@Autowired
	private SysUsersDAO sysUsersDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@RequestMapping(value="danh-sach",  method = RequestMethod.GET)
	public String list(ModelMap model, HttpServletRequest request, HttpServletResponse response){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("username", username);
		try{
			List<SYS_PARAMETER> titleSystem = sysUsersDao.titleDoiMatKhau(); //sysParameterDao.getSPByCodeAndName("TITLE_SYSTEM", "DOI_MAT_KHAU");
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		catch(Exception e){}
		
		return "jspadmin/doiMatKhau";
	}
	
	@RequestMapping(value="danh-sach", method = RequestMethod.POST)
	public String submit(@RequestParam(required = false) String matKhauCu, 
			@RequestParam(required = false) String matKhauMoi,
			@RequestParam(required = false) String nhapLaiMK, ModelMap model, HttpServletRequest request, HttpServletResponse response){
		try{
			List<SYS_PARAMETER> titleSystem = sysUsersDao.titleDoiMatKhau(); //sysParameterDao.getSPByCodeAndName("TITLE_SYSTEM", "DOI_MAT_KHAU");
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		catch(Exception e){}
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("username", username);
		model.addAttribute("matKhauCu", matKhauCu);
		model.addAttribute("matKhauMoi", matKhauMoi);
		model.addAttribute("nhapLaiMK", nhapLaiMK);
		
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String hashedPass = encoder.encodePassword(matKhauCu, null);
		
		int count = sysUsersDao.selectByUsernamePassword(username, hashedPass);
		if(count > 0)
		{
			if(matKhauMoi.equals(nhapLaiMK))
			{
				String hashedPass1 = encoder.encodePassword(matKhauMoi, null);
				sysUsersDao.updatePasswordForUsername(username, hashedPass1);
				saveMessageKey(request, "message.doiMatKhau.success");
			}
			else
			{
				model.addAttribute("passError", "passError");
				saveMessageKey(request, "message.doiMatKhau.nhaplaiMKKoDung");
			}
		}
		else
		{
			saveMessageKey(request, "message.doiMatKhau.khongDungMK");
		}
		
		return "jspadmin/doiMatKhau";
	}
}
