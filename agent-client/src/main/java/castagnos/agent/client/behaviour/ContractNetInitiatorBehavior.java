package castagnos.agent.client.behaviour;

import castagnos.agent.client.agent.AgentClient;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetInitiator;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by jerome on 03/12/2016.
 */
public class ContractNetInitiatorBehavior extends ContractNetInitiator {

    public AgentClient agent;

    public ContractNetInitiatorBehavior(Agent a, ACLMessage cfp) {
        super(a, cfp);
        agent = (AgentClient) a;
    }

    protected void handlePropose(ACLMessage propose, Vector v) {
        try {
            System.out.println("Agent "+propose.getSender().getName()+" Propose "+propose.getContentObject());
        } catch (UnreadableException e) {
            e.printStackTrace();
        }
    }
    protected void handleRefuse(ACLMessage refuse) {
        System.out.println("Agent "+refuse.getSender().getName()+" à refuser. ");
    }
    protected void handleInform(ACLMessage inform) {
        System.out.println("Agent "+inform.getSender().getName()+" à réussi.");
    }

    protected void handleAllResponses(Vector responses, Vector acceptances) {
        System.out.println("Analyse des réponses en cours...");
        if (responses.size() < agent.nResponders) {
            // Some responder didn't reply within the specified timeout
            System.out.println("Il manque " + (agent.nResponders - responses.size()) + " réponses.");
        }
        // Evaluate proposals.
        int bestProposal = -1;
        int valuefictive = 1;
        AID bestProposer = null;
        ACLMessage accept = null;
        Enumeration e = responses.elements();
        while (e.hasMoreElements()) {
            ACLMessage msg = (ACLMessage) e.nextElement();
            if (msg.getPerformative() == ACLMessage.PROPOSE) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                acceptances.addElement(reply);
                //Améliorer
                int proposal = valuefictive;
                if (proposal > bestProposal) {
                    bestProposal = proposal;
                    bestProposer = msg.getSender();
                    accept = reply;
                    valuefictive++;
                }
            }
        }
        // Accept the proposal of the best proposer
        if (accept != null) {
            System.out.println("Accepte la proposition  " + bestProposal + " de l'agent : " + bestProposer.getName());
            accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
        }
        agent.nResponders = -1;
        System.out.println("Fin de l'analyse...");
    }

}
