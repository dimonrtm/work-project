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

public class AdmissionScanActivity extends AppCompatActivity implements View.OnClickListener {
    final int PLEASE_SCAN_STATE = 0;
    final int FILL_TEXT_DATA_PRODUCT_NAME = 1;
    final int FILL_TEXT_DATA_AMOUNT_DOC = 2;
    final int FILL_TEXT_DATA_AMOUNT = 3;
    final int CALL_BARCODE2D_ACTIVITY = 4;
    final int ERROR_EXTERNAL_THREAD = 5;
    private String userCode;
    private String filePath;
    private String barcode;
    private String currentProductCode="";
    private Admission admission;
    private AdmissionProduct tempAdmissionProduct;
    private EditText editTextAdmissionBarcode;
    private TextView tvProductName;
    private TextView tvScanNumberDoc;
    private TextView tvScanNumber;
    private EditText editTextAmountScan;
    private MediaPlayer player;
    private Handler handlerScan = null;
    public static ScannerHelper3501 scanner = null;
    private Thread threadScan = null;
    private boolean out = false;
    private String admissionProductNotFound;
    private String productNotFound;
    private String userWarehouseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission_scan);
        userCode = getIntent().getStringExtra("userCode");
        filePath = getIntent().getStringExtra("filePath");
        userWarehouseCode = getIntent().getStringExtra("userWarehouseCode");
        currentProductCode = getIntent().getStringExtra("currentProductCode");
        editTextAdmissionBarcode = findViewById(R.id.editTextAdmissionBarcode);
        tvProductName = findViewById(R.id.tvProductName);
        tvScanNumberDoc = findViewById(R.id.tvScanNumberDoc);
        tvScanNumber = findViewById(R.id.tvScanNumber);
        editTextAmountScan = findViewById(R.id.editTextAmountScan);
        editTextAmountScan.setText("" + 0);
        Button buttonAdmissionBarcode = findViewById(R.id.buttonAdmissionBarcode);
        buttonAdmissionBarcode.setOnClickListener(this);
        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(this);
        Button buttonAdmissionBack = findViewById(R.id.buttonAdmissionBack);
        buttonAdmissionBack.setOnClickListener(this);
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
                admission = admissionXmlReader.parseAdmission(in);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<AdmissionProduct> listAdmissionProducts = admission.getAdmissionProducts();
            for (AdmissionProduct admissionProduct : listAdmissionProducts) {
                if (admissionProduct.getProductCode().equals(currentProductCode)) {
                    tempAdmissionProduct = admissionProduct;
                }
            }

            if (!(tempAdmissionProduct == null)) {
                if (tempAdmissionProduct.getMarking()) {
                    currentProductCode="";
                    runScanBarcode2D();
                } else {
                    tvProductName.setText(product.getName());
                    tvScanNumberDoc.setText(tempAdmissionProduct.getBalanceValueDocs() + "");
                    editTextAmountScan.setText("" + 1);
                    tvScanNumber.setText(tempAdmissionProduct.getBalanceValue() + "");
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
                            editTextAdmissionBarcode.setText("");
                            return;
                        }
                        player.start();
                        editTextAdmissionBarcode.setText(msg.obj.toString());
                    } else if (msg.what == PLEASE_SCAN_STATE) {
                        editTextAdmissionBarcode.setText("");
                    } else if (msg.what == FILL_TEXT_DATA_PRODUCT_NAME) {
                        tvProductName.setText(msg.obj.toString());
                    } else if (msg.what == FILL_TEXT_DATA_AMOUNT_DOC) {
                        tvScanNumberDoc.setText(msg.obj.toString());
                    } else if (msg.what == FILL_TEXT_DATA_AMOUNT) {
                        editTextAmountScan.setText("" + 1);
                        tvScanNumber.setText(((Integer) msg.obj + 1) + "");
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

        private void scanBarcodeAdmissionProduct () {
            barcode = editTextAdmissionBarcode.getText().toString();
            String productCode = null;
            tempAdmissionProduct = null;
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
                FileInputStream in = new FileInputStream(filePath);
                AdmissionXmlReader admissionXmlReader = new AdmissionXmlReader();
                admission = admissionXmlReader.parseAdmission(in);
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
            Product product = null;
            try {
                FileInputStream in = new FileInputStream(folder);
                ProductXmlReader productXmlReader = new ProductXmlReader();
                product = productXmlReader.parseMapProducts(in, productCode);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<AdmissionProduct> listAdmissionProducts = admission.getAdmissionProducts();
            for (AdmissionProduct admissionProduct : listAdmissionProducts) {
                if (admissionProduct.getProductCode().equals(productCode)) {
                    tempAdmissionProduct = admissionProduct;
                }
            }
            if (!(tempAdmissionProduct == null)) {
                if (tempAdmissionProduct.getMarking()) {
                    handlerScan.sendEmptyMessage(CALL_BARCODE2D_ACTIVITY);
                } else {
                    Message msg = null;
                    msg = handlerScan.obtainMessage(FILL_TEXT_DATA_PRODUCT_NAME, product.getName());
                    handlerScan.sendMessage(msg);
                    msg = handlerScan.obtainMessage(FILL_TEXT_DATA_AMOUNT_DOC, tempAdmissionProduct.getBalanceValueDocs());
                    handlerScan.sendMessage(msg);
                    msg = handlerScan.obtainMessage(FILL_TEXT_DATA_AMOUNT, tempAdmissionProduct.getBalanceValue());
                    handlerScan.sendMessage(msg);
                }
            } else {
                handlerScan.sendEmptyMessage(ERROR_EXTERNAL_THREAD);
            }
        }

        private void startThreadScan () {
            threadScan = new Thread(new Runnable() {
                @Override
                public void run() {
                    handlerScan.sendEmptyMessage(PLEASE_SCAN_STATE);
                    scanner.scan();
                    while (true) {
                        if (out) {
                            break;
                        }

                        if (!(editTextAdmissionBarcode.getText().toString().length() <= 12)) {
                            scanBarcodeAdmissionProduct();
                            out = true;
                        }
                    }
                }
            });
            threadScan.start();
        }

        public void confirm () {
            if (!(tempAdmissionProduct == null)) {
                int balanceValue = Integer.parseInt(tvScanNumber.getText().toString()) + (Integer.parseInt(editTextAmountScan.getText().toString()) - 1);
                if (balanceValue <= tempAdmissionProduct.getBalanceValueDocs()) {
                    tempAdmissionProduct.setBalanceValue(balanceValue);
                } else {
                    Toast.makeText(getApplicationContext(), "Количество отсканированных товаров больше, чем количество товаров по документам", Toast.LENGTH_LONG).show();
                    clean();
                    startThreadScan();
                    return;
                }
                try {
                    OutputStream out = new FileOutputStream(filePath);
                    SaveAdmissionsXmlWriter saveAdmissionsXmlWriter = new SaveAdmissionsXmlWriter(out);
                    saveAdmissionsXmlWriter.admissionWrite(admission);
                    Toast.makeText(getApplicationContext(), "Файл сохранен по адресу" + filePath, Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                clean();
                startThreadScan();
            } else {
                Toast.makeText(getApplicationContext(), productNotFound, Toast.LENGTH_LONG).show();
                clean();
            }
        }

        private void cancel () {
            if (tvProductName.getText().toString().equals("")) {
                clean();
            } else {
                clean();
                startThreadScan();
            }
        }

        private void back () {
            Intent intent = new Intent(this, OpenAdmissionsActivity.class);
            intent.putExtra("userCode", userCode);
            intent.putExtra("filePath", filePath);
            intent.putExtra("userWarehouseCode", userWarehouseCode);
            startActivity(intent);
            finish();
        }

        private void clean () {
            tvProductName.setText("");
            tvScanNumberDoc.setText("");
            editTextAmountScan.setText(0 + "");
            tvScanNumber.setText("");
            editTextAdmissionBarcode.setText("");
            tempAdmissionProduct = null;
            out = false;
        }

        private void runScanBarcode2D () {
            Intent intent = new Intent(this, AdmissionsBarcode2DScanActivity.class);
            intent.putExtra("userCode", userCode);
            intent.putExtra("filePath", filePath);
            intent.putExtra("productCode", tempAdmissionProduct.getProductCode());
            intent.putExtra("userWarehouseCode", userWarehouseCode);
            intent.putExtra("currentProductCode",currentProductCode);
            startActivity(intent);
            finish();
        }

        @Override
        public boolean onKeyUp ( int keyCode, KeyEvent event){
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                back();
                return true;
            } else {
                return super.onKeyUp(keyCode, event);
            }
        }

        @Override
        public void onClick (View view){
            switch (view.getId()) {
                case R.id.buttonAdmissionBarcode:
                    confirm();
                    break;
                case R.id.buttonCancel:
                    cancel();
                    break;
                case R.id.buttonAdmissionBack:
                    back();
                    break;
            }
        }

        @Override
        public void onDestroy () {
            super.onDestroy();
            scanner.Close();
            out = true;
        }
    }

