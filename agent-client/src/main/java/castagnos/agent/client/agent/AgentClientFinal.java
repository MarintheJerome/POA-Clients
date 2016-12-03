package castagnos.agent.client.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;
import jade.proto.ContractNetResponder;
import jade.util.leap.Iterator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 * Created by Yhugo on 01/12/2016.
 */
public class AgentClientFinal extends Agent {

    /**
     * Autre clients dans le réseau d'agent
     */
    private ArrayList<String> others = new ArrayList<String>();
    /**
     * Supermarché que l'on peut voir sur le réseau d'agent
     */
    private ArrayList<String> markets = new ArrayList<String>();

    /**
     * Messages
     */
    private ACLMessage message;

    /*
        Quelques attributs utiles.
     */
    private final String TYPEC = "Client";
    private final String TYPEM = "Supermarché";
    private String SELF = "Supermarché";

    protected void setup() {
        // Ajout dans le registre
        Object[] args = getArguments();
        if (args == null) System.exit(-1);
        if (args.length != 1) System.exit(-2);
        SELF = (String) args[0];
        registerService(SELF);
        System.out.println("Hello my name is "+SELF);

        if(SELF.equals("sender")) sending("Hello my friends !");

        addBehaviour(new ContractNetInitiator(this, message) {
            protected void handlePropose(ACLMessage propose, Vector v) {
                System.out.println("Agent "+propose.getSender().getName()+" proposed "+propose.getContent());
            }
            //TODO
            // Voir model
        });

        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                MessageTemplate.MatchPerformative(ACLMessage.CFP) );

        addBehaviour(new ContractNetResponder(this, template) {
            protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
                System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
                ACLMessage propose = cfp.createReply();
                propose.setPerformative(ACLMessage.PROPOSE);
                propose.setContent("Hi !");
                return propose;
            }

            //TODO
            // Voir model
        });
    }


    /**
     * Méthode de retour des agents
     */
    private ArrayList<String> getOthers(String type) {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(type);
        dfd.addServices(sd);
        ArrayList<String> othersAgent = new ArrayList<String>();
        String info = "";
        try {
            for (DFAgentDescription desc : DFService.search(this, dfd)) {
                if(!desc.getName().getLocalName().equals(SELF)){
                    Iterator iter = desc.getAllServices();
                    while (iter.hasNext()) {
                        sd = (ServiceDescription) iter.next();
                        if(!sd.getName().equals(SELF)){
                            info += sd.getName()+" ";
                            othersAgent.add(sd.getName());
                        }
                    }
                }
            }
            if(!info.equals("")){
                System.out.println(SELF+" has found "+type+" :");
                System.out.println(info);
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        return othersAgent;
    }

    /**
     * Méthode d'ajout de l'agent dans le registre d'agent.
     */
    private void registerService(String name) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(this.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(TYPEC);
        sd.setName(name);
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode d'envoie de message.
     */
    private void sending(String content){
        // MAJ des agents détéctés.
        others = getOthers(TYPEC);
        markets = getOthers(TYPEM);
        message = new ACLMessage(ACLMessage.CFP);
        for (int i = 0; i < others.size(); ++i) {
            message.addReceiver(new AID(others.get(i), AID.ISLOCALNAME));
        }
        message.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
        message.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
        message.setContent(content);
    }
}
