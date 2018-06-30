package application.repositories;

import application.models.Prize;

public class PrizeRepository extends Repository<Prize> {

    /**
     * Instance Singleton du repository
     */
    private static PrizeRepository instance = null;

    /**
     * Crée und nouvelle instance du repository.
     */
    private PrizeRepository() { }

    /**
     * Retourne l'instance Singleton du repository.
     *
     * @return L'instance Singleton du repository
     */
    public static PrizeRepository getInstance() {

        if (instance == null) {
            instance = new PrizeRepository();
        }

        return instance;
    }

    @Override
    protected String specificDir() {
        return "prizes";
    }
}
