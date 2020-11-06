package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.myapplication.model.Balance;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.Warhouse;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.myapplication.xml.readers.BalanceXmlReader;
import com.example.myapplication.xml.readers.Barcode2DXmlReader;
import com.example.myapplication.xml.readers.BarcodeXmlReader;
import com.example.myapplication.xml.readers.ProductXmlReader;
import com.example.myapplication.xml.readers.WarhouseXmlReader;
import com.smartdevicesdk.scanner.ScannerHelper3501;
import com.smartdevicesdk.utils.HandlerMessage;

public class ScanActivity extends AppCompatActivity implements View.OnClickListener {
    final int PLEASE_SCAN_STATE = 0;
    final int FILL_TEXT_DATA_PRODUCT_CODE = 1;
    final int FILL_TEXT_DATA_ARTICUL = 2;
    final int FILL_TEXT_DATA_NAME = 3;
    final int FILL_TEXT_DATA_QUANTITY = 4;
    final int ERROR_EXTERNAL_THREAD = 5;
    final int FILL_TEXT_RESERVE_QUANTITY=6;
    private String userCode;
    private String userWarehouseCode;
    private EditText editTextEnterBarcode;
    private TextView textDataName;
    private TextView textDataArticul;
    private TextView textDataProductCode;
    private TextView textDataBarcode;
    private TextView textDataQuantity;
    private TextView textReserveQuantity;
    private String barcodeNotFound;
    private String productNotFound;
    private String productNotFoundFromWarehouse;
    private String warehousesNotFound;
    private String barcode = "";
    private Handler handlerScan = null;
    public static ScannerHelper3501 scanner = null;
    private String pleaseScan;
    private Thread threadScan = null;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        userCode = getIntent().getStringExtra("userCode");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        editTextEnterBarcode = findViewById(R.id.editTextEnterBarcode);
        textDataName = findViewById(R.id.textDataName);
        textDataArticul = findViewById(R.id.textDataArticul);
        textDataProductCode = findViewById(R.id.textDataProductCode);
        textDataBarcode = findViewById(R.id.textDataBarcode);
        textDataQuantity = findViewById(R.id.textDataQuantity);
        textReserveQuantity=findViewById(R.id.textReserveQuantity);
        Button buttonFindByBarcode = findViewById(R.id.buttonFindByBarcode);
        buttonFindByBarcode.setOnClickListener(this);
        Button buttonScanBack = findViewById(R.id.buttonScanBack);
        buttonScanBack.setOnClickListener(this);
        Button buttonScanScan = findViewById(R.id.buttonScanScan);
        buttonScanScan.setOnClickListener(this);
        Resources res = getResources();
        player = MediaPlayer.create(getApplicationContext(), R.raw.scan);
        barcodeNotFound = res.getString(R.string.barcode_not_found);
        productNotFound = res.getString(R.string.product_not_found);
        warehousesNotFound = res.getString(R.string.warhouses_not_found);
        productNotFoundFromWarehouse=res.getString(R.string.product_not_found_from_warehouse);
//        pleaseScan = res.getString(R.string.please_scan);
        handlerScan = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == HandlerMessage.SCANNER_DATA_MSG) {
                    Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
                    Matcher match = pattern.matcher(msg.obj.toString());
                    boolean val = match.find();
                    if (val) {
                        editTextEnterBarcode.setText("");
                        return;
                    }
                    player.start();
                    editTextEnterBarcode.setText(msg.obj.toString());
                    textDataBarcode.setText(msg.obj.toString());
                } else if (msg.what == PLEASE_SCAN_STATE) {
                    editTextEnterBarcode.setText("");
                } else if (msg.what == FILL_TEXT_DATA_PRODUCT_CODE) {
                    textDataProductCode.setText(msg.obj.toString());
                } else if (msg.what == FILL_TEXT_DATA_ARTICUL) {
                    textDataArticul.setText(msg.obj.toString());
                } else if (msg.what == FILL_TEXT_DATA_NAME) {
                    textDataName.setText(msg.obj.toString());
                } else if (msg.what == FILL_TEXT_DATA_QUANTITY) {
                    textDataQuantity.setText(msg.obj.toString());
                }else if(msg.what==FILL_TEXT_RESERVE_QUANTITY){
                  textReserveQuantity.setText(msg.obj.toString());
                }
                else if (msg.what == ERROR_EXTERNAL_THREAD) {
                    textDataBarcode.setText(msg.obj.toString());
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

        threadScan = new Thread(new Runnable() {
            @Override
            public void run() {
                handlerScan.sendEmptyMessage(PLEASE_SCAN_STATE);
                scanner.scan();
                while (!Thread.currentThread().isInterrupted()) {
                    if (!(editTextEnterBarcode.getText().toString().length() <= 12)) {
                        findProductByBarcode();
                    }
                }
            }
        });
        threadScan.start();
    }

    private void findProductByBarcode() {
        String productCode = null;
        Message msg = null;
        Product product = null;
        List<Warhouse> warhouseList = null;
        Balance balance =null;
        barcode = editTextEnterBarcode.getText().toString();
        String fileName = "barcode.xml";
        try {
            InputStream in = getInputStream(fileName);
            productCode = getProductCode(in, barcode);
            in.close();
        } catch (FileNotFoundException e) {
            getErrorMessage(e.getMessage());
        } catch (XmlPullParserException e) {
            getErrorMessage(e.getMessage());
        } catch (IOException e) {
            getErrorMessage(e.getMessage());
        }
        if (productCode == null) {
            fileName = "barcode2d.xml";
            try {
                InputStream in = getInputStream(fileName);
                productCode = getProductCodeFromBarcode2D(in, barcode);
                in.close();
            } catch (FileNotFoundException e) {
                getErrorMessage(e.getMessage());
            } catch (XmlPullParserException e) {
                getErrorMessage(e.getMessage());
            } catch (IOException e) {
                getErrorMessage(e.getMessage());
            }
        }
        if (productCode == null) {
            setTextviewvsFromScanThread(barcodeNotFound, barcodeNotFound, barcodeNotFound, barcodeNotFound,barcodeNotFound);
            return;
        } else {
            fileName = "Product.xml";
            try {
                InputStream in = getInputStream(fileName);
                product = getProduct(in, productCode);
                in.close();
            } catch (FileNotFoundException e) {
                getErrorMessage(e.getMessage());
            } catch (XmlPullParserException e) {
                getErrorMessage(e.getMessage());
            } catch (IOException e) {
                getErrorMessage(e.getMessage());
            } catch (Exception e) {
                getErrorMessage(e.getMessage());
            }
        }
//        fileName = "warehouse.xml";
//        try {
//            InputStream in = getInputStream(fileName);
//            warhouseList = getWarhouses(in);
//            in.close();
//        } catch (FileNotFoundException e) {
//            getErrorMessage(e.getMessage());
//        } catch (XmlPullParserException e) {
//            getErrorMessage(e.getMessage());
//        } catch (IOException e) {
//            getErrorMessage(e.getMessage());
//        }
        if (product == null) {
            setTextviewvsFromScanThread(productCode, productNotFound, productNotFound, productNotFound,productNotFound);
            return;
        }
//        if (warhouseList == null) {
//            setTextviewvsFromScanThread(productCode, product.getVendorCode(), product.getName(), warehousesNotFound);
//            return;
//        }
        fileName = "balance.xml";
        try {

                InputStream in = getInputStream(fileName);
                balance = getBalance(in,userWarehouseCode, productCode);
                in.close();
        } catch (FileNotFoundException e) {
            getErrorMessage(e.getMessage());
        } catch (XmlPullParserException e) {
            getErrorMessage(e.getMessage());
        } catch (IOException e) {
            getErrorMessage(e.getMessage());
        } catch (Exception e) {
            getErrorMessage(e.getMessage());
        }
        if(balance==null){
            setTextviewvsFromScanThread(productCode,productNotFoundFromWarehouse,productNotFoundFromWarehouse,productNotFoundFromWarehouse,productNotFoundFromWarehouse);
            return;
        }
        setTextviewvsFromScanThread(productCode, product.getVendorCode(), product.getName(), Double.toString(balance.getBalance()),Double.toString(balance.getReserve()));
    }

    private String getProductCode(InputStream in, String barcode) throws IOException, XmlPullParserException {
        String productCode = null;
        BarcodeXmlReader barcodeXmlReader = new BarcodeXmlReader();
        productCode = barcodeXmlReader.parseMapBarcodes(in, barcode);
        return productCode;
    }

    private String getProductCodeFromBarcode2D(InputStream in, String barcode) throws IOException, XmlPullParserException {
        String productCode = null;
        Barcode2DXmlReader barcode2DXmlReader = new Barcode2DXmlReader();
        productCode = barcode2DXmlReader.parseMapBarcodes2D(in, barcode);
        return productCode;
    }

    private Product getProduct(InputStream in, String productCode) throws IOException, XmlPullParserException {
        Product product = null;
        ProductXmlReader productXmlReader = new ProductXmlReader();
        product = productXmlReader.parseMapProducts(in, productCode);
        return product;
    }

    private List<Warhouse> getWarhouses(InputStream in) throws IOException, XmlPullParserException {
        List<Warhouse> warhouseList = null;
        WarhouseXmlReader warhouseXmlReader = new WarhouseXmlReader();
        warhouseList = warhouseXmlReader.parseListWarhouses(in);
        return warhouseList;
    }

    private InputStream getInputStream(String fileName) throws FileNotFoundException {
        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
        folder = folder + fileName;
        File file = new File(folder);
        FileInputStream in = new FileInputStream(folder);
        return in;
    }

    private Balance getBalance(InputStream in, String warehouseCode, String productCode) throws IOException, XmlPullParserException {
        BalanceXmlReader balanceXmlReader = new BalanceXmlReader();
        Balance balance = balanceXmlReader.parseBalance(in, warehouseCode, productCode);
        return balance;
    }

    private void outOfScan() {
        Intent intent = new Intent(this, ControlActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void setTextviewvsFromScanThread(String productCode, String vendorCode, String name, String quantity,String reserveQuantity) {
        Message msg = null;
        msg = handlerScan.obtainMessage(FILL_TEXT_DATA_PRODUCT_CODE, productCode);
        handlerScan.sendMessage(msg);
        msg = handlerScan.obtainMessage(FILL_TEXT_DATA_ARTICUL, vendorCode);
        handlerScan.sendMessage(msg);
        msg = handlerScan.obtainMessage(FILL_TEXT_DATA_NAME, name);
        handlerScan.sendMessage(msg);
        msg = handlerScan.obtainMessage(FILL_TEXT_DATA_QUANTITY, quantity);
        handlerScan.sendMessage(msg);
        msg=handlerScan.obtainMessage(FILL_TEXT_RESERVE_QUANTITY,reserveQuantity);
        handlerScan.sendMessage(msg);
        handlerScan.sendEmptyMessage(PLEASE_SCAN_STATE);
        scanner.scan();
    }

    private void getErrorMessage(String message) {
        Message msg = null;
        msg = handlerScan.obtainMessage(ERROR_EXTERNAL_THREAD, message);
        handlerScan.sendMessage(msg);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            outOfScan();
            return true;
        }
        else{
            return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonFindByBarcode:
//                findProductByBarcode();
                break;
            case R.id.buttonScanBack:
                outOfScan();
                break;
            case R.id.buttonScanScan:
//                findProductByBarcode();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scanner.Close();
        threadScan.interrupt();
    }
}
