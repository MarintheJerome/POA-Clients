package castagnos.agent.modele;

import fr.miage.agents.api.model.Produit;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;

public class CellStyle extends ListCell<Produit> {
	@Override
    public void updateItem(Produit item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
        	Text s = new Text();
        	s.setText("IdProduit : " + item.idProduit + "\n" + 
        			"NomProduit : " + item.nomProduit + "\n" +
        			"Marque : " + item.marque + "\n" +
        			"Catégorie : " + item.idCategorie.nomCategorie);
            setGraphic(s);
        }
    }
}
