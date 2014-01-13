package nl.changer.messagequeue.model;

import com.google.gson.JsonObject;

public class Message {
	
	private static final String TAG = Message.class.getSimpleName();
	
	public long mID;
	
	/***
	 * The actually message
	 * ***/
	public JsonObject mData;
	
	/***
	 * MIME type of the message.
	 ****/
	public String mType;
	
	/****
	 * Url to post the message to.
	 * ***/
	public String mURL;

	public Message( long id, JsonObject data, String type, String url ) {
		mID = id;
		mData = data;
		mURL = url;
	}
}