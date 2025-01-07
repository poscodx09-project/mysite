package mysite.interceptor;
import mysite.service.SiteService;
import mysite.vo.SiteVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SiteInterceptor implements HandlerInterceptor {
    private LocaleResolver localeResolver;
    private SiteService siteService;
    public SiteInterceptor(LocaleResolver localeResolver, SiteService siteService) {
        this.localeResolver = localeResolver;
        this.siteService = siteService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // locale
        String lang = localeResolver.resolveLocale(request).getLanguage();
        request.setAttribute("lang", lang);

        SiteVo siteVo = siteService.getSite();
        request.setAttribute("siteVo",siteVo);

        System.out.println("[lang] " + lang);
        System.out.println("[SiteVo]" + siteVo.toString());

        return true;
    }
}