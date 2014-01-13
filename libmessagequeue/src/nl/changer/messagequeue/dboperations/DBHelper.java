package nl.changer.messagequeue.dboperations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String TAG = DBHelper.class.getSimpleName();

	    private static final int DATABASE_VERSION = 1;
	    private static final String DATABASE_NAME = "messagequeue.db";
	    
	    // Store the surveys into the table
	    public static final String TABLE_QUEUE = "queue";
	    
	    public static final String COL_ID = "_id";
	    
	    /****
	     * Message to be added to the queue
	     ****/
	    public static final String COL_MESSAGE = "message";
	    
	    /***
	     * URL where the message is to be posted
	     * **/
	    public static final String COL_URL = "url";
	    /***
	     * Preferable the MIME type of the data being stored into the table.
	     * ***/
	    public static final String COL_TYPE = "message_type";
	    
	    
	    private static final String CREATE_TABLE_QUEUE = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEUE + " (" +
	    			COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
	    			COL_MESSAGE + " BLOB NOT NULL, " +
	    			COL_TYPE + " TEXT NOT NULL, " +
	                COL_URL + " TEXT NOT NULL);";
	    
	    // sqlite> ALTER TABLE NamesOfFriends ADD COLUMN Email TEXT;
	    /*private static final String ALTER_TABLE_ANSWERS = "ALTER TABLE " + TABLE_ANSWERS + " ADD " +
    			COL_LOCATION + " TEXT NOT NULL, " +
    			COL_SURVEY_TIME + " TEXT NOT NULL;";*/

	    public DBHelper( Context context ) {
	        super( context, DATABASE_NAME, null, DATABASE_VERSION );
	    }

	    @Override
	    public void onCreate( SQLiteDatabase db ) {
	    	Log.d( TAG, "#onCreate Creating required tables" );
	        db.execSQL( CREATE_TABLE_QUEUE );
	        Log.d( TAG, "#onCreate tables created successfully!" );
	    }

		@Override
		public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
			
			/*if( oldVersion == 3 && newVersion == 4 )
				db.execSQL(ALTER_TABLE_ANSWERS);
			
			if( oldVersion == 5 && newVersion == 6 )
				db.execSQL( CREATE_TABLE_QUESTIONS );*/
			
			Log.d( TAG, "#onUpgrade tables created successfully!" );
		}
}
