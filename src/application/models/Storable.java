package application.models;

import java.io.Serializable;

/**
 * Définit les méthodes publiques nécessaires au stockage des modèles
 */
public interface Storable extends Serializable {

    /**
     * Retourne l'identifiant unique de l'objet à stocker.
     *
     * @return Identifiant unique de l'objet à stocker
     */
    int getId();
}
