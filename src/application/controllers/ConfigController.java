package application.controllers;

import java.util.List;

import application.models.Card;
import application.models.Partner;

public class ConfigController extends Controller {

	// Liste des cartons des joueurs absents
	private List<Card> absentBuyerCard;

	// Liste des partenaires
	private List<Partner> partners;

	/**
	 * Instance Singleton du contr�leur
	 */
	private static ConfigController instance = null;

	/**
	 * Cr�e une nouvelle instance du controleur.
	 */
	private ConfigController() {
	}

	/**
	 * Retourne l'instance Singleton du contr�leur.
	 *
	 * @return L'instance Singleton du contr�leur
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
	private void sauvegarde() {
		// TODO
	}
}
