package castagnos.agent.client.agent;

import castagnos.agent.client.behaviour.ReceiveBehaviour;
import castagnos.agent.client.vue.VueClient;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

/**
 * Created by jerome on 13/11/2016.
 */
public class AgentClient extends Agent {

    protected void setup() {
        addBehaviour(new ReceiveBehaviour(this));
        try {
            if(this.getLocalName().equals("receiver")){
               new VueClient().launcher(this);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
