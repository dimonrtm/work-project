package com.example.myapplication.model;

public class User {
    private String userCode;
    private String warehouseCode;
    private String userLogin;
    private String userPassword;

    public User(String userLogin, String userCode,String warehouseCode, String userPassword){
       this.userLogin=userLogin;
       this.userCode=userCode;
       this.warehouseCode=warehouseCode;
       this.userPassword=userPassword;
    }

    public String getUserLogin(){
        return userLogin;
    }

    public void setUserLogin(String userLogin){
        this.userLogin=userLogin;
    }

    public String getWarehouseCode(){
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode){
        this.warehouseCode=warehouseCode;
    }

    public String getUserPassword(){
        return userPassword;
    }

    public void setUserPassword(String userPassword){
        this.userPassword=userPassword;
    }

    public String getUserCode(){
        return userCode;
    }

    public void setUserCode(String userCode){
        this.userCode=userCode;
    }
}
