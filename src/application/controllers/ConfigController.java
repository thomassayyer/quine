package application.controllers;

import java.util.List;

import application.models.Buyer;
import application.models.Card;
import application.models.Partner;
import application.models.Seller;

public class ConfigController extends Controller {

	// Liste des cartons des joueurs absents
	private List<Card> absentBuyerCard;

	// Liste des partenaires
	private List<Partner> partners;

	/**
	 * Instance Singleton du contrôleur
	 */
	private static ConfigController instance = null;

	/**
	 * Crée une nouvelle instance du controleur.
	 */
	private ConfigController() {
	}

	/**
	 * Retourne l'instance Singleton du contrôleur.
	 *
	 * @return L'instance Singleton du contrôleur
	 */
	public static ConfigController getInstance() {

		if (instance == null) {
			instance = new ConfigController() {
			};
		}

		return instance;
	}

	/**
	 * Action des boutons de panels
	 * 
	 * @param panelName
	 *            Nom du panel
	 */
	private void changePanel(String panelName) {
		switch (panelName) {
		case "createCard":
			// TODO
			break;
		case "addCard":
			// TODO
			break;
		case "gestionPartner":
			// TODO
			break;
		default:
			break;
		}
	}

	/**
	 * Action du bouton sauvegarde
	 * 
	 */
	private void save() {
		// TODO
	}

	// Gestion d'un carton

	private void addCard(int[][] grid, String buyerName, String sellerName, Integer id) {
		Buyer buyer = new Buyer(buyerName, false);
		Seller seller = new Seller(sellerName);
		Card card = new Card(id, grid, buyer, seller);
		this.absentBuyerCard.add(card);
	}
}

