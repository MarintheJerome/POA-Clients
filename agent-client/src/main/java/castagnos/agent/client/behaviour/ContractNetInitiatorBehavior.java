package castagnos.agent.client.behaviour;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Vector;

/**
 * Created by jerome on 03/12/2016.
 */
public class ContractNetInitiatorBehavior extends ContractNetInitiator {

    public ContractNetInitiatorBehavior(Agent a, ACLMessage cfp) {
        super(a, cfp);
    }

    protected void handlePropose(ACLMessage propose, Vector v) {
        System.out.println("Agent "+propose.getSender().getName()+" proposed "+propose.getContent());
    }
}
