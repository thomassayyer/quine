package application.models;

import java.io.Serializable;

/**
 * Représente une entreprise partenaire.
 */
public class Partner extends Model implements Serializable {

    /**
     * Nom de l'entreprise partenaire
     */
    private String name;

    /**
     * Chemin d'accès au logo de l'entreprise partenaire
     */
    private String logoFilepath;

    /**
     * Crée une nouvelle entreprise partenaire.
     *
     * @param name         Nom de l'entreprise
     * @param logoFilepath Chemin d'accès au logo de l'entreprise
     */
    public Partner(String name, String logoFilepath) {
        this.name = name;
        this.logoFilepath = logoFilepath;
    }

    /**
     * Getter name
     * @return this.name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter logo file path
     * @return this.logoFilePath
     */
    public String getLogoFilepath() {
        return logoFilepath;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
