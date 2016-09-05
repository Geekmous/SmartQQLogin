package GetData;

import qqlogin.Cookie;

public class Abstract {
    String url;
    String t = "1473083159323",tuin , vfwebqq;
    String Host = "s.web2.qq.com";
    String Referer = "http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1";
    String User_Agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:48.0) Gecko/20100101 Firefox/48.0";
    Cookie cookie;
    
    Abstract(Cookie cookie) {
        this.cookie = cookie;
        vfwebqq = cookie.get("vfwebqq");
    }
}
