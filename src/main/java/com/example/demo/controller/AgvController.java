package com.example.demo.controller;

import com.example.demo.base.Result;
import com.example.demo.model.entity.Mission;
import com.example.demo.service.AgvService;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.secs2.Secs2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AgvController {

    @Autowired
    AgvService agvService;

    @PostMapping("/mission")
    public Result send(@RequestBody Mission mission) throws InterruptedException, SecsException, Secs2Exception {
        return agvService.sendMission(mission);
    }
}
