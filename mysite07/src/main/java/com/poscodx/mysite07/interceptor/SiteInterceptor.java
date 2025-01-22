package com.poscodx.mysite07.interceptor;

import com.poscodx.mysite07.service.SiteService;
import com.poscodx.mysite07.vo.SiteVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

public class SiteInterceptor implements HandlerInterceptor {
	@Autowired
	private LocaleResolver localeResolver;
	
	@Autowired
	private SiteService siteService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		SiteVo siteVo = (SiteVo)request.getServletContext().getAttribute("siteVo");
		
		if(siteVo == null) {
			siteVo = siteService.getSite();
			request.getServletContext().setAttribute("siteVo", siteVo);
		}
		
		// locale
		String lang = localeResolver.resolveLocale(request).getLanguage();
		request.setAttribute("lang", lang);
		
		return true;
	}

}
