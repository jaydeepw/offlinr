package nl.changer.messagequeue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetWatcher extends BroadcastReceiver {
	
	private static final String TAG = NetWatcher.class.getSimpleName();
	
    @Override
    public void onReceive(Context context, Intent intent) {
        //here, check that the network connection is available. If yes, start your service. If not, stop your service.
    	Log.v(TAG, "#onReceive net connected");
    	
       ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo info = cm.getActiveNetworkInfo();
       if( info != null ) {
           if( info.isConnected() ) {
               // start service
               Intent ser = new Intent( context, QueueService.class );
               Log.v(TAG, "#onReceive starting the service");
               context.startService(ser);
           } else {
               // stop service
               Intent ser = new Intent( context, QueueService.class );
               Log.v(TAG, "#onReceive stopping the service");
               context.stopService(ser);
           }
       } else
    	   Log.v(TAG, "#onReceive network info is null");
    }
}
