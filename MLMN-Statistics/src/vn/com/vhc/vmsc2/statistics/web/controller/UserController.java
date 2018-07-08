package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.RoleDAO;
import vn.com.vhc.vmsc2.statistics.dao.UserDAO;
import vn.com.vhc.vmsc2.statistics.dao.UserRoleDAO;
import vn.com.vhc.vmsc2.statistics.domain.Role;
import vn.com.vhc.vmsc2.statistics.domain.User;
import vn.com.vhc.vmsc2.statistics.domain.UserRole;


@Controller
@RequestMapping("/user/*")
public class UserController extends BaseController {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private UserRoleDAO userRoleDAO;

	@RequestMapping("list")
	public ModelAndView list(Model model,@RequestParam(required = false) String fullName,@RequestParam(required = false) String department,HttpServletRequest request) {
		
		if (fullName==null)
			fullName="";
		if (department==null)
			department="";
		
		/*List<User> userList = userDAO.getAll();*/
		List<User> userList = userDAO.getAllByFullNameAndDepartment("%" + fullName + "%","%" + department + "%");
		model.addAttribute("userList", userList);
		return new ModelAndView("userList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request) {

		userDAO.deleteByPrimaryKey(id);
		saveMessage(request, "User đã được xóa thành công.");

		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {
		User user;

		if (id == null) {
			user = new User();
		} else {
			user = userDAO.selectByPrimaryKey(id);

			List<Role> roleList = roleDAO.getAll();
			model.addAttribute("roleList", roleList);

			List<Role> userRoles = userRoleDAO.getByUser(id);
			user.setRoles(userRoles);
		}

		model.addAttribute("user", user);
		model.addAttribute("passreenter", user.getPassword());
		return "userForm";
	}

	/*@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@Valid User user, BindingResult result, HttpServletRequest request,Model model) {
		user.setEnabled(true);
		if (!result.hasErrors())
		{
			model.addAttribute("user", user);
			return "userForm";
		}
		if (user.getUserId() == null) {
			PasswordEncoder encoder = new Md5PasswordEncoder();
			String hashedPass = encoder.encodePassword(user.getPassword(), null);
			
			user.setPassword(hashedPass);
			userDAO.insert(user);
			saveMessage(request, "Thêm mới thành công.");
		}
		else
		{
			if (user.getPassword().isEmpty()) {
				User us = userDAO.selectByPrimaryKey(user.getUserId());
				user.setPassword(us.getPassword());
			} else {
				PasswordEncoder encoder = new Md5PasswordEncoder();
				String hashedPass = encoder.encodePassword(user.getPassword(), null);

				user.setPassword(hashedPass);
			}

			userRoleDAO.deleteByUser(user.getUserId());
			for (Role r : user.getRoles()) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(r.getRoleId());
				userRole.setUserId(user.getUserId());

				userRoleDAO.insert(userRole);
			}

			userDAO.updateByPrimaryKey(user);
			saveMessage(request, "Cập nhật thành công.");
		}
		if(user.getUserId() == null)
		{
			return "redirect:list.htm";
		}
		else 
			return "redirect:form.htm?id=" + user.getUserId();
	}*/
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@Valid User user, BindingResult result, @RequestParam(required = false) String passreenter, @RequestParam(required = false) String password, HttpServletRequest request,Model model) {
		user.setEnabled(true);
		if (user.getUsername().equals("")||user.getPassword().equals(""))
		{
			saveMessage(request, "Nhập thiếu thông tin bắt buộc");
			model.addAttribute("user", user);
			model.addAttribute("passreenter", user.getPassword());
			return "userForm";
		}

		if (!passreenter.equals(user.getPassword())) {
			model.addAttribute("passreentererror", "Mật khẩu xác nhận không chính xác");
			model.addAttribute("user", user);
			return "userForm";
		}
		User us = userDAO.getByUsername(user.getUsername());
		if ((us!=null && user.getUserId()!=null && !us.getUserId().equals(user.getUserId()))||(us!=null && user.getUserId()==null))
		{
			saveMessage(request, "Tên đăng nhập đã tồn tại ");
			model.addAttribute("user", user);
			model.addAttribute("passreenter", user.getPassword());
			return "userForm";
		}
		if (user.getUserId() == null) {
			PasswordEncoder encoder = new Md5PasswordEncoder();
			String hashedPass = encoder.encodePassword(user.getPassword(), null);
			System.out.print("them moi");
			user.setPassword(hashedPass);
			userDAO.insert(user);
			saveMessage(request, "Thêm mới thành công.");
			return "redirect:list.htm";
		}
		else
		{  
			
			if (user.getPassword().isEmpty()) {
				User us1 = userDAO.selectByPrimaryKey(user.getUserId());
				user.setPassword(us1.getPassword());
			} else {
				PasswordEncoder encoder = new Md5PasswordEncoder();
				String hashedPass = encoder.encodePassword(user.getPassword(), null);

				user.setPassword(hashedPass);
			}
			try
				{
					userRoleDAO.deleteByUser(user.getUserId());
					for (Role r : user.getRoles()) {
						UserRole userRole = new UserRole();
						userRole.setRoleId(r.getRoleId());
						userRole.setUserId(user.getUserId());
		
						userRoleDAO.insert(userRole);
					}
				}
				catch (Exception ex) {
					
					saveMessage(request, "Cần phải chọn quyền cho người dùng ");
					model.addAttribute("user", user);
					model.addAttribute("passreenter", user.getPassword());
					return "userForm";
				}
			}
			userDAO.updateByPrimaryKey(user);
			saveMessage(request, "Cập nhật thành công.");
			return "redirect:list.htm";
		}
	@InitBinder
	protected void roleBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(List.class, "roles", new CustomCollectionEditor(List.class) {
			protected Object convertElement(Object element) {
				String id = null;

				if (element instanceof String) {
					id = (String) element;
				}

				return id != null ? roleDAO.selectByPrimaryKey(id) : null;
			}
		});
	}
}