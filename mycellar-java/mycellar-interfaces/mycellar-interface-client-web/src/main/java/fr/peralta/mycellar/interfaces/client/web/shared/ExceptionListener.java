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
package fr.peralta.mycellar.interfaces.client.web.shared;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.interfaces.facades.stack.StackServiceFacade;

/**
 * @author speralta
 */
public class ExceptionListener extends AbstractRequestCycleListener {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionListener.class);

    @SpringBean
    private StackServiceFacade stackServiceFacade;

    /**
     * Default constructor.
     */
    public ExceptionListener() {
        Injector.get().inject(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IRequestHandler onException(RequestCycle cycle, Exception ex) {
        try {
            stackServiceFacade.onException(ex);
        } catch (Exception e) {
            logger.error("Error managing exception.", e);
        }
        return super.onException(cycle, ex);
    }

}
