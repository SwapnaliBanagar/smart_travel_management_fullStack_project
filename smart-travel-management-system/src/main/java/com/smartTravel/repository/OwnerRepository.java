package com.smartTravel.repository;

import com.smartTravel.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity,Long> {

    Optional<OwnerEntity>findByUserName(String username);
    Optional<OwnerEntity>findByEmail(String email);
}
