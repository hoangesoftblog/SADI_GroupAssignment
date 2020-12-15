package com.demo.repository;

import com.demo.model.PriceHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceHistoryStore extends CrudRepository<PriceHistory, Long> {
    @Override
    List<PriceHistory> findAll();
}
