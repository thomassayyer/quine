package application.controllers;

import application.models.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.*;

/**
 * Contrôleur de la page "En jeu".
 */
public class InGameController extends Controller implements Initializable, Storable {

    /**
     * Panel des cartons gagnants
     */
    @FXML
    private VBox winnersCardsPane;

    /**
     * Grid contenant l'ensemble de nombre possible
     */
    @FXML
    private GridPane grid;

    /**
     * Button option CartonPlein
     */
    @FXML
    private Button cartonPlein;

    /**
     * Button option LigneSimple
     */
    @FXML
    private Button ligneSimple;

    /**
     * Image view pour les logos
     */
    @FXML
    private VBox logoVBox;

    /**
     * Liste des cartons de joueurs absent
     */
    private List<Card> absentBuyerCards;

    /**
     * Cartons de la partie
     */
    private List<Card> cards;

    /**
     * Lots en jeu
     */
    private List<Prize> prizes;

    /**
     * Lots gagnés
     */
    private List<Prize> wonPrizes;

    /**
     * Liste des nombres
     */
    private LinkedList<Integer> numbers;

    /**
     * Liste des partenaires
     */
    private List<Partner> partners;

    /**
     * Type de partie : Carton plein ou ligne simple
     */
    private String type = LIGNE_SIMPLE;

    /**
     * Variable global "Carton Plein"
     */
    private static final String CARTON_PLEIN = "Carton Plein";

    /**
     * Variable global "Ligne Simple"
     */
    private static final String LIGNE_SIMPLE = "Ligne Simple";

    /**
     * Contient l'ensemble des cartons gagnés.
     */
    private List<VBox> wonContainers;

    /**
     * Indique s'il faut jouer les cartons des absents automatiquement.
     */
    private boolean playForAbsents;

    /**
     * Crée un nouveau contrôleur pour la vue "InGame.fxml".
     *
     * @param cards    Cartons à jouer
     * @param partners Partenaires ayant laissé un lot
     * @param prizes   Lots à gagner
     */
    public InGameController(List<Card> cards, List<Partner> partners, List<Prize> prizes) {
        this.cards = cards;
        this.partners = partners;
        this.prizes = prizes;
        this.absentBuyerCards = new ArrayList<>();
        this.numbers = new LinkedList<>();
        this.wonPrizes = new ArrayList<>();
        this.wonContainers = new ArrayList<>();

        for (Card card : this.cards) {
            if (!card.getBuyer().isPresent()) {
                this.absentBuyerCards.add(card);
            }
        }
    }

    /**
     * Configure la valeur de la propriété {@link #playForAbsents}.
     *
     * @param playForAbsents Indique s'il faut jouer automatiquement les cartons des absents.
     */
    public void setPlayForAbsents(boolean playForAbsents) {
        this.playForAbsents = playForAbsents;
    }

    /**
     * Renseigne le numéro sorti dans la liste
     *
     * @param number numéro sorti
     */
    private void chooseNumber(int number) {
        Button button = this.getButtonByNum(number);
        if (numbers.contains(number)) {
            removeNumber(number);
        } else {
            for (int i : numbers) {
                Button btn = getButtonByNum(i);
                if (btn != null) {
                    btn.setStyle("-fx-background-color: #ff0000; ");
                }
            }
            if (button != null) {
                button.setStyle("-fx-background-color: #00ff00; ");
            }
            numbers.add(number);
        }

        List<Card> wonCard = this.fillAbsentBuyerCards(number);
        printWonCard(wonCard);
    }


    /**
     * Affiche les cartons gagnants
     * @param wonCard
     */
    private void printWonCard(List<Card> wonCard){
        for (Card card : wonCard) {
            boolean alreadyWon = false;
            for (VBox vb : wonContainers) {
                for (Node n : vb.getChildren()) {
                    if (n instanceof TextField && !n.isVisible()) {
                        int id = Integer.parseInt(((TextField)n).getText());
                        if (id == card.getId()) {
                            alreadyWon = true;
                        }
                    }
                }
            }
            if (alreadyWon) {
                continue;
            }
            VBox container = new VBox();
            container.setSpacing(10);
            container.setAlignment(Pos.CENTER);
            Node text = new Text("Le Carton n° " + card.getId() + " est gagnant pour " + this.type);
            TextField cardIdTextField = new TextField(String.valueOf(card.getId()));
            cardIdTextField.setVisible(false);
            Label label = new Label("Lot gagné :");
            ChoiceBox<Prize> prizeChoiceBox = new ChoiceBox<>();
            TitledPane wonPane = new TitledPane();
            wonPane.setText("Lots gagnés");
            HBox wonHBox = new HBox();
            wonHBox.setSpacing(20);
            wonPane.setContent(wonHBox);
            Button addPrizeBtn = new Button("Ajouter");
            addPrizeBtn.setOnAction(event -> {
                onAddPrizeBtnAction();
            });
            container.getChildren().addAll(text, label, prizeChoiceBox, addPrizeBtn, wonPane, cardIdTextField);
            wonContainers.add(container);
            winnersCardsPane.getChildren().add(container);
            reloadPrizeChoiceBoxes();
        }
    }

