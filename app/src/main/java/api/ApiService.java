package api;  // 根据你创建的包名

import java.util.Map;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;

import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("/login")
    Call<UserApiResponse> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/regist")
    Call<UserApiResponse> addUser(@Field("username") String username, @Field("password") String password,@Field("role") String role);


}
