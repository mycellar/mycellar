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
package fr.peralta.mycellar.interfaces.client.web.components.stack.data;

import java.util.Iterator;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import fr.peralta.mycellar.domain.shared.repository.OrderWayEnum;
import fr.peralta.mycellar.domain.stack.Stack;
import fr.peralta.mycellar.domain.stack.repository.StackOrder;
import fr.peralta.mycellar.domain.stack.repository.StackOrderEnum;
import fr.peralta.mycellar.interfaces.client.web.components.shared.data.MultipleSortableDataProvider;
import fr.peralta.mycellar.interfaces.facades.stack.StackServiceFacade;

/**
 * @author speralta
 */
public class StackDataProvider extends
        MultipleSortableDataProvider<Stack, StackOrderEnum, StackOrder> {

    private static final long serialVersionUID = 201111081450L;

    @SpringBean
    private StackServiceFacade stackServiceFacade;

    /**
     * Default constructor.
     */
    public StackDataProvider() {
        super(new StackOrder().add(StackOrderEnum.COUNT, OrderWayEnum.DESC));
        Injector.get().inject(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<? extends Stack> iterator(long first, long count) {
        return stackServiceFacade.getStacks(getState().getOrders(), first, count).iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long size() {
        return stackServiceFacade.countStacks();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IModel<Stack> model(Stack object) {
        return new Model<Stack>(object);
    }
}
