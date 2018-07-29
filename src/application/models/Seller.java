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
     * Crée un nouveau vendeur.
     *
     * @param name Nom du vendeur
     */
    public Seller(String name) {
        this.name = name;
    }

    /**
     * Retourne le nom du vendeur.
     *
     * @return Le nom du vendeur
     */
    public String getName() {
    	return this.name;
    }

    @Override
    public boolean equals(Object seller) {
        if (!(seller instanceof Seller)) {
            return false;
        }

        return name.equals(((Seller)seller).getName());
    }

}
