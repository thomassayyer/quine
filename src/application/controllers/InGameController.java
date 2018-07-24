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

/**
 * Contrôleur de la page "En jeu".
 */
public class InGameController extends Controller implements Initializable {

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
	
    // TODO: Réfléchir aux méthodes à implémenter.

	// Liste des cartons des joueurs absents
	private List<Card> absentBuyerCard;

	// Liste des nombres
	private LinkedList<Integer> numbers;

	// Liste des partenaires
	private List<Partner> partners;

	// Liste des partenaires
	private List<Prize> prizes;

	private CardRepository cardRepository;
	private PrizeRepository prizeRepository;

	/**
	 * Renseigne le numéro sorti dans la liste
	 *
	 * @param number numéro sorti
	 */
	private void chooseNumber(int number) {
		numbers.add(number);
		List<Card> wonCard = this.fillAbsentBuyerCard(number);
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
            //winnersCardsPane.
            //winnersCardsPane.setContent(node);
        }
    }

	/**
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


	// Work in progress

	private String type;
	private static final String CARTON_PLEIN = "Carton Plein";
	private static final String LIGNE_SIMPLE = "Ligne Simple";

	public void onChangeTypeToCartonPlein() {
		this.type = CARTON_PLEIN;
	}

	public void onChangeTypeToLignSimple() {
		this.type = LIGNE_SIMPLE;
	}


	private List<Card> fillAbsentBuyerCard(int number) {
	    List<Card> cards = new ArrayList<Card>();
		for (Card card : absentBuyerCard) {
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

		try{
			this.absentBuyerCard = cardRepository.absents();
		}catch (IOException | ClassNotFoundException e){
			e.printStackTrace();
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

    // TODO: Pop-up Carton absent gagnant.
}
