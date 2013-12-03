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
package fr.mycellar.infrastructure.shared.search;

import org.apache.solr.analysis.ASCIIFoldingFilterFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.NGramTokenizerFactory;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.cfg.SearchMapping;

/**
 * This configuration is picked up by hibernate search using the
 * <code>hibernate.search.model_mapping</code> code in
 * <code>/META-INF/persistence.xml</code>
 */
public class SearchMappingFactory {
    @Factory
    public SearchMapping getSearchMapping() {
        SearchMapping mapping = new SearchMapping();
        mapping.analyzerDef("custom", NGramTokenizerFactory.class).tokenizerParam("maxGramSize", "40") //
                .filter(ASCIIFoldingFilterFactory.class) //
                .filter(LowerCaseFilterFactory.class);
        return mapping;
    }
}
