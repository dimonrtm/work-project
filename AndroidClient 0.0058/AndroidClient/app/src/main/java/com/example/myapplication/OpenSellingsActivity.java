package com.example.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DTO.AdmissionProductForAdapterDTO;
import com.example.myapplication.adapters.IOpenAdmissionItemClick;
import com.example.myapplication.adapters.OpenAdmissionAdapter;
import com.example.myapplication.model.Admission;
import com.example.myapplication.model.AdmissionMarkingProduct;
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
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenSellingsActivity extends AppCompatActivity implements View.OnClickListener {
    final int REDRAW_RECYCLER_VIEW = 1;
    final int CALL_BARCODE2D_ACTIVITY = 2;
    private String userCode;
    private String filePath;
    private String userWarehouseCode;
    private String currentProductCode="";
    private TextView textOpenSellingsTitle;
    private ArrayAdapter<String> adapter;
    private RecyclerView openSellingsRecyclerview;
    private List<AdmissionProductForAdapterDTO> listSellingsProductForAdapter;
    private List<AdmissionMarkingProduct> listSellingsMarkingProduct;
    private OpenAdmissionAdapter openSellingsAdapter;
    private Admission sell;
    private MediaPlayer player;
    private String sellProductNotFound;
    private Handler handlerScan;
    private String barcodeFromCurrentScan = "";
    public static ScannerHelper3501 scanner = null;
    private Thread threadScan = null;
    private boolean out = false;
    private List<AdmissionProduct> sellingProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_sellings);
        userCode = getIntent().getStringExtra("userCode");
        filePath = getIntent().getStringExtra("filePath");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        Resources res = getResources();
        String[] filePathSplit = filePath.split("/");
        textOpenSellingsTitle = findViewById(R.id.textOpenSellingsTitle);
        textOpenSellingsTitle.setText(filePathSplit[filePathSplit.length - 1]);
        Button buttonOpenSellingsBack=findViewById(R.id.buttonOpenSellingsBack);
        buttonOpenSellingsBack.setOnClickListener(this);
        Button buttonSellingsScan=findViewById(R.id.buttonSellingsScan);
        buttonSellingsScan.setOnClickListener(this);
        Button buttonSellingsSave=findViewById(R.id.buttonSellingsSave);
        buttonSellingsSave.setOnClickListener(this);
        File file = new File(filePath);
        try {
            InputStream in = new FileInputStream(file);
            sell = getSell(in);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (XmlPullParserException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        sellingProducts = sell.getAdmissionProducts();
            listSellingsProductForAdapter = new ArrayList<AdmissionProductForAdapterDTO>();
            String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Product.xml";
            for (AdmissionProduct sProduct : sellingProducts) {
                try {
                    FileInputStream in = new FileInputStream(folder);
                    AdmissionProductForAdapterDTO sellProduct;
                    ProductXmlReader productXmlReader = new ProductXmlReader();
                    Product product = productXmlReader.parseMapProducts(in, sProduct.getProductCode());
                    if(!(product==null)) {
                        sellProduct = new AdmissionProductForAdapterDTO(sProduct.getProductCode(), product.getName(), product.getVendorCode(), sProduct.getBalanceValueDocs(),
                                sProduct.getBalanceValue(), sProduct.getMarking());
                    }
                    else{
                        sellProduct = new AdmissionProductForAdapterDTO(sProduct.getProductCode(), "","", sProduct.getBalanceValueDocs(),
                                sProduct.getBalanceValue(), sProduct.getMarking());
                    }
                    listSellingsProductForAdapter.add(sellProduct);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                } catch (XmlPullParserException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }
        openSellingsAdapter=new OpenAdmissionAdapter(listSellingsProductForAdapter);
        openSellingsRecyclerview = findViewById(R.id.openSellingsRecyclerView);
        openSellingsAdapter.setOnAdapterItemClick(new IOpenAdmissionItemClick() {
            @Override
            public void onItemClicked(String currProductCode) {
                currentProductCode=currProductCode;
                runSellingsScanActivity();
            }
        });
        openSellingsRecyclerview.setAdapter(openSellingsAdapter);
        player = MediaPlayer.create(getApplicationContext(), R.raw.scan);
        sellProductNotFound = res.getString(R.string.admission_product_not_found);
        handlerScan = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == HandlerMessage.SCANNER_DATA_MSG) {
                    Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
                    Matcher match = pattern.matcher(msg.obj.toString());
                    boolean val = match.find();
                    if (val) {
                        barcodeFromCurrentScan = "";
                        return;
                    }
                    player.start();
                    barcodeFromCurrentScan = msg.obj.toString();
                } else if (msg.what == REDRAW_RECYCLER_VIEW) {
                    openSellingsAdapter.notifyDataSetChanged();
                }else if(msg.what==CALL_BARCODE2D_ACTIVITY){
                    runSellingsScanActivity();
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
                scanner.scan();
                while (!out) {
                    if (!(barcodeFromCurrentScan.length() <= 12)) {
                        String productCodeFromCurrentScan="";
                        for (AdmissionProductForAdapterDTO scanProd : listSellingsProductForAdapter) {
                            String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
                            folder = folder + "barcode.xml";
                            File file = new File(folder);
                            try {
                                FileInputStream in = new FileInputStream(folder);
                                BarcodeXmlReader barcodeXmlReader = new BarcodeXmlReader();
                                productCodeFromCurrentScan = barcodeXmlReader.parseMapBarcodes(in, barcodeFromCurrentScan);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (XmlPullParserException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (scanProd.geProductCode().equals(productCodeFromCurrentScan)) {
                                if(scanProd.getMarked()){
                                    handlerScan.sendEmptyMessage(CALL_BARCODE2D_ACTIVITY);
                                    currentProductCode=scanProd.geProductCode();
                                    return;
                                }
                                if (scanProd.getNumber() < scanProd.getNumberDoc()) {
                                    scanProd.setNumber(scanProd.getNumber() + 1);
                                }
                            }
                        }
                        for(AdmissionProduct admProd:sellingProducts){
                            if(admProd.getProductCode().equals(productCodeFromCurrentScan)){
                                admProd.setBalanceValue(admProd.getBalanceValue()+1);
                            }
                        }
                        handlerScan.sendEmptyMessage(REDRAW_RECYCLER_VIEW);
                        barcodeFromCurrentScan = "";
                        scanner.scan();
                    }
                }
            }
        });
        threadScan.start();
    }

    private Admission getSell(InputStream in) throws ParseException, XmlPullParserException, IOException {
        Admission sell = null;
        AdmissionXmlReader admissionXmlReader = new AdmissionXmlReader();
        sell = admissionXmlReader.parseAdmission(in);
        return sell;
    }

    private void outOfOpenSellingsActivity(){
        Intent intent=new Intent(this,SalesActivity.class);
        intent.putExtra("userCode",userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void runSellingsScanActivity(){
        Intent intent=new Intent(this,SellingsScanActivity.class);
        intent.putExtra("userCode",userCode);
        intent.putExtra("filePath",filePath);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        intent.putExtra("currentProductCode",currentProductCode);
        startActivity(intent);
        finish();
    }

    private void save(){
//        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
//        String fileName="admission15.xml";
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
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            outOfOpenSellingsActivity();
            return true;
        }
        else{
            return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.buttonOpenSellingsBack:
                outOfOpenSellingsActivity();
                break;
            case R.id.buttonSellingsScan:
                runSellingsScanActivity();
                break;
            case R.id.buttonSellingsSave:
                save();
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
