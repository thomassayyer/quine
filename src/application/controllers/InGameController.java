package application.controllers;

import java.util.LinkedList;
import java.util.List;

import application.models.Card;
import application.models.Partner;

public class InGameController extends Controller {

	// Liste des cartons des joueurs absents
	private List<Card> absentBuyerCard;

	// Liste des nombres
	private LinkedList<Integer> numbers;

	// Liste des partenaires
	private List<Partner> partners;

	/**
	 * Instance Singleton du contr�leur
	 */
	private static InGameController instance = null;

	/**
	 * Cr�e une nouvelle instance du controleur.
	 */
	private InGameController() {
	}

	/**
	 * Retourne l'instance Singleton du contr�leur.
	 *
	 * @return L'instance Singleton du contr�leur
	 */
	public static InGameController getInstance() {

		if (instance == null) {
			instance = new InGameController() {
			};
		}

		return instance;
	}
}
