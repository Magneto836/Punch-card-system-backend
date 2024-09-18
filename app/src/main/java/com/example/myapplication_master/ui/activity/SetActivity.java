package com.example.myapplication_master.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.text.TextUtils;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import com.example.myapplication_master.R;
import com.example.myapplication_master.ui.adapter.RecordAdapter;
import com.google.android.material.textfield.TextInputLayout;


import java.util.HashMap;
import java.util.Map;

import api.ApiResponse;
import api.ApiService;
import api.CompanyApiResponse;
import api.CompanyPojo;
import api.RecordApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

public class SetActivity extends AppCompatActivity {

    private String TAG = "GetCompanyInformation";


    private double positionX1 = 0  ;
    private double positionY1 = 0  ;
    private String startTime1 = "" ;
    private String endTime1   = "" ;
    private TextInputEditText editTextPositionX, editTextPositionY, editTextClockIn, editTextClockOut;
    private Button changeBtn;
    private Button findBtn;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);  // 确保这个布局文件存在

        GetCompanyInf();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 启用返回按钮
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 处理返回按钮点击事件
        toolbar.setNavigationOnClickListener(v -> onBackPressed());





        // 初始化输入框
        TextInputLayout tlPositionX = findViewById(R.id.tl_positionX);
        TextInputLayout tlPositionY = findViewById(R.id.tl_positionY);
        TextInputLayout tlClockIn = findViewById(R.id.tl_clockIn);
        TextInputLayout tlClockOut = findViewById(R.id.tl_clockOut);

        editTextPositionX = (TextInputEditText) tlPositionX.getEditText();
        editTextPositionY = (TextInputEditText) tlPositionY.getEditText();
        editTextClockIn = (TextInputEditText) tlClockIn.getEditText();
        editTextClockOut = (TextInputEditText) tlClockOut.getEditText();

        // 初始化按钮
        changeBtn = findViewById(R.id.changeBtn);
        findBtn = findViewById(R.id.findBtn);


        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetCompanyInf();

                new AlertDialog.Builder(SetActivity.this)  // 当前 activity 上显示消息框
                        .setTitle("查询成功")
                        .setMessage("目前公司经纬度为("+positionX1+","+positionY1+")，上下班时间分别为"+startTime1+","+endTime1)
                        .setPositiveButton("确定", null)  // OK 按钮
                        .show();



            }
        });



        changeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 提取输入框中的值
                double positionX2 = positionX1;
                double positionY2 = positionY1;
                String clockIn2 = editTextClockIn.getText().toString();
                String clockOut2 = editTextClockOut.getText().toString();

                if(!String.valueOf(editTextPositionX.getText()).isEmpty()){
                    positionX2 = Double.parseDouble(String.valueOf(editTextPositionX.getText()));
                }
                if(!editTextPositionY.getText().toString().isEmpty()){
                    positionY2 = Double.parseDouble(editTextPositionY.getText().toString());

                }
                if(clockIn2.isEmpty()){
                    clockIn2 = startTime1;
                }
                if(clockOut2.isEmpty()){
                    clockOut2 = endTime1;
                }
                // else if 只能执行一个

                // 打印输入的内容，不能写 else ,不然假设执行上面的赋值，下面的就不会执行
                Log.d(TAG, "Position X: " + positionX2);
                Log.d(TAG, "Position Y: " + positionY2);
                Log.d(TAG, "Clock In: " + clockIn2);
                Log.d(TAG, "Clock Out: " + clockOut2);

                // 调用 change() 函数
                if(validateInput(positionX2, positionY2, clockIn2, clockOut2)){
                    changeInf(positionX2,positionY2,clockIn2,clockOut2);
                }



            }
        });


    }

    private void changeInf(double positionX3 ,double positionY3 ,String clockIn3 ,String clockOut3){
// 构建 HTTP 请求体
        Log.d(TAG,"lcoatinoyz值为"+positionY3);
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(1));
        params.put("locationx", String.valueOf(positionX3));
        params.put("locationy",String.valueOf(positionY3));
        params.put("start_time",clockIn3);
        params.put("end_time",clockOut3);

        // 发送请求
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")  // 替换为你的后端地址
                .addConverterFactory(GsonConverterFactory.create())  // 添加 Gson 转换器
                .build();

        ApiService apiService = retrofit.create(ApiService.class);


        Call<ApiResponse> call = apiService.updateCompany(1,positionX3,positionY3,clockIn3,clockOut3);

        call.enqueue(new Callback<ApiResponse>() {

            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d(TAG,"设置马上发请求");

                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getCode() == 200) {
                        Log.d(TAG, "成功设置");
                        positionX1 = positionX3;
                        positionY1 = positionY3;
                        startTime1 = clockIn3;
                        endTime1 = clockOut3;

                        new AlertDialog.Builder(SetActivity.this)  // 当前 activity 上显示消息框
                                .setTitle("设置成功")
                                .setMessage("公司位置信息和时间已成功设置！")
                                .setPositiveButton("确定", null)  // OK 按钮
                                .show();

                        // 登录成功的逻辑
                    } else {
                        Log.d(TAG, "设置失败: " + (apiResponse != null ? apiResponse.getMessage() : "未知错误"));
                    }
                } else {
                    Log.d(TAG, "设置失败，服务器错误");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // 网络或其他错误
                Log.d(TAG, "失败设置: " + t.getMessage());
            }
        });




    }









    private void GetCompanyInf(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")  // 替换为你的后端地址
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        int companyId = 1 ;
        Call<CompanyApiResponse> call = apiService.getCompanyById(companyId);


        call.enqueue(new Callback<CompanyApiResponse>() {
            @Override
            public void onResponse(Call<CompanyApiResponse> call, Response<CompanyApiResponse> response) {
                // progressDialogHelper.dismissProgressDialog();
                Log.d(TAG,"马上发请求");
                if (response.isSuccessful()) {

                    CompanyApiResponse companyApiResponse = response.body();
                    if (companyApiResponse != null && companyApiResponse.getCode() == 200) {
                        CompanyPojo companyPojo = companyApiResponse.getData().getRecords();

                       positionX1 = companyPojo.getLocationX();
                       positionY1 = companyPojo.getLocationY();
                       startTime1 = companyPojo.getStartTime();
                       endTime1 = companyPojo.getEndTime();

                        Log.d(TAG, "成功");

                        Log.d(TAG, "公司信息: " + endTime1);

                    } else {
                        Log.d(TAG, "获取信息失败: " + companyApiResponse.getMessage());
                    }
                }
                else {
                    Log.d(TAG, "失败获取信息: " + response);
                }
            }

            @Override
            public void onFailure(Call<CompanyApiResponse> call, Throwable t) {

                Log.e(TAG, "请求失败：" + t.getMessage());
            }
        });



    }

    private boolean validateInput(double longitude, double  latitude , String clockIn, String clockOut) {
        // 检查经纬度是否在合理范围内
        if (latitude < -90 || latitude > 90) {
            showAlertDialog("纬度必须在 -90 到 90 之间");
            return false;
        }
        if (longitude < -180 || longitude > 180) {
            showAlertDialog("经度必须在 -180 到 180 之间");
            return false;
        }

        // 检查时间格式是否正确，确保是 HH:mm:ss 格式
        if (!clockIn.matches("^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$")) {
            showAlertDialog("开始时间格式或范围不正确，应为 HH:mm:ss");
            return false;
        }
        if (!clockOut.matches("^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$")) {
            showAlertDialog("结束时间格式或范围不正确，应为 HH:mm:ss");
            return false;
        }

        return true;
    }

    private void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("输入无效")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show();
    }

}

