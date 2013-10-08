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
package fr.mycellar.interfaces.web.json;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

import fr.mycellar.interfaces.web.services.InternalErrorHolder;
import fr.peralta.mycellar.interfaces.facades.stack.StackServiceFacade;

/**
 * @author speralta
 */
@Named
@Provider
@Singleton
public class ThrowableMapper implements ExceptionMapper<Throwable> {

    private static Logger logger = LoggerFactory.getLogger(ThrowableMapper.class);

    private StackServiceFacade stackServiceFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    public Response toResponse(Throwable throwable) {
        if (throwable instanceof AccessDeniedException) {
            return Response.status(Status.UNAUTHORIZED).build();
        }
        stackServiceFacade.onThrowable(throwable);
        logger.error("Throwable thrown in web service.", throwable);
        return Response.status(Status.INTERNAL_SERVER_ERROR).type(MediaType.APPLICATION_JSON)
                .entity(new InternalErrorHolder(throwable.getMessage() != null ? throwable.getMessage() : "Internal error.")).build();
    }

    /**
     * @param stackServiceFacade
     *            the stackServiceFacade to set
     */
    @Inject
    public void setStackServiceFacade(StackServiceFacade stackServiceFacade) {
        this.stackServiceFacade = stackServiceFacade;
    }

}
