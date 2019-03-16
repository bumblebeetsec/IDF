package com.bumblee.idfscholarship;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @Headers("Content-Type: application/json")
    @POST("post/organisation/")
    Call<ResponseBody> registerOrg(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("post/student/")
    Call<ResponseBody> registerStudent(@Body String body);


}
