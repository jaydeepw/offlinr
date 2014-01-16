package nl.changer.messagequeue.dboperations;

import java.util.ArrayList;

import nl.changer.messagequeue.Constants;
import nl.changer.messagequeue.model.Message;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

public class QueueManager {

	
	private static final String TAG = QueueManager.class.getSimpleName();
	
	private Context mContext;
	
	public QueueManager(Context ctx) {
		mContext = ctx;
	}
	
	public long add( JSONObject message, String url ) {
		
		long rowId = -1;
		
		if( message == null || TextUtils.isEmpty( message.toString() ) )
			return rowId;
		
		DBHelper dbHelper = null;
		SQLiteDatabase db = null;
		
		try {
			
			dbHelper = new DBHelper(mContext);
			db = dbHelper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put( DBHelper.COL_MESSAGE, message.toString() );
			values.put( DBHelper.COL_URL, url );
			rowId = db.insert( DBHelper.TABLE_QUEUE, null, values );
			
			if( rowId == -1 )
				Log.d( TAG, "#add Error inserting the data into the table" );
			else
				Log.d( TAG, "#add Data inserted into the table successfully" );
		} catch ( Exception e ) {
			e.printStackTrace();
			Log.d( TAG, "#add error: " + e.getMessage() );
		} finally {
			
			dbHelper.close();
			
			if( db.isOpen() )
				db.close();
		} 
		
		return rowId;
	}

	public boolean clear( JSONObject returnObj ) {
		DBHelper dbHelper = new DBHelper(mContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		boolean isSuccessful = false;
		
		// TODO: test this method on android emulator
		// TODO: add narrower exceptions and respective messages
		// in the error object
		try {
			db.delete( DBHelper.TABLE_QUEUE, null, null );
			isSuccessful = true;
		} catch ( Exception e ) {
			e.printStackTrace();
			try {
				returnObj.put( Constants.KEY_MESSAGE, e.getMessage() );
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			isSuccessful = false;
		} finally {
			dbHelper.close();
			if( db.isOpen() )
				db.close();
		}
		
		return isSuccessful;
	}
	
	/***
	 * Deletes all the survey questionID-questionType mappings from the table.
	 * This method must be called after all the survey
	 * answers have been sent to the server.
	 ***/
	public boolean remove( long messageId ) {
		DBHelper dbHelper = new DBHelper( mContext );
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		boolean isSuccessful = false;
		
		// TODO: return useful error message 
		// if the message with the id is not found.
		try {
			int rowsAffected = db.delete( DBHelper.TABLE_QUEUE, DBHelper.COL_ID + "=?", new String[] { Long.toString(messageId) } );
			
			if( rowsAffected <= 0 ) {
				isSuccessful = false;
				Log.w( TAG, "#remove Deletion failed. rowsAffected: " + rowsAffected );
			} else {
				isSuccessful = true;
				Log.v( TAG, "#remove Deletion SUCCESSFUL. rowsAffected: " + rowsAffected );
			}
			
		} catch( Exception e ) {
			e.printStackTrace();
		} finally {
			dbHelper.close();
			db.close();
		}
		
		return isSuccessful;
	}

	public ArrayList<Message> getMessages() {
		
		ArrayList<Message> messages = new ArrayList<Message>();
		
		DBHelper dbHelper = new DBHelper(mContext);
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cur = null;
		
		try {
			cur = db.query( DBHelper.TABLE_QUEUE, null, null, null, null, null, null );
			
			if( cur == null )
				return null;
			
			if( cur.getCount() == 0 )
				return null;
			
			
			while( cur.moveToNext() ) {
				long id = cur.getLong( cur.getColumnIndex(DBHelper.COL_ID) );
				byte[] blob = cur.getBlob( cur.getColumnIndex(DBHelper.COL_MESSAGE) );
				JSONObject data = new JSONObject( new String(blob) );
				String type = cur.getString( cur.getColumnIndex(DBHelper.COL_TYPE) );
				String url = cur.getString( cur.getColumnIndex(DBHelper.COL_URL) );
				
				Message msg = new Message(id, data, type, url);
				
				messages.add(msg);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if( cur != null && !cur.isClosed() )
				cur.close();
			
			dbHelper.close();
			db.close();
		}
		
		Log.v( TAG, "#getMessages messages: " + messages.size() );
		
		return messages;
	}
	
}