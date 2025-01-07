package mysite.event;

import mysite.vo.SiteVo;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import mysite.service.SiteService;

@Component
public class ApplicationContextEventListener {

    @Autowired
    private ApplicationContext applicationContext;

    private static SiteVo siteInfo; // 전역적으로 사용할 Site 정보

    @EventListener({ContextRefreshedEvent.class})
    public void handlerContextRefreshedEvent() {
        System.out.println("-- Context Refreshed Event Received --");

        SiteService siteService = applicationContext.getBean(SiteService.class);
        SiteVo vo = siteService.getSite();

        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("title", vo.getTitle());
        propertyValues.add("welcome", vo.getWelcome());
        propertyValues.add("description", vo.getDescription());
        propertyValues.add("profile", vo.getProfile());

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(SiteVo.class);
        beanDefinition.setPropertyValues(propertyValues);

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
        registry.registerBeanDefinition("site", beanDefinition);
    }
}
