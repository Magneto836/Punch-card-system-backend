package com.example.myapplication_master.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import api.ApiService;
import api.RecordApiResponse;
import api.RecordPojo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.myapplication_master.R;
import com.example.myapplication_master.ui.adapter.RecordAdapter;

public class AllRecordActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecordAdapter recordAdapter;

    private static final String TAG = "AllRecord";
    private List<RecordPojo> recordList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allrecord);  // 确保这个布局文件存在

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 启用返回按钮
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 处理返回按钮点击事件
        toolbar.setNavigationOnClickListener(v -> onBackPressed());




        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查询打卡记录...");
        progressDialog.setCancelable(true);  // 设置是否可以关闭对话框
        progressDialog.show();  // 显示进度框


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        calendarView = findViewById(R.id.calendarView);

// 监听日期选择
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // 月份从 0 开始，转换为常规月份
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            fetchAllRecords(selectedDate);  // 根据选择的日期获取打卡记录
            Log.d(TAG,"日期为"+selectedDate);
        });

        // 默认加载今天的打卡记录
        Calendar calendar = Calendar.getInstance();
        String today = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        fetchAllRecords(today);

    }
    private void fetchAllRecords(String date){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")  // 替换为你的后端地址
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<RecordApiResponse> call = apiService.getRecordsByDate(date);


        call.enqueue(new Callback<RecordApiResponse>() {
            @Override
            public void onResponse(Call<RecordApiResponse> call, Response<RecordApiResponse> response) {
                // progressDialogHelper.dismissProgressDialog();
                Log.d(TAG,"马上发请求");
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();  // 关闭进度框
                    }

                    RecordApiResponse recordApiResponse = response.body();
                    if (recordApiResponse != null && recordApiResponse.getCode() == 200) {
                        recordList.clear();
                        List<RecordPojo> records = recordApiResponse.getData().getRecords();
                        if (records.isEmpty()) {
                            // 显示该日无打卡记录
                            Log.d(TAG, "该日无打卡记录");
                            // 这里你可以选择用一个 TextView 来显示 "该日无打卡记录"
                            TextView emptyView = findViewById(R.id.empty_view);  // 布局中预先添加的 TextView
                            emptyView.setVisibility(View.VISIBLE);  // 显示提示
                            recyclerView.setVisibility(View.GONE);  // 隐藏 RecyclerView
                        }
                        else {
                            recordList.addAll(records);
                            recordAdapter = new RecordAdapter(recordList);  // 创建适配器
                            recyclerView.setAdapter(recordAdapter);  // 绑定适配器
                            Log.d(TAG, "成功");
                            Log.d(TAG, recordList.toString());
                            recyclerView.setVisibility(View.VISIBLE);  // 显示 RecyclerView
                            TextView emptyView = findViewById(R.id.empty_view);
                            emptyView.setVisibility(View.GONE);  // 隐藏提示
                        }



                        //recordAdapter.notifyDataSetChanged();  // 更新 RecyclerView

                    } else {
                        Log.d(TAG, "获取记录失败: " + recordApiResponse.getMessage());
                    }
                }
                else {
                    Log.d(TAG, "失败获取记录: " + response);
                }
            }

            @Override
            public void onFailure(Call<RecordApiResponse> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();  // 关闭进度框
                }
                //progressDialogHelper.dismissProgressDialog();
                Log.e(TAG, "请求失败：" + t.getMessage());
            }
        });






    }


}
