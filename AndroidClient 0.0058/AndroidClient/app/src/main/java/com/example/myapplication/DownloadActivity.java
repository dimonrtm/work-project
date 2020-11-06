package com.example.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class DownloadActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final int WRITE_REQUEST_CODE = 300;
    private static final String TAG = DownloadActivity.class.getSimpleName();
    private String url;
    private EditText editTextFileName;
    private String userCode;
    private String userWarehouseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra("url");
        userCode = getIntent().getStringExtra("userCode");
        userWarehouseCode=getIntent().getStringExtra("userWarehouseCode");
        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_download);
        editTextFileName = (EditText) findViewById(R.id.editTextFileName);
        Button downloadButton = findViewById(R.id.buttonDownload);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckForSDCard.isSDCardPresent()) {
                    String fileName = editTextFileName.getText().toString();
                    url = url + "?fileName=" + fileName;
                    if (EasyPermissions.hasPermissions(DownloadActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        new DownloadFile().execute(url);
                        dBack();
                    } else {
                        EasyPermissions.requestPermissions(DownloadActivity.this,
                                getString(R.string.write_file),
                                WRITE_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "SD Card not found", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button buttonDBack = findViewById(R.id.buttonDBack);
        buttonDBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dBack();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,
                DownloadActivity.this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        url = url + "?filename=" + editTextFileName.getText().toString();
        new DownloadFile().execute(url);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }

    private class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private String fileName;
        private String folder;
        private String error;
        private boolean isDownloaded;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progressDialog = new ProgressDialog(DownloadActivity.this);
            this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();
                int lengthOfFile = connection.getContentLength();
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
//                String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                fileName = f_url[0].substring(f_url[0].lastIndexOf('=') + 1);
//                fileName = timestamp + "_" + fileName;
                folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
                File directory = new File(folder);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                OutputStream output = new FileOutputStream(folder + fileName);
                byte[] data = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    Log.d(TAG, "Progress: " + (int) ((total * 100) / lengthOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
                return "Downloaded at: " + folder + fileName;
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                error = e.toString();
            }
            return "Something went wrong: " + error;
        }

        protected void onProgressUpdate(String... progress) {
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String message) {
            this.progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    private void dBack() {
        Intent intent = new Intent(this, EnterUrlActivity.class);
        intent.putExtra("userCode", userCode);
        intent.putExtra("userWarehouseCode",userWarehouseCode);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            dBack();
            return true;
        }
        else{
            return super.onKeyUp(keyCode, event);
        }
    }
}
