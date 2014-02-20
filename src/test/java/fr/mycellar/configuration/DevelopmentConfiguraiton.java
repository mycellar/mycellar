/*
 * Copyright 2014, MyCellar
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

import java.util.Properties;

import javax.mail.Session;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * @author speralta
 */
@Configuration
@Profile("test")
public class DevelopmentConfiguraiton {

    @Bean
    public Session session() {
        Properties properties = new Properties();

        Session session = Session.getInstance(properties);
        return session;
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder() //
                .setType(EmbeddedDatabaseType.H2) //
                .setName("mycellar") //
                .addScript("file:./src/test/sql/create.sql") //
                .build();
    }

}
