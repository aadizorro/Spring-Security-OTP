package com.java.nikitchem.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.java.nikitchem.serviceImpl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	/*
	 * @Bean(BeanIds.AUTHENTICATION_MANAGER)
	 * 
	 * @Override public AuthenticationManager authenticationManagerBean() throws
	 * Exception { return super.authenticationManagerBean(); }
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/auth", "/authenticate").permitAll()
				.anyRequest().authenticated()/*
												 * .and() .exceptionHandling().authenticationEntryPoint(restEntryPoint)
												 */
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		/*
		 * http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.
		 * STATELESS).and().cors().and().csrf()
		 * .disable().formLogin().disable().httpBasic().disable().exceptionHandling()
		 * .and().authorizeRequests()
		 */
		// .antMatchers("/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif",
		// "/**/*.svg", "/**/*.jpg",
		// "/**/*.html", "/**/*.css", "/**/*.js")
		// .permitAll().antMatchers("auth",
		// "/oauth2/**","authenticate").permitAll().anyRequest().authenticated()

		/*
		 * .and() .oauth2Login() .authorizationEndpoint() .baseUri("/oauth2/authorize")
		 * .authorizationRequestRepository(cookieAuthorizationRequestRepository())
		 * .and() .redirectionEndpoint() .baseUri("/oauth2/callback/*") .and()
		 * .userInfoEndpoint() .userService(customOAuth2UserService) .and()
		 * .successHandler(oAuth2AuthenticationSuccessHandler)
		 * .failureHandler(oAuth2AuthenticationFailureHandler);
		 */

		// Add our custom Token based authentication filter
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
}
