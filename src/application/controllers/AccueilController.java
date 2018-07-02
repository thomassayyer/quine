package application.controllers;

/**
 * Classe Accueil de tous les contr�leurs
 */
public abstract class AccueilController extends Controller {
	
	/**
	 * Instance Singleton du contr�leur
	 */
	private static AccueilController instance = null;

	/**
	 * Cr�e une nouvelle instance du controleur.
	 */
	private AccueilController() {
	}

	/**
	 * Retourne l'instance Singleton du contr�leur.
	 *
	 * @return L'instance Singleton du contr�leur
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
