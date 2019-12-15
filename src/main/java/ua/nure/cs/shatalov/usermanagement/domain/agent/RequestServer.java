package ua.nure.cs.shatalov.usermanagement.domain.agent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import org.hsqldb.lib.Iterator;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.cs.shatalov.usermanagement.domain.User;
import ua.nure.cs.shatalov.usermanagement.domain.db.DaoFactory;
import ua.nure.cs.shatalov.usermanagement.domain.db.DatabaseException;

public class RequestServer extends CyclicBehaviour {

	@Override
	public void action() {
		// TODO Auto-generated method stub
		ACLMessage message = myAgent.receive();
		if (message != null) {
			if (message.getPerformative() == ACLMessage.REQUEST) {
				myAgent.send(createReply(message));
			} else {
				Collection users = parseMessage(message);
				((SearchAgent) myAgent).showUsers(users);
			}
		} else {
			block();
		}
	}

	private Collection parseMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	private ACLMessage createReply(ACLMessage message) {
		// TODO Auto-generated method stub
		ACLMessage reply = message.createReply();
		
		String content = message.getContent();
		StringTokenizer tokenizer = new StringTokenizer(content, ",");
		if (tokenizer.countTokens() == 2) {
			String firstName = tokenizer.nextToken();
			String lastName = tokenizer.nextToken();
			Collection users = null;
			try {
				users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
			} catch (DatabaseException e) {
				e.printStackTrace();
				users = new ArrayList(0);
			}
			
			StringBuffer buffer = new StringBuffer();
			for (Iterator it = users.iterator(); it.hasNext();) {
				User user = (User) it.next();
				buffer.append(user.getId()).append(",");
				buffer.append(user.getFirstName()).append(",");
				buffer.append(user.getLastName()).append(";");
			}
			reply.setContent(buffer.toString());
		}
		return null;
	}

}
