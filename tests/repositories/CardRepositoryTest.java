package repositories;

import application.models.Buyer;
import application.models.Card;
import application.models.Seller;
import application.repositories.CardRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

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
    }

    /**
     * Effectue les tests relatifs à la méthode CardRepository.getInstance().
     */
    @Test
    void getInstance() {
        CardRepository repository = CardRepository.getInstance();

        assertEquals(CardRepositoryTest.repository, repository);
    }

    /**
     * Effectue les tests relatifs à la méthode CardRepository.find().
     */
    @Test
    void find() {
        Card card = new Card(9999, emptyGrid, new Buyer("John DOE", true), new Seller("Jane DOE"));
        File file = new File(basePath + "/9999.object");
        Card actualCard = null;

        // Stockage du carton
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
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
     * Effectue les tests relatifs à la méthode CardRepository.absents().
     */
    @Test
    void absents() {
        List<Card> absents = null;
        Card firstCard = new Card(9997, emptyGrid, new Buyer("John DOE", true), new Seller("Jane DOE"));
        Card secondCard = new Card(9998, emptyGrid, new Buyer("John DOE", false), new Seller("Jane DOE"));
        Card thirdCard = new Card(9999, emptyGrid, new Buyer("John DOE", true), new Seller("Jane DOE"));

        // Stockage des cartons
        try {
            ObjectOutputStream firstStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9997.object")));
            ObjectOutputStream secondStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9998.object")));
            ObjectOutputStream thirdStream = new ObjectOutputStream(new FileOutputStream(new File(basePath + "/9999.object")))
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

        assertFalse(absents.contains(firstCard));
        assertTrue(absents.contains(secondCard));
        assertTrue(absents.contains(thirdCard));
    }

    @Test
    void store() {
    }

    @Test
    void destroy() {
    }

    @Test
    void find1() {
    }

    @Test
    void all() {
    }

    @Test
    void makeSpecificDir() {
    }
}