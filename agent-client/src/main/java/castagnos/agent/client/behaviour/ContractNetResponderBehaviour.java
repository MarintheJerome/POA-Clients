package castagnos.agent.client.behaviour;

import castagnos.agent.client.agent.AgentClient;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetResponder;

import java.io.IOException;
import java.util.Date;

/**
 * Created by jerome on 03/12/2016.
 */
public class ContractNetResponderBehaviour extends ContractNetResponder{

    public AgentClient agent;

    public ContractNetResponderBehaviour(Agent a, MessageTemplate mt) {
        super(a, mt);
        agent = (AgentClient) a;
    }

    protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
        try {
            System.out.println("Agent "+myAgent.getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action --> "+cfp.getContentObject());
        } catch (UnreadableException e) {
            e.printStackTrace();
        }
        ACLMessage propose = cfp.createReply();
        propose.setReplyByDate(new Date(System.currentTimeMillis() + 1000));
        if(true){
            propose.setPerformative(ACLMessage.PROPOSE);
            try {
                propose.setContentObject("Hi !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            propose.setPerformative(ACLMessage.REFUSE);
            try {
                propose.setContentObject("Niet!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return propose;
    }

    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
        System.out.println("Agent "+agent.getLocalName()+": proposition acceptée.");
        if (true) {
            System.out.println("Agent "+agent.getLocalName()+": Action successfully performed");
            ACLMessage inform = accept.createReply();
            inform.setPerformative(ACLMessage.INFORM);
            return inform;
        }else {
            System.out.println("Agent "+agent.getLocalName()+": Action execution failed");
            throw new FailureException("unexpected-error");
        }
    }

    protected void handleRejectProposal(ACLMessage reject) {
        System.out.println("Agent "+agent.getLocalName()+": Proposal rejected");
    }
}
