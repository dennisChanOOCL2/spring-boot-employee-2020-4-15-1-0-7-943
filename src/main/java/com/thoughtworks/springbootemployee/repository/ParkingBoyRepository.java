package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingBoyRepository extends JpaRepository<ParkingBoy, Integer> {
    List<ParkingBoy> findAllByGender(String gender, Pageable pageable);
}
