package application.repositories;

import application.models.Card;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
     * Crée une nouvelle instance du repository.
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
}
