package com.example.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
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

import android.os.Bundle;

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

public class SellingsBarcode2DScanActivity extends AppCompatActivity implements View.OnClickListener {
    final int PLEASE_SCAN_STATE = 0;
    final int FILL_AMOUNT_SCAN = 1;
    final int ERROR_EXTERNAL_THREAD = 2;
    final int PRODUCT_IS_MARKED = 3;
    final int REDRAW_RECYCLER_VIEW = 4;
    private String userCode;
    private String filePath;
    private String productCode;
    private String userWarehouseCode;
    private String currentProductCode;
    private String admissionProductNotFound;
    private String productNotFound;
    private String productIsMarked;
    private Admission sell;
    private AdmissionProduct currentSellingsProduct;
    private AdmissionMarkingProduct currentSellingsMarkingProduct;
    private AdmissionProduct tempAdmissionProduct;
    private boolean out = false;
    private Handler handlerScan = null;
    private MediaPlayer player;
    public static ScannerHelper3501 scanner = null;
    private Thread threadScan;
    private EditText editTextBarcode2DSellings;
    private List<AdmissionMarkingProduct> currentProduct2DBarcodes;
    private TextView tvProductNameBarcode2DSellings;
    private TextView tvNumberDocBarcode2D;
    private TextView tvScanAmountSellings;
    private RecyclerView rvScanBarcode2DSellings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellings_barcode2_dscan);
        userCode = getIntent().getStringExtra("userCode");
        filePath = getIntent().getStringExtra("filePath");
        productCode = getIntent().getStringExtra("productCode");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        currentProductCode=getIntent().getStringExtra("currentProductCode");
        tvProductNameBarcode2DSellings = findViewById(R.id.tvProductNameBarcode2DSellings);
        tvNumberDocBarcode2D = findViewById(R.id.tvNumberDocBarcode2D);
        tvScanAmountSellings = findViewById(R.id.tvScanAmountSellings);
        editTextBarcode2DSellings = findViewById(R.id.editTextBarcode2DSellings);
        Button buttonConfirmSellings = findViewById(R.id.buttonConfirmSellings);
        buttonConfirmSellings.setOnClickListener(this);
        Button buttonCancelBarcode2DSellings = findViewById(R.id.buttonCancelBarcode2DSellings);
        buttonCancelBarcode2DSellings.setOnClickListener(this);
        Button buttonBackBarcode2DSellings = findViewById(R.id.buttonBackBarcode2DSellings);
        buttonBackBarcode2DSellings.setOnClickListener(this);
        Resources res = getResources();
        admissionProductNotFound = res.getString(R.string.admission_product_not_found);
        productNotFound = res.getString(R.string.product_not_found);
        productIsMarked = res.getString(R.string.product_is_marked);
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

            List<AdmissionProduct> listAdmissionProducts = sell.getAdmissionProducts();
            for (AdmissionProduct admissionProduct : listAdmissionProducts) {
                if (admissionProduct.getProductCode().equals(currentProductCode)) {
                    tempAdmissionProduct = admissionProduct;
                }
            }

            if (!(tempAdmissionProduct == null)) {
                tvNumberDocBarcode2D.setText(tempAdmissionProduct.getBalanceValueDocs() + "");
                tvScanAmountSellings.setText(tempAdmissionProduct.getBalanceValue() + "");
            }
        }
        player = MediaPlayer.create(getApplicationContext(), R.raw.scan);
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
        tvProductNameBarcode2DSellings.setText(product.getName());
        List<AdmissionProduct> listSellingsProducts = sell.getAdmissionProducts();
        for (AdmissionProduct admSell : listSellingsProducts) {
            if (admSell.getProductCode().equals(productCode)) {
                currentSellingsProduct = admSell;
            }
        }
        tvNumberDocBarcode2D.setText(currentSellingsProduct.getBalanceValueDocs() + "");
        tvScanAmountSellings.setText(currentSellingsProduct.getBalanceValue() + "");
        currentProduct2DBarcodes = new ArrayList<AdmissionMarkingProduct>();
        List<AdmissionMarkingProduct> sellMarkingProducts = sell.getAdmissionMarkingProducts();
        for (AdmissionMarkingProduct sellMarkProd : sellMarkingProducts) {
            if (sellMarkProd.getProductCode().equals(productCode)) {
                currentProduct2DBarcodes.add(sellMarkProd);
            }
        }
        Scan2DBarcodeAdapter scan2DBarcodeAdapter = new Scan2DBarcodeAdapter(currentProduct2DBarcodes);
        rvScanBarcode2DSellings = findViewById(R.id.rvScanBarcode2DSellings);
        rvScanBarcode2DSellings.setAdapter(scan2DBarcodeAdapter);
        handlerScan = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == HandlerMessage.SCANNER_DATA_MSG) {
                    Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
                    Matcher match = pattern.matcher(msg.obj.toString());
                    boolean val = match.find();
                    if (val) {
                        editTextBarcode2DSellings.setText("");
                        return;
                    }
                    player.start();
                    editTextBarcode2DSellings.setText(msg.obj.toString());
                } else if (msg.what == PLEASE_SCAN_STATE) {
                    editTextBarcode2DSellings.setText("");
                } else if (msg.what == FILL_AMOUNT_SCAN) {
                    tvScanAmountSellings.setText((Integer.parseInt(tvScanAmountSellings.getText().toString()) + 1) + "");
                } else if (msg.what == ERROR_EXTERNAL_THREAD) {
                    clean();
                    startThreadScan();
                    Toast.makeText(getApplicationContext(), admissionProductNotFound, Toast.LENGTH_LONG).show();
                } else if (msg.what == PRODUCT_IS_MARKED) {
                    Toast.makeText(getApplicationContext(), productIsMarked, Toast.LENGTH_LONG).show();
                    startThreadScan();
                } else if(msg.what==REDRAW_RECYCLER_VIEW){
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
//        Toast.makeText(getApplicationContext(),sell.getDate().toString(), Toast.LENGTH_LONG).show();
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

                    if (!(editTextBarcode2DSellings.getText().toString().length() <= 25)) {
                        scanBarcode2DSellingsProduct();
                        out = true;
                    }
                }
            }
        });
        threadScan.start();
    }

    private void scanBarcode2DSellingsProduct() {
        String barcode = editTextBarcode2DSellings.getText().toString();
        for (AdmissionMarkingProduct markingProduct : currentProduct2DBarcodes) {
            if (markingProduct.getBartcodeLabeling().equals(barcode)) {
                currentSellingsMarkingProduct = markingProduct;
            }
        }
        if (currentSellingsMarkingProduct != null) {
            if (!currentSellingsMarkingProduct.getMarkingCompleted()) {
                currentSellingsMarkingProduct.setMarkingCompleted(true);
                handlerScan.sendEmptyMessage(FILL_AMOUNT_SCAN);
                handlerScan.sendEmptyMessage(REDRAW_RECYCLER_VIEW);
            } else {
                handlerScan.sendEmptyMessage(PRODUCT_IS_MARKED);
            }
        } else {
            handlerScan.sendEmptyMessage(ERROR_EXTERNAL_THREAD);
        }
    }

    private void confirm() {
        if (currentSellingsMarkingProduct != null) {
            currentSellingsProduct.setBalanceValue(tvScanAmountSellings.getText().toString());
            try {
                OutputStream out = new FileOutputStream(filePath);
                SaveAdmissionsXmlWriter saveAdmissionsXmlWriter = new SaveAdmissionsXmlWriter(out);
                saveAdmissionsXmlWriter.admissionWrite(sell);
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
        editTextBarcode2DSellings.setText("");
        currentSellingsMarkingProduct = null;
        out = false;
    }

    private void back() {
        Intent intent = new Intent(this, SellingsScanActivity.class);
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
            case R.id.buttonConfirmSellings:
                confirm();
                break;
            case R.id.buttonCancelBarcode2DSellings:
                cancel();
                break;
            case R.id.buttonBackBarcode2DSellings:
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
