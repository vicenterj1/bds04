package com.devsuperior.bds04.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Autowired
	private Environment env;
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	private static final String[] PUBLIC = { "/oauth/token" , "/h2-console/**" };
	
	private static final String[] OPERATOR_GET = { "/cities/**", "/events/**" };

	private static final String[] OPERATOR_POST = { "/events/**" };
	// decodificar e analisar o token ( se é válido p. ex.)
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
			
		//H2 - para liberar o banco de dados de autorização
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll()
		//Neste sistema, somente as rotas de leitura (GET) de eventos e cidades são públicas (não precisa de login).
		//.antMatchers(HttpMethod.GET, OPERATOR_GET).hasAnyRole("CLIENT","ADMIN")
		//Usuários CLIENT podem também inserir (POST) novos eventos.
		.antMatchers(HttpMethod.POST, OPERATOR_POST).hasAnyRole("CLIENT")
		// Os demais acessos são permitidos apenas a usuários ADMIN.
		.anyRequest().hasAnyRole("ADMIN");
		
	}

	
}
