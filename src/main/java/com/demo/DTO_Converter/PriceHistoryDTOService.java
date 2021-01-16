//package com.demo.DTO_Converter;
//
//
//import com.demo.DTO.PriceHistoryDTO;
//import com.demo.model.PriceHistory;
//import com.demo.service.PriceHistoryService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PriceHistoryDTOService {
//    @Autowired
//    ModelMapper modelMapper;
//
//    public PriceHistoryDTO convert(PriceHistory history) {
//        PriceHistoryDTO p = modelMapper.map(history, PriceHistoryDTO.class);
//        return p;
//    }
//
//    @Autowired
//    PriceHistoryService priceHistoryService;
//}
