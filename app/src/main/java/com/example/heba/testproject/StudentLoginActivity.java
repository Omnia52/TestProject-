package com.example.heba.testproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentLoginActivity extends AppCompatActivity {
    private String mStudentAcc,mStudentPass;
    private EditText mStudentAccE,mStudentPassE;
    private Button mLoginBtn;

    /*-------------------------------*/

    TextView textView;
    EditText ID,Email,Password;
    Button login_button;
    String id,email,password;
    String login_url = "http://192.168.1.4/Project/StudentLogin.php";
    AlertDialog.Builder builder;

    /*-----------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        mStudentAccE=(EditText) findViewById(R.id.studentAcc);
        mStudentPassE=(EditText) findViewById(R.id.studentPass);
        mLoginBtn = (Button) findViewById(R.id.studentLogin);
        mStudentAcc=mStudentPass="" ;

        mStudentAccE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStudentAcc=mStudentAccE.getText().toString();
                mStudentPass=mStudentPassE.getText().toString();
                if(mStudentPass.length()>=8&&mStudentAcc.trim().length()!=0)
                    mLoginBtn.setEnabled(true);
                else
                    mLoginBtn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStudentAcc=mStudentAccE.getText().toString();
                mStudentPass=mStudentPassE.getText().toString();
                if(mStudentPass.length()>=8&&mStudentAcc.trim().length()!=0)
                    mLoginBtn.setEnabled(true);
                else
                    mLoginBtn.setEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mStudentPassE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStudentAcc=mStudentAccE.getText().toString();
                mStudentPass=mStudentPassE.getText().toString();
                if(mStudentPass.length()>=8&&mStudentAcc.trim().length()!=0)
                    mLoginBtn.setEnabled(true);
                else
                    mLoginBtn.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mStudentAcc=mStudentAccE.getText().toString();
                mStudentPass=mStudentPassE.getText().toString();
                if(mStudentPass.length()>=8&&mStudentAcc.trim().length()!=0)
                    mLoginBtn.setEnabled(true);
                else
                    mLoginBtn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        /*----------------------------------------------------*/
        builder = new AlertDialog.Builder(StudentLoginActivity.this);
        login_button = (Button)findViewById(R.id.studentLogin);
        ID = (EditText)findViewById(R.id.studentAcc);
        Email = (EditText)findViewById(R.id.studentEmail);
        Password = (EditText)findViewById(R.id.studentPass);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = ID.getText().toString();
                password = Password.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        if(code.equals("Login failed")){
                                            builder.setTitle("Login Error...");
                                            displayAlert(jsonObject.getString("message"));
                                        }
                                        else{
                                            Intent i = new Intent(StudentLoginActivity.this, Question_page.class);
                                            startActivity(i);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(StudentLoginActivity.this,"Error",Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                           Map<String,String> params = new HashMap<String, String>();
                           params.put("ID or Email",id);
                           params.put("Password",password);

                            return params;
                        }
                    };
                    MySingleton.getmInstance(StudentLoginActivity.this).addToRequestQueue(stringRequest);



            }
        });


    }

    public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ID.setText("");
                Password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void studentLogin(View view) {

    }

    public void reg(View view) {
        startActivity(new Intent(this, RegActivity.class));
    }
}
