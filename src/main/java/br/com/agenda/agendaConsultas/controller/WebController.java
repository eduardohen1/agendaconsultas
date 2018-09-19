package br.com.agenda.agendaConsultas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;

@Controller
public class WebController {
	@RequestMapping(value={"/login"})
	public String login(@AuthenticationPrincipal User user) {
		if(user != null) {
			return "redirect:/principal";
		}		
		return "login";
	}
}
