package application.controllers;

import java.util.List;

import application.models.Card;
import application.models.Partner;

/**
 * Contrôleur de la page de configuration.
 */
public class SettingsController extends Controller {

    /**
     * Action lors du clic sur l'onglet "Création d'un carton".
     */
	public void onCreateCard() {
        // TODO: Ouvrir la sous vue "createCard".
    }

    /**
     * Action lors du clic sur l'onglet "Ajout d'un carton".
     */
    public void onAddCard() {
        // TODO: Ouvrir la sous vue "addCard".
    }

    /**
     * Action lors du clic sur l'onglet "Gestion des partenaires".
     */
    public void onManagePartners() {
        // TODO: Ouvrir la sous vue "managePartners".
    }

	/**
	 * Action du bouton sauvegarde
     *
     * @param controller Contrôleur de la page "En jeu"
	 */
	public void onSave(InGameController controller) {
		// TODO: Configurer le contrôleur de la page "En jeu".
	}

}
