package repositories;

import application.models.Buyer;
import application.models.Model;
import application.models.Prize;
import application.repositories.CardRepository;
import application.repositories.PrizeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Effectue les tests relatifs au repository {@link PrizeRepository}.
 */
class PrizeRepositoryTest {

    /**
     * Chemin de base du répertoire de stockage des lots.
     */
    private static final String basePath = System.getProperty("user.dir") + "/storage/prizes";

    /**
     * Une instance de {@link PrizeRepository} à tester
     */
    private static PrizeRepository repository;

    /**
     * S'exécute avant l'exécution de l'ensemble des tests (prépare l'environnement de test).
     */
    @BeforeAll
    static void setUpBeforeAll() {
        repository = PrizeRepository.getInstance();

        // Création du répertoire storage/prizes
        File specificDir = new File(basePath);
        if (!specificDir.exists()) {
            if (!specificDir.mkdirs()) {
                System.out.println("[WARNING] Impossible de créer le répertoire : " + basePath);
            }
        }
    }

    /**
     * Effectue les tests relatifs à la méthode {@link PrizeRepository#getInstance()}.
     */
    @Test
    void getInstance() {
        PrizeRepository repository = PrizeRepository.getInstance();

        assertEquals(PrizeRepositoryTest.repository, repository);
    }

    /**
     * Effectue les tests relatifs à la méthode {@link PrizeRepository#won()}.
     */
    @Test
    void won() {
        List<Prize> won = null;
        Prize firstPrize = new Prize(9997, "Samsung Galaxy Tab 2 7.0");
        Prize secondPrize = new Prize(9998, "Four à micro-ondes");
        Prize thirdPrize = new Prize(9999, "Smartphone Huawei Honor 9");

        secondPrize.setWinner(new Buyer("John DOE", true));
        thirdPrize.setWinner(new Buyer("Jane DOE", false));

        // Stockage des lots
        try {
            ObjectOutputStream firstStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9997.object")));
            ObjectOutputStream secondStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9998.object")));
            ObjectOutputStream thirdStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9999.object")));
            firstStream.writeObject(firstPrize);
            secondStream.writeObject(secondPrize);
            thirdStream.writeObject(thirdPrize);
            firstStream.close();
            secondStream.close();
            thirdStream.close();
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Récupération des lots gagnants
        try {
            won = repository.won();
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

        // Transformation de la liste won en liste d'IDs de lots
        List<Integer> absentsIds = won.stream().map(Prize::getId).collect(Collectors.toList());

        assertEquals(2, won.size());
        assertTrue(absentsIds.contains(9998));
        assertTrue(absentsIds.contains(9999));
    }

    /**
     * Effectue les tests relatifs à la méthode {@link PrizeRepository#store(Model)}.
     */
    @Test
    void store() {
        Prize prize = new Prize(9999, "Smartphone Huawei Honor 9");
        Prize storedPrize = null;
        boolean isSuccess = false;

        isSuccess = repository.store(prize);

        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(new File(basePath + "/9999.object")))) {
            storedPrize = (Prize) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

        assertTrue(isSuccess);
        assertEquals(9999, storedPrize.getId());
    }

    /**
     * Effectue les tests relatifs à la méthode {@link PrizeRepository#destroy(int)}.
     */
    @Test
    void destroy() {
        File file = new File(basePath + "/9999.object");
        Prize card = new Prize(9999, "Four à micro-ondes");
        boolean isSuccess = false;

        // Stockage du carton
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(card);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Suppression du carton
        isSuccess = repository.destroy(9999);

        assertTrue(isSuccess);
        assertFalse(file.exists());
    }

    /**
     * Effectue les tests relatifs à la méthode {@link PrizeRepository#find(int)}.
     */
    @Test
    void find() {
        Prize prize = new Prize(9999, "Samsung Galaxy Tab 2");
        Prize actualPrize = null;

        // Stockage du lot
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9999.object")))) {
            stream.writeObject(prize);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Récupération du carton
        try {
            actualPrize = repository.find(9999);
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(prize.getId(), actualPrize.getId());
    }

    /**
     * Effectue les tests relatifs à la méthode {@link PrizeRepository#all()}.
     */
    @Test
    void all() {
        List<Prize> all = null;
        Prize firstPrize = new Prize(9997, "Samsung Galaxy tab 2 7.0");
        Prize secondPrize = new Prize(9998, "Smartphone Huawei Honor 9");
        Prize thirdPrize = new Prize(9999, "Four à micro-ondes");

        // Stockage des lots
        try {
            ObjectOutputStream firstStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9997.object")));
            ObjectOutputStream secondStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9998.object")));
            ObjectOutputStream thirdStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9999.object")));
            firstStream.writeObject(firstPrize);
            secondStream.writeObject(secondPrize);
            thirdStream.writeObject(thirdPrize);
            firstStream.close();
            secondStream.close();
            thirdStream.close();
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Récupération de l'ensemble des lots
        try {
            all = repository.all();
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

        // Transformation de la liste all en liste d'IDs de lots
        List<Integer> cardsIds = all.stream().map(Prize::getId).collect(Collectors.toList());

        assertEquals(3, all.size());
        assertTrue(cardsIds.contains(9997));
        assertTrue(cardsIds.contains(9998));
        assertTrue(cardsIds.contains(9999));
    }

    /**
     * Effectue les tests relatifs à la méthode {@link PrizeRepository#makeSpecificDir()}.
     */
    @Test
    void makeSpecificDir() {
        // Suppression du répertoire storage/prizes
        File prizesDir = new File(basePath);
        if (prizesDir.exists()) {
            if (!prizesDir.delete()) {
                System.out.println("[WARNING] Impossible de supprimer le dossier : " + basePath);
            }
        }
        boolean isSuccess = false;

        isSuccess = repository.makeSpecificDir();

        assertTrue(isSuccess);
        assertTrue(prizesDir.exists());
    }

    /**
     * S'exécute après l'exécution de chacun des tests.
     */
    @AfterEach
    void tearDownAfterEach() {
        // Suppression du contenu du répertoire storage/prize
        File[] cards = (new File(basePath)).listFiles();
        for (File f : cards != null ? cards : new File[0]) {
            if (f.isFile()) {
                if (!f.delete()) {
                    System.out.println("[WARNING] Impossible de supprimer le fichier : " + basePath + "/" + f.getName());
                }
            }
        }
    }
}