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
package fr.mycellar.interfaces.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mycellar.interfaces.facades.stack.StackServiceFacade;

/**
 * @author speralta
 */
@Singleton
@Named
public class RecordThrowableFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(RecordThrowableFilter.class);

    private StackServiceFacade stackServiceFacade;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Throwable throwable) {
            if (logger.isDebugEnabled()) {
                logger.debug("Throwable in filter.", throwable);
            }
            stackServiceFacade.onThrowable(throwable);
            throw throwable;
        }
    }

    @Override
    public void destroy() {
    }

    @Inject
    public void setStackServiceFacade(StackServiceFacade stackServiceFacade) {
        this.stackServiceFacade = stackServiceFacade;
    }

}
