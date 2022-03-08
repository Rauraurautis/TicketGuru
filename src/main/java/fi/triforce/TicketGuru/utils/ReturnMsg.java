package fi.triforce.TicketGuru.utils;

import java.util.HashMap;

public class ReturnMsg {

    private HashMap<String, String> returnMsg = new HashMap<String, String>();

    public ReturnMsg(String message) {
        this.returnMsg = new HashMap<String, String>();
        returnMsg.put("message", message);
    }

    public HashMap<String, String> getReturnMsg() {
        return returnMsg;
    }
    
}
