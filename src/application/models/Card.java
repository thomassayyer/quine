package application.models;

/**
 * Représente un carton.
 */
public class Card extends Model {

    /**
     * Identifiant unique du carton
     */
    private int id;

    /**
     * Grille du carton
     */
    private int[][] grid;

    /**
     * Acheteur du carton
     */
    private Buyer buyer;

    /**
     * Vendeur du carton
     */
    private Seller seller;

    /**
     * Crée un nouveau carton.
     *
     * @param id     Identifiant unique du carton à créer
     * @param grid   Grille du carton à créer
     * @param buyer  Acheteur du carton
     * @param seller Vendeur du carton
     */
    public Card(int id, int[][] grid, Buyer buyer, Seller seller) {
        this.id = id;
        this.grid = grid;
        this.buyer = buyer;
        this.seller = seller;
    }

    /**
     * Détermine si le carton est vide.
     *
     * @return Vrai si le carton est vide; faux sinon
     */
    public boolean isEmpty() {

        for (int[] row : grid) {
            for (int value : row) {
                if (value != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Vide le carton.
     */
    public void empty() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != 0) {
                    grid[i][j] = 0;
                }
            }
        }
    }

    /**
     * Récupère les coordonnées d'un nombre dans la grille du carton.
     *
     * @param  number  Nombre dont on veut récupérer les coordonnées
     * @return Coordonées du nombre :
     *         <ul>
     *             <li>Indice 0 : Abscisse</li>
     *             <li>Indice 1 : Ordonnée</li>
     *         <ul/>
     */
    public int[] find(int number) {

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == number) {
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }

    public void fill(int number) {
        // TODO: Réfléchir à la pertinence de cette méhtode.
    }

    public void unfill(int number) {
        // TODO: Réfléchir à la pertinence de cette méthode.
    }

}
