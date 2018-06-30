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

    @Override
    protected String specificDirectory() {
        return "cards";
    }

    /**
     * Récupère et retourne l'ensemble des cartons enregistrés.
     *
     * @return Tous les cartons
     */
    public List<Card> all() {
        // TODO: Récupérer tous les cartons enregistrés.
        return null;
    }

    /**
     * Retourne un carton enregistré en fonction de son ID.
     *
     * @param  id  ID du carton à récupérer
     * @return Carton correspondant
     */
    public Card find(int id) {
        // TODO: Récupérer un carton en fonction de son ID.
        return null;
    }

    /**
     * Récupère et retourne les cartons des joueurs absents.
     *
     * @return Cartons des joueurs absents
     */
    public List<Card> absents() {
        // TODO: Récupérer les cartons des joueurs absents.
        return null;
    }

}
