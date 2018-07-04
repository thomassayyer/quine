package application.controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import application.models.Card;
import application.models.Partner;
import application.models.Prize;
import application.repositories.CardRepository;
import application.repositories.PrizeRepository;

/**
 * Contrôleur de la page "En jeu".
 */
public class InGameController extends Controller {
	
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
	 * Initialise le jeu
	 *
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void init() throws ClassNotFoundException, IOException {
		this.absentBuyerCard = cardRepository.absents();
		// this.partners = TODO
		this.prizes = prizeRepository.won();
	}

	/**
	 * Renseigne le num�ro sorti dans la liste
	 *
	 * @param choseNumber
	 */
	private void chooseNumber(int number) {
		numbers.add(number);
		this.fillAbsentBuyerCard(number);
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

	// TODO: Pop-up Carton absent gagnant.
}
