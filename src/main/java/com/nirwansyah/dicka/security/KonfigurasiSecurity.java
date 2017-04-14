/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nirwansyah.dicka.security;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author dickajava
 */

@EnableWebSecurity
@Configuration
public class KonfigurasiSecurity extends WebSecurityConfigurerAdapter{
    
    private static final String SQL_LOGIN 
            = "select username, password, active "
            + "as enabled from s_users where username=?";
    
    private static final String SQL_PERMISSION 
            = "select u.username, r.nama as authority "
            + "from s_users u join s_user_role ur on u.id = ur.id_user "
            + "join s_roles r on ur.id_role = r.id "
            + "where u.username=?";
    
    
    @Autowired private DataSource datasource;
    
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
        auth
                .jdbcAuthentication()
                .dataSource(datasource)
                .usersByUsernameQuery(SQL_LOGIN)
                .authoritiesByUsernameQuery(SQL_PERMISSION);
    }
}
