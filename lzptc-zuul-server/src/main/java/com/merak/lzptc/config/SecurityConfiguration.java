package com.merak.lzptc.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableOAuth2Sso
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	ResourceServerTokenServices tokenServices;
	// @Autowired
	// OAuth2ClientAuthenticationProcessingFilter
	// oAuth2ClientAuthenticationProcessingFilter;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionAuthenticationStrategy(new TokenSessionAuthenticationStrategy());
		http.addFilterAt(new TokenPreAuthenticationFilter(tokenServices),
				AbstractPreAuthenticatedProcessingFilter.class);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated();
	}

	@Bean
	UserInfoRestTemplateCustomizer userInfoRestTemplateCustomizer(LoadBalancerInterceptor loadBalancerInterceptor) {
		return template -> {
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
			interceptors.add(loadBalancerInterceptor);
			AccessTokenProviderChain accessTokenProviderChain = Stream
					.of(new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(),
							new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider())
					.peek(tp -> tp.setInterceptors(interceptors))
					.collect(Collectors.collectingAndThen(Collectors.toList(), AccessTokenProviderChain::new));
			template.setAccessTokenProvider(accessTokenProviderChain);
		};
	}

}
