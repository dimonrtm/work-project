package com.example.myapplication.model;

public class Product {
    private String name;
    private String productCode;
    private String vendorCode;

    public Product(String name,String productCode,String vendorCode){
        this.name=name;
        this.productCode=productCode;
        this.vendorCode=vendorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }
}
