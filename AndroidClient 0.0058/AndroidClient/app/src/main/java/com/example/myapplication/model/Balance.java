package com.example.myapplication.model;

public class Balance {
    private String productCode;
    private String warehouseCode;
    private double balance;
    private double reserve;

    public Balance(String productCode,String warehouseCode,String balance,String reserve){
        this.productCode=productCode;
        this.warehouseCode=warehouseCode;
        this.balance=Double.parseDouble(balance);
        this.reserve=Double.parseDouble(reserve);
    }

    public Balance(String productCode,String warehouseCode,int balance,double reserve){
        this.productCode=productCode;
        this.warehouseCode=warehouseCode;
        this.balance=balance;
        this.reserve=reserve;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getReserve(){
        return reserve;
    }

    public void setReserve(double reserve){
        this.reserve=reserve;
    }
}
