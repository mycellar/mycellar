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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.metamodel.Attribute;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.interfaces.web.services.ErrorHolder;

/**
 * @author speralta
 */
@Named
@Provider
@Singleton
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    private static Logger logger = LoggerFactory.getLogger(BusinessExceptionMapper.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Response toResponse(BusinessException exception) {
        logger.debug("BusinessException thrown in web service.", exception);
        List<String> properties = new ArrayList<String>();
        for (Attribute<?, ?> attribute : exception.getBusinessError().getProperties()) {
            properties.add(attribute.getName());
        }
        return Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON)
                .entity(new ErrorHolder(properties, exception.getBusinessError().getKey())).build();
    }

}
