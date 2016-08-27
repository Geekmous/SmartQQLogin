package qqlogin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {
    
    
    
    public static void main(String...strings){
        String url = "http://d1.web2.qq.com/channel/login2";
        String qq = "404339376";
        String pwd = "xianlei.";
        try {
            URL u = new URL(url);
            HttpURLConnection http = (HttpURLConnection) u.openConnection();
            GetLoginCookie c = new GetLoginCookie(qq, pwd);
            
            
            Map<String, String> cookie = c.getCookies();
            StringBuffer sb = new StringBuffer();
            for(Entry<String, String> entry : cookie.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append(";");
            }
            http.setRequestProperty("Host", "d1.web2.qq.com");
            http.setRequestProperty("Referer", "http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
            http.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:47.0) Gecko/20100101 Firefox/47.0");
            
            
            String ptwebqq = cookie.get("ptwebqq");
            String param = "r={\"ptwebqq\":\"" + ptwebqq + "\",\"clientid\": " + 53999199 + ",\"psessionid\":\"\",\"status\":\"online\"}";
            System.out.println(param);
           
            http.setRequestProperty("Cookie", sb.toString());
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(http.getOutputStream()));
            output.write(param);
            output.flush();
            output.close();
            
            
            
            
            
            BufferedReader input = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
            
            StringBuffer sbs = new StringBuffer();
            while(input.ready()) {
                sbs.append(input.readLine());
                sbs.append("\n");
            }
            String result = sbs.toString();
            System.out.println(result);
            
            
            Pattern pattern = Pattern.compile("\"psessionid\":\".+?\"");
            Matcher matcher = pattern.matcher(result);
            if(matcher.find()) {
                String s = matcher.group();
                
                
                int index = s.indexOf(":");
                int end = s.lastIndexOf("\"");
                String psessionid = s.substring(index + 2, end);
                System.out.println("psessionid = " + psessionid);
                cookie.put("psessionid", psessionid);
                
            }
            
            
            Manaer manager = new Manaer(cookie);
            manager.run();
            
            
            
            
            
            
            
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
