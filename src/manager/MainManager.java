package manager;

import java.util.List;

import dataBase.AbstractDataBase;
import qqlogin.Cookie;

public class MainManager {
    Cookie cookie;
    List<Contact> contacts;
    List groups;
    List discuss;
    AbstractDataBase database;
    
    
    
    
    
    MainManager(Cookie cookie) {
        this.cookie = cookie;
    }
    
    public Contact getContactByQQ(String qq) {
        
        return null;
    } // getcontactByQQ
    
    
    public Group getGroupByCode(String groupCode) {
        
        return null;
    }// getGroupByCode
    
    public Discuss getDicussByCode(String discussCode) {
        
        return null;
    }
    
    
    
    
    
    
}
