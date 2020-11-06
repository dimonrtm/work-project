package com.example.myapplication.model;

public class AdmissionProduct {
    private String productCode;
    private int balanceValue;
    private int balanceValueDocs;
    private boolean marking;

    public AdmissionProduct(String productCode, String balanceValue,String balanceValueDocs,String marking){
        this.productCode=productCode;
        this.balanceValue=Integer.parseInt(balanceValue);
        this.balanceValueDocs=Integer.parseInt(balanceValueDocs);
        this.marking=Boolean.parseBoolean(marking);
    }

    public String getProductCode(){
        return productCode;
    }

    public void setProductCode(String productCode){
        this.productCode=productCode;
    }

    public int getBalanceValue(){
        return balanceValue;
    }

    public void setBalanceValue(String balanceValue){
        this.balanceValue=Integer.parseInt(balanceValue);
    }

    public void setBalanceValue(int balanceValue){
        this.balanceValue=balanceValue;
    }

    public int getBalanceValueDocs(){
        return balanceValueDocs;
    }

    public void setBalanceValueDocs(String balanceValueDocs){
        this.balanceValueDocs=Integer.parseInt(balanceValueDocs);
    }

    public void setBalanceValueDocs(int balanceValueDocs){
        this.balanceValueDocs=balanceValueDocs;
    }

    public boolean getMarking(){
        return marking;
    }

    public void setMarking(String marking){
        this.marking=Boolean.parseBoolean(marking);
    }

    public void setMarking(boolean marking){
        this.marking=marking;
    }
}
