package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.dialogs.OutDialogFragment;
import com.example.myapplication.model.User;
import com.example.myapplication.xml.readers.UserXmlReader;
import com.smartdevicesdk.device.DeviceInfo;
import com.smartdevicesdk.device.DeviceManage;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OutDialogFragment.OutDialogListener {
    private String userLogin;
    private String userPassword;
    private EditText editName;
    private EditText editPassword;
    private Button buttonLogin;
    private String notEnterLogin;
    private String notEnterPassword;
    private String loginNotFound;
    private String wrongPassword;
    private String loginNotXML;
    private String codeNotXML;
    private String passwordNotXML;
    List<User> users;
    User user;
    private static final String ns = null;
    public static DeviceInfo devInfo = DeviceManage.getDevInfo("PDA3501");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        userLogin = editName.getText().toString();
        userPassword = editPassword.getText().toString();
        Resources res = getResources();
        notEnterLogin = res.getString(R.string.not_enter_login);
        notEnterPassword = res.getString(R.string.not_enter_password);
        loginNotFound = res.getString(R.string.login_not_found);
        wrongPassword = res.getString(R.string.wrong_password);
        loginNotXML = res.getString(R.string.login_not_xml);
        codeNotXML = res.getString(R.string.code_not_xml);
        passwordNotXML = res.getString(R.string.password_not_xml);
    }

    @Override
    public void onClick(View view) {
        users = new ArrayList<User>();
        userLogin = editName.getText().toString();
        userPassword = editPassword.getText().toString();
        if (userLogin == null || userLogin.equals("")) {
            Toast.makeText(getApplicationContext(), notEnterLogin, Toast.LENGTH_LONG).show();
            return;
        }
        if (userPassword == null || userPassword.equals("")) {
            Toast.makeText(getApplicationContext(), notEnterPassword, Toast.LENGTH_LONG).show();
            return;
        }

        String folder = Environment.getExternalStorageDirectory() + File.separator + "TestFolder/";
        folder = folder + "users.xml";
        File usersFile = new File(folder);
        try {
            InputStream in = new FileInputStream(usersFile);
            UserXmlReader userXmlReader = new UserXmlReader();
            users = userXmlReader.parseUsers(in, loginNotXML, codeNotXML, passwordNotXML);
        } catch (FileNotFoundException fileNotFoundException) {
            Toast.makeText(getApplicationContext(), fileNotFoundException.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException ioException) {
            Toast.makeText(getApplicationContext(), ioException.getMessage(), Toast.LENGTH_LONG).show();
        } catch (XmlPullParserException xmlPullParserException) {
            Toast.makeText(getApplicationContext(), xmlPullParserException.getMessage(), Toast.LENGTH_LONG).show();
        }
        users.add(new User("user", "5555","00002    ", "1111"));
        user = searchUser(users, userLogin);
        if (user == null) {
            Toast.makeText(getApplicationContext(), loginNotFound, Toast.LENGTH_LONG).show();
            return;
        }
        if (!user.getUserPassword().equals(userPassword)) {
            Toast.makeText(getApplicationContext(), wrongPassword, Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, ControlActivity.class);
        intent.putExtra("userCode", user.getUserCode());
        intent.putExtra("userWarehouseCode",user.getWarehouseCode());
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            DialogFragment newFragment = new OutDialogFragment();
            newFragment.show(getSupportFragmentManager(),"outDial");
            return true;
        }
        else{
            return super.onKeyUp(keyCode, event);
        }
    }

    private User searchUser(List<User> users, String login) {
        User user = null;
        for (User u : users) {
            if (u.getUserLogin().equals(login)) {
                user = u;
            }
        }
        return user;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
