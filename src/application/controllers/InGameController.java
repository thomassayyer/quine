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
	 * Instance Singleton du contrôleur
	 */
	private static InGameController instance = null;

	/**
	 * Crée une nouvelle instance du controleur.
	 */
	private InGameController() {
	}

	/**
	 * Retourne l'instance Singleton du contrôleur.
	 *
	 * @return L'instance Singleton du contrôleur
	 */
	public static InGameController getInstance() {

		if (instance == null) {
			instance = new InGameController() {
			};
		}

		return instance;
	}
}
