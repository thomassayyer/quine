package application.repositories;

import application.controllers.InGameController;

/**
 * Permet la récupération de parties de jeu depuis le stockage interne.
 */
public class GameRepository extends Repository<InGameController> {

    /**
     * Instance Singleton du repository
     */
    private static GameRepository instance = null;

    /**
     * Crée une nouvelle instance du repository.
     */
    private GameRepository() { }

    /**
     * Retourne l'instance Singleton du repository.
     *
     * @return L'instance Singleton du repository
     */
    public static GameRepository getInstance() {

        if (instance == null) {
            instance = new GameRepository();
        }

        return instance;
    }

    @Override
    protected String specificDir() {
        return "games";
    }
}
