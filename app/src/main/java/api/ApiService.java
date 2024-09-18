package api;  // 根据你创建的包名

import com.google.android.gms.common.api.Api;

import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;

import retrofit2.http.GET;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("/login")
    Call<UserApiResponse> login(@Field("username") String username, @Field("password") String password);


    @GET("/GetAllRecords")
    Call<RecordApiResponse> getAllRecords();

    @GET("/dateRecords/{date}")
    Call<RecordApiResponse>  getRecordsByDate(@Path("date") String date);


    @GET("/getCompany/{id}")
    Call<CompanyApiResponse> getCompanyById(@Path("id") int id);

    @GET("/getMonthRecord/{year}/{month}")
    Call <List<StatisticsPojo>> getStsDate(@Path("year")int year , @Path("month") int month);

    @FormUrlEncoded
    @POST("/updateCompany")
    Call<ApiResponse> updateCompany(
            @Field("id") int companyId,
            @Field("locationx") double locationx,
            @Field("locationy") double locationy,
            @Field("start_time") String start_time,
            @Field("end_time") String end_time
    );






}
