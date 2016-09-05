package qqlogin;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class MessageProcess {
    private String JSONResult;
    
    MessageProcess(String result) {
        JSONResult = result;
    }
    
    public Message getMessage() {
        JSONObject json = new JSONObject(JSONResult);
        if(json.has("result")) {
           JSONArray jarray = json.getJSONArray("result");
           JSONObject p = jarray.getJSONObject(0);
           String type = p.getString("poll_type");
           if(type.indexOf("group_message") >= 0) {
               return parseGroupMassage(JSONResult);
               
           }
           else return parseMessage(JSONResult);
        }
        return null;
    }
    
    private Message parseMessage(String content) {
        JSONObject json = new JSONObject(JSONResult);
        
           JSONArray jarray = json.getJSONArray("result");
           JSONObject p = jarray.getJSONObject(0);
           String type = p.getString("poll_type");
           JSONObject va = p.getJSONObject("value");
           JSONArray contentArray = va.getJSONArray("content");
           Object Messagecontent = contentArray.get(1);
           Object from_uin = va.get("from_uin");
           Object to_uin = va.get("to_uin");
           Message m = new Message();
           m.type = type;
           m.content = Messagecontent.toString();
           m.fromUin = from_uin.toString();
           m.toUin = to_uin.toString();
           m.time = new Date().toString(); 
           return m;
        
    }
        
    private Message parseGroupMassage(String content) {
        JSONObject json = new JSONObject(JSONResult);
        System.out.println(content);
        JSONArray jarray = json.getJSONArray("result");
           JSONObject p = jarray.getJSONObject(0);
           String type = p.getString("poll_type");
           JSONObject va = p.getJSONObject("value");
           JSONArray contentArray = va.getJSONArray("content");
           Object Messagecontent = contentArray.get(1);
           Object from_uin = va.get("from_uin");
           Object to_uin = va.get("to_uin");
           Object sendUin = va.get("send_uin");
           Message m = new Message();
           m.type = type;
           m.content = Messagecontent.toString();
           m.groupCode = from_uin.toString();
           m.toUin = to_uin.toString();
           m.time = new Date().toString(); 
           m.fromUin = sendUin.toString();
           return m;
        
    }
}
