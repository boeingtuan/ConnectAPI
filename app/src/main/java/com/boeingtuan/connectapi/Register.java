package com.boeingtuan.connectapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tuan on 04/08/2015.
 */
public class Register extends Activity {

    private static final String url_register = "http://54.148.141.223/task_manager/v1/register";

    private ProgressDialog pdialog;

    EditText inputName;
    EditText inputEmail;
    EditText inputPass;
    EditText inputPassConfirm;
    Button btnReg;

    JSONParser jsonParser =  new JSONParser();
    JSONObject jsonReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        inputName = (EditText) findViewById(R.id.username);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPass = (EditText) findViewById(R.id.password);
        inputPassConfirm = (EditText) findViewById(R.id.confirm_password);

        btnReg = (Button) findViewById(R.id.register);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputName.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "Vui long nhap ten tai khoan", Toast.LENGTH_SHORT).show();
                }
                else if (inputEmail.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "Vui long nhap email", Toast.LENGTH_SHORT).show();
                }
                else if (inputPass.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "Vui long nhap mat khau", Toast.LENGTH_SHORT).show();
                }
                else if (inputPassConfirm.getText().toString() == "") {
                    Toast.makeText(getApplicationContext(), "Vui long xac nhan mat khau", Toast.LENGTH_SHORT).show();
                }
                else if (!inputPass.getText().toString().equals(inputPassConfirm.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Xac nhan mat khau khong chinh xac", Toast.LENGTH_SHORT).show();
                }
                else {
                    new Reg().execute();
                }
            }
        });
    }

    class Reg extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(Register.this);
            pdialog.setMessage("Ðang tao tài khoan. Vui long cho ...");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(false);
            pdialog.show();
        }

        /**
         * checking authorization
         */

        @Override
        protected String doInBackground(String... args) {
            String email = inputEmail.getText().toString();
            String pass =  inputPass.getText().toString();
            String usrname = inputName.getText().toString();

            // Building parameters
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("name", usrname));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("password", pass));

            // Getting JSON object - Post method
            jsonReg = jsonParser.makeHttpRequest(url_register, "POST", params);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pdialog.dismiss();

            if (jsonReg != null && !jsonReg.optBoolean("error")) {
                Toast.makeText(getApplicationContext(), "Tao tai khoan thanh cong", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Tao tai khoan ko thanh cong", Toast.LENGTH_SHORT).show();
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
