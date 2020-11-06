package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.http_requests.HttpGetUsersRequests;
import com.example.myapplication.model.Admission;
import com.example.myapplication.model.AdmissionMarkingProduct;
import com.example.myapplication.model.AdmissionProduct;
import com.example.myapplication.xml.readers_from_strings.SellMarkingProductsXmlReaderFromString;
import com.example.myapplication.xml.readers_from_strings.SellProductsXmlReaderFromString;
import com.example.myapplication.xml.readers_from_strings.SellXmlReaderFromString;
import com.example.myapplication.xml.writers_to_string.AdmissionWriterToString;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GetSellActivity extends AppCompatActivity implements View.OnClickListener {
    private String userCode;
    private EditText editTextSellId;
    private EditText editTextSellWarehouseCode;
    private String sellId;
    private String sellWarehouseCode;
    private String userWarehouseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sell);
        userCode = getIntent().getStringExtra("userCode");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        editTextSellId = findViewById(R.id.editTextSellId);
        editTextSellWarehouseCode = findViewById(R.id.editTextSellWarehouseCode);
        Button buttonGetSellBack = findViewById(R.id.buttonGetSellBack);
        Button buttonGetSellById = findViewById(R.id.buttonGetSellById);
        buttonGetSellBack.setOnClickListener(this);
        buttonGetSellById.setOnClickListener(this);
    }

    private void back() {
        Intent intent = new Intent(this, SqlActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void getSellById() {
        sellId = editTextSellId.getText().toString();
        sellWarehouseCode = editTextSellWarehouseCode.getText().toString();
        String sellUrl = "http://192.168.1.86:8080/sellings/" + sellId + "/" + sellWarehouseCode;
        String sellProductsUrl = "http://192.168.1.86:8080/sellProducts/" + sellId;
        String sellMarkingProductsUrl = "http://192.168.1.86:8080/sellMarkingProducts/" + sellId;
        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/sellings/" +"sell"+ sellId + ".xml";
        getAndWriteSell(sellUrl, sellProductsUrl, sellMarkingProductsUrl, folder);
        Toast.makeText(getApplicationContext(), "Файл записана по адресу" + folder, Toast.LENGTH_LONG).show();
    }

    private String getObjects(String url) {
        String result = "";
        HttpGetUsersRequests getUsersRequest = new HttpGetUsersRequests();
        try {
            result = getUsersRequest.execute(url).get();
        } catch (ExecutionException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return result;
    }

    private void writeObjects(String folder, String result) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(folder);
            fileWriter.write(result);
            fileWriter.flush();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getAndWriteSell(String urlSell, String urlSellProduct, String urlMarkingSellProduct, String folder) {
        String result = "";
        Admission sell = null;
        List<AdmissionProduct> sellProducts = null;
        List<AdmissionMarkingProduct> sellMarkingProducts = null;
        result = getObjects(urlSell);
        try {
            SellXmlReaderFromString sellXmlReaderFromString = new SellXmlReaderFromString(result);
            sell = sellXmlReaderFromString.readSell();
        } catch (XmlPullParserException e) {
            Toast.makeText(getApplicationContext(), e.getMessage()+"Xml", Toast.LENGTH_LONG).show();
            return;
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage()+"IO", Toast.LENGTH_LONG).show();
            return;
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage()+"Parse", Toast.LENGTH_LONG).show();
            return;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage()+"E", Toast.LENGTH_LONG).show();
            return;
        }
        result = getObjects(urlSellProduct);
        try {
            SellProductsXmlReaderFromString sellProductsXmlReaderFromString = new SellProductsXmlReaderFromString(result);
            sellProducts = sellProductsXmlReaderFromString.readSellProducts();
        } catch (XmlPullParserException e) {
            Toast.makeText(getApplicationContext(), e.getMessage()+"Xml", Toast.LENGTH_LONG).show();
            return;
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage()+"IO", Toast.LENGTH_LONG).show();
            return;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage()+"E", Toast.LENGTH_LONG).show();
            return;
        }
        for (AdmissionProduct slProd : sellProducts) {
            sell.addAdmissionProduct(slProd);
        }
        result = getObjects(urlMarkingSellProduct);
        try {
            SellMarkingProductsXmlReaderFromString sellMarkingProductsXmlReaderFromString = new SellMarkingProductsXmlReaderFromString(result);
            sellMarkingProducts = sellMarkingProductsXmlReaderFromString.readSellMarkingProducts();
        } catch (XmlPullParserException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        for (AdmissionMarkingProduct slMProd : sellMarkingProducts) {
            sell.addAdmissionMarkingProduct(slMProd);
        }
        try {
            AdmissionWriterToString admissionWriterToString = new AdmissionWriterToString();
            result = admissionWriterToString.writeAdmission(sell);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        writeObjects(folder, result);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGetSellBack:
                back();
                break;
            case R.id.buttonGetSellById:
                getSellById();
        }
    }
}
