package application.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contrôleur de la page de configuration.
 */
public class SettingsController extends Controller implements Initializable {

    /**
     * Onglet "Création d'un carton"
     */
    @FXML
    private Tab createCardTab;

    /**
     * Onglet "Ajout d'un carton sans joueur"
     */
    @FXML
    private Tab addCardTab;

    /**
     * Onglet "Partenaires de la partie"
     */
    @FXML
    private Tab managePartnersTab;

	/**
	 * Action du bouton sauvegarde
     *
     * @param controller Contrôleur de la page "En jeu"
	 */
	public void onSave(InGameController controller) {
		// TODO: Configurer le contrôleur de la page "En jeu".
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createCardTab.setContent(FXMLLoader.load(getClass().getResource("../../ui/views/CreateCard.fxml")));
            addCardTab.setContent(FXMLLoader.load(getClass().getResource("../../ui/views/AddCard.fxml")));
            managePartnersTab.setContent(FXMLLoader.load(getClass().getResource("../../ui/views/ManagePartners.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
