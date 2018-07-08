package vn.com.vhc.vmsc2.statistics.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.UserDAO;
import vn.com.vhc.vmsc2.statistics.domain.User;


@Controller
public class UserProfileController extends BaseController {
	@Autowired
	private UserDAO userDAO;

	@RequestMapping(value = "/user/password", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {

		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userDAO.getByUsername(currentUser);

		model.addAttribute("user", user);

		return "userPassword";
	}

	@RequestMapping(value = "/user/password", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("User") User user, BindingResult result, @RequestParam(required = false) String passreenter,
			@RequestParam(required = false) String oldpass, @RequestParam(required = false) String password, HttpServletRequest request,Model model) {
		User us = userDAO.getByUsername(user.getUsername());
		PasswordEncoder encoder = new Md5PasswordEncoder();
		String hashedPass1 = encoder.encodePassword(oldpass, null);
		System.out.print("Mat khau cu:"+us.getPassword()+", Mat khau moi:"+hashedPass1);
		if (!hashedPass1.equals(us.getPassword())) {
			model.addAttribute("oldpasserror", "Mật khẩu cũ không đúng");
			model.addAttribute("passreenter", passreenter);
			model.addAttribute("oldpass", oldpass);
			model.addAttribute("password", password);
			model.addAttribute("user", user);
			return "userPassword";
		}
		if (!passreenter.equals(user.getPassword())) {
			model.addAttribute("oldpass", oldpass);
			model.addAttribute("passreentererror",  "Mật khẩu xác nhận không chính xác");
			model.addAttribute("user", user);
			return "userPassword";
		}
		if (password.equals("")) {
			model.addAttribute("oldpass", oldpass);
			model.addAttribute("password", password);
			model.addAttribute("user", user);
			model.addAttribute("passworderror",  "Chưa nhập mật khẩu mới");
			return "userPassword";
		}
		if (user.getPassword().isEmpty()) {
			User us1 = userDAO.selectByPrimaryKey(user.getUserId());
			user.setPassword(us1.getPassword());
		} else {
			String hashedPass = encoder.encodePassword(user.getPassword(), null);

			user.setPassword(hashedPass);
		}

		userDAO.updateByPrimaryKeySelective(user);
		saveMessage(request, "Cập nhật thành công.");
		return "redirect:password.htm?id=" + user.getUserId();
	}
}