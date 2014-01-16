package nl.changer.messagequeue;


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
		
		mQueueMgr = new QueueManager( QueueService.this );
		ArrayList<Message> messages = mQueueMgr.getMessages();
		
		for (Message message : messages) {
			Log.v(TAG, "#onHandleIntent mesg: " + message.mData );
		}
	}
}
