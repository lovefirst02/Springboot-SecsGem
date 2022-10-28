package com.example.demo.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "agvc_current_vehicle_log")
public class Vehicle {

    private static final String SEND_DEFAULT_VALUE = "send";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "Datatime")
    public String dataTime;

    @Column(name = "action")
    public String action = SEND_DEFAULT_VALUE;

    @Column(name = "VehicleID")
    public String vehicleID;

    @Column(name = "iOperatorMainStatus")
    public Integer iOperatorMainStatus;

    @Column(name = "VehicleMotion")
    public Integer vehicleMotion;

    @Column(name = "iOperatorLocation")
    public String iOperatorLocation;

    @Column(name = "ValueOfLaserScore")
    public Integer valueOfLaserScore;

    @Column(name = "VehicleLastPosition")
    public  String vehicleLastPosition;

    @Column(name = "BatteryValue")
    public Integer batteryValue;

    @Column(name = "TemperatureOfBattery")
    public Integer temperatureOfBattery;

    @Column(name = "BatteryCurrentDraw")
    public Integer batteryCurrentDraw;

    @Column(name = "VoltageOfBattery")
    public Integer voltageOfBattery;

    @Column(name = "StatusOfStorage1")
    public Integer statusOfStorage1;

    @Column(name = "StatusOfStorage2")
    public Integer statusOfStorage2;

    @Column(name = "StatusOfStorage3")
    public Integer statusOfStorage3;

    @Column(name = "StatusOfStorage4")
    public Integer statusOfStorage4;

    @Column(name = "InformationOfStorage1")
    public String informationOfStorage1;

    @Column(name = "InformationOfStorage2")
    public String informationOfStorage2;

    @Column(name = "InformationOfStorage3")
    public String informationOfStorage3;

    @Column(name = "InformationOfStorage4")
    public String informationOfStorage4;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Integer getiOperatorMainStatus() {
        return iOperatorMainStatus;
    }

    public void setiOperatorMainStatus(Integer iOperatorMainStatus) {
        this.iOperatorMainStatus = iOperatorMainStatus;
    }

    public Integer getVehicleMotion() {
        return vehicleMotion;
    }

    public void setVehicleMotion(Integer vehicleMotion) {
        this.vehicleMotion = vehicleMotion;
    }

    public String getiOperatorLocation() {
        return iOperatorLocation;
    }

    public void setiOperatorLocation(String iOperatorLocation) {
        this.iOperatorLocation = iOperatorLocation;
    }

    public Integer getValueOfLaserScore() {
        return valueOfLaserScore;
    }

    public void setValueOfLaserScore(Integer valueOfLaserScore) {
        this.valueOfLaserScore = valueOfLaserScore;
    }

    public String getVehicleLastPosition() {
        return vehicleLastPosition;
    }

    public void setVehicleLastPosition(String vehicleLastPosition) {
        this.vehicleLastPosition = vehicleLastPosition;
    }

    public Integer getBatteryValue() {
        return batteryValue;
    }

    public void setBatteryValue(Integer batteryValue) {
        this.batteryValue = batteryValue;
    }

    public Integer getTemperatureOfBattery() {
        return temperatureOfBattery;
    }

    public void setTemperatureOfBattery(Integer temperatureOfBattery) {
        this.temperatureOfBattery = temperatureOfBattery;
    }

    public Integer getBatteryCurrentDraw() {
        return batteryCurrentDraw;
    }

    public void setBatteryCurrentDraw(Integer batteryCurrentDraw) {
        this.batteryCurrentDraw = batteryCurrentDraw;
    }

    public Integer getVoltageOfBattery() {
        return voltageOfBattery;
    }

    public void setVoltageOfBattery(Integer voltageOfBattery) {
        this.voltageOfBattery = voltageOfBattery;
    }

    public Integer getStatusOfStorage1() {
        return statusOfStorage1;
    }

    public void setStatusOfStorage1(Integer statusOfStorage1) {
        this.statusOfStorage1 = statusOfStorage1;
    }

    public Integer getStatusOfStorage2() {
        return statusOfStorage2;
    }

    public void setStatusOfStorage2(Integer statusOfStorage2) {
        this.statusOfStorage2 = statusOfStorage2;
    }

    public Integer getStatusOfStorage3() {
        return statusOfStorage3;
    }

    public void setStatusOfStorage3(Integer statusOfStorage3) {
        this.statusOfStorage3 = statusOfStorage3;
    }

    public Integer getStatusOfStorage4() {
        return statusOfStorage4;
    }

    public void setStatusOfStorage4(Integer statusOfStorage4) {
        this.statusOfStorage4 = statusOfStorage4;
    }

    public String getInformationOfStorage1() {
        return informationOfStorage1;
    }

    public void setInformationOfStorage1(String informationOfStorage1) {
        this.informationOfStorage1 = informationOfStorage1;
    }

    public String getInformationOfStorage2() {
        return informationOfStorage2;
    }

    public void setInformationOfStorage2(String informationOfStorage2) {
        this.informationOfStorage2 = informationOfStorage2;
    }

    public String getInformationOfStorage3() {
        return informationOfStorage3;
    }

    public void setInformationOfStorage3(String informationOfStorage3) {
        this.informationOfStorage3 = informationOfStorage3;
    }

    public String getInformationOfStorage4() {
        return informationOfStorage4;
    }

    public void setInformationOfStorage4(String informationOfStorage4) {
        this.informationOfStorage4 = informationOfStorage4;
    }
}
