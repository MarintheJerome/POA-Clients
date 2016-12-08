package castagnos.agent.client.FIPA.behaviourFIPA;


import castagnos.agent.client.agent.AgentClient;
import java.io.IOException;
import castagnos.agent.client.vue.VueClient;
import fr.miage.agents.api.message.Message;
import fr.miage.agents.api.message.recherche.ResultatRecherche;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetResponder;
import java.util.Date;

/**
 * Created by jerome on 03/12/2016.
 */
public class ContractNetResponderBehaviour extends ContractNetResponder{
	
	VueClient vue = new VueClient();

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
        propose.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
        try {
            switch(((Message) cfp.getContentObject()).type){
                case DemandeEchange:
                    propose.setPerformative(ACLMessage.PROPOSE);
                    propose.setContentObject("Hi !");
                    return propose;
                case ResultatRecherche:
                	ResultatRecherche res = (ResultatRecherche) cfp.getContentObject();
                	vue.afficheResRecherche(res.produitList);
                	break;
                default:
                    break;
            }
        } catch (UnreadableException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
