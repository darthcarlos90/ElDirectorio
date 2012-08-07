package directorio.DAO;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class LoginDAO extends SQLiteOpenHelper{

    private static String DATABASE_NAME="Logindb";

    private static final int DATABASE_VERSION = 2;
    private static final String DETAILS_TABLE_NAME = "Login";

    private static String username;
    private static String estado;

    static String USERNAME=username;
    static String ESTADO=estado;
    private String KEY_ROWID;

    private static final String DETAILS_TABLE_CREATE =
                "CREATE TABLE " + DETAILS_TABLE_NAME + " (" +
                USERNAME + " TEXT, " +
                ESTADO + " TEXT);";

    private SQLiteOpenHelper mSQL;
    private SQLiteDatabase db;



    public LoginDAO(Context ble){
        super(ble, DATABASE_NAME, null,DATABASE_VERSION );
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
         db.execSQL(DETAILS_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DETAILS_TABLE_IF_NOT_EXISTS");
        onCreate(db);
    }


      public void open() throws SQLException
        {
          LoginDAO mSQL = new LoginDAO(null);
            db = mSQL.getWritableDatabase();
         } 
      
      public String dameLog(){
    	  
    	  Cursor holo = db.rawQuery("select * from Login", null);
    	  holo.moveToLast();
    	  String estado = holo.getString(1);
		return estado;
      }

   public void addDetails( String nam, String ag) {

            SQLiteDatabase db = mSQL.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USERNAME, nam);
            //values.put(TIME, System.currentTimeMillis());
            values.put(ESTADO, ag);
            db.insert(DETAILS_TABLE_NAME, null, values);
        }

      public Cursor getAllDetails() 
      {
          return db.query(DETAILS_TABLE_NAME, new String[] {
                  KEY_ROWID, 
                  USERNAME,
                  ESTADO  },  null, null, null, null, null, null);
      }   


      public boolean updateDetails(long rowId, String nam, 
              String ag) 
              {
                  ContentValues args = new ContentValues();
                  args.put(USERNAME, nam);
                  args.put(ESTADO, ag);

                  return db.update(DETAILS_TABLE_NAME, args, 
                                   KEY_ROWID + "=" + rowId, null) > 0;
              }

}