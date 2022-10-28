package com.example.demo.model.dao;

import com.example.demo.model.entity.Mission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MissionDao extends CrudRepository<Mission,Integer> {

    Mission findMissionByCommandID(@Param("CommandID") String commandID);
//    Mission findMissionByCommandIDANDCarrierID1(@Param("CommandID") String commandID, @Param("carrierID1") String carrierID1);
}
