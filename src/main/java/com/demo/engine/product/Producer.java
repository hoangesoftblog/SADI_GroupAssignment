package com.demo.engine.product;

import com.demo.config.AppConfig;
import com.demo.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;
import java.util.List;

@Service
public class Producer {

    @Value(value = AppConfig.CONSTANT.Product.topic)
    private String TOPIC;

    @Autowired
    private KafkaTemplate<String, Product> kafkaTemplate;

    public void add(Product product) {
        ListenableFuture<SendResult<String, Product>> future = this.kafkaTemplate.send(TOPIC, product);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Product>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(String.format("Failed to send \"{}\", because {} ", product, throwable.getMessage()));
            }

            @Override
            public void onSuccess(SendResult<String, Product> stringStringSendResult) {
                System.out.println(String.format("Product Producer -> Producing: {}", product));
            }
        });
    }

}
