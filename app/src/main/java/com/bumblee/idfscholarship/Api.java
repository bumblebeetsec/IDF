package com.bumblee.idfscholarship;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @Headers("Content-Type: application/json")
    @POST("post/organisation/")
    Call<ResponseBody> registerOrg(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("post/student/")
    Call<ResponseBody> registerStudent(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("post/scholarship/")
    Call<ResponseBody> addScholarship(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("post/scholarship/apply/")
    Call<ResponseBody> applyScholarship(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("get/type/")
    Call<ResponseBody> getType(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("get/student/")
    Call<ResponseBody> getStudent(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("get/type/")
    Call<ResponseBody> getOrg(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("get/scholarship/organisation/")
    Call<ResponseBody> getOrganisationScholarships(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("get/scholarship/details/")
    Call<ResponseBody> getScholarshipDetails(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("get/scholarship/")
    Call<ResponseBody> getAll(@Body String body);

    @Headers("Content-Type: application/json")
    @POST("get/scholarship/eligible/")
    Call<ResponseBody> getEligible(@Body String body);


}
