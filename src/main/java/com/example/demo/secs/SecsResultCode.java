package com.example.demo.secs;

public enum SecsResultCode {
    NORMALCOMPLETE(0),
    CANCEL(1),
    ABORT(2),
    E84CHECKFAILED(3),
    E84CHECKERROR(4),
    CARRIERPICKCHECKFAILED(5),
    CARRIERDROPCHECKFAILED(6),
    COMMANDCHECKFAILED(7),
    CARRIERISNOTEXIST(8),
    RESERVED(9),
    CARRIERSWAPCHECKFAILED(10),
    CARRIERIDMISMATCH(11),
    CARRIERIDREADFAIL(12),
    TRANSFERFAILED(99);

    public int code;

    private SecsResultCode(int code){this.code = code;}

    public static String getEnumByInt(Integer code){
        for(SecsResultCode e: SecsResultCode.values()){
            if(e.code == code){
                return e.name();
            }
        }
        return null;
    }
}
