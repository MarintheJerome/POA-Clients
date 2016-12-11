package castagnos.agent.client.agent;

import castagnos.agent.client.behaviour.ReceiveBehaviour;
import fr.miage.agents.api.message.interClients.DemandeEchange;
import fr.miage.agents.api.message.recherche.Rechercher;
import fr.miage.agents.api.message.relationclientsupermarche.Achat;
import fr.miage.agents.api.model.Produit;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.util.leap.Iterator;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
    public Map<String,Integer> marketsDistance = new HashMap<String,Integer>();

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
    	this.distance();
        Object[] args = getArguments();
        if(args != null){
            SELF = (String) args[0];
        }
        // Ajout dans le registre
        registerService(SELF);

        // Ajout du behavior de reception de message
        addBehaviour(new ReceiveBehaviour(this));

        System.out.println("L'agent "+SELF+" est opérationnel.");
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
                            info += sd.getName()+"\n";
                            othersAgent.add(sd.getName());
                        }
                    }
                }
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

    private void distance(){
    	this.markets = this.getOthers(TYPEM);
    	for(String s : markets){
    		int dist = ThreadLocalRandom.current().nextInt(1, 21);
    		this.marketsDistance.put(s, dist);
    	}
    }
    
    public void sendProduit(Produit produit, String receiver, int quantite, String type){
        DFAgentDescription dfd = new DFAgentDescription();
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));
        try {
            msg.setContentObject(new DemandeEchange(produit, quantite));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.send(msg);
    }
    
    private String plusProche(){
        String receiver = null;
        Integer dist = null;
        for(String s : this.markets){
        	if(receiver == null){
        		receiver = s;
        	}
        	else if(this.marketsDistance.get(s)<dist){
        		receiver = s;
        	}
        	dist = this.marketsDistance.get(receiver);
        }
        return receiver;
    }    
    
    public void sendRecherche(Rechercher recherche){
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        
        msg.addReceiver(new AID(this.plusProche(), AID.ISLOCALNAME));
        try {
            msg.setContentObject(recherche);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.send(msg);
    }
    
    public void sendAchat(){
    	Map<Long,Integer> listeAchat = new HashMap<Long,Integer>();
    	for(Produit p : this.panier){
    		Integer  qte = listeAchat.remove(p.idProduit);
    		if(qte == null){
    			listeAchat.put(p.idProduit, 1);
    		}
    		else{
    			listeAchat.put(p.idProduit, qte++);
    		}
    	}
    	Achat achat = new Achat();
    	achat.listeCourses = listeAchat;
        DFAgentDescription dfd = new DFAgentDescription();
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID(this.plusProche(), AID.ISLOCALNAME));
        try {
            msg.setContentObject(achat);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.send(msg);
    }
}
