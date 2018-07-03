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
     * Crée un nouveau vendeur (sans spécifier de plage de cartons).
     *
     * @param name Nom du vendeur
     */
    public Seller(String name) {
        this.name = name;
    }

}
