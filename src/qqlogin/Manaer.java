package qqlogin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;

import tuling.Answer;

public class Manaer {
    Map<String, String> Cookie;
    String CookieParamName [] = {
            "skey",
            "p_skey",
            "pt4_token",
            "uin",
            "p_uin",
            "pt2gguin",
    };
    
    
    
    
    
    
    
    
    Manaer(Map<String, String> cookie) {
        this.Cookie = cookie;
    }
    
    
    
    
    
    
    public void run() {
        this.receiveUserFriends();
        this.group_name_list();
        this.getDiscusList();
        this.getSelfInfo();
        this.getOnlineBuddyes();
        this.getRecentList();
        while(true) {
     
                receiveMessage();
            
        }
    }
    public int receiveMessage() {
        System.err.println("Start Receive");
        String rawUrl = "http://d1.web2.qq.com/channel/poll2";
        String referer = "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2";      
        try {
            URL u = new URL(rawUrl);
            URLConnection con = u.openConnection();
            HttpURLConnection http = (HttpURLConnection) con;       
            String cookie = this.setCookie(Cookie, CookieParamName);
                http.setRequestProperty("Cookie", cookie); 
            if(referer != null) 
                http.setRequestProperty("Referer", referer);
            http.setRequestProperty("Origin", "http://d1.web2.qq.com");
            http.setRequestProperty("Host", "d1.web2.qq.com");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:47.0) Gecko/20100101 Firefox/47.0");
            http.setDoInput(true);
            http.setDoOutput(true);
            
            http.setRequestMethod("POST");
            
            String ptwebqq = Cookie.get("ptwebqq");
            String psessionid = Cookie.get("psessionid");
            String r = "r={\"ptwebqq\":\"" + ptwebqq + "\",\"clientid\":" + 53999199 + ",\"psessionid\":\""+ psessionid + "\",\"key\":\"\"}"; 
            
            
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(http.getOutputStream()));
            output.write(r);
            output.flush();
            output.close();         
            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while(input.ready()) {
                String line = input.readLine();
                sb.append(line + "\n");
            }
            input.close();
            http.disconnect();
            System.err.println("Receive finish");
            
