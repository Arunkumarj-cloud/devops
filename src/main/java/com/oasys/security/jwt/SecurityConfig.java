package com.oasys.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//    private CustomerDetailsService customerService;
	
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

//    @Override
//	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//    	authenticationManagerBuilder.userDetailsService(customerService).passwordEncoder(passwordEncoder());
//	}
    
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
    
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(8);
//	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                    .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/usermanagement/changePassword/{id}").permitAll()
//
//                .antMatchers(HttpMethod.POST,"/users/create").permitAll()
//                .antMatchers(HttpMethod.POST,"/device-registration").permitAll()
//                .antMatchers(HttpMethod.POST,"/registration/getLazyList").permitAll()
                .antMatchers(HttpMethod.GET,"/merchanttype/get/{id}").permitAll() 
//                .antMatchers(HttpMethod.PUT,"/ticket/escalate").permitAll()
                .antMatchers(HttpMethod.GET,"/project-type/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/role/**").permitAll()
                .antMatchers(HttpMethod.GET,"/role/permissions/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/role/get/{id}").permitAll()
                .antMatchers(HttpMethod.GET,"/termsandconditions/getallactive").permitAll()
                .antMatchers(HttpMethod.GET,"/privacypolicy/getallactive").permitAll()
//        .antMatchers(HttpMethod.GET,"/pinCode/getByPinCode/{pincode}").permitAll()
//        .antMatchers(HttpMethod.POST,"/blacklisted-numbers/validate").permitAll()
//        .antMatchers(HttpMethod.POST,"/blacklisted-pan-numbers/validate").permitAll()  
				/* .antMatchers("/**").permitAll() */
				/* 
				 * .antMatchers(HttpMethod.GET,"/versionmanagement/getAllVersion").permitAll()
				 */

//                .antMatchers(HttpMethod.GET,"/users/getallamount").permitAll()
//				
//				  .antMatchers(HttpMethod.GET,"/versionmanagement/getAllVersion").permitAll()
//				 
//                .antMatchers(HttpMethod.POST,"/user/logout").permitAll()
//                .antMatchers(HttpMethod.PUT,"/device-registration/mapping").permitAll()
                    .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                        .permitAll()
                    .antMatchers("/authentication/**"
                    		,"/actuator/info/**","/actuator/**","/v2/api-docs",
                    		"/swagger-resources","/swagger-resources/**","/validatorUrl","/swagger-ui.html","/webjars/**","/hystrix/**",
                    		"/hystrix","*.stream","/hystrix.stream","/proxy.stream","/autuator/refresh","/refresh","/refresh/**")
                        .permitAll().anyRequest()
                        .authenticated().and()
                        .logout().invalidateHttpSession(true)
                        .clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //"/activity/**","/appModule/**","/designation/**","/roleMaster/**","/usermanager/**"
    }

}
