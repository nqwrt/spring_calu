package edu.bit.ex.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.bit.ex.command.BCommand;
import edu.bit.ex.command.BContentCommand;
import edu.bit.ex.command.BDeleteCommand;
import edu.bit.ex.command.BListCommand;
import edu.bit.ex.command.BModifyCommand;
import edu.bit.ex.command.BReplyCommand;
import edu.bit.ex.command.BReplyViewCommand;
import edu.bit.ex.command.BWriteCommand;

@Controller
public class BController {
	
	BCommand command = null;
	
	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("list()");
		
		command = new BListCommand();
		command.execute(model);
		
		return "list"; 
		
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request,Model model) {
		
		System.out.println("content_view()");
		
		model.addAttribute("request",request);
		
		command = new BContentCommand();
		command.execute(model);
		
		return "content_view"; 
		
	}
	
	//request 객체가 필요한 이유 - bid 받아내기 위해서
	//Model model는 DAO 에 request 객체전달
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request,Model model) {
		
		System.out.println("delete()");
		
		model.addAttribute("request",request);
		
		command = new BDeleteCommand();
		command.execute(model);
		
		return "redirect:list"; 
		
	}
	
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		
		System.out.println("write_view()");
		
		
		return "write_view"; 
		
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request,Model model) {
		
		System.out.println("write()");
		
		model.addAttribute("request",request);
		
		command = new BWriteCommand();
		command.execute(model);
		
		return "redirect:list";		
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST )
	public String modify(HttpServletRequest request,Model model) {
		
		System.out.println("modify()");
		
		model.addAttribute("request",request);
		
		command = new BModifyCommand();
		command.execute(model);
		
		return "redirect:list";		
	}
	
	@RequestMapping(value="/reply_view")
	public String reply_view(HttpServletRequest request,Model model) {
		
		System.out.println("reply_view()");		
		model.addAttribute("request",request);
		
		command = new BReplyViewCommand();
		command.execute(model);
		
		return "reply_view";		
	}
	
	@RequestMapping(value="/reply")
	public String reply(HttpServletRequest request,Model model) {
		
		System.out.println("reply()");		
		model.addAttribute("request",request);
		
		command = new BReplyCommand();
		command.execute(model);
		
		return "redirect:list";		
	}
	
	
}
