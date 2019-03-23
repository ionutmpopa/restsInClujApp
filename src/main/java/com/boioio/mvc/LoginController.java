package com.boioio.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView("auth/login");
		
		return modelAndView;
	}

	@RequestMapping(value = "/access-denied", method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		ModelAndView modelAndView = new ModelAndView("auth/access-denied");

		return modelAndView;
	}
	
	
//	@RequestMapping("/onLogin")
//	public ModelAndView onLogin(String username, String pass,
//			HttpServletRequest request) {
//		ModelAndView modelAndView = new ModelAndView();
//		///use UserService to check the login
//		boolean loginWithSuccess =  true;
//		if (loginWithSuccess) {
//			ro.sci.ems.domain.User user = new ro.sci.ems.domain.User();
//			user.setUserName(userName);
//
//			request.getSession().setAttribute("currentUser", user);
//			modelAndView.setView(new RedirectView("/employee"));
//		}
//
//		return modelAndView;
//	}
//
//	@RequestMapping("/logout")
//	public String onLogout(HttpServletRequest request) {
//		request.getSession().invalidate();
//		return "/";
//	}
}
