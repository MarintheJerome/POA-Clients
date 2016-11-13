package castagnos.agent.client.behaviour;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Created by jerome on 13/11/2016.
 */
public class ReceiveBehaviour extends SimpleBehaviour {

    private final static MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

    public ReceiveBehaviour(Agent agent) {
        super(agent);
    }

    public void action() {
        while (true) {
            ACLMessage aclMessage =  myAgent.receive(mt);
            if (aclMessage != null) {
                try {
                    String message = aclMessage.getContent();
                    System.out.println(myAgent.getLocalName() + ": I receive message with content : " + message);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else {
                this.block();
            }
        }
    }

    public boolean done() {
        return false;
    }
}
