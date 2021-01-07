package com.demo.service;

import com.demo.model.Product;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.List;

@Service
public class SearchService implements RemoveDependency<Product> {

    @Autowired
    HibernateSearch searchEngine;

    public List<Product> search(String searchTerm) {
        List<Product> products = searchEngine.fuzzySearch(searchTerm);
        products.forEach(this::GetMethod_RemoveDependency);
        return products;
    }

    @Override
    public Product GetMethod_RemoveDependency(Product product) {
        product.getCategories().forEach(category -> {
            category.setProducts(null);
        });
        return product;
    }


    @Service
    public static class HibernateSearch {
        private EntityManager entityManager;

        @Autowired
        public HibernateSearch(EntityManagerFactory entityManagerFactory) {
            this.entityManager = entityManagerFactory.createEntityManager();
        }

        @PostConstruct
        public void initializeHibernateSearch() {
            try {
                FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
                fullTextEntityManager.createIndexer().startAndWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Transactional
        public List<Product> fuzzySearch(String searchTerm) {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
            QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Product.class).get();
            Query luceneQuery = qb.keyword().fuzzy().withEditDistanceUpTo(1).withPrefixLength(1).onFields("name")
                    .matching(searchTerm).createQuery();

            javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Product.class);

            // execute search
            List<Product> ProductList = null;
            try {
                ProductList = jpaQuery.getResultList();
            } catch (NoResultException nre) {
                // do nothing
            }
            return ProductList;
        }
    }
}

