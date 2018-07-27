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
     * Crée un nouvel acheteur.
     *
     * @param name      Nom de l'acheteur
     * @param isPresent Vrai si l'acheteur est présent; faux sinon
     */
    public Buyer(String name, boolean isPresent) {
        this.name = name;
        this.isPresent = isPresent;
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
     * Retourne le nom de l'acheteur.
     *
     * @return Le nom de l'acheteur
     */
    public String getName() {
        return name;
    }

}
