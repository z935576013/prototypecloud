package com.merak.lzptc.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import com.merak.lzptc.util.CookieUtils;

public class TokenSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

	public static final String COOKIE_NAME = "USER_TOKEN";// 存放Token的cookie Key

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) throws SessionAuthenticationException {
		if (authentication != null) {
			Object obj = authentication.getDetails();
			if (OAuth2AuthenticationDetails.class.isInstance(obj)) {
				OAuth2AuthenticationDetails detail = (OAuth2AuthenticationDetails) obj;
				String accessToken = detail.getTokenValue();
				String token = CookieUtils.getCookie(request, COOKIE_NAME);
				if (accessToken != null && !accessToken.equals(token)) {
					CookieUtils.setCookie(response, COOKIE_NAME, accessToken, "/");
				}
			}
		}
	}

}
