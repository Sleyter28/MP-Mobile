package com.market_pymes.Single;


public class Globals {
    private static Globals instance;

    // Global variable
    private String db_name;

    // Restrict the constructor from being instantiated
    private Globals(){}

    public void setDB(String d){
        this.db_name=d;
    }
    public String getDB(){
        return this.db_name;
    }

    public void logOut() {
        this.db_name = null;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }
}
