package com.merak.lzptc.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.filter.GenericFilterBean;

import com.merak.lzptc.util.CookieUtils;

public class TokenPreAuthenticationFilter extends GenericFilterBean {

	private ResourceServerTokenServices tokenServices;

	public TokenPreAuthenticationFilter(ResourceServerTokenServices tokenServices) {
		this.tokenServices = tokenServices;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String token = CookieUtils.getCookie(request, TokenSessionAuthenticationStrategy.COOKIE_NAME);
		if (StringUtils.isNotEmpty(token)) {
			try {
				OAuth2Authentication result = tokenServices.loadAuthentication(token);
				request.setAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE, token);
				result.setDetails(new OAuth2AuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(result);
			} catch (Exception e) {
				// ignore
			}
		}
		chain.doFilter(request, response);
	}

}
