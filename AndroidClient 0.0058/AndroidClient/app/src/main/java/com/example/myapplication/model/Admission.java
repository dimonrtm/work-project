package com.example.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Admission implements Parcelable {
    private String id;
   private Date date;
   private Date time;
   private String warehouse;
    private List<AdmissionProduct> admissionProducts=new ArrayList<AdmissionProduct>();
    private List<AdmissionMarkingProduct> admissionMarkingProducts=new ArrayList<AdmissionMarkingProduct>();

    public Admission(String id,String date,String time,String warehouse) throws ParseException {
        SimpleDateFormat formatDate=new SimpleDateFormat();
        SimpleDateFormat formatTime=new SimpleDateFormat();
        this.id=id;
        this.date=parseDate(date,"dd.MM.yyyy");
        this.time=parseDate(time,"HH:mm:ss");
        this.warehouse=warehouse;
    }

    public Admission (String id,Date date,Date time,String warehouse){
        this.id=id;
        this.date=date;
        this.time=time;
        this.warehouse=warehouse;
    }

    private Date parseDate(String date,String pattern) throws ParseException {
        Date dateh=null;
      SimpleDateFormat format=new SimpleDateFormat(pattern);
      dateh=format.parse(date);
      return dateh;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id=id;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date=date;
    }

    public void setDate(String date) throws ParseException {
        this.date=parseDate(date,"dd.MM.yyyy");
    }

    public Date getTime(){
        return time;
    }

    public void setTime(Date time){
        this.time=time;
    }

    public void setTime(String time) throws ParseException {
        this.time=parseDate(time,"HH:mm:ss");
    }

    public List<AdmissionProduct> getAdmissionProducts(){
        return admissionProducts;
    }

    public List<AdmissionMarkingProduct> getAdmissionMarkingProducts(){
        return admissionMarkingProducts;
    }
    public void addAdmissionProduct(AdmissionProduct admissionProduct){
        if(admissionProduct!=null) {
            admissionProducts.add(admissionProduct);
        }
        else{
            throw new NullPointerException("Product Not Found");
        }
    }

    public void addAdmissionMarkingProduct(AdmissionMarkingProduct admissionMarkingProduct){
        if(admissionMarkingProduct!=null) {
            admissionMarkingProducts.add(admissionMarkingProduct);
        }
        else{
            throw new NullPointerException("Product Not Found");
        }
    }

    public String getWarehouse(){
        return warehouse;
    }

    public void setWarehouse(String warehouse){
        this.warehouse=warehouse;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(id);
        SimpleDateFormat format=new SimpleDateFormat("dd.MM.yyyy");
        String d=format.format(date);
        parcel.writeString(d);
        format=new SimpleDateFormat("HH:mm:ss");
        String t=format.format(time);
        parcel.writeString(t);
        parcel.writeString(warehouse);
    }

    private Admission(Parcel parcel){
        id=parcel.readString();
        try {
            date=parseDate(parcel.readString(),"dd.MM.yyyy");
            time=parseDate(parcel.readString(),"HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static final Parcelable.Creator<Admission> CREATOR = new Parcelable.Creator<Admission>(){
        @Override
        public Admission createFromParcel(Parcel in){
            return new Admission(in);
        }

        @Override
        public Admission [] newArray(int size){
            return new Admission[size];
        }
    };
}
