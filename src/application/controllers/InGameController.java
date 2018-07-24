package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import application.models.Card;
import application.models.Partner;
import application.models.Prize;
import application.repositories.CardRepository;
import application.repositories.PrizeRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

/**
 * Contrôleur de la page "En jeu".
 */
public class InGameController extends Controller implements Initializable {

    @FXML
	private TitledPane winnersCardsPane;

    /**
     * Liste des cartons des joueurs absents
     */
	private List<Card> absentBuyerCard;

    /**
     * Liste des nombres
     */
	private LinkedList<Integer> numbers;

    /**
     * Liste des partenaires
     */
	private List<Partner> partners;

    /**
     * Liste des lots gagnés
     */
	private List<Prize> prizes;


	private CardRepository cardRepository;
	private PrizeRepository prizeRepository;

	@FXML
	private GridPane grid;

	/**
	 * Renseigne le numéro sorti dans la liste
	 *
	 * @param number
	 */
	private void chooseNumber(int number) {
		numbers.add(number);
		this.fillAbsentBuyerCard(number);

	}

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


	// Work in progress

	private String type;
	private static final String CARTON_PLEIN = "Carton Plein";
	private static final String LIGNE_SIMPLE = "Ligne Simple";

	private void changeType(String type) {
		switch (type) {
			case CARTON_PLEIN:
				this.type = CARTON_PLEIN;
				break;
			case LIGNE_SIMPLE:
				this.type = LIGNE_SIMPLE;
				break;
			default:
				// TODO : exception
				break;
		}
	}

	private void fillAbsentBuyerCard(int number) {
		for (Card card : absentBuyerCard) {
			card.fill(number);
		}
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	    // TODO : Générer la grille de nombres (l'ID de a grille est "gridPane")
    }

    // TODO: Pop-up Carton absent gagnant.
}
