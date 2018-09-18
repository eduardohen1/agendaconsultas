package br.com.agenda.agendaConsultas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/principal")
public class PrincipalController {

	@GetMapping
	public ModelAndView principal() {
		ModelAndView mv = new ModelAndView("principal/index");
		return mv;
	}
	
}
