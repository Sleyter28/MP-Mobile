package com.market_pymes.Single;


public class Globals {
    private static Globals instance;

    // Global variable
    private String db_name;
    private String user_name;
    private String id_user;
    private String id_company;

    // Restrict the constructor from being instantiated
    private Globals(){}



    public void logOut() {
        this.db_name = null;
    }

    public static synchronized Globals getInstance(){
        if(instance==null){
            instance=new Globals();
        }
        return instance;
    }

    public void setDB(String d){
        this.db_name=d;
    }

    public String getDB(){
        return this.db_name;
    }

    public String getId_company() {
        return id_company;
    }

    public void setId_company(String id_company) {
        this.id_company = id_company;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
