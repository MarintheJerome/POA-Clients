package castagnos.agent.client;

import castagnos.agent.client.agent.AgentClient;
import castagnos.agent.client.agent.MockSupermarket;
import castagnos.agent.client.agent.MockSupermarket1;
import castagnos.agent.client.agent.MockSupermarket2;
import fr.miage.agents.api.model.Produit;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by jerome on 14/11/2016.
 */
public class main {

    private static Scanner sc;
    private static ArrayList<AgentClient> listeAgents;
    private static ArrayList<MockSupermarket> listeSupermarche;
    private static ArrayList<String> listeNomAgents;
    private static ArrayList<Produit> listeProduits;
    private static ArrayList<String> listeNomProduits;

    public static void main(String[] args){
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        ProfileImpl p =new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.GUI, "true");
        ContainerController cc =runtime.createMainContainer(p);
        listeSupermarche = new ArrayList<MockSupermarket>();
		try {
			AgentController supermarket1;
			MockSupermarket1 sm1 = new MockSupermarket1();
	        sm1.SELF = "SuperU";
			supermarket1 = cc.acceptNewAgent("SuperU", sm1);
			listeSupermarche.add(sm1);
			supermarket1.start();
	        
	        MockSupermarket2 sm2 = new MockSupermarket2();
	        sm2.SELF = "Auchan";
	        AgentController supermarket2 = cc.acceptNewAgent("Supermarket2", sm2);
	        listeSupermarche.add(sm2);
	        supermarket2.start();
		} catch (StaleProxyException e1) {
			e1.printStackTrace();
		}
        
        
        listeAgents = new ArrayList<AgentClient>();
        listeNomAgents = new ArrayList<String>();
        listeProduits = new ArrayList<Produit>();
        listeNomProduits = new ArrayList<String>();

        int reponseUtilisateur = 0;
        while (reponseUtilisateur != 5){
            affichageOptions();
            reponseUtilisateur = intInput(1, 5);
            switch(reponseUtilisateur){
                case 1:
                    try {
                        createAgent(cc);
                    } catch (StaleProxyException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    affichageAgents();
                    break;
                case 3:
                    affichageProduits();
                    break;
                case 4:
                    demandeEchange();
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }

    private static void affichageOptions(){
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n1 - Créer un agent");
        System.out.println("2 - Voir les agents présents");
        System.out.println("3 - Voir la liste des produits");
        System.out.println("4 - Envoyer une demande d'échange");
        System.out.println("5 - Quitter");
    }

    public static String stringInput(){
        sc = new Scanner(System.in);
        String reponse = "";
        while (!sc.hasNextLine()) {
            System.out.println("Veuillez saisir une chaine de caractères.");
            sc.next();
        }
        reponse = sc.nextLine();
        return reponse;
    }

    public static int intInput(int min, int max){
        sc = new Scanner(System.in);
        int valeurUtilisateur;
        do{
            while (!sc.hasNextInt()) {
                System.out.println("Veuillez saisir SEULEMENT un chiffre entre "+min+" et "+max+".");
                sc.next();
            }
            valeurUtilisateur = sc.nextInt();
            if(valeurUtilisateur < min || valeurUtilisateur > max){
                System.out.println("Valeur incorrecte. Saisissez un chiffre entre "+min+" et "+max+".");
            }
        } while (valeurUtilisateur < min || valeurUtilisateur > max);
        return valeurUtilisateur;
    }

    private static void createAgent(ContainerController cc) throws StaleProxyException {
        System.out.print("Nom de l'agent à créer : ");
        String nomAgent = stringInput();
        AgentClient ac = new AgentClient();
        ac.SELF = nomAgent;
        AgentController ninja = cc.acceptNewAgent(nomAgent, ac);
        ninja.start();
        listeAgents.add(ac);
        listeNomAgents.add(nomAgent);
    }

    private static boolean existeAgent(int nombre){
        return listeAgents.size() > nombre;
    }

    private static void affichageAgents() {
        if(existeAgent(0)) {
            System.out.print("Type de l'agent (Client ou Supermarché) : ");
            String type = stringInput();
            ArrayList<String> agents = listeAgents.get(0).getOthers(type);
            for(String agent : agents){
                System.out.println(agent);
            }
            // On affiche quand même l'agent qui nous sert à faire l'appel
            if(type.equals("Client")) {
                System.out.println(listeAgents.get(0).SELF);
            }
        }
        else{
            System.out.println("Pas d'agent présent.");
        }
    }

    private static void affichageProduits() {
    	Set<Produit> set = new HashSet<Produit>();
    	for(MockSupermarket mock : listeSupermarche){
    		for(Produit produit : mock.stock){
    			set.add(produit);
    		}
    	}
    	for(Produit produit : set){
    		System.out.println(produit.nomProduit+" - "+produit.prixProduit+" €");
    	}
    }

    private static void demandeEchange() {
        if(existeAgent(1)) { // au moins 2 agents
            System.out.print("Quel agent initiera l'échange ? ");
            String agentEnvoyeur = stringInput();
            if(listeNomAgents.contains(agentEnvoyeur)){ // bon nom agent envoyeur
                System.out.print("Quel agent recevra l'échange ? ");
                String agentReceveur = stringInput();
                if(listeNomAgents.contains(agentReceveur) && !agentReceveur.equals(agentEnvoyeur)){ // bon nom agent receveur
                    System.out.print("Quel produit demandé ? ");
                    String nomProduit = stringInput();
                    if(listeNomProduits.contains(nomProduit)){ // le produit existe
                        System.out.print("Quantité voulue : ");
                        int quantiteVoulue = intInput(1, 50);
                        getAgentFromName(agentEnvoyeur).sendProduit(getProductFromName(nomProduit), agentReceveur, quantiteVoulue, "Client");
                    }
                    else{ // le produit existe pas
                        System.out.println("Le produit n'existe pas.");
                    }
                }
                else{ // mauvais nom agent receveur
                    System.out.println("Mauvais nom d'agent.");
                }
            }else{ // mauvais nom agent envoyeur
                System.out.println("L'agent "+agentEnvoyeur+" n'existe pas");
            }
        }else{ // il y a pas d'agent
            System.out.println("Il faut au moins 2 agents pour un échange.");
        }
    }

    private static AgentClient getAgentFromName(String name){
        for(AgentClient agent : listeAgents){
            if(agent.getLocalName().equals(name))
                return agent;
        }
        return null;
    }

    private static Produit getProductFromName(String name){
        for(Produit produit : listeProduits){
            if(produit.nomProduit.equals(name))
                return produit;
        }
        return null;
    }
}
