package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EnterUrlActivity extends AppCompatActivity implements View.OnClickListener {

    private String url;
    private String userCode;
    private EditText editURL;
    private Button buttonDPage;
    private Button buttonEUBack;
    private String userWarehouseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_url);
        userCode = getIntent().getStringExtra("userCode");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        editURL = findViewById(R.id.editURL);
        buttonDPage = findViewById(R.id.buttonDPage);
        buttonDPage.setOnClickListener(this);
        buttonEUBack = findViewById(R.id.buttonEUBack);
        buttonEUBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EUBack();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, DownloadActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("url", editURL.getText().toString());
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    public void EUBack() {
        Intent intent = new Intent(this, ControlActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            EUBack();
            return true;
        }
        else{
            return super.onKeyUp(keyCode, event);
        }
    }
}
