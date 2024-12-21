package com.thick124.lop124LTTD03.nhomTPB.Service;

import android.icu.text.UnicodeSet;

import com.thick124.lop124LTTD03.nhomTPB.Models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Dataservice {
    @GET("ShowthongtinPHP.php")
    Call<List<User>> getThongtin();

    @FormUrlEncoded
    @POST("add_thongtin.php")
    Call<ResponseBody> Addthongtin(
            @Field("hoTen") String hoTen,
            @Field("avatar") String avatar,
            @Field("gioiThieu") String gioiThieu
    );
    @FormUrlEncoded
    @POST("Sua_thongtin.php")
    Call<ResponseBody> Suathongtin(
            @Field("hoTen") String id,
            @Field("hoTen") String hoTen,
            @Field("avatar") String avatar,
            @Field("gioiThieu") String gioiThieu
    );
    @FormUrlEncoded
    @POST("Delete_thongtin.php")
    Call<ResponseBody> Deletethongtin(
            @Field("id") String id
    );
}