            System.err.println("Start Process");
            String result = sb.toString();
            JSONObject json = new JSONObject(result);
            if(json.has("result")) {
               JSONArray jarray = json.getJSONArray("result");
               JSONObject p = jarray.getJSONObject(0);
               String type = p.getString("poll_type");
               JSONObject va = p.getJSONObject("value");
               JSONArray contentArray = va.getJSONArray("content");
               String content = contentArray.getString(1);
               Object from_uin = va.get("from_uin");
               Object to_uin = va.get("to_uin");
               Message m = new Message();
               
               m.type = type;
               m.content = content;
               m.fromUin = from_uin.toString();
               m.toUin = to_uin.toString();
               System.err.println("JSON finished");
               if(m.type.indexOf("group") <0 ) {
                   
                   System.out.println("Receive PersonnalMessage");
                   this.OnReceivePersonalMessage(m);
               }
               else {
                   System.out.println("Receive Group : Message");
                   this.OnReceiveGroupMessage(m);
               }
            }
            System.out.println("Process finish");
            
            
            System.out.println(result);
            if(result.indexOf("to_uin") >= 0) {
                return 1;
            }
            else return 0;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }
    
    
    public int receiveUserFriends() {
        String rawUrl = "http://s.web2.qq.com/api/get_user_friends2";
        String referer = "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1";
        
        try {
            URL u = new URL(rawUrl);
            URLConnection con = u.openConnection();
            
            HttpURLConnection http = (HttpURLConnection) con;
           
            
            String cookie = this.setCookie(Cookie, CookieParamName);
            System.out.println("cookie = " + cookie);
                http.setRequestProperty("Cookie", cookie);
            
            if(referer != null) 
                http.setRequestProperty("Referer", referer);
            http.setRequestProperty("Origin", "http://s.web2.qq.com");
            http.setRequestProperty("Host", "s.web2.qq.com");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:47.0) Gecko/20100101 Firefox/47.0");
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            
            String ptwebqq = Cookie.get("ptwebqq");
            String psessionid = Cookie.get("psessionid");
            String r = "r={\"ptwebqq\":\"" + ptwebqq + "\",\"clientid\":" + 53999199 + ",\"psessionid\":\""+ psessionid + "\",\"key\":\"\"}";
            long uin = Long.parseLong(Cookie.get("uin").replace("o", ""));
            String param = "r={\"vfwebqq\":\"" + Cookie.get("vfwebqq") + "\",\"hash\":\"" + HashCode.gethashCode(uin, ptwebqq) + "\"}";
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(http.getOutputStream()));
            System.out.println(param);
            output.write(param);
            output.flush();
            output.close();
            
            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while(input.ready()) {
                String line = input.readLine();
                sb.append(line + "\n");
            }
            
            
            input.close();
            http.disconnect();
            String result = sb.toString();
            System.out.println(result);
            if(result.indexOf("to_uin") >= 0) {
                return 1;
            }
            else return 0;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int group_name_list() {
        String rawUrl = "http://s.web2.qq.com/api/get_group_name_list_mask2";
        String referer = "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1";
        
        try {
            URL u = new URL(rawUrl);
            URLConnection con = u.openConnection();
            
            HttpURLConnection http = (HttpURLConnection) con;
           
            
            String cookie = this.setCookie(Cookie, CookieParamName);
            System.out.println("cookie = " + cookie);
                http.setRequestProperty("Cookie", cookie);
            
            if(referer != null) 
                http.setRequestProperty("Referer", referer);
            http.setRequestProperty("Origin", "http://s.web2.qq.com");
            http.setRequestProperty("Host", "s.web2.qq.com");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:47.0) Gecko/20100101 Firefox/47.0");
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            
            String ptwebqq = Cookie.get("ptwebqq");
            String psessionid = Cookie.get("psessionid");
            String r = "r={\"ptwebqq\":\"" + ptwebqq + "\",\"clientid\":" + 53999199 + ",\"psessionid\":\""+ psessionid + "\",\"key\":\"\"}";
            long uin = Long.parseLong(Cookie.get("uin").replace("o", ""));
            String param = "r={\"vfwebqq\":\"" + Cookie.get("vfwebqq") + "\",\"hash\":\"" + HashCode.gethashCode(uin, ptwebqq) + "\"}";
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(http.getOutputStream()));
            System.out.println(param);
            output.write(param);
            output.flush();
            output.close();
            
            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while(input.ready()) {
                String line = input.readLine();
                sb.append(line + "\n");
            }
            
            
            input.close();
            http.disconnect();
            String result = sb.toString();
            System.err.println("group List");
            System.out.println(result);
            if(result.indexOf("to_uin") >= 0) {
                return 1;
            }
            else return 0;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int getDiscusList() {
        String rawUrl = "http://s.web2.qq.com/api/get_discus_list?";
        String referer = "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1";
        String ptwebqq = Cookie.get("ptwebqq");
        String psessionid = Cookie.get("psessionid");
        rawUrl = rawUrl + "clientid=53999199&psessionid=" + psessionid + "&t=1472274729901&vfwebqq=" + Cookie.get("vfwebqq");
        try {
            URL u = new URL(rawUrl);
            URLConnection con = u.openConnection();
            
            HttpURLConnection http = (HttpURLConnection) con;
           
            
            String cookie = this.setCookie(Cookie, CookieParamName);
            System.out.println("cookie = " + cookie);
                http.setRequestProperty("Cookie", cookie);
            
            if(referer != null) 
                http.setRequestProperty("Referer", referer);
            http.setRequestProperty("Origin", "http://s.web2.qq.com");
            http.setRequestProperty("Host", "s.web2.qq.com");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:47.0) Gecko/20100101 Firefox/47.0");

            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while(input.ready()) {
                String line = input.readLine();
                sb.append(line + "\n");
            }
            
            
            input.close();
            http.disconnect();
            String result = sb.toString();
            System.err.println("Dicus List");
            System.out.println(result);
            if(result.indexOf("to_uin") >= 0) {
                return 1;
            }
            else return 0;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int getSelfInfo() {
        String rawUrl = "http://s.web2.qq.com/api/get_self_info2?t=1472274729906";
        String referer = "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1";
        String ptwebqq = Cookie.get("ptwebqq");
        String psessionid = Cookie.get("psessionid");
        
        try {
            URL u = new URL(rawUrl);
            URLConnection con = u.openConnection();
            
            HttpURLConnection http = (HttpURLConnection) con;
           
            
            String cookie = this.setCookie(Cookie, CookieParamName);
            System.out.println("cookie = " + cookie);
                http.setRequestProperty("Cookie", cookie);
            
            if(referer != null) 
                http.setRequestProperty("Referer", referer);
            http.setRequestProperty("Origin", "http://s.web2.qq.com");
            http.setRequestProperty("Host", "s.web2.qq.com");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:47.0) Gecko/20100101 Firefox/47.0");

            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while(input.ready()) {
                String line = input.readLine();
                sb.append(line + "\n");
            }        
            input.close();
            http.disconnect();
            String result = sb.toString();
            System.err.println("Dicus List");
            System.out.println(result);
            if(result.indexOf("to_uin") >= 0) {
                return 1;
            }
            else return 0;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int getOnlineBuddyes() {
        String rawUrl = "http://d1.web2.qq.com/channel/get_online_buddies2?";
        String referer = "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2";
        String ptwebqq = Cookie.get("ptwebqq");
        String psessionid = Cookie.get("psessionid");
        rawUrl = rawUrl + "clientid=53999199&psessionid=" + psessionid + "&t=1472274729901&vfwebqq=" + Cookie.get("vfwebqq");
        try {
            URL u = new URL(rawUrl);
            URLConnection con = u.openConnection();
            
            HttpURLConnection http = (HttpURLConnection) con;
           
            
            String cookie = this.setCookie(Cookie, CookieParamName);
            System.out.println("cookie = " + cookie);
                http.setRequestProperty("Cookie", cookie);
            
            if(referer != null) 
                http.setRequestProperty("Referer", referer);
            http.setRequestProperty("Origin", "http://d1.web2.qq.com");
            http.setRequestProperty("Host", "d1.web2.qq.com");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:47.0) Gecko/20100101 Firefox/47.0");
            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while(input.ready()) {
                String line = input.readLine();
                sb.append(line + "\n");
            }
            
            
            input.close();
            http.disconnect();
            String result = sb.toString();
            System.err.println("Online Buddy");
            System.out.println(result);
            if(result.indexOf("to_uin") >= 0) {
                return 1;
            }
            else return 0;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int getRecentList() {
        String rawUrl = "http://s.web2.qq.com/api/get_group_name_list_mask2";
        String referer = "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1";
        
        try {
            URL u = new URL(rawUrl);
            URLConnection con = u.openConnection();
            
            HttpURLConnection http = (HttpURLConnection) con;
           
            
            String cookie = this.setCookie(Cookie, CookieParamName);
            System.out.println("cookie = " + cookie);
                http.setRequestProperty("Cookie", cookie);
            
            if(referer != null) 
                http.setRequestProperty("Referer", referer);
            http.setRequestProperty("Origin", "http://s.web2.qq.com");
            http.setRequestProperty("Host", "s.web2.qq.com");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:47.0) Gecko/20100101 Firefox/47.0");
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            
            String ptwebqq = Cookie.get("ptwebqq");
            String psessionid = Cookie.get("psessionid");
            String r = "r={\"vfwebqq\":\"" + Cookie.get("vfwebqq") + "\",\"clientid\":" + 53999199 + ",\"psessionid\":\""+ psessionid + "\",\"key\":\"\"}";
           
            
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(http.getOutputStream()));
            System.out.println(r);
            output.write(r);
            output.flush();
            output.close();
            
            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while(input.ready()) {
                String line = input.readLine();
                sb.append(line + "\n");
            }
            
            
            input.close();
            http.disconnect();
            String result = sb.toString();
            System.err.println("Rencent List");
            System.out.println(result);
            if(result.indexOf("to_uin") >= 0) {
                return 1;
            }
            else return 0;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public String getFriendUin(String tuin) {
        String rawUrl = "http://s.web2.qq.com/api/get_friend_uin2?";
        String referer = "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2";
        String ptwebqq = Cookie.get("ptwebqq");
        String psessionid = Cookie.get("psessionid");
        rawUrl = rawUrl + "type=1&tuin=" + tuin + "&t=1472274729901&vfwebqq=" + Cookie.get("vfwebqq");
        try {
            URL u = new URL(rawUrl);
            URLConnection con = u.openConnection();
            
            HttpURLConnection http = (HttpURLConnection) con;
           
            
            String cookie = this.setCookie(Cookie, CookieParamName);
            System.out.println("cookie = " + cookie);
                http.setRequestProperty("Cookie", cookie);
            
            if(referer != null) 
                http.setRequestProperty("Referer", referer);
            http.setRequestProperty("Origin", "http://d1.web2.qq.com");
            http.setRequestProperty("Host", "d1.web2.qq.com");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:47.0) Gecko/20100101 Firefox/47.0");
            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while(input.ready()) {
                String line = input.readLine();
                sb.append(line + "\n");
            }
            
            
            input.close();
            http.disconnect();
            String result = sb.toString();
            System.err.println("Online Buddy");
            System.out.println(result);
            return result;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
    
    public int sendPersonMessage(String tuin, String content) {
        String rawUrl = "http://d1.web2.qq.com/channel/send_buddy_msg2";
        String referer = "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2";
        
        try {
            URL u = new URL(rawUrl);
            URLConnection con = u.openConnection();
            
            HttpURLConnection http = (HttpURLConnection) con;
           
            
            String cookie = this.setCookie(Cookie, CookieParamName);
            System.out.println("cookie = " + cookie);
                http.setRequestProperty("Cookie", cookie);
            
            if(referer != null) 
                http.setRequestProperty("Referer", referer);
            http.setRequestProperty("Origin", "http://s.web2.qq.com");
            http.setRequestProperty("Host", "s.web2.qq.com");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:47.0) Gecko/20100101 Firefox/47.0");
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            
            String ptwebqq = Cookie.get("ptwebqq");
            String psessionid = Cookie.get("psessionid");
            String r = "r={\"to\":" + tuin + ",\"content\":\"[\\\"" + content + "\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\",\"face\":645,\"clientid\": "+ 53999199 + ",\"msg_id\":" + 33000001 + ",\"psessionid\":\"8368046764001d636f6e6e7365727665725f77656271714031302e3133332e34312e383400001ad00000066b026e040015808a206d0000000a406172314338344a69526d0000002859185d94e66218548d1ecb1a12513c86126b3afb97a3c2955b1070324790733ddb059ab166de6857\"}";
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(http.getOutputStream()));
            System.out.println(r);
            output.write(r);
            output.flush();
            output.close();
            
            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while(input.ready()) {
                String line = input.readLine();
                sb.append(line + "\n");
            }
            
            
            input.close();
            http.disconnect();
            String result = sb.toString();
            System.out.println(result);
            if(result.indexOf("ok") >= 0) {
                return 1;
            }
            else return 0;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int sendGroupMessage(String Group_uin,String content) {
        String rawUrl = "http://d1.web2.qq.com/channel/send_qun_msg2";
        String referer = "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2";
        
        try {
            URL u = new URL(rawUrl);
            URLConnection con = u.openConnection();
            
            HttpURLConnection http = (HttpURLConnection) con;
           
            
            String cookie = this.setCookie(Cookie, CookieParamName);
            
                http.setRequestProperty("Cookie", cookie);
            
            if(referer != null) 
                http.setRequestProperty("Referer", referer);
            http.setRequestProperty("Origin", "http://s.web2.qq.com");
            http.setRequestProperty("Host", "s.web2.qq.com");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:47.0) Gecko/20100101 Firefox/47.0");
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            int ms_id = new Random().nextInt(Integer.MAX_VALUE);
            String r = "r={\"group_uin\":" + Group_uin + ",\"content\":\"[\\\"" + content + "\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\",\"face\":645,\"clientid\": "+ 53999199 + ",\"msg_id\":" + ms_id++ + ",\"psessionid\":\"8368046764001d636f6e6e7365727665725f77656271714031302e3133332e34312e383400001ad00000066b026e040015808a206d0000000a406172314338344a69526d0000002859185d94e66218548d1ecb1a12513c86126b3afb97a3c2955b1070324790733ddb059ab166de6857\"}";
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(http.getOutputStream()));
            System.out.println("Group Post :::" + r);
            output.write(r);
            output.flush();
            output.close();
            
            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer sb = new StringBuffer();
            while(input.ready()) {
                String line = input.readLine();
                sb.append(line + "\n");
            }
            
            
            input.close();
            http.disconnect();
            String result = sb.toString();
            System.out.println(result);
            if(result.indexOf("ok") >= 0) {
                return 1;
            }
            else return 0;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return 0;
        
    }
    
    public void OnReceivePersonalMessage(Message m) {
        Answer answer = new Answer();
        String ans = answer.ask(m.content);
        this.sendPersonMessage(m.fromUin, ans);
    }
    
    public void OnReceiveGroupMessage(Message m) {
        Answer answer = new Answer();
        String ans = answer.ask(m.content);
        this.sendGroupMessage(m.fromUin, ans);
    }
    
    public String setCookie(Map<String, String> Cookie, String [] name) {
        StringBuffer sb = new StringBuffer();
        for(Entry<String, String> entry : Cookie.entrySet()) {
            //System.err.println("<Cookie : " + entry.getKey() + " =  " + entry.getValue());
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(";");
        }
        return sb.toString();
    }
}
