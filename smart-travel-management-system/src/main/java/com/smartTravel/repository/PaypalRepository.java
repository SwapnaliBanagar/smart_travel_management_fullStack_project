package com.smartTravel.repository;

import com.smartTravel.entity.PaypalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaypalRepository extends JpaRepository<PaypalEntity,Long> {

    PaypalEntity findByOrderId(String orderId);
}
