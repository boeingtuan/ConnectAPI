package com.boeingtuan.connectapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private static final String url_login = "http://54.148.141.223/task_manager/v1/login";

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    EditText inputEmail;
    EditText inputPass;
    Button btnLogin;
    TextView btnReg;

    JSONObject jsonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPass = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login);
        btnReg = (TextView) findViewById(R.id.register);

        // register new account
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });

        // handling the login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputEmail.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Vui l?ng đi?n email", Toast.LENGTH_SHORT).show();
                }
                else if (inputPass.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Vui l?ng đi?n m?t kh?u", Toast.LENGTH_SHORT).show();
                }
                else {
                    new Login().execute();
                }
            }
        });
    }

    class Login extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Đang đăng nhập. Vui lòng chờ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * checking authorization
         */
        @Override
        protected String doInBackground(String... args) {
            String email = inputEmail.getText().toString();
            String pass = inputPass.getText().toString();

            // Building parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", pass));

            // Getting JSON object - Post method
            jsonLogin = jsonParser.makeHttpRequest(url_login, "POST", params);

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();

            if (jsonLogin != null && !jsonLogin.optBoolean("error")) {
                Intent i = new Intent(getApplicationContext(), Task.class);
                i.putExtra("jsonLogin", jsonLogin.toString());
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Sai ten dang nhap", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