    /**
     * Getter d'un élément dans une grid en fonction de sa position
     *
     * @param number
     *
     * @return L'élément souhaité
     */
    private Button getButtonByNum (int number) {
        for (Node n : grid.getChildren()) {
            if (n instanceof Button) {
                if (Integer.parseInt(((Button)n).getText()) == number) {
                    return (Button)n;
                }
            }
        }
        return null;
    }

    /**
     * Button changement de partie en mode Carton Plein
     */
    public void onChangeTypeToCartonPlein() {
        this.type = CARTON_PLEIN;
        cartonPlein.setText("[X] Carton plein");
        ligneSimple.setText("Ligne simple");
        clear();
    }

    /**
     * Button changement de partie en mode Ligne simple
     */
    public void onChangeTypeToLignSimple() {
        this.type = LIGNE_SIMPLE;
        ligneSimple.setText("[X] Ligne simple");
        cartonPlein.setText("Carton plein");
    }

    /**
     * Remplie les cartons des joueurs absents
     * @param number numéro tiré
     * @return Liste des cartons gagants
     */
    private List<Card> fillAbsentBuyerCards(int number) {
        if (!this.playForAbsents) {
            return new ArrayList<>();
        }

        List<Card> cards = new ArrayList<>();
        for (Card card : absentBuyerCards) {
            card.fill(number);
            if(this.type.equals(CARTON_PLEIN) && card.cardDone()){
                cards.add(card);
            }
            if(this.type.equals(LIGNE_SIMPLE) && card.lineDone()){
                cards.add(card);
            }
        }
        return cards;
    }

    /**
     * Vide un numéro des cartons des joueurs absents
     * @param number numéro tiré
     * @return Liste des cartons dont le numéro a été supprimé
     */
    private List<Card> unfillAbsentBuyerCards(int number) {
        if (!this.playForAbsents) {
            return new ArrayList<>();
        }

        List<Card> cards = new ArrayList<>();
        for (Card card : absentBuyerCards) {
            card.unfill(number);
            if (this.type.equals(CARTON_PLEIN) && !card.cardDone()) {
                cards.add(card);
            }
            if (this.type.equals(LIGNE_SIMPLE) && !card.lineDone()) {
                cards.add(card);
            }
        }
        return cards;
    }

    /**
     * Initialise la partie
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (Partner partner : partners){
            // Ajoute les images dans la image view
            Image logo = new Image(partner.getLogoFilepath());
            ImageView logoView = new ImageView(logo);
            logoView.setFitWidth(150);
            logoView.setFitHeight(150);
            logoVBox.getChildren().addAll(logoView);
        }

        // Initialise la grid
        for (int column = 1; column < 11; column++) {
            for (int row = 0; row < 9; row++) {
                int number = 10 * row + column;
                Button button = new Button(String.valueOf(number));
                button.setOnAction(event -> {
                    this.chooseNumber(number);
                });
                grid.add(button, column, row);
            }
        }
    }

    @Override
    public int getId() {
        return 1;
    }

    /**
     * Action lors du clic sur le bouton d'ajout d'un lot.
     */
    private void onAddPrizeBtnAction() {
        for (VBox vb : wonContainers) {
            ChoiceBox<Prize> choiceBox = null;
            Buyer cardBuyer = null;
            HBox hbox = null;
            for (Node n : vb.getChildren()) {
                if (n instanceof TextField && !n.isVisible()) {
                    for (Card c : cards) {
                        if (c.getId() == Integer.parseInt(((TextField)n).getText())) {
                            cardBuyer = c.getBuyer();
                        }
                    }
                }
                if (n instanceof ChoiceBox) {
                    choiceBox = (ChoiceBox<Prize>)n;
                }
                if (n instanceof TitledPane) {
                    hbox = (HBox) ((TitledPane)n).getContent();
                }
            }

            Prize wonPrize = null;
            if (choiceBox != null) {
                wonPrize = choiceBox.getValue();
                wonPrize.setWinner(cardBuyer);
            }
            wonPrizes.add(wonPrize);
            if (hbox != null && wonPrize != null) {
                hbox.getChildren().add(new Label(String.valueOf(wonPrize.getId())));
            }
            if (choiceBox != null) {
                choiceBox.getItems().remove(choiceBox.getValue());
            }
        }
    }

