package com.example.Prototype.client;

import org.greenrobot.eventbus.EventBus;

import com.example.CinemaPrototype.Classes.Branch;
import com.example.CinemaPrototype.Classes.MovieList;
import com.example.CinemaPrototype.ocsf.AbstractClient;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if(msg.getClass().equals(MovieList.class)) {
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