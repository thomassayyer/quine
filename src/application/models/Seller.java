package application.models;

import java.io.Serializable;

/**
 * Représente le vendeur d'un carton.
 */
public class Seller extends Model implements Serializable {

    /**
     * Nom du vendeur
     */
    private String name;

    /**
     * Premier ID de carton dans la plage des cartons à vendre.
     */
    private int firstCard;

    /**
     * Dernier ID de carton danss la plage des cartons à vendre.
     */
    private int lastCard;

    /**
     * Crée un nouveau vendeur (sans spécifier de plage de cartons).
     *
     * @param name Nom du vendeur
     */
    public Seller(String name) {
        this.name = name;
    }

    /**
     * Crée un nouveau vendeur.
     *
     * @param name      Nom du vendeur
     * @param firstCard Premier carton dans la plage des cartons à vendre.
     * @param lastCard  Dernier carton dans la plage des cartons à vendre.
     */
    public Seller(String name, int firstCard, int lastCard) {
        this.name = name;
        this.firstCard = firstCard;
        this.lastCard = lastCard;
    }

}
