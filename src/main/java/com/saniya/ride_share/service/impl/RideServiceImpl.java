package com.saniya.ride_share.service.impl;

import com.saniya.ride_share.dto.RideDtos;
import com.saniya.ride_share.exception.BadRequestException;
import com.saniya.ride_share.exception.NotFoundException;
import com.saniya.ride_share.model.Ride;
import com.saniya.ride_share.repository.RideRepository;
import com.saniya.ride_share.service.RideService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    public RideServiceImpl(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    @Override
    public Ride createRide(RideDtos.CreateRideRequest request, String userId) {
        Ride ride = new Ride();
        ride.setUserId(userId);
        ride.setPickupLocation(request.getPickupLocation());
        ride.setDropLocation(request.getDropLocation());
        ride.setStatus("REQUESTED");
        ride.setCreatedAt(Instant.now());
        return rideRepository.save(ride);
    }

    @Override
    public List<Ride> getRequestedRides() {
        return rideRepository.findByStatus("REQUESTED");
    }

    @Override
    public Ride acceptRide(String rideId, String driverId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));
        if (!"REQUESTED".equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not in REQUESTED status");
        }
        ride.setDriverId(driverId);
        ride.setStatus("ACCEPTED");
        return rideRepository.save(ride);
    }

    @Override
    public Ride completeRide(String rideId, String userId) {
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new NotFoundException("Ride not found"));
        if (!"ACCEPTED".equals(ride.getStatus())) {
            throw new BadRequestException("Ride is not in ACCEPTED status");
        }
        if (!userId.equals(ride.getUserId()) && !userId.equals(ride.getDriverId())) {
            throw new BadRequestException("Only user or driver can complete the ride");
        }
        ride.setStatus("COMPLETED");
        return rideRepository.save(ride);
    }

    @Override
    public List<Ride> getUserRides(String userId) {
        return rideRepository.findByUserId(userId);
    }
}

