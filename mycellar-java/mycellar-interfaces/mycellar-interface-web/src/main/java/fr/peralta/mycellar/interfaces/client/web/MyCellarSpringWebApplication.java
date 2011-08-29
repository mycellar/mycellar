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
package fr.peralta.mycellar.interfaces.client.web;

import java.net.MalformedURLException;

import org.apache.wicket.WicketRuntimeException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wicketstuff.security.hive.HiveMind;
import org.wicketstuff.security.hive.config.PolicyFileHiveFactory;
import org.wicketstuff.security.hive.config.SwarmPolicyFileHiveFactory;

/**
 * @author speralta
 */
public class MyCellarSpringWebApplication extends MyCellarWebApplication {

    /**
     * {@inheritDoc}
     */
    @Override
    protected ApplicationContext getApplicationContext() {
        return new ClassPathXmlApplicationContext("context-interface-web.xml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setUpHive() {
        PolicyFileHiveFactory factory = new SwarmPolicyFileHiveFactory(getActionFactory());
        try {
            factory.addPolicyFile(getServletContext().getResource("/WEB-INF/mycellar.hive"));
        } catch (MalformedURLException e) {
            throw new WicketRuntimeException(e);
        }
        HiveMind.registerHive(getHiveKey(), factory);
    }

}
