package com.jiseondev.bloodpressurelog;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import adapter.BpCursorAdapter;
import common.CommonUtils;
import database.BpLogDBHelper;

public class BpListActivity extends AppCompatActivity {

    // 다국어 처리

    BpLogDBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_bp);

        initDatabase();
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

        ListView lvResult = findViewById(R.id.lv_bp_result);

        String sql = "select * from " + BpLogDBHelper.BP_RECORD_TABLE_NAME + ";";
        Cursor c = db.rawQuery(sql, null);

        String[] strs = new String[]{
                BpLogDBHelper.RECORD_DATE,
                BpLogDBHelper.TIME_TYPE,
                BpLogDBHelper.BP_MAX,
                BpLogDBHelper.BP_MIN,
                BpLogDBHelper.MEMO};

        int[] ints = new int[]{
                R.id.tv_date,
                R.id.tv_time_type,
                R.id.tv_bp_max,
                R.id.tv_bp_min,
                R.id.tv_memo};

        BpCursorAdapter adapter = new BpCursorAdapter(lvResult.getContext(), R.layout.view_list_bp_record, c, strs, ints);
        lvResult.setAdapter(adapter);
    }

    @SuppressLint("Range")
    private void initDatabase() {
        dbHelper = new BpLogDBHelper(BpListActivity.this, "bprecorddb.db", null, 1);
        db = dbHelper.getWritableDatabase();
        dbHelper.onCreate(db);

//        db 항목 출력
//        Cursor c = db.query(BpLogDBHelper.BP_RECORD_TABLE_NAME, null, null, null, null, null, null, null);
//        while (c.moveToNext()) {
//            System.out.println("jiseon result : " + c.getString(c.getColumnIndex(BpLogDBHelper.RECORD_DATE)));
//            System.out.println("jiseon result : " + c.getString(c.getColumnIndex(BpLogDBHelper.TIME_TYPE)));
//            System.out.println("jiseon result : " + c.getString(c.getColumnIndex(BpLogDBHelper.BP_MAX)));
//            System.out.println("jiseon result : " + c.getString(c.getColumnIndex(BpLogDBHelper.BP_MIN)));
//            System.out.println("jiseon result : " + c.getString(c.getColumnIndex(BpLogDBHelper.MEMO)));
//        }
    }
}
