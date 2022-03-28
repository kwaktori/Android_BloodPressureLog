package com.jiseondev.bloodpressurelog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import common.CommonUtils;

public class BpWriteActivity extends AppCompatActivity {

    public static final int RECORD_TYPE_MORNING = 10;
    public static final int RECORD_TYPE_EVENING = 20;
    public static final int RECORD_TYPE_NIGHT = 30;

    public static final int NORMAL_BP_MAX = 120;
    public static final int NORMAL_BP_MIN = 80;

    public static final int WARNING_BP_MAX = 130;

    public static final int HIGH_BEFORE_BP_MAX = 140;
    public static final int HIGH_BEFORE_BP_MIN = 90;

    public static final int HIGH_ONE_BP_MAX = 160;
    public static final int HIGH_ONE_BP_MIN = 100;

    private int recordType = 0;

    private ImageView ivResultIcon;
    private TextView tvResultMsg;
    private View resultView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_write_bp);

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

        TextView tvDate = findViewById(R.id.tv_date_time);

        TextView tvMorning = findViewById(R.id.tv_morning);
        TextView tvEvening = findViewById(R.id.tv_evening);
        TextView tvNight = findViewById(R.id.tv_night);

        EditText etBpMax = findViewById(R.id.et_bp_max);
        EditText etBpMin = findViewById(R.id.et_bp_min);

        EditText etMemo = findViewById(R.id.et_memo);

        TextView btnConfirm = findViewById(R.id.tv_confirm);
        TextView btnSave = findViewById(R.id.tv_save);

        resultView = findViewById(R.id.cl_result_view);

        ivResultIcon = findViewById(R.id.iv_result_icon);
        tvResultMsg = findViewById(R.id.tv_result);

        tvMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordType = RECORD_TYPE_MORNING;

                tvMorning.setSelected(true);
                tvEvening.setSelected(false);
                tvNight.setSelected(false);

                tvMorning.setTextColor(getColor(R.color.btn_on));
                tvEvening.setTextColor(getColor(R.color.btn_off));
                tvNight.setTextColor(getColor(R.color.btn_off));
            }
        });

        tvEvening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordType = RECORD_TYPE_EVENING;

                tvMorning.setSelected(false);
                tvEvening.setSelected(true);
                tvNight.setSelected(false);

                tvMorning.setTextColor(getColor(R.color.btn_off));
                tvEvening.setTextColor(getColor(R.color.btn_on));
                tvNight.setTextColor(getColor(R.color.btn_off));
            }
        });

        tvNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordType = RECORD_TYPE_NIGHT;

                tvMorning.setSelected(false);
                tvEvening.setSelected(false);
                tvNight.setSelected(true);

                tvMorning.setTextColor(getColor(R.color.btn_off));
                tvEvening.setTextColor(getColor(R.color.btn_off));
                tvNight.setTextColor(getColor(R.color.btn_on));
            }
        });

        tvDate.setText(getTime());

        resultView.setVisibility(View.GONE);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = tvDate.getText().toString();

                if (recordType == 0) {
                    Toast.makeText(getApplicationContext(), "현재 시간대를 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else if (etBpMax.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "최고 혈압을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (etBpMin.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "최저 혈압을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    int bpMax = Integer.parseInt(etBpMax.getText().toString());
                    int bpMin = Integer.parseInt(etBpMin.getText().toString());

                    String memo = etMemo.getText().toString();

                    // 키보드 숨기기
                    InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    goResult(date, bpMax, bpMin, memo);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd(E) HH:mm");

        long nowDateTime = System.currentTimeMillis();
        Date date = new Date(nowDateTime);

        return dateFormat.format(date);
    }

    // 결과 측정
    private void goResult(String date, int bpMax, int bpMin, String memo) {
        if (bpMax < NORMAL_BP_MAX && bpMin < NORMAL_BP_MIN) {
            // 정상 혈압
            ivResultIcon.setImageResource(R.drawable.ic_result_good);
            tvResultMsg.setText(R.string.bp_result_good);
            tvResultMsg.setTextColor(getColor(R.color.color_result_msg_good));

        } else if ((bpMax >= NORMAL_BP_MAX && bpMax < WARNING_BP_MAX) && (bpMin < NORMAL_BP_MIN)) {
            // 주의 혈압
            ivResultIcon.setImageResource(R.drawable.ic_result_good);
            tvResultMsg.setText(R.string.bp_result_warning);
            tvResultMsg.setTextColor(getColor(R.color.color_result_msg_warning));

        } else if ((bpMax >= WARNING_BP_MAX && bpMax < HIGH_BEFORE_BP_MAX) || (bpMin >= NORMAL_BP_MIN && bpMin < HIGH_BEFORE_BP_MIN)) {
            // 고혈압 전단계
            ivResultIcon.setImageResource(R.drawable.ic_result_bad);
            tvResultMsg.setText(R.string.bp_result_high_bp_before);
            tvResultMsg.setTextColor(getColor(R.color.color_result_msg_warning));

        } else if ((bpMax >= HIGH_BEFORE_BP_MAX && bpMax < HIGH_ONE_BP_MAX) || (bpMin >= HIGH_BEFORE_BP_MIN && bpMin < HIGH_ONE_BP_MIN)) {
            // 고혈압 1기
            ivResultIcon.setImageResource(R.drawable.ic_result_bad);
            tvResultMsg.setText(R.string.bp_result_high_bp_one);
            tvResultMsg.setTextColor(getColor(R.color.color_result_msg_bad));

        } else {
            // 고혈압 2기
            ivResultIcon.setImageResource(R.drawable.ic_result_bad);
            tvResultMsg.setText(R.string.bp_result_high_bp_two);
            tvResultMsg.setTextColor(getColor(R.color.color_result_msg_bad));

        }

        resultView.setVisibility(View.VISIBLE);

    }

    // DB에 저장
    private void recordToDB() {

    }
}
