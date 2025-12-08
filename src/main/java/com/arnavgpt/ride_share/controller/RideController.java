package com.arnavgpt.ride_share.controller;

import com.arnavgpt.ride_share.dto.RideDtos;
import com.arnavgpt.ride_share.model.Ride;
import com.arnavgpt.ride_share.service.RideService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping("/rides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Ride> createRide(@Valid @RequestBody RideDtos.CreateRideRequest request, Authentication authentication) {
        String userId = authentication.getName();
        return ResponseEntity.ok(rideService.createRide(request, userId));
    }

    @GetMapping("/driver/rides/requests")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<List<Ride>> getRequestedRides() {
        return ResponseEntity.ok(rideService.getRequestedRides());
    }

    @PostMapping("/driver/rides/{rideId}/accept")
    @PreAuthorize("hasRole('DRIVER')")
    public ResponseEntity<Ride> acceptRide(@PathVariable String rideId, Authentication authentication) {
        String driverId = authentication.getName();
        return ResponseEntity.ok(rideService.acceptRide(rideId, driverId));
    }

    @PostMapping("/rides/{rideId}/complete")
    @PreAuthorize("hasAnyRole('USER','DRIVER')")
    public ResponseEntity<Ride> completeRide(@PathVariable String rideId, Authentication authentication) {
        String userId = authentication.getName();
        return ResponseEntity.ok(rideService.completeRide(rideId, userId));
    }

    @GetMapping("/user/rides")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Ride>> getUserRides(Authentication authentication) {
        String userId = authentication.getName();
        return ResponseEntity.ok(rideService.getUserRides(userId));
    }
}

