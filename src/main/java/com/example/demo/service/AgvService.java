package com.example.demo.service;

import com.example.demo.base.Result;
import com.example.demo.base.ResultResponse;
import com.example.demo.model.dao.VehicleDao;
import com.example.demo.model.entity.Mission;
import com.example.demo.model.entity.Vehicle;
import com.example.demo.secs.AgvActiveConnection;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.secs2.Secs2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@Service
public class AgvService {

    @Autowired
    VehicleDao vehicleDao;


    @Transactional
    public void updateVehicle(Vehicle vehicle){
        Vehicle resVehicle = vehicleDao.findVehicleIDByVehicleID(vehicle.getVehicleID());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        String date = df.format(new Date());
        if (resVehicle == null){
            vehicle.setDataTime(date);
            vehicleDao.save(vehicle);
        }else {
            resVehicle.setDataTime(date);
            resVehicle.setBatteryValue(vehicle.getBatteryValue());
            resVehicle.setBatteryCurrentDraw(vehicle.getBatteryCurrentDraw());
            resVehicle.setiOperatorLocation(vehicle.getiOperatorLocation());
            resVehicle.setiOperatorMainStatus(vehicle.getiOperatorMainStatus());
            resVehicle.setTemperatureOfBattery(vehicle.getTemperatureOfBattery());
            resVehicle.setVehicleMotion(vehicle.getVehicleMotion());
            resVehicle.setVoltageOfBattery(vehicle.getVoltageOfBattery());
            resVehicle.setValueOfLaserScore(vehicle.getValueOfLaserScore());
            resVehicle.setVehicleLastPosition(vehicle.getVehicleLastPosition());
            resVehicle.setInformationOfStorage1(vehicle.getInformationOfStorage1());
            resVehicle.setInformationOfStorage2(vehicle.getInformationOfStorage2());
            resVehicle.setInformationOfStorage3(vehicle.getInformationOfStorage3());
            resVehicle.setInformationOfStorage4(vehicle.getInformationOfStorage4());
            resVehicle.setStatusOfStorage1(vehicle.getStatusOfStorage1());
            resVehicle.setStatusOfStorage2(vehicle.getStatusOfStorage2());
            resVehicle.setStatusOfStorage3(vehicle.getStatusOfStorage3());
            resVehicle.setStatusOfStorage4(vehicle.getStatusOfStorage4());
            vehicleDao.save(resVehicle);
        }
    }
}
