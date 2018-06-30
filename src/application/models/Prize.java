package application.models;

import java.io.Serializable;

/**
 * Représente un lot à gagner.
 */
public class Prize extends Model implements Serializable {

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
     * Crée un nouveau lot.
     *
     * @param label   Libellé du lot
     * @param partner Partenaire fournisseur du lot
     */
    public Prize(String label, Partner partner) {
        this.label = label;
        this.partner = partner;
    }

}
