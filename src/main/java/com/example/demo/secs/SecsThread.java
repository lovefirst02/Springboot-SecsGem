package com.example.demo.secs;

import com.example.demo.secs.AgvActiveConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecsThread implements CommandLineRunner {

    @Override
    public void run(String ...args){
        try{
            AgvActiveConnection ac = AgvActiveConnection.getInstance();
            ac.conn.open();
            ac.conn.waitUntilCommunicatable();
            while (true){
                if(ac.conn.isCommunicatable()){
                    ac.getCurrentStatus();
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
