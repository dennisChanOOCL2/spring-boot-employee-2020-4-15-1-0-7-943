package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {
    @Autowired
    ParkingBoyService parkingBoyService;

    @GetMapping
    public List<ParkingBoy> getAll() {
        return parkingBoyService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingBoy createParkingBoy(@RequestBody ParkingBoy parkingBoy){
        return parkingBoyService.createParkingBoy(parkingBoy);
    }


}
