package nl.changer.messagequeue;

import io.trigger.forge.android.core.ForgeApp;

import java.util.ArrayList;

import nl.changer.messagequeue.dboperations.QueueManager;
import nl.changer.messagequeue.model.Message;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class QueueService extends IntentService {

	private static final String TAG = QueueService.class.getSimpleName();
	
	private static QueueManager mQueueMgr;
	
	public QueueService() {
		super(TAG);
	}
	
	public QueueService( String name ) {
		super(TAG);
	}

	@Override
	protected void onHandleIntent( Intent intent ) {
		Log.v( TAG, "#onHandleIntent Service has been started" );
		
		if( ForgeApp.getApp() == null)
			Log.w( TAG, "#onHandleIntent getApp is null" );
		
		if( ForgeApp.getApp().getApplicationContext() == null)
			Log.w( TAG, "#onHandleIntent getApplicationContext is null" );
		
		mQueueMgr = new QueueManager( ForgeApp.getApp().getApplicationContext() );
		ArrayList<Message> messages = mQueueMgr.getMessages();
		
		for (Message message : messages) {
			Log.v(TAG, "#onHandleIntent mesg: " + message.mData );
		}
	}
}
