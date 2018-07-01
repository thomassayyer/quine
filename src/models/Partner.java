package models;

/**
 * Représente une entreprise partenaire.
 */
public class Partner extends Model {

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

}
