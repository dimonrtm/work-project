package com.example.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.http_requests.HttpGetUsersRequests;
import com.example.myapplication.model.Admission;
import com.example.myapplication.model.AdmissionMarkingProduct;
import com.example.myapplication.model.AdmissionProduct;
import com.example.myapplication.model.Balance;
import com.example.myapplication.model.Barcode;
import com.example.myapplication.model.Barcode2D;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.User;
import com.example.myapplication.model.Warhouse;
import com.example.myapplication.xml.readers.AdmissionXmlReader;
import com.example.myapplication.xml.readers_from_strings.BalancesXmlReaderFromString;
import com.example.myapplication.xml.readers_from_strings.Barcodes2DXmlReaderFromString;
import com.example.myapplication.xml.readers_from_strings.BarcodesXmlReaderFromString;
import com.example.myapplication.xml.readers_from_strings.ProductsXmlReaderFromString;
import com.example.myapplication.xml.readers_from_strings.UsersXmlReaderFromString;
import com.example.myapplication.xml.readers_from_strings.WarehousesXmlReaderFromString;
import com.example.myapplication.xml.writers_to_string.BalancesWriterToString;
import com.example.myapplication.xml.writers_to_string.Barcodes2DWriterToString;
import com.example.myapplication.xml.writers_to_string.BarcodesWriterToString;
import com.example.myapplication.xml.writers_to_string.ProductsWriterToString;
import com.example.myapplication.xml.writers_to_string.UsersWriterToString;
import com.example.myapplication.xml.writers_to_string.WarehousesWriterToString;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

