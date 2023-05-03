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
			EventBus.getDefault().post(new BranchesListEvent((BranchesList) msg));
		}else if(msg.getClass().equals(MovieList.class)){
			EventBus.getDefault().post(new MovieListEvent((MovieList) msg));
		} else if (msg.getClass().equals(Person.class) || msg.getClass().equals(NetworkManager.class) ||
					msg.getClass().equals(Employee.class) || msg.getClass().equals(BranchManager.class)
							|| msg.getClass().equals(ContentManager.class)) {
			EventBus.getDefault().post((Person) msg);
		}else if(msg.getClass().equals(goBack.class)){
			EventBus.getDefault().post((goBack)msg);
		}else if(msg.getClass().equals(String.class)){
			EventBus.getDefault().post(new StringReceiver((String) msg));
		}else if(msg.getClass().equals(Costumer.class)){
			EventBus.getDefault().post((Costumer)msg);
		}else if(msg.getClass().equals(ReportList.class)){
			EventBus.getDefault().post((ReportList) msg);
		}else if(msg.getClass().equals(ComplaintReportList.class)){
			EventBus.getDefault().post((ComplaintReportList)msg);
		}else if(msg instanceof List){
			EventBus.getDefault().post((List<Time>)msg);
		}
	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 2523);
		}
		return client;
	}

}