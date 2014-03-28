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
package fr.mycellar.infrastructure.shared.repository.query.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

import fr.mycellar.infrastructure.shared.repository.query.Path;
import fr.mycellar.infrastructure.shared.repository.query.Range;
import fr.mycellar.infrastructure.shared.repository.query.SearchBuilder;

/**
 * @author speralta
 */
public class RangesBuilder<FROM> extends AbstractBuilder<FROM> {

    private final List<Range<FROM, ?>> ranges;

    public RangesBuilder(SearchBuilder<FROM> searchParameters) {
        super(searchParameters);
        ranges = new ArrayList<>();
    }

    public RangesBuilder(SearchBuilder<FROM> searchParameters, List<Range<FROM, ?>> ranges) {
        super(searchParameters);
        this.ranges = ranges;
    }

    public List<Range<FROM, ?>> getRanges() {
        return Collections.unmodifiableList(ranges);
    }

    public <TO> RangeBuilder<FROM, FROM, TO> on(SingularAttribute<? super FROM, TO> attribute) {
        return new RangeBuilder<>(this, attribute);
    }

    public <TO> RangeBuilder<FROM, FROM, TO> on(PluralAttribute<? super FROM, ?, TO> attribute) {
        return new RangeBuilder<>(this, attribute);
    }

    public <TO extends Comparable<? super TO>> SearchBuilder<FROM> between(TO from, TO to, SingularAttribute<? super FROM, TO> attribute) {
        return between(from, to, new Path<FROM, TO>(attribute));
    }

    <TO extends Comparable<? super TO>> SearchBuilder<FROM> between(TO from, TO to, Path<FROM, TO> path) {
        ranges.add(new Range<FROM, TO>(from, to, path));
        return toSearchParameters();
    }

}
