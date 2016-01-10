package com.nicedev.component.mdb.client;

import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

@Startup
@Singleton
public class MdbClient {

	@PostConstruct
	public void start() throws Exception {
		Hashtable<String, String> env = new Hashtable<String, String>();
		// env.put(Context.PROVIDER_URL, "remote://localhost:8585");
		// env.put(Context.INITIAL_CONTEXT_FACTORY,
		// "org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		Context ctx = new InitialContext(env);
		ConnectionFactory cf = (ConnectionFactory) ctx
				.lookup("java:/ConnectionFactory");
		Queue queue = (Queue) ctx.lookup("java:/queue/test");
		Connection connection = cf.createConnection();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		MessageProducer mp = session.createProducer(queue);
		int i = 0;
		while (true) {
			TextMessage message = session
					.createTextMessage("Hello Mdb Component Msg Nbr: " + i);
			System.out.println("message sent with the number : " + i);
			mp.send(message);
			i++;
		}
	}
}
