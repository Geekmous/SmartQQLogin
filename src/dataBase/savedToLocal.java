package dataBase;

import qqlogin.Message;

public class savedToLocal {
    Message msg;
    String databaseName = "Message.db";
    
    
    public void save(Message m) {
        System.out.println("fromuin :" + m.fromUin + " touin : " + m.toUin + " content : " + m.content + " time : " + m.time);
        
        if(m.type.indexOf("group") >= 0) 
            saveGroupMessage(m);
        else saveMessage(m);
    }
    private void saveGroupMessage(Message m) {
        AbstractDataBase database = new Sqlite();
        database.connect();
        database.creatDB(m.toUin + ".db");
        String createtable = "CREATE TABLE GROUPMESSAGE " + 
                            "(GROUPCODE TEXT NOT NULL," +
                            "FROMUIN TEXT NOT NULL," +
                            "TOUIN TEXT NOT NULL," +
                            "CONTENT TEXT NOT NULL," +
                            "TIME TEXT NOT NULL)";
        database.executeUpdata(createtable);
        
        String insertdata = "INSERT INTO GROUPMESSAGE (GROUPCODE, FROMUIN, TOUIN, CONTENT, TIME)" +
                            "VALUES('" + m.groupCode + "','" + m.fromUin + "','" + m.toUin + "','" + m.content + "','" + m.time + "')";
        System.out.println("inser: " + insertdata);
        database.executeUpdata(insertdata);
        
        database.close();
        
    }
    
    private void saveMessage(Message m) {
        AbstractDataBase database = new Sqlite();
        database.connect();
        database.creatDB(m.toUin + ".db");
        String createtable = "CREATE TABLE MESSAGE " + 
                            "(FROMUIN TEXT NOT NULL," +
                            "TOUIN TEXT NOT NULL," +
                            "CONTENT TEXT NOT NULL," +
                            "TIME TEXT NOT NULL)";
        database.executeUpdata(createtable);
        
        String insertdata = "INSERT INTO  MESSAGE(FROMUIN, TOUIN, CONTENT, TIME)" +
                            "VALUES('" + m.fromUin + "','" + m.toUin + "','" + m.content + "','" + m.time + "')";
        database.executeUpdata(insertdata);
        database.close();
    }
}
