package com.example.Prototype.client;

import com.example.Prototype.client.ocsf.AbstractServer;
import com.example.Prototype.client.ocsf.ConnectionToClient;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleServer extends AbstractServer {
	private static Session session;

	public SimpleServer(int port) {
		super(port);
		
	}

	private static List<Branch> getAllBranches() throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Branch> query = builder.createQuery(Branch.class);
		query.from(Branch.class);
		List<Branch> data = session.createQuery(query).getResultList();
		return data;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		if (msgString.startsWith("#showBranches")) {
			try {
				List<Branch> branches=getAllBranches();
				BranchesList branchesList=new BranchesList();
				for(Branch branch:branches)
					branchesList.setBranch(branch);
				client.sendToClient(branchesList);
				System.out.format("Sent branches to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}