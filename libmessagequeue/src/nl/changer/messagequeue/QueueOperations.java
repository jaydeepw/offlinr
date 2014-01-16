package nl.changer.messagequeue;

import nl.changer.messagequeue.dboperations.QueueManager;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class QueueOperations {

	private static final String TAG = QueueOperations.class.getSimpleName();
	
	private static Context mContext;
	private static QueueManager mQueueMgr;
	
	public QueueOperations(final Context ctx) {
		mContext = ctx;
		mQueueMgr = new QueueManager(mContext);
	}
	
	/****
	 * Add a message to the queue
	 * @param message Message to be added to the queue.
	 * @param url Url to send this message to.
	 * @return Message id 
	 * ***/
	public static long add( JSONObject message, String url ) {
		Log.v( TAG, "#add ready to add to queue obj: " + message );
		
		long messageId = -1;
		
		if( message == null || message.length() == 0 ) {
			// calling error will return the control
			throw new IllegalArgumentException("Invalid message");
		} else {
			messageId = mQueueMgr.add( message, url );
		}
		
		return messageId;
	}
	
	public static void remove( final long id ) {
		
		JSONObject returnObj = new JSONObject();
		
		if( id == -1 ) {
			throw new IllegalArgumentException("Invalid message id. Message cannot be removed");
		} else {
			if( mQueueMgr.remove( id ) ) {
				
			} else {
				try {
					returnObj.put( Constants.KEY_MESSAGE, "Failed to remove" );
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void clear() {
		Log.v( TAG, "#clear called: " );
		
		JSONObject returnObj = new JSONObject();
		
		mQueueMgr = new QueueManager(mContext);
		if( mQueueMgr.clear(returnObj) ) {
			
		} else {
			
		}
	}
}