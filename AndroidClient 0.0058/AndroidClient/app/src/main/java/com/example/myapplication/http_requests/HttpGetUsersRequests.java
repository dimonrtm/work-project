package com.example.myapplication.http_requests;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpGetUsersRequests extends AsyncTask<String,Void,String> {

    public static final String REQUEST_METHOD="GET";
    public static final int READ_TIMEOUT=15000;
    public static final int CONNECTION_TIMEOUT=15000;

    @Override
    protected String doInBackground(String... params) {
        String stringURL=params[0];
        String result="";
        String inputLine;
        try{
            URL usersURL=new URL(stringURL);
            HttpURLConnection connection=(HttpURLConnection)usersURL.openConnection();
            connection.setRequestMethod(REQUEST_METHOD);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            connection.connect();
            InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder=new StringBuilder();
            while((inputLine=bufferedReader.readLine())!=null){
                stringBuilder.append(inputLine);
            }
            bufferedReader.close();
            inputStreamReader.close();
            result=stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}
