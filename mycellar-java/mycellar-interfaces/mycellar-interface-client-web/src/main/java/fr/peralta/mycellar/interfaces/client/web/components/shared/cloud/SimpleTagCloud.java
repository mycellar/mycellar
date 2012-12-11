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
package fr.peralta.mycellar.interfaces.client.web.components.shared.cloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.event.IEventSource;
import org.apache.wicket.model.IModel;

import fr.peralta.mycellar.domain.shared.repository.CountEnum;
import fr.peralta.mycellar.domain.shared.repository.FilterEnum;
import fr.peralta.mycellar.domain.shared.repository.SearchForm;
import fr.peralta.mycellar.interfaces.client.web.components.shared.SimpleComponent;

/**
 * @author speralta
 * 
 * @param <O>
 */
public abstract class SimpleTagCloud<O> extends SimpleComponent<O, TagCloudPanel<O>> implements
        TagDataProvider<O> {

    private static final long serialVersionUID = 201111161904L;

    private final CountEnum count;
    private final FilterEnum[] filters;

    /**
     * @param id
     * @param label
     * @param searchFormModel
     * @param count
     * @param filters
     */
    public SimpleTagCloud(String id, IModel<String> label, IModel<SearchForm> searchFormModel,
            CountEnum count, FilterEnum... filters) {
        super(id, label, searchFormModel);
        this.count = count;
        this.filters = filters;
        initializeIfUnique();
    }

    protected void initializeIfUnique() {
        Map<O, Long> choices = getChoices(getSearchFormModel().getObject(), count, filters);
        if (choices.size() == 1) {
            markAsValued(choices.keySet().iterator().next());
        }
    }

    /**
     * @param object
     * @return
     */
    protected String getSelectorLabelFor(O object) {
        return getRendererServiceFacade().render(object);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    protected O getModelObjectFromEvent(IEventSource source) {
        if (source instanceof Tag) {
            return (O) ((Tag<?>) source).getDefaultModelObject();
        } else {
            throw new WicketRuntimeException("Event did not come from Tag.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final TagCloudPanel<O> createSelectorComponent(String id) {
        return new TagCloudPanel<O>(id, new TagDataModel<O>(this));
    }

    /**
     * @return
     */
    @Override
    public final List<TagData<O>> getList() {
        Map<O, Long> choices;
        if (isReadyToSelect()) {
            choices = getChoices(getSearchFormModel().getObject(), count, filters);
        } else {
            choices = new HashMap<O, Long>();
        }
        List<TagData<O>> list = new ArrayList<TagData<O>>();
        long min = -1;
        long max = 0;
        for (long value : choices.values()) {
            if (min == -1) {
                min = value;
            } else {
                min = Math.min(min, value);
            }
            max = Math.max(max, value);
        }
        for (O object : choices.keySet()) {
            list.add(new TagData<O>(object, ((float) (choices.get(object) - min) / (float) Math
                    .max(1, max - min)) + 1, getSelectorLabelFor(object)));
        }
        Collections.sort(list, new TagDataComparator());
        return list;
    }

    /**
     * @return
     */
    protected abstract Map<O, Long> getChoices(SearchForm searchForm, CountEnum count,
            FilterEnum... filters);

}
