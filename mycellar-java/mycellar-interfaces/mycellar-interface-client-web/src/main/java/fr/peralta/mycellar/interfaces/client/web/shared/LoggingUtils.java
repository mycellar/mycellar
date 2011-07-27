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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.IEvent;
import org.slf4j.Logger;

/**
 * @author speralta
 */
public class LoggingUtils {

    /**
     * @param logger
     * @param event
     */
    public static void logEventReceived(Logger logger, IEvent<?> event) {
        if (logger.isTraceEnabled() && !(event.getPayload() instanceof AjaxRequestTarget)) {
            logger.trace(
                    "Event[{}] {} received from {}, payload : {}",
                    new Object[] { event.toString(), event.getType(), event.getSource(),
                            event.getPayload() });
        }
    }

    /**
     * @param logger
     * @param event
     */
    public static void logEventProcessed(Logger logger, IEvent<?> event) {
        if (logger.isTraceEnabled() && !(event.getPayload() instanceof AjaxRequestTarget)) {
            logger.trace("Event[{}] processed.", new Object[] { event.toString() });
        }
    }

    /**
     * Refuse instanciation.
     */
    private LoggingUtils() {
        throw new IllegalStateException("Util class might not be instanciated.");
    }

}
