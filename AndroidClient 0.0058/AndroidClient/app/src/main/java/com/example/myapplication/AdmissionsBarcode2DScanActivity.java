package com.example.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DTO.AdmissionProductForAdapterDTO;
import com.example.myapplication.adapters.Scan2DBarcodeAdapter;
import com.example.myapplication.model.Admission;
import com.example.myapplication.model.AdmissionMarkingProduct;
import com.example.myapplication.model.AdmissionProduct;
import com.example.myapplication.model.Product;
import com.example.myapplication.xml.readers.AdmissionXmlReader;
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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdmissionsBarcode2DScanActivity extends AppCompatActivity implements View.OnClickListener {
    final int PLEASE_SCAN_STATE = 0;
    final int FILL_AMOUNT_SCAN = 1;
    final int ERROR_EXTERNAL_THREAD = 2;
    final int PRODUCT_IS_MARKED=3;
    final int REDRAW_RECYCLER_VIEW = 4;
    private String userCode;
    private String filePath;
    private String productCode;
    private String admissionProductNotFound;
    private String productNotFound;
    private String productIsMarked;
    private String userWarehouseCode;
    private String currentProductCode;
    private AdmissionProduct tempAdmissionProduct;
    private Admission admission;
    private AdmissionProduct currentAdmissionProduct;
    private AdmissionMarkingProduct currentAdmissionMarkingProduct;
    private boolean out = false;
    private List<AdmissionMarkingProduct> currentProduct2DBarcodes;
    private Handler handlerScan = null;
    private MediaPlayer player;
    public static ScannerHelper3501 scanner = null;
    private Thread threadScan;
    private TextView tvProductNameBarcode2D;
    private TextView tvNumberDocBarcode2D;
    private TextView tvScanAmount;
    private RecyclerView rvScanBarcode2DAdmissions;
    private EditText editTextBarcode2D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admissions_barcode2_dscan);
        userCode = getIntent().getStringExtra("userCode");
        filePath = getIntent().getStringExtra("filePath");
        productCode = getIntent().getStringExtra("productCode");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        currentProductCode=getIntent().getStringExtra("currentProductCode");
        tvProductNameBarcode2D = findViewById(R.id.tvProductNameBarcode2D);
        tvNumberDocBarcode2D = findViewById(R.id.tvNumberDocBarcode2D);
        tvScanAmount = findViewById(R.id.tvScanAmount);
        editTextBarcode2D = findViewById(R.id.editTextBarcode2D);
        Button buttonConfirm = findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(this);
        Button buttonCancelBarcode2D = findViewById(R.id.buttonCancelBarcode2D);
        buttonCancelBarcode2D.setOnClickListener(this);
        Button buttonBackBarcode2D = findViewById(R.id.buttonBackBarcode2D);
        buttonBackBarcode2D.setOnClickListener(this);
        Resources res = getResources();
        admissionProductNotFound = res.getString(R.string.admission_product_not_found);
        productNotFound = res.getString(R.string.product_not_found);
        productIsMarked=res.getString(R.string.product_is_marked);
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
                    tvNumberDocBarcode2D.setText(tempAdmissionProduct.getBalanceValueDocs() + "");
                    tvScanAmount.setText(tempAdmissionProduct.getBalanceValue() + "");
            }
        }
        player = MediaPlayer.create(getApplicationContext(), R.raw.scan);
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
        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Product.xml";
         product = null;
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
        tvProductNameBarcode2D.setText(product.getName());
        List<AdmissionProduct> listAdmissionProducts = admission.getAdmissionProducts();
        for (AdmissionProduct admProd : listAdmissionProducts) {
            if (admProd.getProductCode().equals(productCode)) {
                currentAdmissionProduct = admProd;
            }
        }
        tvNumberDocBarcode2D.setText(currentAdmissionProduct.getBalanceValueDocs() + "");
        tvScanAmount.setText(currentAdmissionProduct.getBalanceValue() + "");
        currentProduct2DBarcodes = new ArrayList<AdmissionMarkingProduct>();
        List<AdmissionMarkingProduct> admissionMarkingProducts = admission.getAdmissionMarkingProducts();
        for (AdmissionMarkingProduct admMarkProd : admissionMarkingProducts) {
            if (admMarkProd.getProductCode().equals(productCode)) {
                currentProduct2DBarcodes.add(admMarkProd);
            }
        }
        Scan2DBarcodeAdapter scan2DBarcodeAdapter = new Scan2DBarcodeAdapter(currentProduct2DBarcodes);
        rvScanBarcode2DAdmissions = findViewById(R.id.rvScanBarcode2DAdmissions);
        rvScanBarcode2DAdmissions.setAdapter(scan2DBarcodeAdapter);
        handlerScan = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == HandlerMessage.SCANNER_DATA_MSG) {
                    Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
                    Matcher match = pattern.matcher(msg.obj.toString());
                    boolean val = match.find();
                    if (val) {
                        editTextBarcode2D.setText("");
                        return;
                    }
                    player.start();
                    editTextBarcode2D.setText(msg.obj.toString());
                } else if (msg.what == PLEASE_SCAN_STATE) {
                    editTextBarcode2D.setText("");
                } else if (msg.what == FILL_AMOUNT_SCAN) {
                    tvScanAmount.setText((Integer.parseInt(tvScanAmount.getText().toString()) + 1) + "");
                } else if (msg.what == ERROR_EXTERNAL_THREAD) {
                    clean();
                    startThreadScan();
                    Toast.makeText(getApplicationContext(), admissionProductNotFound, Toast.LENGTH_LONG).show();
                }else if(msg.what==PRODUCT_IS_MARKED){
                    Toast.makeText(getApplicationContext(),productIsMarked, Toast.LENGTH_LONG).show();
                }else if(msg.what==REDRAW_RECYCLER_VIEW){
                    scan2DBarcodeAdapter.notifyDataSetChanged();
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

//        Toast.makeText(getApplicationContext(),admission.getDate().toString(), Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(),filePath, Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplicationContext(),productCode, Toast.LENGTH_LONG).show();
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

                    if (!(editTextBarcode2D.getText().toString().length() <= 25)) {
                        scanBarcode2DAdmissionProduct();
                        out = true;
                    }
                }
            }
        });
        threadScan.start();
    }

    private void scanBarcode2DAdmissionProduct() {
        String barcode = editTextBarcode2D.getText().toString();
        for (AdmissionMarkingProduct markingProduct : currentProduct2DBarcodes) {
            if (markingProduct.getBartcodeLabeling().equals(barcode)) {
                currentAdmissionMarkingProduct = markingProduct;
            }
        }
        if (currentAdmissionMarkingProduct != null) {
            if(!currentAdmissionMarkingProduct.getMarkingCompleted()) {
                currentAdmissionMarkingProduct.setMarkingCompleted(true);
                handlerScan.sendEmptyMessage(FILL_AMOUNT_SCAN);
                handlerScan.sendEmptyMessage(REDRAW_RECYCLER_VIEW);
            }
            else{
               handlerScan.sendEmptyMessage(PRODUCT_IS_MARKED);
            }
        } else {
            handlerScan.sendEmptyMessage(ERROR_EXTERNAL_THREAD);
        }
    }

    private void confirm() {
        if (currentAdmissionMarkingProduct != null) {
            currentAdmissionProduct.setBalanceValue(tvScanAmount.getText().toString());
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

    private void cancel() {
        clean();
        startThreadScan();
    }

    public void clean() {
        editTextBarcode2D.setText("");
        currentAdmissionMarkingProduct = null;
        out = false;
    }

    private void back() {
        Intent intent = new Intent(this, AdmissionScanActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("filePath", filePath);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        intent.putExtra("currentProductCode","");
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonConfirm:
                confirm();
                break;
            case R.id.buttonCancelBarcode2D:
                cancel();
                break;
            case R.id.buttonBackBarcode2D:
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
