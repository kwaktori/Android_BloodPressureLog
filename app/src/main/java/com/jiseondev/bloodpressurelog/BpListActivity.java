package com.jiseondev.bloodpressurelog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import common.CommonUtils;

public class BpListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_bp);

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        CommonUtils.setSystembarColor(this, getColor(R.color.bg_main_blue), true);
    }

    private void initView() {
        ImageView btnBack = (ImageView) findViewById(R.id.iv_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
