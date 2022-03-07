package com.jiseondev.bloodpressurelog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import common.CommonUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        CommonUtils.setSystembarColor(this, getColor(R.color.bg_main_blue), true);
    }

    private void initView() {
        View writeBpView = findViewById(R.id.cl_write_bp);
        View listBpView = findViewById(R.id.cl_list_bp);

        writeBpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent writeIntent = new Intent(getApplicationContext(), BpWriteActivity.class);
                startActivity(writeIntent);
            }
        });

        listBpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listIntent = new Intent(getApplicationContext(), BpListActivity.class);
                startActivity(listIntent);
            }
        });
    }
}