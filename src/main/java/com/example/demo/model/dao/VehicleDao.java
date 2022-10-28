package com.example.demo.model.dao;

import com.example.demo.model.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VehicleDao extends CrudRepository<Vehicle,Integer> {

    Vehicle findVehicleIDByVehicleID(@Param("vehicleID") String vehicleID);
}
