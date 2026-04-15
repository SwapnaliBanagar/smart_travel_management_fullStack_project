package com.smartTravel.controller;

import com.smartTravel.dto.PaypalDto;
import com.smartTravel.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/paypal")
@CrossOrigin(origins = "http://localhost:4200")
public class PaypalController {
    @Autowired
    PaypalService paypalService;

    @PostMapping("/create-order")
    public Map<String, String> createOrder(@RequestBody PaypalDto paypalDto) throws Exception
    {
       return paypalService.createOrder(paypalDto);
    }


    @PostMapping("/capture-order")
    public String captureOrder(@RequestParam String orderId) throws Exception {
        return paypalService.captureOrder(orderId);
    }

}
