package com.example.myapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.AdmissionMarkingProduct;

import java.util.List;

public class Scan2DBarcodeAdapter extends RecyclerView.Adapter<Scan2DBarcodeAdapter.ViewHolder> {
    List<AdmissionMarkingProduct> listAdmissionMarkingProduct;

    public Scan2DBarcodeAdapter(List<AdmissionMarkingProduct> listAdmissionMarkingProduct) {
        this.listAdmissionMarkingProduct = listAdmissionMarkingProduct;
    }

    @Override
    public int getItemCount(){
        return listAdmissionMarkingProduct==null ? 0 : listAdmissionMarkingProduct.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvBarcode2DItem;
        public CheckBox cbBarcode2DItem;

        public ViewHolder(View view){
            super(view);
            tvBarcode2DItem=view.findViewById(R.id.tvBarcode2DItem);
            cbBarcode2DItem=view.findViewById(R.id.cbBarcode2DItem);
        }
    }

    @Override
    public Scan2DBarcodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.ittem_barcode_2d,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        AdmissionMarkingProduct currProduct=listAdmissionMarkingProduct.get(position);
        holder.tvBarcode2DItem.setText(currProduct.getBartcodeLabeling());
        holder.cbBarcode2DItem.setChecked(currProduct.getMarkingCompleted());
    }
}
