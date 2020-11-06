package com.example.myapplication.DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class AdmissionProductForAdapterDTO{ //implements Parcelable {
    private String productCode;
    private String name;
    private String articul;
    private int numberDoc;
    private int number;
    private boolean marked;

    public AdmissionProductForAdapterDTO(String productCode, String name,String articul, int numberDoc, int number, boolean marked) {
        this.productCode = productCode;
        this.name = name;
        this.articul=articul;
        this.numberDoc = numberDoc;
        this.number = number;
        this.marked = marked;
    }

    public String geProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode){
        this.productCode=productCode;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public int getNumberDoc(){
        return numberDoc;
    }

    public void setNumberDoc(int numberDoc){
        this.numberDoc=numberDoc;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number){
        this.number=number;
    }

    public boolean getMarked(){
        return marked;
    }

    public void setMarked(boolean marked){
        this.marked=marked;
    }

    public String getArticul() {
        return articul;
    }

    public void setArticul(String articul) {
        this.articul = articul;
    }

//    @Override
//    public int describeContents(){
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel,int flags){
//        parcel.writeString(productCode);
//        parcel.writeString(name);
//        parcel.writeInt(numberDoc);
//        parcel.writeInt(number);
//        parcel.writeString(""+marked);
//    }
//
//    private AdmissionProductForAdapterDTO(Parcel parcel){
//        productCode=parcel.readString();
//        name=parcel.readString();
//        numberDoc=parcel.readInt();
//        number=parcel.readInt();
//        marked=Boolean.parseBoolean(parcel.readString());
//    }
//
//    public static final Parcelable.Creator<AdmissionProductForAdapterDTO> CREATOR = new Parcelable.Creator<AdmissionProductForAdapterDTO>(){
//        @Override
//        public AdmissionProductForAdapterDTO createFromParcel(Parcel in){
//            return new AdmissionProductForAdapterDTO(in);
//        }
//
//        @Override
//        public AdmissionProductForAdapterDTO [] newArray(int size){
//            return new AdmissionProductForAdapterDTO[size];
//        }
//    };
}
