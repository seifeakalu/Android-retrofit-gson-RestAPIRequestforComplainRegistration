package com.example.dwhp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.dwhp.model.MessageContent;
import com.example.dwhp.model.MessageRequest;
import com.example.dwhp.remote.ApiUtils;
import com.example.dwhp.remote.MessageService;
import com.example.dwhp.security.TokenUtil;
import org.json.JSONObject;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMessageList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    int pageSize=5;
    int oldArrayListSize=0;
    int pageNumber=0;
    boolean likeCounted = false;
    MessageService messageService;
    private RecyclerView messageRV;
    private ArrayList<MessageContent> messageDataArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ProgressBar progressBar;
    private ProgressBar progressBarBottom;
    LinearLayoutManager manager;
    private Spinner spinner;
    private ViewPager mViewPager;
    private static final String[] paths = {"ዋና መነሻ ገጽ", "ቅሬታ ይጨምሩ", "የእኔ ቅሬታዎች"};
    private boolean dataFetched = true;
    TokenUtil tokenUtil = new TokenUtil();
    @SuppressLint("WrongConstant")
    SharedPreferences sh ;
    @SuppressLint({"WrongConstant", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        messageService = ApiUtils.getMessageService();
        messageRV = findViewById(R.id.idRVMessage);
        progressBar = findViewById(R.id.idPBLoading);
        progressBarBottom = findViewById(R.id.idPBLoadingBottom);
        messageDataArrayList = new ArrayList<>();
        progressBarBottom.setVisibility(View.INVISIBLE);
        manager = new LinearLayoutManager(MainMessageList.this);
        recyclerViewAdapter = new RecyclerViewAdapter(messageDataArrayList, MainMessageList.this);
        sh= getSharedPreferences("MySharedPref", MODE_APPEND);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainMessageList.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(MainMessageList.this);
        setupOnClickListener();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:

                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                Intent intent = new Intent(MainMessageList.this,RegisterMessage.class);
                startActivity(intent);
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:

                Toast.makeText(MainMessageList.this, "This part is not yet functional. ", Toast.LENGTH_LONG).show();
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
    public void setupOnClickListener(){
        getMessages();
        messageRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if( dataFetched){
                        onScrollCall();
                        dataFetched = false;
                    }
                }
            }


        });

    }

    public void getPositionAgreed(int position) {
        int messageId=messageDataArrayList.get(position).getId();
        agreeMessage(messageId,position);

    }
    public void getPositionDisAgreed(int position) {
        int messageId=messageDataArrayList.get(position).getId();
        disagreeMessage(messageId,position);

    }

    public void incrementAgreedCount(int position){
        int messageId=messageDataArrayList.get(position).getId();
        Integer addCount=Integer.parseInt( messageDataArrayList.get(position).getAgreeCount());
        Integer result;
        result= addCount+1;
        messageDataArrayList.get(position).setAgreeCount(result.toString());
        recyclerViewAdapter.notifyItemChanged(position);
    }
    public void incrementDisagreedCount(int position){
        int messageId=messageDataArrayList.get(position).getId();
        Integer addCount=Integer.parseInt( messageDataArrayList.get(position).getDisagreeCount());
        Integer result;
        result= addCount+1;
        messageDataArrayList.get(position).setDisagreeCount(result.toString());
        recyclerViewAdapter.notifyItemChanged(position);
    }


    public void buildRecycler(){
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("item cliked");
                System.out.println(messageDataArrayList.get(position).getId());
            }
            @Override
            public void onUpdateAgreedCount(int position) {
                getPositionAgreed(position);
            }
            @Override
            public void onUpdateDisagreedCount(int position) {
                getPositionDisAgreed(position);
            }

        });
    }

    private void disagreeMessage(int messageId, final int position){
        Call<Void> call = messageService.countDisagreed(tokenUtil.getToken(sh),messageId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    incrementDisagreedCount(position);
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainMessageList.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(MainMessageList.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainMessageList.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void agreeMessage(int messageId, final int position){
        Call<Void> call = messageService.countAgreed(tokenUtil.getToken(sh),messageId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    incrementAgreedCount(position);
                }
                else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainMessageList.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        Toast.makeText(MainMessageList.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainMessageList.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private  void  onScrollCall() {
        Call<MessageRequest> call = messageService.getAllMessages(tokenUtil.getToken(sh),pageNumber,pageSize);
        progressBarBottom.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<MessageRequest>() {
            @Override
            public void onResponse(Call<MessageRequest> call, Response<MessageRequest> response) {
                if (response.isSuccessful()) {
                    progressBarBottom.setVisibility(View.INVISIBLE);
                    if(messageDataArrayList.size() != (response.body().getTotalElements())){
                    oldArrayListSize= messageDataArrayList.size();
                    messageDataArrayList.addAll(response.body().getMessageContent());
                    recyclerViewAdapter.notifyItemInserted(messageDataArrayList.size());
                    buildRecycler();
                    pageNumber+=1;
                    dataFetched=true;
                    }
                    else {
                        Toast.makeText(MainMessageList.this, "No more data available", Toast.LENGTH_SHORT).show();
                        dataFetched=true;
                    }
//                    System.out.println(messageDataArrayList.size()+"with " +pageSize*pageNumber);
                }
                else {

                    progressBarBottom.setVisibility(View.INVISIBLE);
                    }

            }

            @Override
            public void onFailure(Call<MessageRequest> call, Throwable t) {

                Toast.makeText(MainMessageList.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getMessages() {
        Call<MessageRequest> call = messageService.getAllMessages(tokenUtil.getToken(sh),pageNumber,pageSize);
        call.enqueue(new Callback<MessageRequest>() {

            @Override
            public void onResponse(Call<MessageRequest> call, Response<MessageRequest> response) {

                if (response.isSuccessful()) {
                       progressBar.setVisibility(View.GONE);
                       messageDataArrayList.addAll(response.body().getMessageContent());
                       recyclerViewAdapter.notifyItemInserted(messageDataArrayList.size());
                       recyclerViewAdapter = new RecyclerViewAdapter(messageDataArrayList, MainMessageList.this);
                       messageRV.setLayoutManager(manager);
                       messageRV.setAdapter(recyclerViewAdapter);
                       recyclerViewAdapter.notifyDataSetChanged();
                       buildRecycler();
                       pageNumber+=1;

                  }

            }

            @Override
            public void onFailure(Call<MessageRequest> call, Throwable t) {

                Toast.makeText(MainMessageList.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
