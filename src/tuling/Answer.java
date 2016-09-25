package tuling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

public class Answer {
    private String URL = "http://www.tuling123.com/openapi/api";
    private String Key = "dca273c846ba9fac6cef761415bc09d1";
    private String ask, answer;
    
    public void setAsk(String ask) {
        this.ask = ask;
    }
    
    public String ask(String content) {
        this.ask = content;
        if(content != null) {
            try {
                ask = URLEncoder.encode(this.ask, "utf-8");
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }   
        }
        
        String url = URL + "?key=" + this.Key + "&info=" + this.ask;
        
        
        
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        HttpURLConnection http = null;
        try {
            http = (HttpURLConnection) u.openConnection();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(http.getInputStream()));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        try {
            while(input.ready()) {
                sb.append(input.readLine());
                sb.append("\n");
            }
            
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        JSONObject json = new JSONObject(sb.toString());
        return json.getString("text");
         
    }
    public static void main(String ...strings) {
        Answer answer = new Answer();
        System.out.println(answer.ask("niasdsd"));
    }
}
