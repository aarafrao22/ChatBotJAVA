package com.example.chatbotjava;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Retrofit {

    @GET
    Call<MessageModel> getMessage(@Url String url);
}
