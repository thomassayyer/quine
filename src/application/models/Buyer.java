package application.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente l'acheteur d'un carton.
 */
public class Buyer extends Model implements Serializable {

    /**
     * Nom de l'acheteur
     */
    private String name;

    /**
     * Indique si l'acheteur du carton est présent le jour du jeu.
     */
    private boolean isPresent;

    /**
     * L'ensemble des prix gagnés par le joueur (ou "acheteur")
     */
    private List<Prize> prizes;

    /**
     * Crée un nouvel acheteur.
     *
     * @param name      Nom de l'acheteur
     * @param isPresent Vrai si l'acheteur est présent; faux sinon
     */
    public Buyer(String name, boolean isPresent) {
        this.name = name;
        this.isPresent = isPresent;
        this.prizes = new ArrayList<>();
    }

    /**
     * Détermine si l'acheteur est présent le jour du jeu.
     *
     * @return Vrai si le joueur est présent; faux sinon
     */
    public boolean isPresent() {
        return isPresent;
    }

    /**
     * Retourne les prix gagnés par le joueur.
     *
     * @return Les prix gagnés par le joueur
     */
    public List<Prize> getPrizes() { return prizes; }

    /**
     * Ajoute un prix à la liste des prix gagnés.
     *
     * @param prize Prix à ajouter
     *
     * @return Vrai si le prix a été ajouté; faux sinon
     */
    public boolean addPrize(Prize prize) {
        return prizes.add(prize);
    }

    /**
     * Supprime un prix de la liste des prix gagnés.
     *
     * @param prize Prix à supprimer
     *
     * @return Vrai si le prix a été supprimé; faux sinon
     */
    public boolean removePrize(Prize prize) {
        return prizes.remove(prize);
    }

}
