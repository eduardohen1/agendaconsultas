package br.com.agenda.agendaConsultas.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
		
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication().dataSource(dataSource)
			.usersByUsernameQuery("SELECT login, senha, tipo, ativo FROM usuario WHERE login = ?")
			.authoritiesByUsernameQuery("SELECT u.login, r.role FROM usuario u INNER JOIN usuario_roles r ON u.id = r.id_usuario WHERE u.login = ?");
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		   .antMatchers("/layout/**"); //tudo que puder acessar sem estar logado, é colocado aqui.
	}
		
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/prinpal").authenticated()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		http.exceptionHandling().accessDeniedPage("/403");		
	}	
	/**/
}
