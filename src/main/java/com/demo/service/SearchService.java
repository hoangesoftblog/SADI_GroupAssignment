package com.demo.service;

import com.demo.model.Product;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortFieldContext;
import org.hibernate.search.query.dsl.sort.SortScoreContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class SearchService {
    public class SearchFilterBuilder {
        private HibernateSearch search;

        //Search term
        private String searchTerm;
        // Filter based on price, brands, categories
        private Long price_higher_than, price_lower_than;
        private Double rating_higher_than, rating_lower_than;
        private List<String> brands;
        private List<String> category_names;

        public SearchFilterBuilder(HibernateSearch search) {
            this.search = search;
        }

        public SearchFilterBuilder setSearchTerm(String searchTerm) {
            System.out.println(search);
            this.searchTerm = searchTerm;
            if (searchTerm.length() > 0) queries.add(search.search(searchTerm));
            return this;
        }

        public SearchFilterBuilder setPrice_higher_than(Long price_higher_than) {
            this.price_higher_than = price_higher_than;
            if (price_higher_than != null) queries.add(search.filterWithPriceLowerThan(price_higher_than));
            return this;
        }

        public SearchFilterBuilder setPrice_lower_than(Long price_lower_than) {
            this.price_lower_than = price_lower_than;
            if (price_lower_than != null) queries.add(search.filterWithPriceHigherThan(price_lower_than));
            return this;
        }

        public SearchFilterBuilder setRating_higher_than(Double rating_higher_than) {
            this.rating_higher_than = rating_higher_than;
            if (rating_higher_than != null) queries.add(search.filterWithRatingHigherTHan(rating_higher_than));
            return this;
        }

        public SearchFilterBuilder setRating_lower_than(Double rating_lower_than) {
            this.rating_lower_than = rating_lower_than;
            if (rating_lower_than != null) queries.add(search.filterWithRatingLowerThan(rating_lower_than));
            return this;
        }

        public SearchFilterBuilder setBrands(List<String> brands) {
            this.brands = brands;
            if (brands != null && brands.size() > 0) queries.add(search.filterWithBrands(brands));
            return this;
        }

        public SearchFilterBuilder setCategory_names(List<String> category_names) {
            this.category_names = category_names;
            if (category_names != null && category_names.size() > 0) queries.add(search.filterWithCategories(category_names));
            return this;
        }

        private List<Query> queries = new ArrayList<>();

        public List<Query> build() {
            return queries;
        }
    }

    @Autowired
    private HibernateSearch searchEngine;

    public List<Product> search(String searchTerm,
                                Long price_higher_than, Long price_lower_than,
                                Double rating_higher_than, Double rating_lower_than,
                                List<String> brands, List<String> category_names,
                                String by, String order) {
        List<Query> queryList = new SearchFilterBuilder(searchEngine)
                .setSearchTerm(searchTerm)
                .setPrice_higher_than(price_higher_than).setPrice_lower_than(price_lower_than)
                .setRating_higher_than(rating_higher_than).setRating_lower_than(rating_lower_than)
                .setBrands(brands).setCategory_names(category_names)
                .build();

        Query[] query_arr = new Query[queryList.size()];
        Query finalQuery = searchEngine.combineQuery(queryList.toArray(query_arr));

        Sort sort;
        if (by.equals("relevance")) {
            sort = searchEngine.sort_by_relevance(order);
        } else {
            sort = searchEngine.sort_by_field(by, order);
        }

        List<Product> products = (List<Product>) searchEngine.build(finalQuery).setSort(sort).getResultList();

        return products;
    }

    public List<Product> getMoreLikeThis(Product product) {
        Query q = searchEngine.moreLikeThis(product);
        Sort sort = searchEngine.sort_by_relevance("desc");
        List<Product> products = (List<Product>) searchEngine.build(q).setSort(sort).setMaxResults(5).setFirstResult(0).getResultList();
        return products;
    }


    @Service
    public static class HibernateSearch {
        private EntityManager entityManager;
        QueryBuilder qb;

        @Autowired
        public HibernateSearch(EntityManagerFactory entityManagerFactory) {
            this.entityManager = entityManagerFactory.createEntityManager();
        }

        @PostConstruct
        public void initializeHibernateSearch() {
            try {
                FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
                fullTextEntityManager.createIndexer().startAndWait();
                qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Product.class).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public Query search(String searchTerm) {
            Query query = qb.keyword()
//                    .fuzzy()
                    // This function allow to return value with maximum different = 1
                    // compared to the searchTerm
//                    .withEditDistanceUpTo(1)
//                    .withPrefixLength(1)
                    .onFields("name")
                    .matching(searchTerm).createQuery();

            return query;
        }

        public Query filterWithPriceLowerThan(Long price_higher_than) {
            Query query_LowerBounds = qb.range()
                    .onField("currentPrice")
                    .above(price_higher_than)
                    .createQuery();

            return query_LowerBounds;
        }

        public Query filterWithPriceHigherThan(Long price_lower_than){
            Query query_UpperBounds = qb.range()
                    .onField("currentPrice")
                    .below(price_lower_than)
                    .createQuery();
            return query_UpperBounds;
        }

        public Query filterWithBrands(List<String> brands) {
            Query query = qb.keyword()
                    .onField("brand")
                    .matching(String.join(" ", brands))
                    .createQuery();
            return query;
        }

        public Query filterWithCategories(List<String> category_names) {
            Query query = qb.keyword()
                    .onField("categories.name")
                    .matching(String.join(" ", category_names))
                    .createQuery();

            return query;
        }

        public Query filterWithRatingHigherTHan(Double rating_higher_than) {
            Query query_UpperBounds = qb.range()
                    .onField("rating")
                    .above(rating_higher_than)
                    .createQuery();
            return query_UpperBounds;
        }

        public Query filterWithRatingLowerThan(Double rating_lower_than) {
            Query queryLowerBounds = qb.range()
                    .onField("currentPrice")
                    .below(rating_lower_than)
                    .createQuery();
            return queryLowerBounds;
        }

        public Query moreLikeThis(Product exampleProduct){
            Query moreLikeThis = qb.moreLikeThis()
                    .excludeEntityUsedForComparison()
                    .comparingField("name")
                    .toEntity(exampleProduct)
                    .createQuery();

            return moreLikeThis;
        }

        public Query combineQuery(Query ...queries) {
            BooleanJunction combined = qb.bool();
            for (Query q: queries) {
                combined.must(q);
            }
            return combined.createQuery();
        }

        public Sort sort_by_relevance(String order){
            SortScoreContext sort = qb.sort().byScore();

            SortScoreContext sort2;
            if (order.toLowerCase().equals("asc")) sort2 = sort.asc();
            else sort2 = sort;

            return sort2.andByIndexOrder().createSort();
        }

        public Sort sort_by_field(String field, String order) {
            SortFieldContext sort = qb.sort().byField(field);

            SortFieldContext sort2;
            if (order.toLowerCase().equals("desc")) sort2 = sort.desc();
            else sort2 = sort;

            return sort2.andByIndexOrder().createSort();
        }

        public FullTextQuery build(Query query) {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

            org.hibernate.search.jpa.FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Product.class);

            return fullTextQuery;
        }

        public FullTextQuery pagination(FullTextQuery query, Integer firstResult, Integer maxResults) {
            return query.setFirstResult(firstResult).setMaxResults(maxResults);
        }
    }
}

