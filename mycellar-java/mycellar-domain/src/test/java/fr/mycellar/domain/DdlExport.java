package fr.mycellar.domain;

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

import org.hibernate.tool.hbm2ddl.SchemaExport;

import fr.mycellar.domain.admin.Configuration;
import fr.mycellar.domain.booking.Booking;
import fr.mycellar.domain.booking.BookingBottle;
import fr.mycellar.domain.booking.BookingEvent;
import fr.mycellar.domain.contact.Contact;
import fr.mycellar.domain.image.Image;
import fr.mycellar.domain.position.Address;
import fr.mycellar.domain.position.Map;
import fr.mycellar.domain.position.Position;
import fr.mycellar.domain.shared.IdentifiedEntity;
import fr.mycellar.domain.shared.NamedEntity;
import fr.mycellar.domain.stack.Stack;
import fr.mycellar.domain.stock.Bottle;
import fr.mycellar.domain.stock.Cellar;
import fr.mycellar.domain.stock.CellarShare;
import fr.mycellar.domain.stock.Input;
import fr.mycellar.domain.stock.Output;
import fr.mycellar.domain.stock.Stock;
import fr.mycellar.domain.user.User;
import fr.mycellar.domain.wine.Appellation;
import fr.mycellar.domain.wine.Country;
import fr.mycellar.domain.wine.Format;
import fr.mycellar.domain.wine.Producer;
import fr.mycellar.domain.wine.Region;
import fr.mycellar.domain.wine.Varietal;
import fr.mycellar.domain.wine.Wine;

/**
 * @author speralta
 */
public class DdlExport {
    public static void main(String... args) {
        org.hibernate.cfg.Configuration cfg = new org.hibernate.cfg.Configuration();
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        cfg.addAnnotatedClass(Image.class);
        cfg.addAnnotatedClass(Address.class);
        cfg.addAnnotatedClass(Map.class);
        cfg.addAnnotatedClass(Position.class);
        cfg.addAnnotatedClass(IdentifiedEntity.class);
        cfg.addAnnotatedClass(NamedEntity.class);
        cfg.addAnnotatedClass(Bottle.class);
        cfg.addAnnotatedClass(Cellar.class);
        cfg.addAnnotatedClass(Input.class);
        cfg.addAnnotatedClass(Output.class);
        cfg.addAnnotatedClass(Stock.class);
        cfg.addAnnotatedClass(User.class);
        cfg.addAnnotatedClass(Appellation.class);
        cfg.addAnnotatedClass(Country.class);
        cfg.addAnnotatedClass(Format.class);
        cfg.addAnnotatedClass(Producer.class);
        cfg.addAnnotatedClass(Region.class);
        cfg.addAnnotatedClass(Varietal.class);
        cfg.addAnnotatedClass(Wine.class);
        cfg.addAnnotatedClass(Stack.class);
        cfg.addAnnotatedClass(CellarShare.class);
        cfg.addAnnotatedClass(Booking.class);
        cfg.addAnnotatedClass(BookingEvent.class);
        cfg.addAnnotatedClass(BookingBottle.class);
        cfg.addAnnotatedClass(Contact.class);
        cfg.addAnnotatedClass(Configuration.class);
        SchemaExport schemaExport = new SchemaExport(cfg);
        schemaExport.setDelimiter(";");
        schemaExport.execute(true, false, false, true);
    }
}
