package com.lihao.usermanager.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lihao.usermanager.R;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HTTP;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lihao on 2016/12/12.
 */

public class QueryAllActivity extends AppCompatActivity {

    public TextView resultText;

    public Button query;

    public EditText inputNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_all);
        inputNum = (EditText) findViewById(R.id.et_input);
        query = (Button) findViewById(R.id.bt_submit);
        resultText = (TextView) findViewById(R.id.tv_result);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInfoByID();
            }
        });
    }

    public void onCliceButton(){
        String requestNum = inputNum.getText().toString().trim();
        String baseUrl = "http://172.30.45.5:8080/start/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WebService service = retrofit.create(WebService.class);
        Call<UserBean> call = service.getUser(Integer.parseInt(requestNum));
        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                resultText.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                resultText.setText(t.toString());
            }
        });
        Toast.makeText(QueryAllActivity.this,"请求完成!",Toast.LENGTH_SHORT).show();
    }

    public void queryByRxjava(){
        String requestNum = inputNum.getText().toString().trim();
        String baseUrl = "http://192.168.1.107:8080/start/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        WebService service = retrofit.create(WebService.class);
        service.queryUserById(Integer.parseInt(requestNum))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserBean>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(QueryAllActivity.this,"请求完成!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        resultText.setText(e.toString());
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        resultText.setText(userBean.toString());
                    }
                });
    }

    public void getUserInfoByID(){
        int userId = Integer.parseInt(inputNum.getText().toString().trim());
        HttpRequest.getInstance().getUserInfoById(userId, new Subscriber<UserBean>() {
            @Override
            public void onCompleted() {
                Toast.makeText(QueryAllActivity.this,"请求完成!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                resultText.setText(e.toString());
            }

            @Override
            public void onNext(UserBean userBean) {
                resultText.setText(userBean.toString());
            }
        });
    }
}
