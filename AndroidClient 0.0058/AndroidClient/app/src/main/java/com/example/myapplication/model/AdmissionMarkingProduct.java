package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AdmissionMarkingProduct implements Parcelable {
    private String admissionMarkingProductId;
    private String productCode;
    private String barcodeLabeling;
    private boolean markingCompleted;

    public AdmissionMarkingProduct(String admissionMarkingProductId,String productCode,String barcodeLabeling,String markingCompleted){
        this.admissionMarkingProductId=admissionMarkingProductId;
        this.productCode=productCode;
        this.barcodeLabeling=barcodeLabeling;
        this.markingCompleted=Boolean.parseBoolean(markingCompleted);
    }

    public String getProductCode(){
        return productCode;
    }

    public void setProductCode(String productCode){
        this.productCode=productCode;
    }

    public String getBartcodeLabeling(){
        return barcodeLabeling;
    }

    public void setBarcodeLabeling(String barcodeLabeling){
        this.barcodeLabeling=barcodeLabeling;
    }

    public boolean getMarkingCompleted(){
        return markingCompleted;
    }

    public void setMarkingCompleted(boolean markingCompleted){
        this.markingCompleted=markingCompleted;
    }

    public void setMarkingCompleted(String markingCompleted){
        this.markingCompleted=Boolean.parseBoolean(markingCompleted);
    }

    public String getAdmissionMarkingProductId() {
        return admissionMarkingProductId;
    }

    public void setAdmissionMarkingProductId(String admissionMarkingProductId) {
        this.admissionMarkingProductId = admissionMarkingProductId;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(productCode);
        parcel.writeString(barcodeLabeling);
        parcel.writeString(""+markingCompleted);
    }

    private AdmissionMarkingProduct(Parcel parcel){
        productCode=parcel.readString();
        barcodeLabeling=parcel.readString();
        markingCompleted=Boolean.parseBoolean(parcel.readString());
    }
    public static final Parcelable.Creator<AdmissionMarkingProduct> CREATOR = new Parcelable.Creator<AdmissionMarkingProduct>(){
        @Override
        public AdmissionMarkingProduct createFromParcel(Parcel in){
            return new AdmissionMarkingProduct(in);
        }

        @Override
        public AdmissionMarkingProduct [] newArray(int size){
            return new AdmissionMarkingProduct[size];
        }
    };
}
