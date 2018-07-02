package application.repositories;

import application.models.Prize;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Récupère et retourne les lots gagnants.
     *
     * @return Lots gagnants
     *
     * @throws IOException            Lorsqu'un des fichiers n'est pas accessible.
     * @throws ClassNotFoundException Lorsqu'un lot n'a pas pu être instancié.
     */
    public List<Prize> won() throws IOException, ClassNotFoundException {
        File[] files = new File(basePath + "/" + specificDir()).listFiles();
        List<Prize> prizes = new ArrayList<>();

        // Ternaire nécessaire pour éviter l'exception java.lang.NullPointerException
        for (File file : files != null ? files : new File[0]) {
            if (file.isFile()) {
                Prize prize = find(file.getName());
                if (prize.isWon()) {
                    prizes.add(prize);
                }
            }
        }

        return prizes;
    }

}
