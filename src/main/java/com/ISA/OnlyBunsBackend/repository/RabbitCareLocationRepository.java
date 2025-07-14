package com.ISA.OnlyBunsBackend.repository;


import com.ISA.OnlyBunsBackend.model.RabbitCareLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RabbitCareLocationRepository extends JpaRepository<RabbitCareLocation, Long> {
    //RabbitCareLocation findLocationMessageById(Long id);
}
