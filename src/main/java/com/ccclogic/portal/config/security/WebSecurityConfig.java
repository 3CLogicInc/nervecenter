package com.ccclogic.portal.config.security;


import com.ccclogic.portal.config.ErrorCodeMessages;
import com.ccclogic.portal.config.security.authentication.OAuthAuthenticationProvider;
import com.ccclogic.portal.config.security.filters.ApplicationFilter;
import com.ccclogic.portal.config.security.filters.CCCLogicExceptionTranslationFilter;
import com.ccclogic.portal.config.security.filters.OAuthAccessTokenAuthenticationFilter;
import com.ccclogic.portal.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Autowired
    private ErrorCodeMessages errorCodeMessages;

    public WebSecurityConfig() {
        super(true);
    }

    /**
     * Stateless application, not using session to store authentication.NullSecurityContextRepository used
     * in place of default HttpSessionSecurityContextRepository.
     * <p>
     * CCCLogicAccessDeniedHandler is added to prevent spring boot from creating additional request to /error.
     * <p>
     * ApplicationFilter handles ServiceException or ClientException created in filter chain.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin/**").permitAll();
        http.authorizeRequests().antMatchers(urlsToIncludeInOAuth()).authenticated();
        http.authorizeRequests().antMatchers(urlsToExcludeFromOAuth()).anonymous();
        http.antMatcher("/api/**");
        http.authorizeRequests().anyRequest().authenticated();
        http.headers();
        http.anonymous();
        //http.cors();
        http.logout();
        http.addFilterAt(new SecurityContextPersistenceFilter(new NullSecurityContextRepository()), SecurityContextPersistenceFilter.class);
        http.addFilterBefore(OAuthAccessTokenAuthenticationFilter(), LogoutFilter.class);
        http.addFilterAt(new CCCLogicExceptionTranslationFilter(), ExceptionTranslationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**/swagger-ui.html");
        web.ignoring().antMatchers("/api/**/swagger-resources/**");
        web.ignoring().antMatchers("/api/**/springfox-swagger-ui/**");
        web.ignoring().antMatchers("/api/**/api-docs/**");
    }

    @Override
    protected AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> authProviders = new ArrayList<>();
        authProviders.add(new OAuthAuthenticationProvider());
        AuthenticationManager authManager = new ProviderManager(authProviders);
        return authManager;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(StringUtil.convertCsvToList(env.getProperty("cors.allowed.origins")));
        corsConfig.setAllowedMethods(StringUtil.convertCsvToList(env.getProperty("cors.allowed.methods")));
        corsConfig.setAllowedHeaders(StringUtil.convertCsvToList(env.getProperty("cors.allowed.headers")));
        source.registerCorsConfiguration("/callcenters/**", corsConfig);
        return source;
    }

    protected OAuthAccessTokenAuthenticationFilter OAuthAccessTokenAuthenticationFilter() {
        OAuthAccessTokenAuthenticationFilter oauthAccessTokenAuthenticationFilter = new OAuthAccessTokenAuthenticationFilter(authenticationManager());
        oauthAccessTokenAuthenticationFilter.setExcludeUrls(urlsToExcludeFromOAuth());
        oauthAccessTokenAuthenticationFilter.setIncludeUrls(urlsToIncludeInOAuth());
        return oauthAccessTokenAuthenticationFilter;
    }

    protected String[] urlsToExcludeFromOAuth() {
        return new String[]{"/api/**/users/tokens/refresh/**", "/api/**/users/tokens",
                "/api/**/activate/**", "/api/**/athenaQuery/**", "/api/**/service-token/**"};
    }

    protected String[] urlsToIncludeInOAuth() {
        return new String[]{"/api/**/ivrs/**/versions/**/activate/**"};
    }

    @Bean
    public FilterRegistrationBean registerCorsFilter() {
        FilterRegistrationBean reg = new FilterRegistrationBean(corsFilter());
        reg.setOrder(3);
        return reg;
    }

//    @Bean
//    public FilterRegistrationBean registerApplicationFilter() {
//        FilterRegistrationBean reg = new FilterRegistrationBean(applicationFilter());
//        reg.setOrder(4);
//        return reg;
//    }

    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    public ApplicationFilter applicationFilter() {
        return new ApplicationFilter(errorCodeMessages);
    }

}