public class SqlActivity extends AppCompatActivity implements View.OnClickListener {
    private String userCode;
    private String userWarehouseCode;
    //    private Map<String, User> users;
//    private Map<String,String> barcodes;
//    private Map<String, Product> products;
//    private Map<String, Warhouse> warehouses;
//    private MultiKeyMap<String,Double> balance;
    private Admission admission;
    private String loginNotXML;
    private String codeNotXML;
    private String passwordNotXML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        userCode = getIntent().getStringExtra("userCode");
        userWarehouseCode = getIntent().getStringExtra("userWarehouseCode");
        Button buttonGetUsers = findViewById(R.id.buttonGetUsers);
        Button buttonGetProducts = findViewById(R.id.buttonGetProducts);
        Button buttonGetWarehouses = findViewById(R.id.buttonGetWarehouses);
        Button buttonGetBarcodes = findViewById(R.id.buttonGetBarcodes);
        Button buttonGet2DBarcodes = findViewById(R.id.buttonGet2DBarcodes);
        Button buttonGetBalances = findViewById(R.id.buttonGetBalances);
        Button buttonDBack = findViewById(R.id.buttonDBack);
        Button buttonGetAdmission = findViewById(R.id.buttonGetAdmisson);
        Button buttonGetAll = findViewById(R.id.buttonGetAll);
        Button buttonGetSell = findViewById(R.id.buttonGetSell);
        Button buttonSendAllDocuments = findViewById(R.id.buttonSendAllDocuments);
        buttonGetUsers.setOnClickListener(this);
        buttonGetProducts.setOnClickListener(this);
        buttonGetWarehouses.setOnClickListener(this);
        buttonGetBarcodes.setOnClickListener(this);
        buttonGet2DBarcodes.setOnClickListener(this);
        buttonGetBalances.setOnClickListener(this);
        buttonDBack.setOnClickListener(this);
        buttonGetAdmission.setOnClickListener(this);
        buttonGetAll.setOnClickListener(this);
        buttonGetSell.setOnClickListener(this);
        buttonSendAllDocuments.setOnClickListener(this);
    }

    private void getUsers() {
        Resources res = getResources();
        loginNotXML = res.getString(R.string.login_not_xml);
        codeNotXML = res.getString(R.string.code_not_xml);
        passwordNotXML = res.getString(R.string.password_not_xml);
//        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
//        String fileName="admission000000000000000015.xml";
//        OutputStream outputStream= null;
//        try {
//            Toast.makeText(getApplicationContext(),"I am here", Toast.LENGTH_LONG).show();
//            String pathFile=Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Admissions/admission00000000001.xml";
//            InputStream in=new FileInputStream(pathFile);
//            outputStream = new FileOutputStream(folder+fileName);
//            XmlSerializer serializer= Xml.newSerializer();
//            serializer.setOutput(outputStream,"UTF-8");
//            serializer.startDocument(null, Boolean.valueOf(true));
//            AdmissionXmlWriter admissionXmlWriter=new AdmissionXmlWriter(serializer);
////            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
//            Admission admission=admissionXmlWriter.parseAdmission(in);
//            serializer.endDocument();
//            serializer.flush();
//            outputStream.close();
//            Toast.makeText(getApplicationContext(),"You are here", Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
//        } catch (ParseException e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
//        } catch (XmlPullParserException e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
//        }catch(Exception e){
//            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//        folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
//        fileName="barcode000000000000000015.xml";
//        outputStream= null;
//        try {
//            String pathFile=Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Admissions/admission00000000001.xml";
//            InputStream in=new FileInputStream(pathFile);
//            outputStream = new FileOutputStream(folder+fileName);
//            XmlSerializer serializer= Xml.newSerializer();
//            serializer.setOutput(outputStream,"UTF-8");
//            serializer.startDocument(null, Boolean.valueOf(true));
//        } catch (FileNotFoundException e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//        String pathFile=Environment.getExternalStorageDirectory() + File.separator + "TestFolder/users.xml";
//        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
//        String fileName="users15.xml";
//        try {
////            Toast.makeText(getApplicationContext(),"I am here", Toast.LENGTH_LONG).show();
//            InputStream in=new FileInputStream(pathFile);
//            OutputStream outputStream = new FileOutputStream(folder+fileName);
//            UserXmlWriter userXmlWriter=new UserXmlWriter(outputStream);
//            users=userXmlWriter.parseUsers(in,loginNotXML,codeNotXML,passwordNotXML);
//            userXmlWriter.usersWrite(users);
//            outputStream.close();
////            Toast.makeText(getApplicationContext(),"You are here", Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
//        } catch (XmlPullParserException e) {
//            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//        pathFile=Environment.getExternalStorageDirectory() + File.separator + "TestFolder/barcode.xml";
//        folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
//        fileName="barcode15.xml";
//        try {
////            Toast.makeText(getApplicationContext(),"I am here", Toast.LENGTH_LONG).show();
//            InputStream in=new FileInputStream(pathFile);
//            OutputStream outputStream = new FileOutputStream(folder+fileName);
//            BarcodeXmlWriter barcodeXmlWriter=new BarcodeXmlWriter(outputStream);
//            barcodes=barcodeXmlWriter.parseMapBarcodes(in);
//            barcodeXmlWriter.writeBarcodes(barcodes);
////            Toast.makeText(getApplicationContext(),"You are here", Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        pathFile=Environment.getExternalStorageDirectory() + File.separator + "TestFolder/barcode2d.xml";
//        folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
//        fileName="barcode2d15.xml";
//        try {
////            Toast.makeText(getApplicationContext(),"I am here", Toast.LENGTH_LONG).show();
//            InputStream in=new FileInputStream(pathFile);
//            OutputStream outputStream = new FileOutputStream(folder+fileName);
//            Barcode2DXmlWriter barcode2DXmlWriter=new Barcode2DXmlWriter(outputStream);
//            barcodes=barcode2DXmlWriter.parseMap2DBarcodes(in);
//            barcode2DXmlWriter.writeBarcodes2D(barcodes);
////            Toast.makeText(getApplicationContext(),"You are here", Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        pathFile=Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Product.xml";
//        folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
//        fileName="product15.xml";
//        try {
////            Toast.makeText(getApplicationContext(),"I am here", Toast.LENGTH_LONG).show();
//            InputStream in=new FileInputStream(pathFile);
//            OutputStream outputStream = new FileOutputStream(folder+fileName);
//            ProductXmlWriter productXmlWriter=new ProductXmlWriter(outputStream);
//            products=productXmlWriter.parseMapProducts(in);
//            productXmlWriter.writeProducts(products);
////            Toast.makeText(getApplicationContext(),"You are here", Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        pathFile=Environment.getExternalStorageDirectory() + File.separator + "TestFolder/warehouse.xml";
//        folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
//        fileName="warehouse15.xml";
//        try {
////            Toast.makeText(getApplicationContext(),"I am here", Toast.LENGTH_LONG).show();
//            InputStream in=new FileInputStream(pathFile);
//            OutputStream outputStream = new FileOutputStream(folder+fileName);
//            WarehouseXmlWriter warehouseXmlWriter=new WarehouseXmlWriter(outputStream);
//            warehouses=warehouseXmlWriter.parseMapWarhouses(in);
//            warehouseXmlWriter.writeWarehouse(warehouses);
////            Toast.makeText(getApplicationContext(),"You are here", Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        pathFile=Environment.getExternalStorageDirectory() + File.separator + "TestFolder/warehouse.xml";
//        folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
//        fileName="warehouse15.xml";
//        try {
////            Toast.makeText(getApplicationContext(),"I am here", Toast.LENGTH_LONG).show();
//            InputStream in=new FileInputStream(pathFile);
//            OutputStream outputStream = new FileOutputStream(folder+fileName);
//            WarehouseXmlWriter warehouseXmlWriter=new WarehouseXmlWriter(outputStream);
//            warehouses=warehouseXmlWriter.parseMapWarhouses(in);
//            warehouseXmlWriter.writeWarehouse(warehouses);
////            Toast.makeText(getApplicationContext(),"You are here", Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        pathFile=Environment.getExternalStorageDirectory() + File.separator + "TestFolder/balance.xml";
//        folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
//        fileName="balance15.xml";
//        try {
//            Toast.makeText(getApplicationContext(),"I am here", Toast.LENGTH_LONG).show();
//            InputStream in=new FileInputStream(pathFile);
//            OutputStream outputStream = new FileOutputStream(folder+fileName);
//            BalanceXmlWriter balanceXmlWriter=new BalanceXmlWriter(outputStream);
//            balance=balanceXmlWriter.parseBalance(in);
//            MapIterator<MultiKey<? extends String>,Double> it =balance.mapIterator();
//            balanceXmlWriter.writeBalance(balance);
//            Toast.makeText(getApplicationContext(),"You are here", Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        pathFile=Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Admissions/admission00000000001.xml";
//        folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
//        fileName="admission15.xml";
//        try {
//            Toast.makeText(getApplicationContext(),"I am here", Toast.LENGTH_LONG).show();
//            InputStream in=new FileInputStream(pathFile);
//            OutputStream outputStream = new FileOutputStream(folder+fileName);
//            AdmissionXmlWriter admissionXmlWriter=new AdmissionXmlWriter(outputStream);
//            admission=admissionXmlWriter.parseAdmission(in);
//            admissionXmlWriter.writeAdmission(admission);
//            Toast.makeText(getApplicationContext(),"You are here", Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        String strURL = "http://192.168.1.86:8080/users";
        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/users.xml";
        getAndWriteUsers(strURL, folder);
        Toast.makeText(getApplicationContext(), "Файл записан по адресу " + folder, Toast.LENGTH_LONG).show();
    }

    private void getProducts() {
        String strURL = "http://192.168.1.86:8080/products";
        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Product.xml";
        getAndWriteProducts(strURL, folder);
        Toast.makeText(getApplicationContext(), "Файл записана по адресу" + folder, Toast.LENGTH_LONG).show();
    }

    private void getWarehouses() {
        String strURL = "http://192.168.1.86:8080/warehouses";
        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/warehouse.xml";
        getAndWriteWarehouses(strURL, folder);
        Toast.makeText(getApplicationContext(), "Файл записана по адресу" + folder, Toast.LENGTH_LONG).show();
    }

    private void getBarcodes() {
        String strURL = "http://192.168.1.86:8080/barcodes";
        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/barcode.xml";
        getAndWriteBarcodes(strURL, folder);
        Toast.makeText(getApplicationContext(), "Файл записана по адресу" + folder, Toast.LENGTH_LONG).show();
    }

    private void get2DBarcodes() {
        String strURL = "http://192.168.1.86:8080/barcodes2D";
        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/barcode2D.xml";
        getAndWriteBarcodes2D(strURL, folder);
        Toast.makeText(getApplicationContext(), "Файл записана по адресу" + folder, Toast.LENGTH_LONG).show();
    }

    private void getBalances() {
        String strURL = "http://192.168.1.86:8080/balances";
        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/balance.xml";
        getAndWriteBalances(strURL, folder);
        Toast.makeText(getApplicationContext(), "Файл записана по адресу" + folder, Toast.LENGTH_LONG).show();
    }

    private void back() {
        Intent intent = new Intent(this, ControlActivity.class);
        intent.putExtra("user_code", userCode);
        intent.putExtra("userWarehouseCode", userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void getAdmission() {
        Intent intent = new Intent(this, GetAdmissionActivity.class);
        intent.putExtra("user_code", userCode);
        intent.putExtra("userWarehouseCode", userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void getSell() {
        Intent intent = new Intent(this, GetSellActivity.class);
        intent.putExtra("user_code", userCode);
        intent.putExtra("userWarehouseCode", userWarehouseCode);
        startActivity(intent);
        finish();
    }

    private void sendAllDocuments() {
        String pathAdmissions = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/Admissions/";
        String pathSellings = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/sellings/";
        try {
            sendAllDocumentsFromDir(pathAdmissions, "admission");
            sendAllDocumentsFromDir(pathSellings,"sell");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void sendAllDocumentsFromDir(String path, String typeTransaction) throws IOException, ParseException, XmlPullParserException {
        File dir = new File(path);
        File[] arrFiles = dir.listFiles();
        List<File> allFiles = Arrays.asList(arrFiles);
        for (File file : allFiles) {
            FileInputStream in = new FileInputStream(file);
            AdmissionXmlReader admissionXmlReader = new AdmissionXmlReader();
            Admission admission = admissionXmlReader.parseAdmission(in);
            final String[] responsePut = new String[1];
            List<AdmissionProduct> admissionProducts = admission.getAdmissionProducts();
            for (AdmissionProduct admProd : admissionProducts) {
                String url = "http://192.168.1.86:8080/" + typeTransaction + "Products/" + admission.getId() + "/" + admProd.getProductCode();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("balance_value", admProd.getBalanceValue());
                params.put("balance_value_doc", admProd.getBalanceValueDocs());
                params.put("marking", admProd.getMarking());
                client.put(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        responsePut[0] = responseBody.toString();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        responsePut[0] = responseBody.toString();
                    }
                });
            }
            List<AdmissionMarkingProduct> admissionMarkingProducts = admission.getAdmissionMarkingProducts();
            for (AdmissionMarkingProduct markProd : admissionMarkingProducts) {
                String url = "http://192.168.1.86:8080/" + typeTransaction + "MarkingProducts/" + markProd.getAdmissionMarkingProductId() + "/" + admission.getId() + "/" + markProd.getProductCode();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("barcode_labeling", markProd.getBartcodeLabeling());
                params.put("marking_completed", markProd.getMarkingCompleted());
                client.put(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        responsePut[0] = responseBody.toString();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        responsePut[0] = responseBody.toString();
                    }
                });
            }
            Toast.makeText(getApplicationContext(),"Все локальные файлы сохранены в базу данных", Toast.LENGTH_LONG).show();
        }
    }

    private String getObjects(String url) {
        String result = "";
        HttpGetUsersRequests getUsersRequest = new HttpGetUsersRequests();
        try {
            result = getUsersRequest.execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getAndWriteUsers(String url, String folder) {
        String result = "";
        List<User> users = null;
        result = getObjects(url);
        try {
            UsersXmlReaderFromString usersXmlReaderFromString = new UsersXmlReaderFromString(result);
            users = usersXmlReaderFromString.readUsers();
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
        try {
            UsersWriterToString usersWriterToString = new UsersWriterToString();
            result = usersWriterToString.writeUsers(users);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        writeObjects(folder, result);
    }

    private void getAndWriteProducts(String url, String folder) {
        String result = "";
        List<Product> products = null;
        result = getObjects(url);
        try {
            ProductsXmlReaderFromString productsXmlReaderFromString = new ProductsXmlReaderFromString(result);
            products = productsXmlReaderFromString.readProducts();
        } catch (XmlPullParserException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            ProductsWriterToString productsWriterToString = new ProductsWriterToString();
            result = productsWriterToString.writeProducts(products);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        writeObjects(folder, result);
    }

    private void getAndWriteWarehouses(String url, String folder) {
        String result = "";
        List<Warhouse> warehouses = null;
        result = getObjects(url);
        try {
            WarehousesXmlReaderFromString warehousesXmlReaderFromString = new WarehousesXmlReaderFromString(result);
            warehouses = warehousesXmlReaderFromString.readWarehouses();
        } catch (XmlPullParserException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            WarehousesWriterToString warehousesWriterToString = new WarehousesWriterToString();
            result = warehousesWriterToString.writeWarehouses(warehouses);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        writeObjects(folder, result);
    }

    private void getAndWriteBarcodes(String url, String folder) {
        String result = "";
        List<Barcode> barcodes = null;
        result = getObjects(url);
        try {
            BarcodesXmlReaderFromString barcodesXmlReaderFromString = new BarcodesXmlReaderFromString(result);
            barcodes = barcodesXmlReaderFromString.readBarcodes();
        } catch (XmlPullParserException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            BarcodesWriterToString barcodesWriterToString = new BarcodesWriterToString();
            result = barcodesWriterToString.writeBarcodes(barcodes);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        writeObjects(folder, result);
    }

    private void getAndWriteBarcodes2D(String url, String folder) {
        String result = "";
        List<Barcode2D> barcodes2D = null;
        result = getObjects(url);
        try {
            Barcodes2DXmlReaderFromString barcodes2DXmlReaderFromString = new Barcodes2DXmlReaderFromString(result);
            barcodes2D = barcodes2DXmlReaderFromString.readBarcodes2D();
        } catch (XmlPullParserException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            Barcodes2DWriterToString barcodes2DWriterToString = new Barcodes2DWriterToString();
            result = barcodes2DWriterToString.writeBarcodes2D(barcodes2D);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        writeObjects(folder, result);
    }

    private void getAndWriteBalances(String url, String folder) {
        String result = "";
        List<Balance> balances = null;
        result = getObjects(url);
        try {
            BalancesXmlReaderFromString balancesXmlReaderFromString = new BalancesXmlReaderFromString(result);
            balances = balancesXmlReaderFromString.readBalances();
        } catch (XmlPullParserException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            BalancesWriterToString balancesWriterToString = new BalancesWriterToString();
            result = balancesWriterToString.writeBalances(balances);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        writeObjects(folder, result);
    }

    private void getAll() {
        getUsers();
        getProducts();
        getWarehouses();
        getBarcodes();
        get2DBarcodes();
        getBalances();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGetUsers:
                getUsers();
                break;
            case R.id.buttonGetProducts:
                getProducts();
                break;
            case R.id.buttonGetWarehouses:
                getWarehouses();
                break;
            case R.id.buttonGetBarcodes:
                getBarcodes();
                break;
            case R.id.buttonGet2DBarcodes:
                get2DBarcodes();
                break;
            case R.id.buttonGetBalances:
                getBalances();
                break;
            case R.id.buttonDBack:
                back();
                break;
            case R.id.buttonGetAdmisson:
                getAdmission();
                break;
            case R.id.buttonGetAll:
                getAll();
                break;
            case R.id.buttonGetSell:
                getSell();
                break;
            case R.id.buttonSendAllDocuments:
                sendAllDocuments();
                break;
        }
    }

}
