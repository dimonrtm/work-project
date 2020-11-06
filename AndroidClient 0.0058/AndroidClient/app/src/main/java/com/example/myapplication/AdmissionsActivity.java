package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapters.AdmissionAdapter;
import com.example.myapplication.adapters.IAdmissionAdapterItemClick;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AdmissionsActivity extends AppCompatActivity implements View.OnClickListener {
    private String userCode;
    private EditText editTextDataFrom;
    private EditText editTextDataTo;
    private RecyclerView lvAdmissions;
    private String selectedText;
    private String userWarehouseCode;
    final static String PATH_FOLDER=Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Admissions/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admissions);
        userCode=getIntent().getStringExtra("userCode");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        editTextDataFrom=findViewById(R.id.editTextDataFrom);
        Date data= Calendar.getInstance().getTime();
        String currentDate= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        editTextDataFrom.setText(currentDate);
        editTextDataTo=findViewById(R.id.editTextDataTo);
        editTextDataTo.setText(currentDate);
        File folder=new File(PATH_FOLDER);
        String [] fileNames=folder.list();
        lvAdmissions=findViewById(R.id.lvAdmissions);
//        lvAdmissions.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.admission_item,fileNames);
        AdmissionAdapter adapter=new AdmissionAdapter(fileNames,"admission");
//        Toast.makeText(getApplicationContext(),selectedText+"", Toast.LENGTH_LONG).show();
//        lvAdmissions.setClickable(true);
        adapter.setOnAdapterItemClick(new IAdmissionAdapterItemClick() {
            @Override
            public void onItemClicked(AdmissionAdapter.ViewHolder holder,RadioButton rb) {
              RadioButton currentlyChecked=adapter.getCurrentlyChecked();
              currentlyChecked.setChecked(false);
              RadioButton rbutton=holder.rbAdmissionSelected;
              rbutton.setChecked(true);
              adapter.setCurrentlyChecked(rbutton);
              selectedText=holder.fileName;
            }
        });
        lvAdmissions.setAdapter(adapter);
        selectedText=adapter.getSelectedText(0);
        Button buttonOpen=findViewById(R.id.buttonOpen);
        buttonOpen.setOnClickListener(this);
        Button buttonAdmissionBack=findViewById(R.id.buttonAdmissionBack);
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

    public void open(){
        Intent intent=new Intent(this,OpenAdmissionsActivity.class);
        intent.putExtra("userCode",userCode);
        intent.putExtra("filePath",PATH_FOLDER+selectedText);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.buttonAdmissionBack:
                back();
                break;
            case R.id.buttonOpen:
                open();
                break;
        }
    }
}
