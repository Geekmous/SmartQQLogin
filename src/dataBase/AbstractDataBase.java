package dataBase;

import java.sql.ResultSet;

public abstract class AbstractDataBase {
    public abstract void connect();
    public abstract void creatDB(String name);
    public abstract void selectDB(String name);
    public abstract void executeUpdata(String sql);
    public abstract ResultSet executeQuery(String sql);
    public abstract void commit();
    public abstract void close();
    
}
