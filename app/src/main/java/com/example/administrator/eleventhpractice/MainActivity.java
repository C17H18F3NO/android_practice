package com.example.administrator.eleventhpractice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    ImageView imageView;
    CheckBox checkBox;
    Button buttonLogin;
    String usernameDataFile = "usernameData";
    String passwordDataFile = "passwordData";
//    SharedPreferences pref;
//    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        pref = PreferenceManager.getDefaultSharedPreferences(this);

        editTextUsername = findViewById(R.id.edit_username);
        editTextPassword = findViewById(R.id.edit_password);
        imageView = findViewById(R.id.image);
        checkBox = findViewById(R.id.remember);
        buttonLogin = findViewById(R.id.login);

//        boolean isRemember = pref.getBoolean("remember_usernameAndPassword", false);
//        if(isRemember){
//            String username = pref.getString("username", "");
//            String password = pref.getString("password", "");
//            editTextUsername.setText(username);
//            editTextPassword.setText(password);
//            checkBox.setChecked(true);
//        }

        String usernameData = load(usernameDataFile);
        String passwordData = load(passwordDataFile);
        if(!TextUtils.isEmpty(usernameData) && !TextUtils.isEmpty(passwordData)){
            editTextUsername.setText(usernameData);
            editTextPassword.setText(passwordData);
            editTextUsername.setSelection(usernameData.length());
            editTextPassword.setSelection(passwordData.length());
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if(username.equals("") | password.equals("")){
                    Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
                }
                else if(!username.equals("LiuYinfeng") | !password.equals("123456")){
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                }
                else if(username.equals("LiuYinfeng") && password.equals("123456")){
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
//                    editor = pref.edit();
//                    if(checkBox.isChecked()){
//                        editor.putBoolean("remember_usernameAndPassword", true);
//                        editor.putString("username", username);
//                        editor.putString("password", password);
//                    }else{
//                        editor.clear();
//                    }
//                    editor.apply();
//                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else{
//                    Toast.makeText(MainActivity.this, "用户名或密码得不到", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(checkBox.isChecked()) {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            save(username, usernameDataFile);
            save(password, passwordDataFile);
        }
    }

    public void save(String Text, String FileName){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
//            out = openFileOutput(FileName, Context.MODE_APPEND);
            out = openFileOutput(FileName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(Text);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public String load(String fileName){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
