package castagnos.agent.client.agent;

import castagnos.agent.client.behaviour.ReceiveBehaviour;
import fr.miage.agents.api.message.interClients.DemandeEchange;
import fr.miage.agents.api.model.Produit;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;

import java.io.IOException;

import java.util.ArrayList;

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

        Object[] args = getArguments();
        if(args != null){
            SELF = (String) args[0];
        }
        // Ajout dans le registre
        registerService(SELF);

        System.out.println("L'agent : "+SELF+" est opérationnel.");

        // Ajout du behavior de reception de message
        addBehaviour(new ReceiveBehaviour(this));

        /*//Ouverture du client Main
        if(SELF.equals("MainClient")){
            VueClient.launchMain(this);
        }*/
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

    public void sendProduit(Produit produit, String receiver, String type){
        DFAgentDescription dfd = new DFAgentDescription();
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));
        try {
            msg.setContentObject(new DemandeEchange(produit, 5));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.send(msg);
    }
}
