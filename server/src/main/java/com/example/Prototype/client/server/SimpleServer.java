package com.example.Prototype.client.server;

import java.io.IOException;

import com.example.server.Classes.MovieList;

public class SimpleServer extends AbstractServer {

	public SimpleServer(int port) {
		super(port);
		
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String msgString = msg.toString();
		if (msgString.startsWith("#movielist")) {
			MovieList warning = new MovieList();
			try {
				client.sendToClient(warning);
				System.out.format("Sent movies to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}