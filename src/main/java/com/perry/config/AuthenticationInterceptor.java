package com.perry.config;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.perry.domain.authentication.AuthenticationDomainServiceImpl;

@Named
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

	@Inject
	private AuthenticationDomainServiceImpl authenticationDomainService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getMethod().equals("OPTIONS")) {
			return true;
		}
		String clientId = getCookieValueByName("client_id", request.getCookies());
		if (clientId != null) {
			boolean isValidClient = authenticationDomainService.isValidClient(clientId);
			return isValidClient;
		}
		return true;
	}

	private String getCookieValueByName(String name, Cookie[] cookies) {
		if(cookies == null){
			logger.info("No Cookies Available.");
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				logger.info("Found ClientId: "+cookie.getValue());
				return cookie.getValue();
			}
		}
		logger.info("No ClientId found.");
		return null;

	}

}