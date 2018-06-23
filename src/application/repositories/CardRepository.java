package application.repositories;

import application.models.Card;

import java.util.List;

/**
 * Permet la récupération de cartons depuis le stockage interne.
 */
public class CardRepository extends Repository {

    /**
     * Instance Singleton du repository
     */
    private static CardRepository instance = null;

    /**
     * Retourne l'instance Singleton du repository.
     *
     * @return L'instance Singleton du repository
     */
    public static CardRepository getInstance() {

        if (instance == null) {
            instance = new CardRepository();
        }

        return instance;
    }

    /**
     * Enregistre un nouveau carton dans le stockage interne.
     *
     * @param card Carton à enregistrer
     */
    public void store(Card card) {
        //
    }

    /**
     * Récupère et retourne l'ensemble des cartons enregistrés.
     *
     * @return Tous les cartons
     */
    public List<Card> all() {
        return null;
    }

    /**
     * Retourne un carton enregistré en fonction de son ID.
     *
     * @param  id  ID du carton à récupérer
     * @return Carton correspondant
     */
    public Card find(int id) {
        return null;
    }

    /**
     * Récupère et retourne les cartons des personnes absentes.
     *
     * @return Cartons des personnes absentes
     */
    public List<Card> absents() {
        return null;
    }

}
