package com.example.myapplication;

import android.content.Intent;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.adapters.AdmissionAdapter;
import com.example.myapplication.adapters.IAdmissionAdapterItemClick;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SalesActivity extends AppCompatActivity implements View.OnClickListener {
    private String userCode;
    private String userWarehouseCode;
    private EditText editTextSalesDataFrom;
    private EditText editTextSalesDataTo;
    private RecyclerView lvSales;
    private String selectedText;
    final static String PATH_FOLDER= Environment.getExternalStorageDirectory() + File.separator + "TestFolder/sellings/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        userCode=getIntent().getStringExtra("userCode");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        editTextSalesDataFrom=findViewById(R.id.editTextSalesDataFrom);
        Date data= Calendar.getInstance().getTime();
        String currentDate= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        editTextSalesDataFrom.setText(currentDate);
        editTextSalesDataTo=findViewById(R.id.editTextSalesDataTo);
        editTextSalesDataTo.setText(currentDate);
        File folder=new File(PATH_FOLDER);
        String [] fileNames=folder.list();
        lvSales=findViewById(R.id.lvSales);
//        lvSales.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,fileNames);
        AdmissionAdapter sellingsAdapter=new AdmissionAdapter(fileNames,"sell");
        lvSales.setAdapter(sellingsAdapter);
//        lvSales.setItemChecked(0,true);
        selectedText=sellingsAdapter.getSelectedText(0);
        sellingsAdapter.setOnAdapterItemClick(new IAdmissionAdapterItemClick() {
            @Override
            public void onItemClicked(AdmissionAdapter.ViewHolder holder,RadioButton rb) {
                RadioButton currentlyChecked=sellingsAdapter.getCurrentlyChecked();
                currentlyChecked.setChecked(false);
                RadioButton rbutton=holder.rbAdmissionSelected;
                rbutton.setChecked(true);
                sellingsAdapter.setCurrentlyChecked(rbutton);
                selectedText=holder.fileName;
            }
        });
        Button buttonOpen=findViewById(R.id.buttonSalesOpen);
        buttonOpen.setOnClickListener(this);
        Button buttonAdmissionBack=findViewById(R.id.buttonSalesBack);
        buttonAdmissionBack.setOnClickListener(this);
//        for(String fileName:fileNames){
//            Toast.makeText(getApplicationContext(),fileName, Toast.LENGTH_LONG).show();
//        }
//       Toast.makeText(getApplicationContext(),currentDate, Toast.LENGTH_LONG).show();
    }

    public void back(){
        Intent intent=new Intent(this,ControlActivity.class);
        intent.putExtra("userCode",userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    public void open(){
        Intent intent=new Intent(this,OpenSellingsActivity.class);
        intent.putExtra("userCode",userCode);
        intent.putExtra("filePath",PATH_FOLDER+selectedText);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            back();
            return true;
        }
        else{
            return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.buttonSalesBack:
                back();
                break;
            case R.id.buttonSalesOpen:
                open();
                break;
        }
    }
}
