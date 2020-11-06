package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DTO.AdmissionProductForAdapterDTO;
import com.example.myapplication.R;

import java.util.List;

public class OpenAdmissionAdapter extends RecyclerView.Adapter<OpenAdmissionAdapter.ViewHolder>{
    List<AdmissionProductForAdapterDTO> admissionProducts;
    IOpenAdmissionItemClick adapterItemClickListener;

    public OpenAdmissionAdapter(List<AdmissionProductForAdapterDTO> admissionProducts){
        this.admissionProducts=admissionProducts;
    }

    public void setOnAdapterItemClick(IOpenAdmissionItemClick adapterItemClick){
        adapterItemClickListener=adapterItemClick;
    }

    @Override
    public int getItemCount(){
        return admissionProducts==null? 0 : admissionProducts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvName;
        public TextView tvArticul;
        public TextView tvNumberDoc;
        public TextView tvNumber;
        public CheckBox cbMarked;
        public String currentProductId;
        public View.OnClickListener listener;

        public ViewHolder(View view,View.OnClickListener listener){
            super(view);
            this.listener=listener;
            tvName=view.findViewById(R.id.tvName);
            tvArticul=view.findViewById(R.id.tvArticul);
            tvNumberDoc=view.findViewById(R.id.tvNumberDoc);
            tvNumber=view.findViewById(R.id.tvNumber);
            cbMarked=view.findViewById(R.id.cbMarked);
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
    public OpenAdmissionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(v,null);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        AdmissionProductForAdapterDTO admissionProduct=admissionProducts.get(position);
        holder.tvName.setText(admissionProduct.getName());
        holder.tvArticul.setText(admissionProduct.getArticul());
        holder.tvNumberDoc.setText(""+admissionProduct.getNumberDoc());
        holder.tvNumber.setText(""+admissionProduct.getNumber());
        holder.cbMarked.setChecked(admissionProduct.getMarked());
        holder.currentProductId=admissionProduct.geProductCode();
        holder.listener=new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(adapterItemClickListener!=null){
                    adapterItemClickListener.onItemClicked(holder.currentProductId);
                }
            }
        };
    }
}
