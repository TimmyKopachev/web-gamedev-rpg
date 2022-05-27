package org.web.gamedev.rpg.forum.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import lombok.Data;

public class QuerydslFilterBuilder {

    static {

    }

    private Collection<AttributeFilter> attributeFilters = new HashSet();

    public QuerydslFilterBuilder() {
    }

    public QuerydslFilterBuilder add(AttributeFilter attributeFilter) {
        if (attributeFilter.getValues() != null && !attributeFilter.getValues().isEmpty()) {
            this.attributeFilters.add(attributeFilter);
        }
        return this;
    }

    public BooleanExpression build() {
        Set<BooleanExpression> attributeFilterExpressions = new HashSet();
        this.attributeFilters.stream().forEach((attributeFilter) -> {
            BooleanExpression attributeFilterExpression = attributeFilter.getValues().stream().map((value) -> {
                return attributeFilter.getFunction().apply(value);
            }).reduce(BooleanExpression::or).get();
            attributeFilterExpressions.add(attributeFilterExpression);
        });
        return attributeFilterExpressions.stream().reduce(BooleanExpression::and).orElse(null);
    }

    @Data
    public static class AttributeFilter {
        private Function function;
        private Collection values;

        public <T> AttributeFilter(Function<T, BooleanExpression> function, Collection<T> values) {
            this.function = function;
            this.values = values;
        }

        public Function<Object, BooleanExpression> getFunction() {
            return this.function;
        }

        public Collection<Object> getValues() {
            return this.values;
        }

        public void setFunction(final Function function) {
            this.function = function;
        }

        public void setValues(final Collection values) {
            this.values = values;
        }
    }
}
