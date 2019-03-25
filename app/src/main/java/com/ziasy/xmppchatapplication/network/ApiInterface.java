package com.ziasy.xmppchatapplication.network;

import com.ziasy.xmppchatapplication.RegisterRequestResponse;
import com.ziasy.xmppchatapplication.model.FileUploadModel;
import com.ziasy.xmppchatapplication.model.UserHistoryModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {
    @Headers({"Authorization:E6jhLtJRA4QowlKx", "Content-Type: application/json", "Accept: application/json"})
    @POST("plugins/restapi/v1/users")
    Call<ResponseBody> registerUser(@Body RegisterRequestResponse registerRequestResponse);

    @POST(":8090/getchat.php ")
    Call<UserHistoryModel> getUserHistoryList(@Query("to") String to, @Query("from") String from);

    @Multipart
    @POST("/upload.php")
    Call<FileUploadModel> postImage(@Part MultipartBody.Part image, @Part("fileToUpload") RequestBody name);

    @Multipart
    @POST("/uploadm.php")
    Call<FileUploadModel> uploadImage(@Part MultipartBody.Part image, @Part("fileToUpload") RequestBody name, @Part("num") String num);

}
