package qqlogin;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Cookie {
    Map<String, String> cookies = new HashMap<String, String>();
    
    public void put(String key, String value) {
        cookies.put(key, value);
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for(Entry<String, String> entry : cookies.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(";");
        }
        return sb.toString();
    }
    
    public String get(String key) {
        return cookies.get(key);
    }
}
