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
package fr.peralta.mycellar.interfaces.client.web.components;

import java.util.List;

import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import fr.peralta.mycellar.interfaces.facades.stock.dto.Bottle;

/**
 * @author speralta
 */
public class BottlesEditPanel extends Panel {

    private static final long serialVersionUID = -4215338295303606205L;

    private static enum Action {
        SAVE, ADD;
    }

    private static final class AddBottleLink extends Link<Void> {
        private static final long serialVersionUID = -4414882498885500855L;

        /**
         * @param id
         */
        public AddBottleLink(String id) {
            super(id);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onClick() {
            send(findParent(BottlesEditPanel.class), Broadcast.EXACT,
                    Action.ADD);
        }

    }

    private static final class BottleForm extends Form<Bottle> {
        private static final long serialVersionUID = 5193024912810513000L;

        /**
         * Default Constructor.
         */
        public BottleForm() {
            super("bottle", new CompoundPropertyModel<Bottle>(new Bottle()));
            add(new BottleEditPanel("newBottle"));
            add(new Button("saveBottle"));
            setVisibilityAllowed(false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onSubmit() {
            send(findParent(BottlesEditPanel.class), Broadcast.EXACT,
                    Action.SAVE);
        }
    }

    /**
     * @param id
     */
    public BottlesEditPanel(String id) {
        super(id);
        BottlesView bottlesView = new BottlesView("bottles");
        add(bottlesView);
        add(new AddBottleLink("addBottle"));
        add(new BottleForm());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(IEvent<?> event) {
        switch ((Action) event.getPayload()) {
        case ADD:
            get("bottle").setVisibilityAllowed(true);
            break;
        case SAVE:
            ((List<Bottle>) getDefaultModelObject()).add((Bottle) get("bottle")
                    .getDefaultModelObject());
            replace(new BottleForm());
            break;
        default:
            throw new NotImplementedException();
        }

    }
}
