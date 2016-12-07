package castagnos.agent.client.agent;

import castagnos.agent.client.behaviour.ContractNetInitiatorBehavior;
import castagnos.agent.client.behaviour.ContractNetResponderBehaviour;
import fr.miage.agents.api.model.Produit;
import castagnos.agent.client.vue.VueClient;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yhugo on 01/12/2016.
 */
public class AgentClient extends Agent {

    /**
     * Autre clients dans le réseau d'agent
     */
    public ArrayList<String> others = new ArrayList<String>();

    /**
     * Supermarché que l'on peut voir sur le réseau d'agent
     */
    private ArrayList<String> markets = new ArrayList<String>();

    public ArrayList<Produit> panier = new ArrayList<Produit>();

    public int nResponders = -1;

    /**
     * Messages
     */
    private ACLMessage message;

    /**
     * Quelques attributs utiles.
     */
    public final static String TYPEC = "Client";
    public final static String TYPEM = "Supermarché";
    public String SELF = "";

    protected void setup() {
        // Ajout dans le registre
        Object[] args = getArguments();
        if (args == null) System.exit(-1);
        if (args.length != 1) System.exit(-2);
        SELF = (String) args[0];
        registerService(SELF);
        System.out.println("L'agent : "+SELF+" est opérationnel.");


        if(SELF.equals("sender")){
            try {
                sending("Hello my friends !", TYPEC);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Ouverture du client Main
        if(SELF.equals("MainClient")){
        //    VueClient.launchMain(this);
        }

        // Ajouts des comportements de l'agent
        addBehaviour(new ContractNetInitiatorBehavior(this, message));

        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                MessageTemplate.MatchPerformative(ACLMessage.CFP) );

        addBehaviour(new ContractNetResponderBehaviour(this, template));

        //Ouverture du client Main
        if(SELF.equals("MainClient")){
            VueClient.launchMain(this);
        }
    }


    /**
     * Méthode de retour des agents
     */
    public ArrayList<String> getOthers(String type) {
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
                System.out.print(SELF+" a trouvé les agents "+type+" : ");
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

    public void sending(Serializable content, String type) throws IOException {
        // MAJ des agents détéctés.
        others = getOthers(TYPEC);
        markets = getOthers(TYPEM);
        if(type.equals(TYPEC) || type.equals(TYPEM)){
            message = new ACLMessage(ACLMessage.CFP);
            ArrayList<String> used = null;
            if(type.equals(TYPEC)) used = others;
            if(type.equals(TYPEM)) used = markets;
            for (int i = 0; i < used.size(); ++i) {
                message.addReceiver(new AID(used.get(i), AID.ISLOCALNAME));
            }
            nResponders = used.size();
            message.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
            message.setReplyByDate(new Date(System.currentTimeMillis() + 1000));
            message.setContentObject(content);
        }else{
            System.out.println("Agent "+getName()+" a tenté de lancer un message foireux.");
        }
    }

    public void sending(Serializable content, String type, String receiver) throws IOException {
        // MAJ des agents détéctés.
        others = getOthers(TYPEC);
        markets = getOthers(TYPEM);
        if(type.equals(TYPEC) || type.equals(TYPEM)){
            message = new ACLMessage(ACLMessage.CFP);
            ArrayList<String> used = null;
            if(type.equals(TYPEC)) used = others;
            if(type.equals(TYPEM)) used = markets;
            message.addReceiver(new AID(receiver, AID.ISLOCALNAME));
            nResponders = used.size();
            message.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
            message.setReplyByDate(new Date(System.currentTimeMillis() + 1000));
            message.setContentObject(content);
        }else{
            System.out.println("Agent "+getName()+" a tenté de lancer un message foireux.");
        }
    }
}
