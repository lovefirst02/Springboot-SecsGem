package com.example.demo.service;

import com.example.demo.base.Result;
import com.example.demo.base.ResultResponse;
import com.example.demo.model.dao.MissionDao;
import com.example.demo.model.entity.Mission;
import com.example.demo.secs.AgvActiveConnection;
import com.example.demo.secs.EventCode;
import com.example.demo.secs.SecsResultCode;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@Service
@Slf4j
public class AgvMissionService {

    @Autowired
    MissionDao missionDao;

    public Result sendMission(Mission mission) throws InterruptedException, SecsException, Secs2Exception {
        Result result;
        try{
            AgvActiveConnection ac = AgvActiveConnection.getInstance();
            String res = ac.sendMission(mission);
            result = ResultResponse.getSucessResult(res);
            return result;
        }catch (Exception e){
            result = ResultResponse.getFailResult(e.getMessage());
            return result;
        }
    }

    public Result getMission(){
        Result result;
        try{
            AgvActiveConnection ac = AgvActiveConnection.getInstance();
            result = ResultResponse.getSucessResult(ac.getAllMission());
            return result;
        }catch (Exception e){
            result = ResultResponse.getFailResult(e.getMessage());
            return result;
        }
    }

    public void updateMission(Mission mission){
        Mission resMission = missionDao.findMissionByCommandID(mission.commandID);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
        String date = df.format(new Date());
        if (resMission != null){
            resMission.setDataTime(date);
            resMission.setVehicleID(mission.getVehicleID());
            resMission.setTransferStatus(mission.getTransferStatus());
            resMission.setTransferCompleteStatus(mission.getTransferCompleteStatus());
            resMission.setTransfetCompleteCode(mission.getTransfetCompleteCode());
            missionDao.save(resMission);
        }else{
            mission.setDataTime(date);
            mission.setVehicleID("");
            mission.setTransferStatus(EventCode.getEnumByInt(0));
            missionDao.save(mission);
        }
    }
    public void handleEvent(SecsMessage secs) throws Secs2Exception {
        Integer CEID = secs.secs2().getInt(1,0);
        AgvActiveConnection ac = AgvActiveConnection.getInstance();
        Map<String,Mission> commandProtocol = ac.getAllMission();
        switch (CEID){
            case 108:
            case 111:
                try{
                    String commandID = secs.secs2().getAscii(2,0,1,0);
                    commandProtocol.get(commandID).setTransferStatus(EventCode.getEnumByInt(CEID));
                    updateMission(commandProtocol.get(commandID));
                }catch (Exception e){
                    log.error(e.getMessage());
                }
                break;
            case 201:
                try{
                    String commandID = secs.secs2().getAscii(2,0,1,1);
                    String vehicleID = secs.secs2().getAscii(2,0,1,0);
                    commandProtocol.get(commandID).setTransferStatus(EventCode.getEnumByInt(CEID));
                    commandProtocol.get(commandID).setVehicleID(vehicleID);
                    updateMission(commandProtocol.get(commandID));
                }catch (Exception e){
                    log.error(e.getMessage());
                }
                break;
            case 204:
            case 203:
                try {
                    String carrierID = secs.secs2().getAscii(2,0,1,3);
                    commandProtocol.forEach((r,v) -> {
                        if (v.getCarrierID1().equals(carrierID)){
                            v.setTransferStatus(EventCode.getEnumByInt(CEID));
                            updateMission(v);
                        }
                    });
                }catch (Exception e){
                    log.error(e.getMessage());
                }
                break;
            case 205:
                try{
                    String vehicleID = secs.secs2().getAscii(2,0,1,0);
                    String sourcePort = secs.secs2().getAscii(2,0,1,1);
                    commandProtocol.forEach((r,v) -> {
                        if (v.getVehicleID().equals(vehicleID) && v.getSourcePort1().equals(sourcePort)){
                            v.setTransferStatus(EventCode.getEnumByInt(CEID));
                            updateMission(v);
                        }
                    });
                }catch (Exception e){
                    log.error(e.getMessage());
                }
                break;
            case 207:
            case 208:
                try{
                    String carrierID = secs.secs2().getAscii(2,0,1,2);
                    commandProtocol.forEach((r,v) -> {
                        if (v.getCarrierID1().equals(carrierID)){
                            v.setTransferStatus(EventCode.getEnumByInt(CEID));
                            updateMission(v);
                        }
                    });
                }catch (Exception e){
                    log.error(e.getMessage());
                }
                break;
            case 151:
            case 164:
                try{
                    String commandID = secs.secs2().getAscii(2,0,1,3);
                    commandProtocol.get(commandID).setTransferStatus(EventCode.getEnumByInt(CEID));
                    updateMission(commandProtocol.get(commandID));
                }catch (Exception e){
                    log.error(e.getMessage());
                }
                break;
            case 206:
                String vehicleID = secs.secs2().getAscii(2,0,1,0);
                String arrivePort = secs.secs2().getAscii(2,0,1,1);
                break;
            case 251:
                break;
            case 107:
                try{
                    String commandID = secs.secs2().getAscii(2,0,1,0,0);
                    Integer resultCode = secs.secs2().getInt(2,0,1,1,0);
                    commandProtocol.get(commandID).setTransferStatus(EventCode.getEnumByInt(CEID));
                    commandProtocol.get(commandID).setTransfetCompleteCode(resultCode);
                    commandProtocol.get(commandID).setTransferCompleteStatus(SecsResultCode.getEnumByInt(resultCode));
                    updateMission(commandProtocol.get(commandID));
                    commandProtocol.remove(commandID);
                }catch (Exception e){
                    log.error(e.getMessage());
                }
                break;
            case 202:
                try{
                    String commandID = secs.secs2().getAscii(2,0,1,1);
                    if (commandProtocol.get(commandID) != null){
                        commandProtocol.remove(commandID);
                    }
                }catch (Exception e){
                    log.warn(e.getMessage());
                }
                break;
            default:
//                log.info(secs.toString());
        }
    }

    public void removeDate(){
        try {
            missionDao.deleteAll();
            log.info("Delete Mission Table Sucess");
        }catch (Exception e){
            log.error(String.format("Delete Mission Table Fail (%s)",e.getMessage()));
        }
    }
}
