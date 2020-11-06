package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.http_requests.HttpGetUsersRequests;
import com.example.myapplication.model.Admission;
import com.example.myapplication.model.AdmissionMarkingProduct;
import com.example.myapplication.model.AdmissionProduct;
import com.example.myapplication.xml.readers_from_strings.AdmissionMarkingProductsXmlReaderFromString;
import com.example.myapplication.xml.readers_from_strings.AdmissionProductsXmlReaderFromString;
import com.example.myapplication.xml.readers_from_strings.AdmissionXmlReaderFromString;
import com.example.myapplication.xml.writers_to_string.AdmissionWriterToString;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GetAdmissionActivity extends AppCompatActivity implements View.OnClickListener {
    private String userCode;
    private EditText editTextAdmissionId;
    private EditText editTextWarehouseCode;
    private String admissionId;
    private String userWarehouseCode;
    private String warehouseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_admission);
        userCode = getIntent().getStringExtra("userCode");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        editTextAdmissionId = findViewById(R.id.editTextAdmissionId);
        editTextWarehouseCode = findViewById(R.id.editTextWarehouseCode);
        Button buttonGetAdmissionBack = findViewById(R.id.buttonGetAdmissionBack);
        Button buttonGetAdmissionById = findViewById(R.id.buttonGetAdmissionById);
        buttonGetAdmissionBack.setOnClickListener(this);
        buttonGetAdmissionById.setOnClickListener(this);
    }

    private void back() {
        Intent intent = new Intent(this, SqlActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void getAdmissionById() {
        admissionId = editTextAdmissionId.getText().toString();
        warehouseCode = editTextWarehouseCode.getText().toString();
        String admissionUrl = "http://192.168.1.86:8080/admissions/" + admissionId + "/" + warehouseCode;
        String admissionProductsUrl = "http://192.168.1.86:8080/admissionProducts/" + admissionId;
        String admissionMarkingProductsUrl = "http://192.168.1.86:8080/admissionMarkingProducts/" + admissionId;
        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Admissions/" +"admission"+ admissionId+".xml";
        getAndWriteAdmission(admissionUrl, admissionProductsUrl, admissionMarkingProductsUrl, folder);
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

    private void getAndWriteAdmission(String urlAdmission, String urlAdmissionProduct, String urlMarkingAdmissionProduct, String folder) {
        String result = "";
        Admission admission = null;
        List<AdmissionProduct> admissionProducts = null;
        List<AdmissionMarkingProduct> admissionMarkingProducts = null;
        result = getObjects(urlAdmission);
        try {
            AdmissionXmlReaderFromString admissionXmlReaderFromString = new AdmissionXmlReaderFromString(result);
            admission = admissionXmlReaderFromString.readAdmission();
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
        result = getObjects(urlAdmissionProduct);
        try {
            AdmissionProductsXmlReaderFromString admissionProductsXmlReaderFromString = new AdmissionProductsXmlReaderFromString(result);
            admissionProducts = admissionProductsXmlReaderFromString.readAdmissionProducts();
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
        for (AdmissionProduct admProd : admissionProducts) {
                admission.addAdmissionProduct(admProd);
        }
        result = getObjects(urlMarkingAdmissionProduct);
        try {
            AdmissionMarkingProductsXmlReaderFromString admissionMarkingProductsXmlReaderFromString = new AdmissionMarkingProductsXmlReaderFromString(result);
            admissionMarkingProducts = admissionMarkingProductsXmlReaderFromString.readAdmissionMarkingProducts();
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
        for (AdmissionMarkingProduct admMProd : admissionMarkingProducts) {
            admission.addAdmissionMarkingProduct(admMProd);
        }
        try {
            AdmissionWriterToString admissionWriterToString = new AdmissionWriterToString();
            result = admissionWriterToString.writeAdmission(admission);
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
            case R.id.buttonGetAdmissionBack:
                back();
                break;
            case R.id.buttonGetAdmissionById:
                getAdmissionById();
        }
    }
}
