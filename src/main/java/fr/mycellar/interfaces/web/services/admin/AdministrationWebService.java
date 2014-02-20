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
package fr.mycellar.interfaces.web.services.admin;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author speralta
 */
@Named
@Singleton
@Path("/admin/administration")
public class AdministrationWebService {

    @Inject
    private DataSource dataSource;

    @GET
    @Path("database")
    public DatabaseDto getDabatabaseInfos() {
        String driver;
        String userName;
        String url;
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            driver = metaData.getDriverName();
            userName = metaData.getUserName();
            url = metaData.getURL();
        } catch (SQLException e) {
            driver = "";
            userName = "";
            url = "";
        }
        DatabaseDto result = new DatabaseDto();
        result.setDriver(driver);
        result.setUserName(userName);
        result.setUrl(url);
        return result;
    }

}
