package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import application.models.Card;
import application.models.Partner;
import application.models.Prize;
import application.models.Storable;
import application.repositories.CardRepository;
import application.repositories.PrizeRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.swing.text.html.ImageView;

/**
 * Contrôleur de la page "En jeu".
 */
public class InGameController extends Controller implements Initializable, Storable {

	/**
	 * Panel des cartons gagnants
	 */
    @FXML
	private TitledPane winnersCardsPane;

    /**
	   * Grid contenant l'ensemble de nombre possible
     */
    @FXML
	private GridPane grid;

	/**
	 * Button option CartonPlein
	 */
	@FXML
	private Button cartonPlein;

	/**
	 * Button option LigneSimple
	 */
    @FXML
	private Button ligneSimple;

    /**
     * Image view pour les logos
     */
    @FXML
    private ImageView logoImageView;

    /**
     * Liste des cartons de joueurs absent
     */
	private List<Card> absentBuyerCards;

    /**
     * Cartons de la partie
     */
	private List<Card> cards;
  
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
     * Liste des prix
     */
	private List<Prize> prizes;

    /**
     * Liste des lots gagnés
     */
	private List<Prize> prizes;

  /**
     * CardRepository
     */
	private CardRepository cardRepository;

    /**
     * PrizeRepository
     */
	private PrizeRepository prizeRepository;

    /**
     * Crée un nouveau contrôleur pour la vue "InGame.fxml".
     *
     * @param cards    Cartons à jouer
     * @param partners Partenaires ayant laissé un lot
     */
	public InGameController(List<Card> cards, List<Partner> partners) {
        this.cards = cards;
        this.partners = partners;
        this.absentBuyerCards = new ArrayList<>();

        for (Card card : this.cards) {
            if (!card.getBuyer().isPresent()) {
                this.absentBuyerCards.add(card);
            }
        }
    }

	/**
	 * Renseigne le numéro sorti dans la liste
	 *
	 * @param number numéro sorti
	 */
	private void chooseNumber(int number) {
		List<Card> wonCard = this.fillAbsentBuyerCards(number);
        printWonCard(wonCard);
		Button button = this.getButtonByNum(number);
		// TODO : Modifier la couleur du bouton
	}


    /**
     * Affiche les cartons gagnants
     * @param wonCard
     */
	private void printWonCard(List<Card> wonCard){
	    Node node = new VBox();
        for (Card card : wonCard) {
            Node text = new Text("Le Carton n° " + card.toString() + " est gagnant pour " + this.type);
            // TODO : POP UP DU GAGNANT
            //winnersCardsPane.
            //winnersCardsPane.setContent(node);
        }
    }

	/*
	 * Getter d'un élément dans une grid en fonction de sa position
	 *
	 * @param number
	 *
	 * @return L'élément souhaité
	 */
	private Button getButtonByNum (int number) {
		int row = (number - (number%10)) / 10;
		int column = number%10;
		for (Node node : grid.getChildren()) {
			Integer rowIndex = grid.getRowIndex(node);
			Integer columnIndex = grid.getColumnIndex(node);
			if (rowIndex != null && columnIndex != null && rowIndex == row && columnIndex == column) {
				return (Button) node;
			}
		}
		return null;
	}

    /**
     * Type de partie : Carton plein ou ligne simple
     */
	private String type;

    /**
     * Variable global "Carton Plein"
     */
	private static final String CARTON_PLEIN = "Carton Plein";

    /**
     * Variable global "Ligne Simple"
     */
	private static final String LIGNE_SIMPLE = "Ligne Simple";

    /**
     * Button changement de partie en mode Carton Plein
     */
	public void onChangeTypeToCartonPlein() {
		this.type = CARTON_PLEIN;
	}

    /**
     * Button changement de partie en mode Ligne simple
     */
	public void onChangeTypeToLignSimple() {
		this.type = LIGNE_SIMPLE;
	}

    /**
     * Remplie les cartons des joueurs absents
     * @param number numéro tiré
     * @return Liste des cartons gagants
     */
	private List<Card> fillAbsentBuyerCards(int number) {
	    List<Card> cards = new ArrayList<Card>();
		for (Card card : absentBuyerCards) {
			card.fill(number);
			if(this.type == CARTON_PLEIN || card.cardDone()){
                cards.add(card);
            }
            if(this.type == LIGNE_SIMPLE ||card.lineDone()){
                cards.add(card);
            }
		}
		return cards;
	}

	/**
	 * Initialise la partie
	 * @param location
	 * @param resources
	 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // TODO: Affichage des logos.
        for (Partner partner : partners){
		    // Ajoute les images dans la image view
            logoImageView.append(null);

        }

		// Initialise la grid
		for (int column = 1; column < 10; column++) {
			for (int row = 0; row < 9; row++) {
				int number = 10 * row + column;
				Button button = new Button(String.valueOf(number));
				button.setOnAction(event -> {
					this.chooseNumber(number);
				});
				grid.add(button, column, row);
			}
		}
    }

    @Override
    public int getId() {
        return 1;
    }
}
