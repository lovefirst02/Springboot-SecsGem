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
    CARRIERREMOVED(164),//Castec 152 ASE 164
    VEHICLEARRIVED(206), //Castec 201 ASE 206
    VEHICLEACQUIRESTARTED(203), //Castec 202 ASE 203
    VEHICLEACQUIRECOMPLETED(204), //Castec 203 ASE 204
    VEHICLEASSIGNED(201), //Castec 204 ASE 201
    VEHICLEDEPARTED(205),
    VEHICLEDEPOSITESTARTED(207), //Castec 206 ASE 207
    VEHICLEDEPOSITECOMPLETED(208), //Castec 207 ASE 208
    VEHICLEINSTALLER(210), //Castec 208 ASE 210
    VEHICLEREMOVED(211), //Castec 209 ASE 211
    VEHICLEUNASSIGNED(202); //Castec 210 ASE 202



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
