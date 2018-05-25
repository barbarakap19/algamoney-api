package com.example.algamoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	// vai pegar do usuario e a senha
	private AuthenticationManager authenticationManager;
	
	@Override
	//configurar aplicação(cliente)
	//autorizar o cliente(quem o usuario está usando)
	//autorizar o cliente a usar o authorization server
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			
			.withClient("angular")
			.secret("@ngul@r0")
			//limitar o acesso do cliente
			.scopes("read", "write")
			.authorizedGrantTypes("password", "refresh_token")
			//quanto tempo o tpken vai ficar ativo
			.accessTokenValiditySeconds(1800)
			.refreshTokenValiditySeconds(3600 * 24)
		
			.and()
				
				.withClient("mobile")
				.secret("m0b1l30")
				.scopes("read")
				.authorizedGrantTypes("password", "refresh_token")
				.accessTokenValiditySeconds(1800)
				.refreshTokenValiditySeconds(3600 * 24);
			}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		//o angular vem buscar o token
			.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter())
			.reuseRefreshTokens(false)
			.authenticationManager(authenticationManager);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks");
		return accessTokenConverter;
	}

	@Bean
	//onde armazenar o token
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
}
