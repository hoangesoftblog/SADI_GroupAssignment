package com.demo.config;

import com.demo.service.ProductService;
import com.demo.model.RandomInterface;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    public static class CONSTANT {
        public static class Product {
            public final static String groupId = "product-groupId";
            public static final String get_groupId = "product-get-groupId";
            public final static String topic = "product";

//            // Real max length of varchar
            public static final int max_length = 65535;
//            public static final int max_length = 1023;
        }

        public static class Message {
            public final static String groupId = "message-groupId";
            public final static String topic = "message";
        }

        public static class Pagination {
            public final static int page_size = 20;
        }

        public static class Route {
            public final static String login = "";
        }
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Primary
    @Bean
    public RandomInterface getProductServiceOrProducer(){
        RandomInterface randomInterface;
        try {
            Class.forName("com.demo.engine.product.Consumer");
            randomInterface = new com.demo.engine.product.Producer();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            randomInterface = new ProductService();
        }
        return randomInterface;
    }

    @Bean
    public PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
