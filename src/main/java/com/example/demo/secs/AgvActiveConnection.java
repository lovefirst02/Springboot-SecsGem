package com.example.demo.secs;

import com.example.demo.model.entity.Mission;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetSocketAddress;
import java.util.*;


public class AgvActiveConnection {
    public SecsCommunicator conn;

    @Autowired
    Setting setting;

    private static AgvActiveConnection instance = new AgvActiveConnection();

    private AgvActiveConnection(){
        HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
        config.socketAddress(new InetSocketAddress("127.0.0.1",5000));
        config.connectionMode(HsmsConnectionMode.ACTIVE);
        config.sessionId(0);
        config.isEquip(true);
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
                            Secs2 secs = Secs2.binary((byte) 0x0);
                            try {
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
                            }
                    }
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

    public void getCurrentStatus() throws InterruptedException, SecsException, Secs2Exception {
        List<Map<String,Object>> result = new ArrayList<>();
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
            agv.put("VehicleID",reply.get().secs2().getAscii(0,i,0));
            agv.put("VehicleLastPosition",reply.get().secs2().getAscii(0,i,3));
            agv.put("BatteryValue",reply.get().secs2().getInt(0,i,6,0));
            for(int x = 10; x < agvDetail; x++){
                if (reply.get().secs2().get(0,i,x).secs2Item() == Secs2Item.UINT2){
                    agv.put(String.format("StatusOfStorage_%d",storageCount),reply.get().secs2().getInt(0,i,x,0));
                    storageCount = storageCount + 1;
                } else if (reply.get().secs2().get(0,i,x).secs2Item() == Secs2Item.ASCII) {
                    agv.put(String.format("InformationOfStorage_%d",storageDataCount),reply.get().secs2().getAscii(0,i,x));
                    storageDataCount = storageDataCount + 1;
                }
            }
            result.add(agv);
        }
        System.out.println(result);
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
//        System.out.println(reply.get().secs2().getByte(0,0));
        return HCACK.get(reply.get().secs2().getByte(0,0)).name();
    }
}
