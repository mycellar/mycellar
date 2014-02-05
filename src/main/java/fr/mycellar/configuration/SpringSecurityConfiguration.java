/*
 * Copyright 2011, MyCellar
 *
 * This file is part of MyCellar.
 *
 * MyCellar is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyCellar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyCellar. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.mycellar.configuration;

import java.security.SecureRandom;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;

import fr.mycellar.interfaces.web.security.MyCellarAuthenticationProvider;
import fr.mycellar.interfaces.web.security.RestAuthenticationEntryPoint;
import fr.mycellar.interfaces.web.security.SecurityContextTokenRepository;

/**
 * @author speralta
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String TOKEN_HEADER_NAME = "Rest-Token";

    @Inject
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Inject
    private SecurityContextTokenRepository securityContextTokenRepository;

    @Inject
    private MyCellarAuthenticationProvider myCellarAuthenticationProvider;

    @Bean
    public KeyBasedPersistenceTokenService keyBasedPersistenceTokenService() {
        KeyBasedPersistenceTokenService keyBasedPersistenceTokenService = new KeyBasedPersistenceTokenService();
        keyBasedPersistenceTokenService.setSecureRandom(new SecureRandom());
        keyBasedPersistenceTokenService.setServerInteger(1);
        keyBasedPersistenceTokenService.setServerSecret("_s_e_c_r_e_t_");
        return keyBasedPersistenceTokenService;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myCellarAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and() //
                .securityContext().securityContextRepository(securityContextTokenRepository).and() //
                .antMatcher("**") //
                .authorizeRequests() //
                .antMatchers("/api/admin/**").hasRole("ADMIN") //
                .antMatchers("/api/**").permitAll() //
                .antMatchers("/cellar/**").hasRole("CELLAR") //
                .antMatchers("/admin/**").hasRole("ADMIN") //
                .antMatchers("/booking/**").hasRole("BOOKING") //
                .antMatchers("/monitoring/**").hasRole("MONITORING");
    }
}
