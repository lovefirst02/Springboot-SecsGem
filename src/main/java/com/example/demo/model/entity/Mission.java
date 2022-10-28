package com.example.demo.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "agvc_current_mission")
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "Datatime")
    public String dataTime;

    @Column(name = "action",columnDefinition = "varchar(255) default 'recv'")
    public String action = "recv";

    @Column(name = "DataID",columnDefinition = "int default 0")
    public Integer dataID = 0;
    @Column(name = "CommandID")
    public String commandID;
    @Column(name = "Priority")
    public Integer priority;

    @Column(name = "Replace")
    public Integer replace = 0;
    @Column(name = "VehicleID")
    public String vehicleID;
    @Column(name = "CarrierID1")
    public String carrierID1;
    @Column(name = "WCount1")
    public String wCount1;
    @Column(name = "SourcePort1")
    public String sourcePort1;
    @Column(name = "DestPort1")
    public String destPort1;
    @Column(name = "WaferQty")
    public String wafer_qty;
    @Column(name = "CarrierID2")
    public String carrierID2;
    @Column(name = "WCount2")
    public String wCount2;
    @Column(name = "SourcePort2")
    public String sourcePort2;
    @Column(name = "DestPort2")
    public String destPort2;

    @Column(name = "TransferCompletedStatus")
    public String transferCompleteStatus;

    @Column(name = "TransferCompletedCode")
    public Integer transfetCompleteCode;

    @Column(name = "TransferStatus")
    public String transferStatus;

    public String getCommandID() {
        return commandID;
    }

    public void setCommandID(String commandID) {
        this.commandID = commandID;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getReplace() {
        return replace;
    }

    public void setReplace(Integer replace) {
        this.replace = replace;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getCarrierID1() {
        return carrierID1;
    }

    public void setCarrierID1(String carrierID1) {
        this.carrierID1 = carrierID1;
    }

    public String getwCount1() {
        return wCount1;
    }

    public void setwCount1(String wCount1) {
        this.wCount1 = wCount1;
    }

    public String getSourcePort1() {
        return sourcePort1;
    }

    public void setSourcePort1(String sourcePort1) {
        this.sourcePort1 = sourcePort1;
    }

    public String getDestPort1() {
        return destPort1;
    }

    public void setDestPort1(String destPort1) {
        this.destPort1 = destPort1;
    }

    public String getWafer_qty() {
        return wafer_qty;
    }

    public void setWafer_qty(String wafer_qty) {
        this.wafer_qty = wafer_qty;
    }

    public String getCarrierID2() {
        return carrierID2;
    }

    public void setCarrierID2(String carrierID2) {
        this.carrierID2 = carrierID2;
    }

    public String getwCount2() {
        return wCount2;
    }

    public void setwCount2(String wCount2) {
        this.wCount2 = wCount2;
    }

    public String getSourcePort2() {
        return sourcePort2;
    }

    public void setSourcePort2(String sourcePort2) {
        this.sourcePort2 = sourcePort2;
    }

    public String getDestPort2() {
        return destPort2;
    }

    public void setDestPort2(String destPort2) {
        this.destPort2 = destPort2;
    }

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

    public Integer getDataID() {
        return dataID;
    }

    public void setDataID(Integer dataID) {
        this.dataID = dataID;
    }

    public String getTransferCompleteStatus() {
        return transferCompleteStatus;
    }

    public void setTransferCompleteStatus(String transferCompleteStatus) {
        this.transferCompleteStatus = transferCompleteStatus;
    }

    public Integer getTransfetCompleteCode() {
        return transfetCompleteCode;
    }

    public void setTransfetCompleteCode(Integer transfetCompleteCode) {
        this.transfetCompleteCode = transfetCompleteCode;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }
}
