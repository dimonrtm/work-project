package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class AdmissionAdapter extends RecyclerView.Adapter<AdmissionAdapter.ViewHolder> {
    RadioButton currentlyChecked;
    String typeTransaction;
    IAdmissionAdapterItemClick adapterItemClickListener;
    String[] fileNames;
    String[] fileContentsNames;

    public AdmissionAdapter(String[] fileNames, String typeTransaction) {
        this.fileNames = fileNames;
        this.typeTransaction = typeTransaction;
        this.fileContentsNames = new String[this.fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            String fileContentName = "";
            if (typeTransaction.equals("admission")) {
                fileContentName = fileNames[i].replace("admission", "пост").replace(".xml", "");
            } else if (typeTransaction.equals("sell")) {
                fileContentName = fileNames[i].replace("selling", "реал").replace(".xml", "");
            } else {
                break;
            }
            fileContentsNames[i] = fileContentName;
        }
    }

    public String getSelectedText(int position){
        return fileNames[position];
    }

    public void setOnAdapterItemClick(IAdmissionAdapterItemClick adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    public RadioButton getCurrentlyChecked() {
        return currentlyChecked;
    }

    public void setCurrentlyChecked(RadioButton rb) {
        this.currentlyChecked = rb;
    }

    @Override
    public int getItemCount() {
        return fileNames == null ? 0 : fileNames.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvFilename;
        public RadioButton rbAdmissionSelected;
        public View.OnClickListener listener;
        public String fileName;

        public ViewHolder(View view,View.OnClickListener listener){
            super(view);
            this.listener=listener;
            tvFilename=view.findViewById(R.id.tvFileName);
            rbAdmissionSelected=view.findViewById(R.id.rbAdmissionSelected);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            if(this.listener!=null){
               this.listener.onClick(view);
            }
        }
    }

    @Override
    public AdmissionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.admission_item,parent,false);
        return new ViewHolder(v,null);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
       holder.tvFilename.setText(fileContentsNames[position]);
       holder.fileName=fileNames[position];
       if(position==0){
           if(currentlyChecked==null){
               holder.rbAdmissionSelected.setChecked(true);
               currentlyChecked=holder.rbAdmissionSelected;
           }
       }
       holder.listener=new View.OnClickListener(){
           @Override
           public void onClick(View v){
               if(adapterItemClickListener!=null){
                   adapterItemClickListener.onItemClicked(holder,currentlyChecked);
               }
           }
       };
    }

}
