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

/**
 * Created by Tuan on 04/08/2015.
 */
public class Register extends Activity {

    private ProgressDialog pdialog;

    EditText inputName;
    EditText inputEmail;
    EditText inputPass;
    EditText inputPassConfirm;
    Button btnReg;

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
            pdialog.setMessage("Ðang t?o tài kho?n. Vui l?ng ch? ...");
            pdialog.setIndeterminate(false);
            pdialog.setCancelable(false);
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pdialog.dismiss();
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
