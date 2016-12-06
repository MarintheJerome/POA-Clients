package castagnos.agent.client.behaviour;

import java.io.IOException;

import castagnos.agent.client.agent.AgentClient;
import castagnos.agent.client.vue.VueClient;
import fr.miage.agents.api.message.Message;
import fr.miage.agents.api.message.recherche.ResultatRecherche;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetResponder;

/**
 * Created by jerome on 03/12/2016.
 */
public class ContractNetResponderBehaviour extends ContractNetResponder{
	
	VueClient vue = new VueClient();

    private AgentClient agentClient;

    public ContractNetResponderBehaviour(Agent a, MessageTemplate mt) {
        super(a, mt);
        this.agentClient = (AgentClient) a;
    }

    protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
        try {
            System.out.println("Agent "+myAgent.getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action --> "+cfp.getContentObject());
        } catch (UnreadableException e) {
            e.printStackTrace();
        }
        try {
            switch(((Message) cfp.getContentObject()).type){
                case DemandeEchange:
                    //TODO traiter la demande 

                    break;
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
        ACLMessage propose = cfp.createReply();
        propose.setPerformative(ACLMessage.PROPOSE);
        propose.setContent("Hi !");
        return propose;
    }
}
