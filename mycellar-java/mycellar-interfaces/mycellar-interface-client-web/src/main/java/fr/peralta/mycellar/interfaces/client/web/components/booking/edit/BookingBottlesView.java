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
package fr.peralta.mycellar.interfaces.client.web.components.booking.edit;

import java.util.Collection;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;

import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ActionLink;
import fr.peralta.mycellar.interfaces.client.web.components.shared.set.SetView;

@SuppressWarnings("unchecked")
class BookingBottlesView extends SetView<BookingBottle> {

    private static final long serialVersionUID = 201108082321L;

    /**
     * @param id
     */
    public BookingBottlesView(String id) {
        super(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void populateItem(final Item<BookingBottle> item) {
        item.setDefaultModel(new CompoundPropertyModel<BookingBottle>(item.getModelObject()));
        item.add(new Label("bottle.wine.appellation.region.country.name"));
        item.add(new Label("bottle.wine.appellation.region.name"));
        item.add(new Label("bottle.wine.appellation.name"));
        item.add(new Label("bottle.wine.producer.name"));
        item.add(new Label("bottle.wine.name"));
        item.add(new Label("bottle.wine.color"));
        item.add(new Label("bottle.wine.vintage"));
        item.add(new Label("bottle.format.name"));
        item.add(new Label("max"));
        item.add(new Label("price"));
        item.add(new WebMarkupContainer("remove").add(new ActionLink("editBottle", Action.SELECT))
                .add(new Link<Void>("removeBottle") {
                    private static final long serialVersionUID = 201205221145L;

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void onClick() {
                        addStateChange();

                        item.modelChanging();

                        // Remove item and invalidate listView
                        ((Collection<BookingBottle>) BookingBottlesView.this
                                .getDefaultModelObject()).remove(item.getModelObject());
                        item.getModelObject().setBookingEvent(null);

                        BookingBottlesView.this.modelChanged();
                        BookingBottlesView.this.removeAll();
                    }
                }).add(new Link<Void>("moveDownBottle") {
                    private static final long serialVersionUID = 201205221145L;

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void onClick() {
                        addStateChange();

                        item.modelChanging();

                        // Move down item and invalidate listView
                        Collection<BookingBottle> bottles = (Collection<BookingBottle>) BookingBottlesView.this
                                .getDefaultModelObject();
                        BookingBottle current = item.getModelObject();
                        BookingBottle previous = null;
                        for (BookingBottle bottle : bottles) {
                            if (bottle.getPosition().equals(current.getPosition() + 1)) {
                                previous = bottle;
                                break;
                            }
                        }
                        if (previous != null) {
                            bottles.remove(current);
                            bottles.remove(previous);
                            previous.setPosition(current.getPosition());
                            current.setPosition(current.getPosition() + 1);
                            bottles.add(current);
                            bottles.add(previous);
                        }

                        BookingBottlesView.this.modelChanged();
                        BookingBottlesView.this.removeAll();
                    }
                }).add(new Link<Void>("moveUpBottle") {
                    private static final long serialVersionUID = 201205221145L;

                    /**
                     * {@inheritDoc}
                     */
                    @Override
                    public void onClick() {
                        addStateChange();

                        item.modelChanging();

                        // Move up item and invalidate listView
                        Collection<BookingBottle> bottles = (Collection<BookingBottle>) BookingBottlesView.this
                                .getDefaultModelObject();
                        BookingBottle current = item.getModelObject();
                        BookingBottle next = null;
                        for (BookingBottle bottle : bottles) {
                            if (bottle.getPosition().equals(current.getPosition() - 1)) {
                                next = bottle;
                                break;
                            }
                        }
                        if (next != null) {
                            bottles.remove(current);
                            bottles.remove(next);
                            next.setPosition(current.getPosition());
                            current.setPosition(current.getPosition() - 1);
                            bottles.add(current);
                            bottles.add(next);
                        }

                        BookingBottlesView.this.modelChanged();
                        BookingBottlesView.this.removeAll();
                    }
                }));
    }
}