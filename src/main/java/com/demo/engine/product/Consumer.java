//package com.demo.engine.product;
//
//import com.demo.config.AppConfig;
//import com.demo.model.Product;
//import com.demo.service.ProductService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//
//import org.springframework.stereotype.Service;
//
//
//import java.io.IOException;
//
//@Service
//public class Consumer {
//    private final Logger logger = LoggerFactory.getLogger(Consumer.class);
//
//    @Autowired
//    private ProductService service;
//
//    @KafkaListener(topics = AppConfig.CONSTANT.Product.topic, groupId = AppConfig.CONSTANT.Product.groupId)
//    public void consume(Product product) throws IOException {
////        logger.info(String.format("Product -> Consumed product -> %s", product));
//
//        //Save product with product to database
//        service.add(product);
//
////        logger.info(String.format("#### -> ID product -> %s", product.getId()));
//    }
//
////    @KafkaListener(topics = AppConfig.CONSTANT.Product.topic, groupId = AppConfig.CONSTANT.Product.groupId)
////    public void consumer2(Product product) {
////
////    }
//}
