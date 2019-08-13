package edu.bit.ex.emp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Servlet implementation class BoardFrontController
 */

@Controller
public class EmpController {

	@RequestMapping("/login")
	public String list(HttpServletRequest req, Model model) {
		System.out.println("login()");

		String name = req.getParameter("name");
		String number = req.getParameter("number");

		String result_number;
		EmpDao empDao = new EmpDao();

		 boolean isLogin = empDao.isLogin(name,number);
		 
		
		  result_number = empDao.login(name);
		  
		  if(result_number != null) 
		  { 
			  if(result_number.equals(number)) 
			  { 
				  // 로그인 성공
		  		  model.addAttribute("user", name); 
		  		  return "emp/index"; 
			  } else 
			  {
				  // 비밀번호 틀림
			  	 return "emp/login"; 
			  }
		  
		  } else { 
			  // 아이디 없음 
			  return "emp/login"; 
		  }
		 
		 
		  

		/*
		 * boolean isLogin = empDao.isLogin(name,number); if(isLogin == true) {
		 * model.addAttribute("user", name); return "emp/index"; } else { return
		 * "emp/login"; }
		 */

	}

}
