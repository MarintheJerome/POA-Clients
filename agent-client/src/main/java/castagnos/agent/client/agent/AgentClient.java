package castagnos.agent.client.agent;

import castagnos.agent.client.behaviour.ReceiveBehaviour;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

/**
 * Created by jerome on 13/11/2016.
 */
public class AgentClient extends Agent{

    protected void setup() {
        addBehaviour(new ReceiveBehaviour(this));

        Object[] args = getArguments();
        String message = (String) args[0];
        AID aid = new AID();
        System.out.println("Hello. My name is " + this.getLocalName());

        // pour pas s'envoyer à lui même
        if(!this.getLocalName().equals("receiver")){
            DFAgentDescription dfd = new DFAgentDescription();
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.addReceiver(new AID("receiver", AID.ISLOCALNAME));
            msg.setContent("Salut, j'envoie un message");
            this.send(msg);
        }
    }
}
