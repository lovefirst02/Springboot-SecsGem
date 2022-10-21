package com.example.demo.service;

import com.example.demo.base.Result;
import com.example.demo.base.ResultResponse;
import com.example.demo.model.entity.Mission;
import com.example.demo.secs.AgvActiveConnection;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.secs2.Secs2Exception;
import org.springframework.stereotype.Service;

@Service
public class AgvService {

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
}
