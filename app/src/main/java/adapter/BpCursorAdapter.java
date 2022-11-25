package adapter;

import static com.jiseondev.bloodpressurelog.BpWriteActivity.RECORD_TYPE_EVENING;
import static com.jiseondev.bloodpressurelog.BpWriteActivity.RECORD_TYPE_MORNING;
import static com.jiseondev.bloodpressurelog.BpWriteActivity.RECORD_TYPE_NIGHT;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.jiseondev.bloodpressurelog.R;

public class BpCursorAdapter extends SimpleCursorAdapter {

    private Cursor c;
    private int layout;
    private Context context;
    private String[] from;
    private int[] to;

    public BpCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.layout = layout;
        this.context = context;
        this.from = from;
        this.to = to;
    }

    @SuppressLint("Range")
    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, null);
        }
        c.moveToPosition(pos);

        String date = c.getString(c.getColumnIndex(from[0]));
        String timeType = c.getString(c.getColumnIndex(from[1]));
        String bpMax = c.getString(c.getColumnIndex(from[2]));
        String bpMin = c.getString(c.getColumnIndex(from[3]));
        String memo = c.getString(c.getColumnIndex(from[4]));

        // 일시
        TextView tvDate = (TextView) v.findViewById(to[0]);
        tvDate.setText(context.getString(R.string.bp_write_date) + " : " + date);

        // 시간 타입 (아침/저녁/밤)
        TextView tvTimeType = (TextView) v.findViewById(to[1]);

        int timeTypeInt = Integer.parseInt(timeType);
        String timeTypeRes = "";

        switch (timeTypeInt) {
            case RECORD_TYPE_MORNING:
                timeTypeRes = context.getString(R.string.bp_morning);
                break;

            case RECORD_TYPE_EVENING:
                timeTypeRes = context.getString(R.string.bp_dinner);
                break;

            case RECORD_TYPE_NIGHT:
                timeTypeRes = context.getString(R.string.bp_night);
                break;

        }

        tvTimeType.setText(timeTypeRes);

        // 최고 혈압
        TextView tvMax = (TextView) v.findViewById(to[2]);
        tvMax.setText(context.getString(R.string.bp_write_max) + " : " + bpMax);

        // 최저 혈압
        TextView tvMin = (TextView) v.findViewById(to[3]);
        tvMin.setText(context.getString(R.string.bp_write_min) + " : " + bpMin);

        // 메모
        TextView tvMemo = (TextView) v.findViewById(to[4]);
        tvMemo.setText(context.getString(R.string.bp_write_memo) + " : " + memo);

        return (v);
    }
}
