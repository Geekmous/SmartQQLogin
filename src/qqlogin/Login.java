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
       
        GetLoginCookie get = new GetLoginCookie();
              
        Cookie cookie = get.getCookies();
        
        Manaer manager = new Manaer(cookie);
        manager.run();
        
    }
}
