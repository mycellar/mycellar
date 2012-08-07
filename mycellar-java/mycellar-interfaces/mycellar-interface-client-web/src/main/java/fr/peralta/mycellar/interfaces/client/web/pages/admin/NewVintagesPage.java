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
package fr.peralta.mycellar.interfaces.client.web.pages.admin;

import java.io.Serializable;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.domain.wine.Wine;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SearchFormModel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.ContainerVisibleFeedbackMessageFilter;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FeedbackPanel;
import fr.peralta.mycellar.interfaces.client.web.components.shared.feedback.FormComponentFeedbackBorder;
import fr.peralta.mycellar.interfaces.client.web.components.wine.list.WineSimpleList;
import fr.peralta.mycellar.interfaces.client.web.pages.shared.AdminSuperPage;
import fr.peralta.mycellar.interfaces.client.web.shared.FormValidationHelper;
import fr.peralta.mycellar.interfaces.facades.wine.WineServiceFacade;

/**
 * @author speralta
 */
public class NewVintagesPage extends AdminSuperPage {

    private static final long serialVersionUID = 201208020827L;

    private static class NewVintage implements Serializable {

        private static final long serialVersionUID = 201208020828L;

        private Wine wine;
        private int from;
        private int to;

    }

    private static class NewVintageForm extends Form<NewVintage> {

        private static final long serialVersionUID = 201208020833L;

        @SpringBean
        private WineServiceFacade wineServiceFacade;

        /**
         * @param id
         */
        public NewVintageForm(String id) {
            super(id, new CompoundPropertyModel<NewVintage>(new NewVintage()));
            add(new WineSimpleList("wine", new StringResourceModel("wine", null),
                    new SearchFormModel(new SearchForm()), CountEnum.WINE));
            add(new FormComponentFeedbackBorder("from").add(new NumberTextField<Integer>("from")
                    .setRequired(true)));
            add(new FormComponentFeedbackBorder("to").add(new NumberTextField<Integer>("to")));
            add(new FeedbackPanel("feedback", new ContainerVisibleFeedbackMessageFilter(this)));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected void onSubmit() {
            try {
                wineServiceFacade.createVintages(getModelObject().wine, getModelObject().from,
                        getModelObject().to);
            } catch (BusinessException e) {
                FormValidationHelper.error(this, e);
            }
        }

    }

    /**
     * @param parameters
     */
    public NewVintagesPage(PageParameters parameters) {
        super(parameters);
        add(new NewVintageForm("form"));
    }

}
