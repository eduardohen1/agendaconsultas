package br.com.agenda.agendaConsultas.config;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class MyCsrfRequestMatcher implements RequestMatcher {
	
	@Override
	public boolean matches(HttpServletRequest request){
		return !request.getServletPath().equals("/login");
	}
	
}
