package com.arnavgpt.ride_share.service;

import com.arnavgpt.ride_share.dto.RideDtos;
import com.arnavgpt.ride_share.model.Ride;

import java.util.List;

public interface RideService {

    Ride createRide(RideDtos.CreateRideRequest request, String userId);

    List<Ride> getRequestedRides();

    Ride acceptRide(String rideId, String driverId);

    Ride completeRide(String rideId, String userId);

    List<Ride> getUserRides(String userId);
}

