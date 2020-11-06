package com.example.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.Admission;
import com.example.myapplication.model.AdmissionProduct;
import com.example.myapplication.model.Product;
import com.example.myapplication.xml.readers.AdmissionXmlReader;
import com.example.myapplication.xml.readers.BarcodeXmlReader;
import com.example.myapplication.xml.readers.ProductXmlReader;
import com.example.myapplication.xml.writers_in_hashtable.SaveAdmissionsXmlWriter;
import com.smartdevicesdk.scanner.ScannerHelper3501;
import com.smartdevicesdk.utils.HandlerMessage;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SellingsScanActivity extends AppCompatActivity implements View.OnClickListener {
    final int PLEASE_SCAN_STATE = 0;
    final int FILL_TEXT_DATA_PRODUCT_NAME = 1;
    final int FILL_TEXT_DATA_AMOUNT_DOC = 2;
    final int FILL_TEXT_DATA_AMOUNT = 3;
    final int CALL_BARCODE2D_ACTIVITY = 4;
    final int ERROR_EXTERNAL_THREAD = 5;
    private String userCode;
    private String filePath;
    private String barcode;
    private String userWarehouseCode;
    private String currentProductCode;
    private Admission sell;
    private AdmissionProduct tempSellProduct;
    private EditText editTextSellingsBarcode;
    private TextView tvSellingsProductName;
    private TextView tvSellingsScanNumberDoc;
    private TextView tvSellingsScanNumber;
    private EditText editTextSellingsAmountScan;
    private MediaPlayer player;
    private Handler handlerScan = null;
    public static ScannerHelper3501 scanner = null;
    private Thread threadScan = null;
    private boolean out = false;
    private String admissionProductNotFound;
    private String productNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellings_scan);
        userCode = getIntent().getStringExtra("userCode");
        filePath = getIntent().getStringExtra("filePath");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        editTextSellingsBarcode = findViewById(R.id.editTextSellingsBarcode);
        tvSellingsProductName = findViewById(R.id.tvSellingsProductName);
        tvSellingsScanNumberDoc = findViewById(R.id.tvSellingsScanNumberDoc);
        tvSellingsScanNumber = findViewById(R.id.tvSellingsScanNumber);
        editTextSellingsAmountScan = findViewById(R.id.editTextSellingsAmountScan);
        currentProductCode = getIntent().getStringExtra("currentProductCode");
        editTextSellingsAmountScan.setText("" + 0);
        Button buttonSellingsBarcode = findViewById(R.id.buttonSellingsBarcode);
        buttonSellingsBarcode.setOnClickListener(this);
        Button buttonSellingsCancel = findViewById(R.id.buttonSellingsCancel);
        buttonSellingsCancel.setOnClickListener(this);
        Button buttonSellingsBack = findViewById(R.id.buttonSellingsBack);
        buttonSellingsBack.setOnClickListener(this);
        Resources res = getResources();
        //TODO 09.03.2020 Завтра убрать этот говнокод
        Product product = null;
        if (!currentProductCode.equals("")) {
            String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Product.xml";
            try {
                FileInputStream in = new FileInputStream(folder);
                ProductXmlReader productXmlReader = new ProductXmlReader();
                product = productXmlReader.parseMapProducts(in, currentProductCode);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileInputStream in = new FileInputStream(filePath);
                AdmissionXmlReader admissionXmlReader = new AdmissionXmlReader();
                sell = admissionXmlReader.parseAdmission(in);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<AdmissionProduct> listSellingsProducts = sell.getAdmissionProducts();
            for (AdmissionProduct admissionProduct : listSellingsProducts) {
                if (admissionProduct.getProductCode().equals(currentProductCode)) {
                    tempSellProduct = admissionProduct;
                }
            }

            if (!(tempSellProduct == null)) {
                if (tempSellProduct.getMarking()) {
                    currentProductCode="";
                    runScanBarcode2D();
                } else {
                    tvSellingsProductName.setText(product.getName());
                    tvSellingsScanNumberDoc.setText(tempSellProduct.getBalanceValueDocs() + "");
                    editTextSellingsAmountScan.setText("" + 1);
                    tvSellingsScanNumber.setText(tempSellProduct.getBalanceValue() + "");
                }
            }
        }
        player = MediaPlayer.create(getApplicationContext(), R.raw.scan);
        admissionProductNotFound = res.getString(R.string.admission_product_not_found);
        productNotFound = res.getString(R.string.product_not_found);
        handlerScan = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == HandlerMessage.SCANNER_DATA_MSG) {
                    Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
                    Matcher match = pattern.matcher(msg.obj.toString());
                    boolean val = match.find();
                    if (val) {
                        editTextSellingsBarcode.setText("");
                        return;
                    }
                    player.start();
                    editTextSellingsBarcode.setText(msg.obj.toString());
                } else if (msg.what == PLEASE_SCAN_STATE) {
                    editTextSellingsBarcode.setText("");
                } else if (msg.what == FILL_TEXT_DATA_PRODUCT_NAME) {
                    tvSellingsProductName.setText(msg.obj.toString());
                } else if (msg.what == FILL_TEXT_DATA_AMOUNT_DOC) {
                    tvSellingsScanNumberDoc.setText(msg.obj.toString());
                } else if (msg.what == FILL_TEXT_DATA_AMOUNT) {
                    editTextSellingsAmountScan.setText("" + 1);
                    tvSellingsScanNumber.setText(((Integer) msg.obj + 1) + "");
                } else if (msg.what == ERROR_EXTERNAL_THREAD) {
                    clean();
                    startThreadScan();
                    Toast.makeText(getApplicationContext(), admissionProductNotFound, Toast.LENGTH_LONG).show();
                } else if (msg.what == CALL_BARCODE2D_ACTIVITY) {
                    runScanBarcode2D();
                }
            }

        };
        String device = MainActivity.devInfo.getScannerSerialport();
        int baudrate = MainActivity.devInfo.getScannerBaudrate();
        try {
            scanner = new ScannerHelper3501(device, baudrate, handlerScan);
        } catch (Error e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        startThreadScan();
    }

    private void scanBarcodeSellingsProduct() {
        barcode = editTextSellingsBarcode.getText().toString();
        String productCode = null;
        tempSellProduct = null;
        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
        folder = folder + "barcode.xml";
        File file = new File(folder);
        try {
            FileInputStream in = new FileInputStream(folder);
            BarcodeXmlReader barcodeXmlReader = new BarcodeXmlReader();
            productCode = barcodeXmlReader.parseMapBarcodes(in, barcode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileInputStream in=new FileInputStream(filePath);
            AdmissionXmlReader admissionXmlReader=new AdmissionXmlReader();
            sell=admissionXmlReader.parseAdmission(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Product.xml";
        Product product=null;
        try {
            FileInputStream in=new FileInputStream(folder);
            ProductXmlReader productXmlReader=new ProductXmlReader();
            product=productXmlReader.parseMapProducts(in,productCode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<AdmissionProduct> listSellingsProducts=sell.getAdmissionProducts();
        for (AdmissionProduct sellProduct:listSellingsProducts){
            if(sellProduct.getProductCode().equals(productCode)){
                tempSellProduct=sellProduct;
            }
        }
        if(!(tempSellProduct==null)){
            if(tempSellProduct.getMarking()){
                handlerScan.sendEmptyMessage(CALL_BARCODE2D_ACTIVITY);
            }
            else {
                Message msg = null;
                msg = handlerScan.obtainMessage(FILL_TEXT_DATA_PRODUCT_NAME,product.getName());
                handlerScan.sendMessage(msg);
                msg = handlerScan.obtainMessage(FILL_TEXT_DATA_AMOUNT_DOC, tempSellProduct.getBalanceValueDocs());
                handlerScan.sendMessage(msg);
                msg = handlerScan.obtainMessage(FILL_TEXT_DATA_AMOUNT, tempSellProduct.getBalanceValue());
                handlerScan.sendMessage(msg);
            }
        }
        else{
            handlerScan.sendEmptyMessage(ERROR_EXTERNAL_THREAD);
        }
    }

    private void startThreadScan() {
        threadScan = new Thread(new Runnable() {
            @Override
            public void run() {
                handlerScan.sendEmptyMessage(PLEASE_SCAN_STATE);
                scanner.scan();
                while (true) {
                    if (out) {
                        break;
                    }

                    if (!(editTextSellingsBarcode.getText().toString().length() <= 12)) {
                        scanBarcodeSellingsProduct();
                        out = true;
                    }
                }
            }
        });
        threadScan.start();
    }

    public void confirm() {
        if (!(tempSellProduct == null)) {
            int balanceValue=Integer.parseInt(tvSellingsScanNumber.getText().toString()) + (Integer.parseInt(editTextSellingsAmountScan.getText().toString()) - 1);
            if(balanceValue <= tempSellProduct.getBalanceValueDocs()) {
                tempSellProduct.setBalanceValue(balanceValue);
            }
            else{
                Toast.makeText(getApplicationContext(),"Количество отсканированных товаров больше, чем количество товаров по документам", Toast.LENGTH_LONG).show();
                clean();
                startThreadScan();
                return;
            }
            try {
                OutputStream out=new FileOutputStream(filePath);
                SaveAdmissionsXmlWriter saveAdmissionsXmlWriter=new SaveAdmissionsXmlWriter(out);
                saveAdmissionsXmlWriter.admissionWrite(sell);
                Toast.makeText(getApplicationContext(),"Файл сохранен по адресу"+filePath, Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
            clean();
            startThreadScan();
        } else {
            Toast.makeText(getApplicationContext(), productNotFound, Toast.LENGTH_LONG).show();
            clean();
        }
    }

    private void cancel() {
        if (tvSellingsProductName.getText().toString().equals("")) {
            clean();
        } else {
            clean();
            startThreadScan();
        }
    }

    private void back() {
        Intent intent = new Intent(this, OpenSellingsActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("filePath", filePath);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void clean() {
        tvSellingsProductName.setText("");
        tvSellingsScanNumberDoc.setText("");
        editTextSellingsAmountScan.setText(0 + "");
        tvSellingsScanNumber.setText("");
        editTextSellingsBarcode.setText("");
        tempSellProduct=null;
        out = false;
    }

    private void runScanBarcode2D(){
        Intent intent=new Intent(this,SellingsBarcode2DScanActivity.class);
        intent.putExtra("userCode",userCode);
        intent.putExtra("filePath",filePath);
        intent.putExtra("productCode",tempSellProduct.getProductCode());
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        intent.putExtra("currentProductCode",currentProductCode);
        startActivity(intent);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSellingsBarcode:
                confirm();
                break;
            case R.id.buttonSellingsCancel:
                cancel();
                break;
            case R.id.buttonSellingsBack:
                back();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scanner.Close();
        out = true;
    }
}
