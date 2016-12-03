package castagnos.agent.client.behaviour;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

/**
 * Created by jerome on 03/12/2016.
 */
public class ContractNetResponderBehaviour extends ContractNetResponder{

    public ContractNetResponderBehaviour(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
        System.out.println("Agent "+myAgent.getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
        ACLMessage propose = cfp.createReply();
        propose.setPerformative(ACLMessage.PROPOSE);
        propose.setContent("Hi !");
        return propose;
    }
}