package com.demo.service;

import com.demo.config.DataSource;
import com.demo.config.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestService {

    @Autowired
    public ProductService productService;

    @DataSource(DataSourceType.MASTER)
    public String save() {
        System.out.println("========save======");
        productService.delete(1L);
        return null;
    }

    @DataSource(DataSourceType.SLAVE)
    public String query() {
        System.out.println("=========query=====");
        System.out.println("Search result：" + productService.getAllWithPage(0));
        return null;
    }
}
