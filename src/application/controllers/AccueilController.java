package application.controllers;

/**
 * Classe Accueil de tous les contrôleurs
 */
public abstract class AccueilController extends Controller {
	
	/**
	 * Instance Singleton du contrôleur
	 */
	private static AccueilController instance = null;

	/**
	 * Crée une nouvelle instance du controleur.
	 */
	private AccueilController() {
	}

	/**
	 * Retourne l'instance Singleton du contrôleur.
	 *
	 * @return L'instance Singleton du contrôleur
	 */
	public static AccueilController getInstance() {

		if (instance == null) {
			instance = new AccueilController() {
			};
		}

		return instance;
	}

	/**
	 * Lancement du jeu de quine.
	 * 
	 * @return
	 *
	 */
	public void play() {

    }

	/**
	 * Ouvertue du menu de configuration.
	 * 
	 * @return
	 *
	 */
	public void config() {

	}
    
}
