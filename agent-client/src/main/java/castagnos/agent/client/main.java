package castagnos.agent.client;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 * Created by jerome on 14/11/2016.
 */
public class main {
    public static void main(String[] args){
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        ProfileImpl p =new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.GUI, "true");
        ContainerController cc =runtime.createMainContainer(p);
        AgentController receiver;
        AgentController sender;
        AgentController printer;
        try {
            Object valeurs[] = {"MainClient"};
            receiver = cc.createNewAgent("MainClient", "castagnos.agent.client.agent.AgentClient", valeurs);
            receiver.start();
            Object valeurs2[] = {"sender"};
            sender = cc.createNewAgent("sender", "castagnos.agent.client.agent.AgentClient", valeurs2);
            sender.start();
            Object valeurs3[] = {"printer"};
            printer = cc.createNewAgent("printer", "castagnos.agent.client.agent.AgentClient", valeurs3);
            printer.start();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
