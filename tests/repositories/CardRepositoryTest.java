package repositories;

import application.models.Buyer;
import application.models.Card;
import application.models.Model;
import application.models.Seller;
import application.repositories.CardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Effectue les tests relatifs au repositry {@link CardRepository}.
 */
class CardRepositoryTest {

    private static final String basePath = System.getProperty("user.dir") + "/storage/cards";

    /**
     * Une instance de {@link CardRepository} à tester
     */
    private static CardRepository repository;

    /**
     * Une grille vide à utiliser dans les tests.
     */
    private static int[][] emptyGrid;

    /**
     * S'exécute avant l'exécution de l'ensemble des tests (prépare l'environnement de test).
     */
    @BeforeAll
    static void setUpBeforeAll() {
        repository = CardRepository.getInstance();
        emptyGrid = new int[][]{{0,0,0},{0,0,0},{0,0,0}};

        // Création du répertoire storage/cards
        File specificDir = new File(basePath);
        if (!specificDir.exists()) {
            if (!specificDir.mkdirs()) {
                System.out.println("[WARNING] Impossible de créer le répertoire : " + basePath);
            }
        }
    }

    /**
     * Effectue les tests relatifs à la méthode {@link CardRepository#getInstance()}.
     */
    @Test
    void getInstance() {
        CardRepository repository = CardRepository.getInstance();

        assertEquals(CardRepositoryTest.repository, repository);
    }

    /**
     * Effectue les tests relatifs à la méthode {@link CardRepository#find(String)}.
     */
    @Test
    void findByFilename() {
        Card card = new Card(9999, emptyGrid, new Buyer("John DOE", true), new Seller("Jane DOE"));
        Card actualCard = null;

        // Stockage du carton
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9999.object")))) {
            stream.writeObject(card);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Récupération du carton
        try {
            actualCard = repository.find("9999.object");
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(card.getId(), actualCard.getId());
    }

    /**
     * Effectue les tests relatifs à la méthode {@link CardRepository#find(int)}.
     */
    @Test
    void findById() {
        Card card = new Card(9999, emptyGrid, new Buyer("John DOE", true), new Seller("Jane DOE"));
        Card actualCard = null;

        // Stockage du carton
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9999.object")))) {
            stream.writeObject(card);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Récupération du carton
        try {
            actualCard = repository.find(9999);
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(card.getId(), actualCard.getId());
    }

    /**
     * Effectue les tests relatifs à la méthode {@link CardRepository#absents()}.
     */
    @Test
    void absents() {
        List<Card> absents = null;
        Card firstCard = new Card(9997, emptyGrid, new Buyer("John DOE", true), new Seller("Jane DOE"));
        Card secondCard = new Card(9998, emptyGrid, new Buyer("John DOE", false), new Seller("Jane DOE"));
        Card thirdCard = new Card(9999, emptyGrid, new Buyer("John DOE", false), new Seller("Jane DOE"));

        // Stockage des cartons
        try {
            ObjectOutputStream firstStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9997.object")));
            ObjectOutputStream secondStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9998.object")));
            ObjectOutputStream thirdStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9999.object")));
            firstStream.writeObject(firstCard);
            secondStream.writeObject(secondCard);
            thirdStream.writeObject(thirdCard);
            firstStream.close();
            secondStream.close();
            thirdStream.close();
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Récupération des cartons des absents
        try {
            absents = repository.absents();
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

        // Transformation de la liste absents en liste d'IDs de cartons
        List<Integer> absentsIds = absents.stream().map(Card::getId).collect(Collectors.toList());

        assertEquals(2, absents.size());
        assertTrue(absentsIds.contains(9998));
        assertTrue(absentsIds.contains(9999));
    }

    /**
     * Effectue les tests relatifs à la méthode {@link CardRepository#store(Model, String)}.
     */
    @Test
    void store() {
        Card card = new Card(9999, emptyGrid, new Buyer("John DOE", true), new Seller("Jane DOE"));
        Card storedCard = null;

        repository.store(card, "9999.object");

        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(new File(basePath + "/9999.object")))) {
            storedCard = (Card) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

        assertEquals(9999, storedCard.getId());
    }

    /**
     * Effectue les tests relatifs à la méthode {@link CardRepository#destroy(String)}.
     */
    @Test
    void destroyByFilename() {
        Card card = new Card(9999, emptyGrid, new Buyer("John DOE", true), new Seller("Jane DOE"));
        boolean isSuccess = false;

        // Stockage du carton
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9999.object")))) {
            stream.writeObject(card);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Suppression du carton
        isSuccess = repository.destroy("9999.object");

        assertTrue(isSuccess);
    }

    /**
     * Effectue les tests relatifs à la méthode {@link CardRepository#destroy(int)}.
     */
    @Test
    void destroyById() {
        Card card = new Card(9999, emptyGrid, new Buyer("John DOE", true), new Seller("Jane DOE"));
        boolean isSuccess = false;

        // Stockage du carton
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9999.object")))) {
            stream.writeObject(card);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Suppression du carton
        isSuccess = repository.destroy(9999);

        assertTrue(isSuccess);
    }

    /**
     * Effectue les tests relatifs à la méthode {@link CardRepository#all()}.
     */
    @Test
    void all() {
        List<Card> all = null;
        Card firstCard = new Card(9997, emptyGrid, new Buyer("John DOE", true), new Seller("Jane DOE"));
        Card secondCard = new Card(9998, emptyGrid, new Buyer("John DOE", false), new Seller("Jane DOE"));
        Card thirdCard = new Card(9999, emptyGrid, new Buyer("John DOE", false), new Seller("Jane DOE"));

        // Stockage des cartons
        try {
            ObjectOutputStream firstStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9997.object")));
            ObjectOutputStream secondStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9998.object")));
            ObjectOutputStream thirdStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9999.object")));
            firstStream.writeObject(firstCard);
            secondStream.writeObject(secondCard);
            thirdStream.writeObject(thirdCard);
            firstStream.close();
            secondStream.close();
            thirdStream.close();
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // Récupération de l'ensemble des cartons
        try {
            all = repository.all();
        } catch (IOException | ClassNotFoundException e) {
            fail(e.getMessage());
        }

        // Transformation de la liste all en liste d'IDs de cartons
        List<Integer> cardsIds = all.stream().map(Card::getId).collect(Collectors.toList());

        assertEquals(3, all.size());
        assertTrue(cardsIds.contains(9997));
        assertTrue(cardsIds.contains(9998));
        assertTrue(cardsIds.contains(9999));
    }

    /**
     * Effectue les tests relatifs à la méthode {@link CardRepository#makeSpecificDir()}.
     */
    @Test
    void makeSpecificDir() {
        // Suppression du répertoire storage/cards
        File cardsDir = new File(basePath);
        if (cardsDir.exists()) {
            if (!cardsDir.delete()) {
                System.out.println("[WARNING] Impossible de supprimer le dossier : " + basePath);
            }
        }

        repository.makeSpecificDir();

        assertTrue((new File(basePath)).exists());
    }

    /**
     * S'exécute après l'exécution de chacun des tests.
     */
    @AfterEach
    void tearDownAfterEach() {
        // Suppression du contenu du répertoire storage/cards
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