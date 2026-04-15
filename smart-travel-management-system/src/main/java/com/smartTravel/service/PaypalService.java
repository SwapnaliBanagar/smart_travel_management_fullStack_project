package com.smartTravel.service;
import com.smartTravel.dto.PaypalDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
public interface PaypalService {

    public Map<String, String> createOrder(PaypalDto paypalDto) throws Exception;
    public String captureOrder( String orderId) throws Exception;

}
