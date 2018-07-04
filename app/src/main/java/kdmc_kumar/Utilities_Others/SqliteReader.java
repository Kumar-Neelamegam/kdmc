package kdmc_kumar.Utilities_Others;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import kdmc_kumar.Core_Modules.BaseConfig;

public class SqliteReader {


    String Query="";

    private static SqliteReader sqliteReader=null;

    public SqliteReader setQuery(String Query)
    {

        this.Query=Query;

        return this;
    }

    public static SqliteReader getInstance()
    {
        if(sqliteReader==null)
        {
            sqliteReader=new SqliteReader();
        }

        return sqliteReader;
    }

    public void onDataReceiver(Data data) throws Exception
    {
        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery(this.Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    data.onData(c);

                } while (c.moveToNext());
            }
        }
        assert c != null;
        c.close();
        db.close();

    }



    public interface Data
    {
        void onData(Cursor c);
    }

}