    /**
     * Recharge les menus déroulants contenant les lots à gagner.
     */
    private void reloadPrizeChoiceBoxes() {
        for (VBox vb : wonContainers) {
            for (Node n : vb.getChildren()) {
                if (n instanceof ChoiceBox) {
                    ChoiceBox<Prize> prizeChoiceBox = (ChoiceBox<Prize>)n;
                    prizeChoiceBox.getItems().clear();
                    for (Prize p : prizes) {
                        if (!wonPrizes.contains(p)) {
                            prizeChoiceBox.getItems().add(p);
                        }
                    }
                }
            }
        }
    }

    /**
     * Supprime le dernier numéro sorti.
     */
    public void removeLastNumber() {
        removeNumber(numbers.getLast());
    }

    /**
     * Supprime un numéro et le remet en jeu.
     *
     * @param number
     */
    private void removeNumber(int number) {
        Button btn = getButtonByNum(number);
        if (btn != null) {
            getButtonByNum(number).setStyle("");
        }
        numbers.remove((Object)number);
        if (!numbers.isEmpty()) {
            Button lastBtn = getButtonByNum(numbers.getLast());
            if (lastBtn != null) {
                lastBtn.setStyle("-fx-background-color: #00ff00; ");
            }
        }
        List<Card> cards = unfillAbsentBuyerCards(number);
        for (Card c : cards) {
            List<VBox> containers = new ArrayList<>(wonContainers);
            for (VBox vb : containers) {
                for (Node n : vb.getChildren()) {
                    if (n instanceof TextField && !n.isVisible()) {
                        if (Integer.parseInt(((TextField)n).getText()) == c.getId()) {
                            vb.getChildren().clear();
                            wonContainers.remove(vb);
                            break;
                        }
                    }
                }
            }
            List<Prize> prizes = new ArrayList<>(wonPrizes);
            for (Prize p : prizes) {
                if (p.getWinner().getName().equals(c.getBuyer().getName())) {
                    wonPrizes.remove(p);
                }
            }
        }
    }

    /**
     * Remet la partie à 0.
     */
    public void clear() {
        List<Integer> numbers = (List<Integer>) this.numbers.clone();
        for (int num : numbers) {
            removeNumber(num);
        }
    }

    private void writeInPDF(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        PdfPCell sellerColumn = new PdfPCell(new Phrase("Seller"));
        PdfPCell cardNumber = new PdfPCell(new Phrase("Card number"));
        sellerColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
        cardNumber.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(sellerColumn);
        table.addCell(cardNumber);
        table.setHeaderRows(1);
        Map<Seller, Stack<Integer>> sellersCards = new HashMap<>();
        cards.sort(Comparator.comparingInt(Card::getId));
        for (Card card : cards) {
            boolean isSellerPresent = false;
            for (Map.Entry<Seller, Stack<Integer>> entry : sellersCards.entrySet()) {
                if (entry.getKey().equals(card.getSeller())) {
                    entry.getValue().add(card.getId());
                    isSellerPresent = true;
                    break;
                }
            }
            if (!isSellerPresent) {
                Stack<Integer> cards = new Stack<>();
                cards.add(card.getId());
                sellersCards.put(card.getSeller(), cards);
            }
        }
        for (Map.Entry<Seller, Stack<Integer>> entry : sellersCards.entrySet()) {
            table.addCell(entry.getKey().getName());
            table.addCell("Du carton n°" + entry.getValue().firstElement() + " au n°" + entry.getValue().lastElement());
        }
        document.add(table);
    }

    public void createPdf() throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("sellersCards.pdf"));
        document.open();
        this.writeInPDF(document);
        document.close();
    }

}
