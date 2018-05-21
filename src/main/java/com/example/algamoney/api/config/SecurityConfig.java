package com.example.algamoney.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity //habilitar segurança
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	// autenticaçao em memoria, significa que eu vou passar o usuario, senha e a permissão que o usuario podia ter
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("ROLE");
	}
	
	@Override
	//configuração de autorizaçao das nossas requisiçoes
	protected void configure(HttpSecurity http) throws Exception {
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
}
