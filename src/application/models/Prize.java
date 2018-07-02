package application.models;

/**
 * Représente un lot à gagner.
 */
public class Prize extends Model implements Storable {

    /**
     * Identifiant unique du lot.
     */
    private int id;

    /**
     * Libellé du lot
     */
    private String label;

    /**
     * Gagnant du lot
     */
    private Buyer winner;

    /**
     * Partenaire fournisseur du lot
     */
    private transient Partner partner;

    /**
     * Crée un nouveau lot (sans partenaire).
     *
     * @param id    ID du lot
     * @param label Libellé du lot
     */
    public Prize(int id, String label) {
        this.id = id;
        this.label = label;
    }

    /**
     * Crée un nouveau lot.
     *
     * @param id      ID du lot
     * @param label   Libellé du lot
     * @param partner Partenaire fournisseur du lot
     */
    public Prize(int id, String label, Partner partner) {
        this(id, label);
        this.partner = partner;
    }

    @Override
    public int getId() { return id; }

    /**
     * Détermine si le lot a été gagné.
     *
     * @return Vrai si le lot a été gagné; faux sinon
     */
    public boolean isWon() {
        return winner != null;
    }

    /**
     * Configure le gagnant du lot.
     *
     * @param winner Gagnant du lot
     */
    public void setWinner(Buyer winner) {
        this.winner = winner;
    }
}
