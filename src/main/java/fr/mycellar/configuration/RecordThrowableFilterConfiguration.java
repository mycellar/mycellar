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

import javax.inject.Inject;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.mycellar.interfaces.web.RecordThrowableFilter;

/**
 * @author speralta
 */
@Configuration
public class RecordThrowableFilterConfiguration {

    @Inject
    private RecordThrowableFilter recordThrowableFilter;

    @Bean
    public FilterRegistrationBean recordThrowableFilter() {
        FilterRegistrationBean recordThrowableFilter = new FilterRegistrationBean();
        recordThrowableFilter.setFilter(this.recordThrowableFilter);
        return recordThrowableFilter;
    }

}
