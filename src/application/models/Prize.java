package application.models;

/**
 * Représente un lot à gagner.
 */
public class Prize extends Model {

    /**
     * Libellé du lot
     */
    private String label;

    /**
     * Gagnant du lot
     */
    private Buyer winner;

    /**
     * Crée un nouveau lot.
     *
     * @param label Libellé du lot
     */
    public Prize(String label) {
        this.label = label;
    }

}
