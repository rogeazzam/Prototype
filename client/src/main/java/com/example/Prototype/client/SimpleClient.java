package com.example.Prototype.client;

import com.example.Prototype.client.ocsf.AbstractClient;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if(msg.getClass().equals(BranchesList.class)) {
			EventBus.getDefault().post(new BranchesList((BranchesList) msg));
		}else if(msg.getClass().equals(MovieList.class)){
			EventBus.getDefault().post(new MovieList((MovieList) msg));
		}
	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

}