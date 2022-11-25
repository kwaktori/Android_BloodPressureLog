package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BpLogDBHelper extends SQLiteOpenHelper {

    public static final String BP_RECORD_TABLE_NAME = "bptable";
    public static final String RECORD_DATE = "record_date";
    public static final String TIME_TYPE = "time_type";
    public static final String BP_MAX = "bp_max";
    public static final String BP_MIN = "bp_min";
    public static final String MEMO = "memo";

    public BpLogDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE if not exists " + BP_RECORD_TABLE_NAME
                + "("
                + "_id integer primary key autoincrement, "
                + RECORD_DATE + " text, "
                + TIME_TYPE + " text, "
                + BP_MAX + " text, "
                + BP_MIN + " text, "
                + MEMO  + " text);";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE if exists " + BP_RECORD_TABLE_NAME;

        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
