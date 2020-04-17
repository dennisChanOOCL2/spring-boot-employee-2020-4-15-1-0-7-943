package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;

import java.util.List;

public class ParkingBoyService {

    ParkingBoyRepository parkingBoyRepository;

    public List<ParkingBoy> getAll() {
        return parkingBoyRepository.findAll();
    }

    public ParkingBoy createParkingBoy(ParkingBoy parkingBoy) {
        return parkingBoyRepository.save(parkingBoy);
    }
}
