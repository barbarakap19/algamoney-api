package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableWebSecurity //habilitar segurança
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired // injetado
	// autenticaçao em memoria, significa que eu vou passar o usuario, senha e a permissão que o usuario podia ter
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override //sobrescrito
	//configuração de autorizaçao das nossas requisiçoes
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		// para categorias qualquer um pode acessar
				.antMatchers("/categorias").permitAll()
				// o resto precisa esta autenticada
				.anyRequest().authenticated()
				.and()
				//tipo de autenticaçao
			.httpBasic().and()
			//desabilitar criaçao de sessao no servidor
			// api rest nao mantenha estado de nada
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			// conseguir fazer sql injection do serviço web
			.csrf().disable();
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(true);
	}
}
