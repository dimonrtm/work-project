package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ControlActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonConnect;
    private Button buttonOut;
    private Button buttonAdmissions;
    private Button buttonSelling;
    private String userCode;
    private String userWarehouseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userCode = getIntent().getStringExtra("userCode");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        setContentView(R.layout.activity_control);
        buttonConnect = findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(this);
        buttonOut = findViewById(R.id.buttonOut);
        buttonOut.setOnClickListener(this);
        buttonAdmissions = findViewById(R.id.buttonAdmission);
        buttonAdmissions.setOnClickListener(this);
        buttonSelling=findViewById(R.id.buttonSelling);
        buttonSelling.setOnClickListener(this);
        Button buttonScan = findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(this);
        Button buttonWeb=findViewById(R.id.buttonWeb);
        buttonWeb.setOnClickListener(this);

    }

    private void getConnectServerActivity() {
        Intent intent = new Intent(this, EnterUrlActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void outOfSystem() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getScanActivity() {
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void getAdmissionsActivity() {
        Intent intent = new Intent(this, AdmissionsActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void getSqlActivity(){
        Intent intent = new Intent(this, SqlActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void getSalesActivity(){
        Intent intent = new Intent(this, SalesActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            outOfSystem();
            return true;
        }
        else{
            return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonConnect:
                getConnectServerActivity();
                break;
            case R.id.buttonOut:
                outOfSystem();
                break;
            case R.id.buttonScan:
                getScanActivity();
                break;
            case R.id.buttonAdmission:
                getAdmissionsActivity();
                break;
            case R.id.buttonWeb:
                getSqlActivity();
                break;
            case R.id.buttonSelling:
                getSalesActivity();
                break;
        }
    }
}
