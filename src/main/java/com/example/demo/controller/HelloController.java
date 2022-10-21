package com.example.demo.controller;

import com.example.demo.secs.AgvActiveConnection;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.secs2.Secs2Exception;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String sayHello(){
        return "Hello World";
    }

    @GetMapping("/check")
    public String checkConn() throws InterruptedException, SecsException, Secs2Exception {
        AgvActiveConnection ac = AgvActiveConnection.getInstance();
        ac.getCurrentStatus();
        return "HELLO";
    }

//    @GetMapping("/mission")
//    public String sendMissionTest() throws InterruptedException, SecsException, Secs2Exception {
//        AgvActiveConnection ac = AgvActiveConnection.getInstance();
//        ac.sendMission();
//        return "HELLO";
//    }
}
