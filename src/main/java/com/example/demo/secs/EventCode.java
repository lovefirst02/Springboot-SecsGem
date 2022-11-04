package com.example.demo.secs;

public enum EventCode {
    TRANSFERQUEUE(0),
    TRANSFERABORTCOMPLETED(101),
    TRANSFERABORTFAILED(102),
    TRANSFERABORTINITIATED(103),
    TRANSFERCANCELCOMPLETED(104),
    TRANSFERCANCELFAILED(105),
    TRANSFERCANCELINITIATED(106),
    TRANSFERCOMPLETE(107),
    TRANSFERINITIATED(108),
    TRANSFERRING(111),
    CARRIERINSTALLER(151),
    CARRIERREMOVED(164),
    VEHICLEARRIVED(206),
    VEHICLEACQUIRESTARTED(203),
    VEHICLEACQUIRECOMPLETED(204),
    VEHICLEASSIGNED(201),
    VEHICLEDEPARTED(205),
    VEHICLEDEPOSITESTARTED(207),
    VEHICLEDEPOSITECOMPLETED(208),
    VEHICLEINSTALLER(210),
    VEHICLEREMOVED(211),
    VEHICLEUNASSIGNED(202);



    public int code;
    private EventCode(int code){
        this.code = code;
    }

//    public String getName(){
//        return this.name();
//    }

    public static String getEnumByInt(Integer code){
        for(EventCode e:EventCode.values()){
            if(e.code == code){
                return e.name();
            }
        }
        return null;
    }
}
