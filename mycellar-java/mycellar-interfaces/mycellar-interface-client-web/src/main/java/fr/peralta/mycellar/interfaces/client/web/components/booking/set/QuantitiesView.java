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
package fr.peralta.mycellar.interfaces.client.web.components.booking.set;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.form.select.Select;
import org.apache.wicket.extensions.markup.html.form.select.SelectOptions;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.peralta.mycellar.domain.booking.BookingBottle;
import fr.peralta.mycellar.interfaces.client.web.behaviors.OnEventModelChangedAjaxBehavior;
import fr.peralta.mycellar.interfaces.client.web.components.shared.Action;
import fr.peralta.mycellar.interfaces.client.web.components.shared.AjaxTool;
import fr.peralta.mycellar.interfaces.client.web.components.shared.ToStringRenderer;
import fr.peralta.mycellar.interfaces.client.web.components.shared.set.SetView;
import fr.peralta.mycellar.interfaces.client.web.renderers.RendererServiceFacade;
import fr.peralta.mycellar.interfaces.client.web.shared.LoggingHelper;

/**
 * @author speralta
 */
public class QuantitiesView extends SetView<BookingBottle> {

    /**
     * @author speralta
     */
    private static class LineTotal extends AbstractReadOnlyModel<String> {

        private static final long serialVersionUID = 201205300916L;

        private final IModel<BookingBottle> bookingBottleModel;
        private final IModel<Map<BookingBottle, Integer>> mapModel;

        @SpringBean
        private RendererServiceFacade rendererServiceFacade;

        /**
         * @param bookingBottleModel
         * @param mapModel
         */
        public LineTotal(IModel<BookingBottle> bookingBottleModel,
                IModel<Map<BookingBottle, Integer>> mapModel) {
            Injector.get().inject(this);
            this.bookingBottleModel = bookingBottleModel;
            this.mapModel = mapModel;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void detach() {
            super.detach();
            bookingBottleModel.detach();
            mapModel.detach();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getObject() {
            return rendererServiceFacade.render(bookingBottleModel.getObject().getPrice()
                    * mapModel.getObject().get(bookingBottleModel.getObject()));
        }

    }

    /**
     * @author speralta
     */
    private static class ValueModel implements IModel<Integer> {

        private static final long serialVersionUID = 201205300916L;

        private final IModel<BookingBottle> bookingBottleModel;
        private final IModel<Map<BookingBottle, Integer>> mapModel;

        /**
         * @param bookingBottleModel
         * @param mapModel
         */
        public ValueModel(IModel<BookingBottle> bookingBottleModel,
                IModel<Map<BookingBottle, Integer>> mapModel) {
            this.bookingBottleModel = bookingBottleModel;
            this.mapModel = mapModel;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void detach() {
            bookingBottleModel.detach();
            mapModel.detach();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Integer getObject() {
            return mapModel.getObject().get(bookingBottleModel.getObject());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setObject(Integer object) {
            mapModel.getObject().put(bookingBottleModel.getObject(), object);
        }

    }

    private static final long serialVersionUID = 201108082321L;
    private static final Logger logger = LoggerFactory.getLogger(QuantitiesView.class);

    @SpringBean
    private RendererServiceFacade rendererServiceFacade;

    private IModel<Map<BookingBottle, Integer>> mapModel;
    private final boolean readonly;

    /**
     * @param id
     * @param readonly
     */
    public QuantitiesView(String id, boolean readonly) {
        super(id);
        this.readonly = readonly;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void detachModels() {
        if (mapModel != null) {
            mapModel.detach();
        }
        super.detachModels();
    }

    /**
     * @param mapModel
     *            the mapModel to set
     * @return this for chaining
     */
    public QuantitiesView setMapModel(IModel<Map<BookingBottle, Integer>> mapModel) {
        this.mapModel = mapModel;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void populateItem(final Item<BookingBottle> item) {
        if (mapModel == null) {
            throw new WicketRuntimeException("MapModel not set.");
        }
        item.add(new ExternalLink("bottle", item.getModelObject().getUrl(), rendererServiceFacade
                .render(item.getModelObject().getBottle())));
        item.add(new Label("price", rendererServiceFacade.render(item.getModelObject().getPrice())));
        boolean maxSet = item.getModelObject().getMax() > 0;
        item.add(new NumberTextField<Integer>("valueInput", new ValueModel(item.getModel(),
                mapModel)).setType(Integer.class).setVisibilityAllowed(!readonly && !maxSet)
                .add(new OnEventModelChangedAjaxBehavior("onkeyup")));
        Select<Integer> select = new Select<Integer>("valueSelect", new ValueModel(item.getModel(),
                mapModel));
        List<Integer> numbers = new ArrayList<Integer>();
        for (int i = 0; i <= item.getModelObject().getMax(); i++) {
            numbers.add(i);
        }
        select.add(new SelectOptions<Integer>("options", numbers, new ToStringRenderer<Integer>()));
        select.add(new OnEventModelChangedAjaxBehavior("onclick"));
        item.add(select.setType(Integer.class).setVisibilityAllowed(!readonly && maxSet));
        item.add(new Label("value", new ValueModel(item.getModel(), mapModel))
                .setVisibilityAllowed(readonly));
        item.add(new Label("linePrice", new LineTotal(item.getModel(), mapModel))
                .setOutputMarkupId(true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(IEvent<?> event) {
        LoggingHelper.logEventReceived(logger, event);
        if (event.getPayload() instanceof Action) {
            Action action = (Action) event.getPayload();
            switch (action) {
            case MODEL_CHANGED:
                if (event.getSource() instanceof Component) {
                    Component source = (Component) event.getSource();
                    if ("valueInput".equals(source.getId()) || "valueSelect".equals(source.getId())) {
                        AjaxTool.ajaxReRender(source.getParent().get("linePrice"));
                    }
                }
                break;
            default:
                break;
            }
        }
        LoggingHelper.logEventProcessed(logger, event);
    }

}
