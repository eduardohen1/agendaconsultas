package br.com.agenda.agendaConsultas.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
		
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
		
		auth
			.jdbcAuthentication()
			.usersByUsernameQuery("SELECT username, password, enabled FROM usuario WHERE username = ?")
			.authoritiesByUsernameQuery("SELECT r.role, u.username FROM usuario u INNER JOIN usuario_roles r ON u.id = r.id_usuario WHERE u.username = ?")
			.dataSource(dataSource).passwordEncoder(passwordEncoder());
	 System.out.println(passwordEncoder().encode("123456").toString());
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
				.antMatchers("/principal").hasRole("USER")
				.antMatchers("/principal").hasRole("ADMIN")
				.antMatchers("/principal/**").hasRole("USER")
				.antMatchers("/principal/**").hasRole("ADMIN")
			    //.antMatchers("/principal").authenticated()
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
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
