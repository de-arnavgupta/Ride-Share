package com.arnavgpt.ride_share.dto;

import jakarta.validation.constraints.NotBlank;

public class RideDtos {

    public static class CreateRideRequest {
        @NotBlank(message = "Pickup is required")
        private String pickupLocation;
        @NotBlank(message = "Drop is required")
        private String dropLocation;
        // getters and setters
        public String getPickupLocation() { return pickupLocation; }
        public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
        public String getDropLocation() { return dropLocation; }
        public void setDropLocation(String dropLocation) { this.dropLocation = dropLocation; }
    }
}

