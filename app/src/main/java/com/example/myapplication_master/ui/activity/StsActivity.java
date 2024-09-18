package com.example.myapplication_master.ui.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.DatePicker;
import android.widget.Toast;

import com.example.myapplication_master.R;
import com.example.myapplication_master.databinding.ActivityLoginBinding;
import com.example.myapplication_master.databinding.ActivityStatisticsBinding;
import com.example.myapplication_master.ui.adapter.RecordAdapter;
import com.example.myapplication_master.ui.adapter.StatisticsAdapter;
import com.google.android.material.textfield.TextInputEditText;


import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import api.ApiService;
import api.CompanyApiResponse;
import api.CompanyPojo;
import api.CustomDateDialog;
import api.StatisticsPojo;
import api.StsApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StsActivity extends AppCompatActivity {
    private StatisticsAdapter statisticsAdapter;
    private String TAG =  "StatisticsAct";
    private TextView selectedMonthTextView;
    private CustomDateDialog dialog;
    private ActivityStatisticsBinding binding;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding = ActivityStatisticsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 启用返回按钮
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 处理返回按钮点击事件
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        Button dateButton = findViewById(R.id.datebtn);


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateInput = binding.tlMonth.getEditText().getText().toString();

                // 正则表达式验证格式 xxxx-xx
                String regex = "^\\d{4}-\\d{2}$";
                if (dateInput.matches(regex)) {
                    // 如果格式正确，分别获取年份和月份
                    String[] parts = dateInput.split("-");
                    int year = Integer.parseInt(parts[0]);  // 获取年份
                    int month = Integer.parseInt(parts[1]); // 获取月份

                    // 可以继续进行下一步操作
                    Log.d(TAG, "格式正确！年份: " + year + " 月份: " + month);

                    StsResp(year,month);

                } else {
                    // 格式不正确，弹出警告提示
                    Toast.makeText(StsActivity.this, "格式错误，请输入格式：xxxx-xx", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,dateInput);
                }
            }
        });

    }

    private void StsResp(int year,int month){

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView noRecordsTextView = findViewById(R.id.no_records_text);

        recyclerView.setAdapter(null); // 在请求之前设置适配器为 null

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")  // 替换为你的后端地址
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        int companyId = 1 ;
        Call<List<StatisticsPojo>> call = apiService.getStsDate(year,month);

        call.enqueue(new Callback<List<StatisticsPojo>>() {
            @Override
            public void onResponse(Call<List<StatisticsPojo>> call, Response<List<StatisticsPojo>> response) {
                // progressDialogHelper.dismissProgressDialog();
                Log.d(TAG,"马上发请求");
                if (response.isSuccessful()) {

                    List<StatisticsPojo> records = response.body();
                    if (records  != null&&!records.isEmpty()) {
                        statisticsAdapter = new StatisticsAdapter(records);  // 创建适配器
                        recyclerView.setAdapter(statisticsAdapter);  // 绑定适配器
                        Log.d(TAG, "成功");
                        noRecordsTextView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    } else {
                        noRecordsTextView.setVisibility(View.VISIBLE);
                        Log.d(TAG, "获取信息失败:  records is null" );
                    }
                }
                else {
                    noRecordsTextView.setVisibility(View.VISIBLE);
                    Log.d(TAG, "失败获取信息: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<StatisticsPojo>> call, Throwable t) {
                noRecordsTextView.setVisibility(View.VISIBLE);
                Log.e(TAG, "请求失败：" + t.getMessage());
            }
        });


    }




}





