package nl.changer.messagequeue;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import io.trigger.forge.android.core.ForgeApp;
import io.trigger.forge.android.core.ForgeParam;
import io.trigger.forge.android.core.ForgeTask;
import nl.changer.messagequeue.dboperations.QueueManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

public class TestAPI {

	private static final String TAG = TestAPI.class.getSimpleName();
	
	private static QueueManager mQueueMgr;
	
	public static void add( final ForgeTask task, @ForgeParam("text") final JsonObject message ) {
		Log.v( TAG, "#add ready to add to queue obj: " + message );
		
		// TODO: check for message to be a 'null' JSON object
		if( message == null) {
			// calling error will return the control
			task.error("String blank");
		} else {
			mQueueMgr = new QueueManager( ForgeApp.getActivity() );
			long messageId = mQueueMgr.add( message, "Hello url" );
			JsonObject successMsg = new JsonObject();
			successMsg.addProperty("id", messageId);
			task.success( successMsg );
		}
	}
	
	public static void remove( final ForgeTask task, @ForgeParam("id") final long id ) {
		Log.v( TAG, "#remove id: " + id );
		
		JsonObject returnObj = new JsonObject();
		
		if( id == -1 ) {
			task.error("Invalid id");
		} else {
			mQueueMgr = new QueueManager( ForgeApp.getActivity() );
			if( mQueueMgr.remove( id ) )
				task.success();
			else {
				returnObj.addProperty( Constants.KEY_MESSAGE, "Failed to remove" );
				task.error(returnObj);
			}
				
		}
	}
	
	public static void clear( final ForgeTask task ) {
		Log.v( TAG, "#clear called: " );
		
		JsonObject returnObj = new JsonObject();
		
		mQueueMgr = new QueueManager( ForgeApp.getActivity() );
		if( mQueueMgr.clear(returnObj) )
			task.success();
		else {
			task.error(returnObj);
		}
	}
}