package com.example.chatbotjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText edEditText;
    private FloatingActionButton btnFloatingActionButton;
    private final String BotKey = "bot";
    private final String UserKey = "user";
    private ArrayList<ChatModel> chatModelsArrayList;
    private ChatRVAdapter chatRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        edEditText = findViewById(R.id.txtMessage);
        btnFloatingActionButton = findViewById(R.id.btnSend);

        chatModelsArrayList = new ArrayList<ChatModel>();
        chatRVAdapter = new ChatRVAdapter(chatModelsArrayList,this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chatRVAdapter);

        btnFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edEditText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Type Anything First", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    getResponse(edEditText.getText().toString());
                }
            }
        });


    }

    private void getResponse(String message) {
        chatModelsArrayList.add(new ChatModel(UserKey,message));
        chatRVAdapter.notifyDataSetChanged();

        String url = "http://api.brainshop.ai/get?bid=161229&key=V34Ew7CIMrmeSQiG&uid=[uid]&msg="+message;
        String baseURL = "http://api.brainshop.ai";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MessageModel> call= retrofitAPI.getMessage(url);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                MessageModel msg = response.body();
                chatModelsArrayList.add(new ChatModel(BotKey,msg.getCnt()));
                chatRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                chatModelsArrayList.add(new ChatModel(BotKey,"Plz Revert Your Message"));
                chatRVAdapter.notifyDataSetChanged();
            }
        });

            }
        };

