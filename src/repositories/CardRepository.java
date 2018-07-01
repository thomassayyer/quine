package repositories;

import models.Card;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Permet la récupération de cartons depuis le stockage interne.
 */
public class CardRepository extends Repository<Card> {

    /**
     * Instance Singleton du repository
     */
    private static CardRepository instance = null;

    /**
     * Crée und nouvelle instance du repository.
     */
    private CardRepository() { }

    /**
     * Retourne l'instance Singleton du repository.
     *
     * @return L'instance Singleton du repository
     */
    public static CardRepository getInstance() {

        if (instance == null) {
            instance = new CardRepository();
        }

        return instance;
    }

    @Override
    protected String specificDir() {
        return "cards";
    }

    /**
     * Récupère et retourne un carton en fonction de son ID.
     *
     * @param id ID du carton à récupérer
     *
     * @return Le carton correspondant
     *
     * @throws IOException            Lorsque le fichier est inaccessible
     * @throws ClassNotFoundException Lorsque le carton n'est pas instanciable
     */
    public Card find(int id) throws IOException, ClassNotFoundException {
        return find(id + ".object");
    }

    /**
     * Récupère et retourne les cartons des joueurs absents.
     *
     * @return Cartons des joueurs absents
     *
     * @throws IOException            Lorsqu'un des fichiers n'est pas accessible.
     * @throws ClassNotFoundException Lorsqu'un carton n'a pas pu être instancié.
     */
    public List<Card> absents() throws IOException, ClassNotFoundException {
        File[] files = new File(basePath + "/" + specificDir()).listFiles();
        List<Card> cards = new ArrayList<>();

        // Ternaire nécessaire pour éviter l'exception java.lang.NullPointerException
        for (File file : files != null ? files : new File[0]) {
            if (file.isFile()) {
                Card card = find(file.getName());
                if (!card.getBuyer().isPresent()) {
                    cards.add(card);
                }
            }
        }

        return cards;
    }

}
