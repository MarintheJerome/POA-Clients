package castagnos.agent.client;

import castagnos.agent.client.agent.AgentClient;
import fr.miage.agents.api.model.Produit;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by jerome on 14/11/2016.
 */
public class main {

    private static Scanner sc;
    private static ArrayList<AgentClient> listeAgents;

    public static void main(String[] args){
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        ProfileImpl p =new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.GUI, "true");
        ContainerController cc =runtime.createMainContainer(p);

        listeAgents = new ArrayList<AgentClient>();

        int reponseUtilisateur = 0;
        while (reponseUtilisateur != 3){
            affichageOptions();
            reponseUtilisateur = intInput(1, 3);
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
                case 6:
                    System.exit(0);
            }
        }
    }

    private static void affichageOptions(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n1 - Créer un agent");
        System.out.println("2 - Voir les agents présents");
        System.out.println("3 - Quitter");
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
    }

    private static void affichageAgents() {
        if(listeAgents.size() > 0) {
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
}
