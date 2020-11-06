package com.example.myapplication.model;

public class Barcode {
    String barcode;
    String productCode;

    public Barcode(String barcode, String productCode){
        this.barcode=barcode;
        this.productCode=productCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

}
