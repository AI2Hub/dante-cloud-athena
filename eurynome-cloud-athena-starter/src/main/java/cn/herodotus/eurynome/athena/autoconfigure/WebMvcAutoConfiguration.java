package cn.herodotus.eurynome.athena.autoconfigure;

import com.google.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>Description: WebMvcAutoConfiguration </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 11:00
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class WebMvcAutoConfiguration implements WebMvcConfigurer {

    private final static String[] METHODS = new String[] { "GET", "POST", "PUT", "DELETE" };
    private final static String[] HEADERS = new String[] { HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE };

    /**
     * 多个WebSecurityConfigurerAdapter
     */
    @Configuration(proxyBeanMethods = false)
    @Order(101)
    public static class StaticResourceSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(WebSecurity web) throws Exception {
            log.debug("[Eurynome] |- Bean [Static Resource Web Security Configurer Adapter] Auto Configure.");
            web.ignoring().antMatchers(
                    "/error",
                    "/static/**",
                    "/swagger-ui/*",
                    "/swagger-resources/**",
                    "/v3/api-docs",
                    "/webjars/**",
                    "/components/**",
                    "/features/**",
                    "/favicon.ico");
        }
    }


    /**
     * 资源处理器
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
