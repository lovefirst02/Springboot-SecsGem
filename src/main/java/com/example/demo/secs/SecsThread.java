package com.example.demo.secs;

import com.example.demo.model.entity.Vehicle;
import com.example.demo.service.AgvService;
import com.example.demo.service.AgvMissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SecsThread implements CommandLineRunner {

    @Autowired
    Setting setting;

    @Autowired
    AgvService agvService;

    @Autowired
    AgvMissionService agvMissionService;

    @Override
    public void run(String ...args){
        try{
            AgvActiveConnection ac = AgvActiveConnection.getInstance();
            agvMissionService.removeDate();
            ac.conn.open();
            ac.conn.waitUntilCommunicatable();
            while (true){
                if(ac.conn.isCommunicatable()){
                    List<Vehicle> res = ac.getCurrentStatus();
                    res.stream().forEach(r -> {
                        agvService.updateVehicle(r);
                    });
                }else{
                    log.error("nononoonoo");
                    ac.conn.waitUntilCommunicatable();
                }
                Thread.sleep(1000);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
