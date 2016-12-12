package com.lihao.usermanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

public class QueryActivity extends AppCompatActivity {

    private TextView resultText;
    private Button submit;
    private EditText inputNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        initView();
    }

    private void initView() {
        resultText = (TextView) findViewById(R.id.tv_result);
        submit = (Button) findViewById(R.id.bt_submit);
        inputNum = (EditText) findViewById(R.id.et_input);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryNum = inputNum.getText().toString().trim();
                if(queryNum.length()>0){
                    submitNum(queryNum);
                }
            }
        });
    }

    private void submitNum(final String num){
        StringRequest newReq = new StringRequest(Request.Method.POST, "http://192.168.1.109:8080/start/myservlet", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Lihao", response.toString());
                try{
                    JSONObject result = new JSONObject(response);
                    resultText.setText(
                            "查询结果:\n" +
                            "工号:" + result.getString("id") + "\n" +
                            "姓名:" + result.getString("name") + "\n" +
                            "性别:" + result.getString("sex") + "\n" +
                            "年龄:" + result.getString("age") + "\n");
                }catch (Exception e){
                    resultText.setText(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new LinkedHashMap<>();
                params.put("username",num);
                Log.d("Lihao", params.toString());
                return params;
            }
        };
        MyApplication.getQueue().add(newReq);
    }
}
