package com.smartTravel.serviceImpl;

import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import com.smartTravel.dto.PaypalDto;
import com.smartTravel.entity.PaypalEntity;
import com.smartTravel.entity.SeatEntity;
import com.smartTravel.repository.PaypalRepository;
import com.smartTravel.repository.SeatRepository;
import com.smartTravel.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaypalServiceImpl implements PaypalService {
    @Autowired
    private PayPalHttpClient payPalHttpClient;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    PaypalRepository paypalRepository;


    @Override
    public Map<String, String> createOrder(PaypalDto paypalDto) throws Exception {

        // Get Seats
        List<Long> seatIds = paypalDto.getSeatIds();
        System.out.println("Selected seat ids:"+seatIds);

        // 🔥 Validate seats
        List<SeatEntity> seats = seatRepository.findAllById(seatIds);

        for (SeatEntity seat : seats) {
            if (seat.getStatus().equals("Booked")) {
                throw new RuntimeException("Seat already booked: " + seat.getSeatNumber());
            }
        }


        // ✅ 3. Create PayPal Order
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        AmountWithBreakdown amountBreakdown = new AmountWithBreakdown()
                .currencyCode("USD")
                .value(String.valueOf(paypalDto.getAmount()));

        PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest()
                .amountWithBreakdown(amountBreakdown);

        orderRequest.purchaseUnits(List.of(purchaseUnit));

        OrdersCreateRequest request = new OrdersCreateRequest();
        request.requestBody(orderRequest);

        Order order = payPalHttpClient.execute(request).result();


        // ✅ 4. Save Payment in DB
        PaypalEntity payment = new PaypalEntity(
                order.id(),
                paypalDto.getAmount(),
                "USD",
                seats
        );
        System.out.println("Save Payment:" + payment.getAmount());

        paypalRepository.save(payment);

        // approve order
        String approveUrl = "";
        for (LinkDescription link : order.links()) {
            if ("approve".equalsIgnoreCase(link.rel())) {
                approveUrl = link.href();// 👈 return approval URL
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("orderId", order.id());
        System.out.println("Order ID:"+order.id());
        response.put("approveUrl", approveUrl);
        System.out.println("ApproveUrl"+approveUrl);

// ✅ 5. Return orderId and approveUrl
        return response;
    }


    public String captureOrder(String orderId) throws Exception {

        System.out.println("Inside captureOrder() method.... ");
        // ✅ 1. Capture payment from PayPal
//        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
//        request.requestBody(new OrderRequest());
//
//        Order order = payPalHttpClient.execute(request).result();

        // ✅ 2. Get payment from DB
        PaypalEntity payment = paypalRepository.findByOrderId(orderId);

        if (payment == null) {
            throw new RuntimeException("Payment not found");
        }

        List<SeatEntity> seats = payment.getSeats();

        // ✅ 3. Check payment status
        /*if ("COMPLETED".equals(order.status())) {

            payment.setStatus("SUCCESS");

            for (SeatEntity s : seats) {
                s.setStatus("BOOKED");
            }

        } else {

            payment.setStatus("FAILED");

            for (SeatEntity s : seats) {
                s.setStatus("AVAILABLE");
            }
        }*/

        payment.setStatus("SUCCESS");
        for (SeatEntity s : seats) {
            s.setStatus("BOOKED");
        }

        System.out.println("Payment successfully done:"+payment.getStatus());

        // ✅ 4. Save updates
        paypalRepository.save(payment);
        seatRepository.saveAll(seats);

        return payment.getStatus();
    }
}
