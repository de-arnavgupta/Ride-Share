package com.saniya.ride_share.repository;

import com.saniya.ride_share.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RideRepository extends MongoRepository<Ride, String> {

    List<Ride> findByStatus(String status);

    List<Ride> findByUserId(String userId);
}

