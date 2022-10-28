package com.example.demo.secs;

import com.example.demo.model.entity.Mission;
import com.example.demo.model.entity.Vehicle;
import com.example.demo.service.AgvMissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.gem.ClockType;
import com.shimizukenta.secs.gem.HCACK;
import com.shimizukenta.secs.hsms.HsmsConnectionMode;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicator;
import com.shimizukenta.secs.hsmsss.HsmsSsCommunicatorConfig;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2Exception;
import com.shimizukenta.secs.secs2.Secs2Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.*;

@Component
@Slf4j
public class AgvActiveConnection {
    public SecsCommunicator conn;

    public Map<String,Mission> commandProtocol = new HashMap<>();

    @Autowired
    protected AgvMissionService agvMissionService;


    private static AgvActiveConnection  agvActiveConnection ;
    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        agvActiveConnection = this;
        agvActiveConnection.agvMissionService = this.agvMissionService;
        // 初使化时将已静态化的testService实例化
    }


    private static AgvActiveConnection instance = new AgvActiveConnection();

    private AgvActiveConnection(){
        HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
        config.socketAddress(new InetSocketAddress("127.0.0.1",5000));
        config.connectionMode(HsmsConnectionMode.ACTIVE);
        config.sessionId(0);
        config.isEquip(false);
        config.timeout().t3(45.0F);
        config.timeout().t6( 5.0F);
        config.timeout().t7(10.0F);
        config.timeout().t8( 6.0F);
        config.linktest(120.0F);
        config.gem().clockType(ClockType.A16);
        conn = HsmsSsCommunicator.newInstance(config);
        conn.addSecsMessageReceiveListener((SecsMessage msg) ->{
            switch (msg.getStream()){
                case 6:
                    switch (msg.getFunction()){
                        case 11:
                            try {
                                agvActiveConnection.agvMissionService.handleEvent(msg);
                                Secs2 secs = Secs2.binary((byte) 0x0);
                                conn.send(
                                        msg,
                                        6,
                                        12,
                                        false,
                                        secs
                                );
                            } catch (SecsException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } catch (Secs2Exception e) {
                                throw new RuntimeException(e);
                            }
                            break;
                    }
                    break;
                case 5:
                    switch (msg.getFunction()){
                        case 1:
                            Secs2 secs = Secs2.binary((byte) 0x0);
                            System.out.printf("S5F1 : %s",msg.secs2());
                            try {
                                conn.send(
                                        msg,
                                        5,
                                        2,
                                        false,
                                        secs
                                );
                            } catch (SecsException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                    }
                    break;
            }

        });
    }

    public static AgvActiveConnection getInstance(){
        if (instance == null){
            synchronized (AgvActiveConnection.class){
                if(instance == null){
                    return new AgvActiveConnection();
                }
            }
        }
        return instance;
    }

    public List<Vehicle> getCurrentStatus() throws InterruptedException, SecsException, Secs2Exception {
        List<Vehicle> result = new ArrayList<>();
        Secs2 secs = Secs2.list(
                Secs2.uint2(1101)
        );
        Optional<SecsMessage> reply = conn.send(
                1,
                3,
                true,
                secs
        );
        Integer agvCount = reply.get().secs2().get(0).size();
        for(int i = 0; i < agvCount; i++){
            Integer storageCount = 1;
            Integer storageDataCount = 1;
            Map<String, Object> agv = new HashMap<String ,Object>();
            Integer agvDetail = reply.get().secs2().get(0,i).size();
            agv.put("vehicleID",reply.get().secs2().getAscii(0,i,0));
            agv.put("iOperatorMainStatus",reply.get().secs2().getInt(0,i,1,0));
            agv.put("vehicleMotion",reply.get().secs2().getInt(0,i,2,0));
            agv.put("iOperatorLocation",reply.get().secs2().getAscii(0,i,3));
            agv.put("valueOfLaserScore",reply.get().secs2().getInt(0,i,4,0));
            agv.put("vehicleLastPosition",reply.get().secs2().getAscii(0,i,5));
            agv.put("batteryValue",reply.get().secs2().getInt(0,i,6,0));
            agv.put("temperatureOfBattery",reply.get().secs2().getInt(0,i,7,0));
            agv.put("batteryCurrentDraw",reply.get().secs2().getInt(0,i,8,0));
            agv.put("voltageOfBattery",reply.get().secs2().getInt(0,i,9,0));
            for(int x = 10; x < agvDetail; x++){
                if (reply.get().secs2().get(0,i,x).secs2Item() == Secs2Item.UINT2){
                    agv.put(String.format("statusOfStorage%d",storageCount),reply.get().secs2().getInt(0,i,x,0));
                    storageCount = storageCount + 1;
                } else if (reply.get().secs2().get(0,i,x).secs2Item() == Secs2Item.ASCII) {
                    agv.put(String.format("informationOfStorage%d",storageDataCount),reply.get().secs2().getAscii(0,i,x));
                    storageDataCount = storageDataCount + 1;
                }
            }
            ObjectMapper mergeMap = new ObjectMapper();
            Vehicle mergeMapResult = mergeMap.convertValue(agv,Vehicle.class);
            result.add(mergeMapResult);
//            log.info(mergeMapResult.vehicleID);
        }
        return result;
    }

    public Map<String,Mission> getAllMission(){
        return commandProtocol;
    }

    public String sendMission(Mission mission) throws InterruptedException, SecsException, Secs2Exception {
        Secs2 secs = Secs2.list(
                Secs2.uint4(0),
                Secs2.ascii(""),
                Secs2.ascii("TRANSFER_WaferCount"),
                Secs2.list(
                        Secs2.list(
                                Secs2.ascii("COMMANDINFO"),
                                Secs2.list(
                                        Secs2.list(
                                                Secs2.ascii("COMMANDID"),
                                                Secs2.ascii(mission.getCommandID())//CommandID
                                        ),
                                        Secs2.list(
                                                Secs2.ascii("PRIORITY"),
                                                Secs2.uint2(mission.getPriority())//PRIORITY
                                        ),
                                        Secs2.list(
                                                Secs2.ascii("REPLACE"),
                                                Secs2.uint2(mission.getReplace())//REPLACE
                                        )
                                )
                        ),
                        Secs2.list(
                                Secs2.ascii("TRANSFERINFO"),
                                Secs2.list(
                                        Secs2.list(
                                                Secs2.ascii("CARRIERID"),
                                                Secs2.ascii(mission.getCarrierID1())//CARRIERID
                                        ),
                                        Secs2.list(
                                                Secs2.ascii("SOURCEPORT"),
                                                Secs2.ascii(mission.getSourcePort1())//SOURCEPORT
                                        ),
                                        Secs2.list(
                                                Secs2.ascii("DESTPORT"),
                                                Secs2.ascii(mission.getDestPort1())//DESTPORT
                                        )
                                )
                        ),
                        Secs2.list(
                                Secs2.ascii("GENERALCMD_INFO"),
                                Secs2.list(
                                        Secs2.list(
                                                Secs2.ascii("HOSTDESC"),
                                                Secs2.ascii(mission.getSourcePort1())//HOSTDESC
                                        ),
                                        Secs2.list(
                                                Secs2.ascii("OWNER"),
                                                Secs2.ascii("user")//OWNER
                                        ),
                                        Secs2.list(
                                                Secs2.ascii("KEEPFLAG"),
                                                Secs2.ascii("1")//KEEPFLAG
                                        ),
                                        Secs2.list(
                                                Secs2.ascii("ABNORMALFLAG"),
                                                Secs2.ascii("0")//ABNORMALFLAG
                                        ),
                                        Secs2.list(
                                                Secs2.ascii("WAFERQTY"),
                                                Secs2.ascii(mission.getWafer_qty())//WAFERQTY
                                        )
                                )
                        )
                )
        );
        Optional<SecsMessage> reply = conn.send(
                2,
                49,
                true,
                secs
        );
        if(reply.get().secs2().getByte(0,0) == 0 || reply.get().secs2().getByte(0,0) == 4){
            commandProtocol.put(mission.getCommandID(),mission);
            agvActiveConnection.agvMissionService.updateMission(mission);
        }
        return HCACK.get(reply.get().secs2().getByte(0,0)).name();
    }
}
