package application.models;

import java.util.Stack;

/**
 * Représente un carton.
 */
public class Card extends Model implements Storable {

    /**
     * Identifiant unique du carton.
     */
    private int id;

    /**
     * Grille du carton
     */
    private int[][] grid;

    /**
     * Grille complétée
     */
    private int[][] filledGrid;

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
        this(id, grid);
        this.buyer = buyer;
        this.seller = seller;
    }

    /**
     * Crée un nouveau carton.
     *
     * @param id   Identifiant unique du carton à créer
     * @param grid Grille du carton à créer
     */
    public Card(int id, int[][] grid) {
        this.id = id;
        this.grid = grid;
        this.filledGrid = new int[3][5];

        for (int i = 0; i < filledGrid.length; i++) {
            for (int j = 0; j < filledGrid[i].length; j++) {
                filledGrid[i][j] = 0;
            }
        }
    }

    @Override
    public int getId() { return id; }

    /**
     * Retourne l'acheteur du carton.
     *
     * @return L'acheteur du carton
     */
    public Buyer getBuyer() {
        return buyer;
    }

    /**
     * Retourne la grille.
     *
     * @return La grille
     */
    public int[][] getGrid() { return grid; }

    /**
     * Configure la valeur de la propriété {@link #buyer}.
     *
     * @param buyer Acheteur du carton
     */
    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    /**
     * Configure la valeur de la propriété {@link #seller}.
     *
     * @param seller Vendeur du carton
     */
    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    /**
     * Retourne la valeur de la propriété {@link #seller}.
     *
     * @return Vendeur du carton
     */
    public Seller getSeller() {
        return seller;
    }

    /**
     * Détermine si le carton est vide.
     *
     * @return Vrai si le carton est vide; faux sinon
     */
    public boolean isEmpty() {

        for (int[] row : filledGrid) {
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
        for (int i = 0; i < filledGrid.length; i++) {
            for (int j = 0; j < filledGrid[i].length; j++) {
                if (filledGrid[i][j] != 0) {
                    filledGrid[i][j] = 0;
                }
            }
        }
    }

    /**
     * Récupère les coordonnées d'un nombre dans la grille du carton.
     *
     * @param number Nombre dont on veut récupérer les coordonnées
     *
     * @return Coordonées du nombre (peut être à plusieurs endroits). Pour chaque ligne :
     *         <ul>
     *             <li>[0] => Abscisse</li>
     *             <li>[1] => Ordonnée</li>
     *         <ul/>
     */
    private Stack<int[]> find(int number) {

        Stack<int[]> coordinates = new Stack<>();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == number) {
                    coordinates.add(new int[]{i, j});
                }
            }
        }

        return coordinates;
    }

    /**
     * Ajoute 1 dans la / les cellule(s) correspondante(s) au numéro dans la grille remplie.
     *
     * @param number Numéro tiré
     */
    public void fill(int number) {
        find(number).forEach(coordinate -> filledGrid[coordinate[0]][coordinate[1]] = 1);
    }

    /**
     * Ajoute 0 dans la / les cellule(s) correspondante(s) au numéro dans la grille remplie.
     *
     * @param number Numéro tiré
     */
    public void unfill(int number) {
        find(number).forEach(coordinate -> filledGrid[coordinate[0]][coordinate[1]] = 0);
    }

    /**
     * Renvoie true si une ligne est rempli
     * @return
     */
    public boolean lineDone(){
        for (int[] columns : filledGrid) {
            int count = 0;
            for (int value : columns) {
                if (value == 1) {
                    count++;
                }
            }
            if(count == 5){
                return true;
            }
        }

        return false;
    }

    /**
     * Renvoie true si le carton est rempli
     * @return
     */
    public boolean cardDone(){
        int count = 0;
        for (int row = 0; row < filledGrid.length; row++) {
            for (int column = 0; column < filledGrid[row].length; column++) {
                if (filledGrid[row][column] == 1) {
                    count ++;
                }
            }
        }
        if(count == 15){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public void beforeSerialization() {
        this.seller = null;
        this.buyer = null;
    }

}
