package application.controllers;

import application.models.*;
import application.repositories.CardRepository;
import application.repositories.GameRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Contrôleur de la page de configuration.
 */
public class SettingsController extends Controller implements Initializable {

    /**
     * Permet la récupération de cartons du stockage interne.
     */
    private CardRepository cards;

    /**
     * Permet la récupération de parties de jeu du stockage interne.
     */
    private GameRepository games;

    /**
     * Liste des cartons ajoutés à la prochaine partie
     */
    private List<Card> addedCards;

    /**
     * Liste des partenaires ajoutés à la prochaine partie.
     */
    private List<Partner> addedPartners;

    /**
     * Liste des lots ajoutés à la prochaine partie.
     */
    private List<Prize> addedPrizes;

    /**
     * Chemin absolue du logo du partenaire
     */
    private String imagePath = null;

    /**
     * Champ de saisie de l'ID d'un nouveau carton
     */
    @FXML
    private TextField newCardId;

    /**
     * Grille contenant les numéro d'un nouveau carton
     */
    @FXML
    private GridPane newCardGrid;

    /**
     * Menu déroulant contenant les cartons lors de l'ajout d'un carton à la partie
     */
    @FXML
    private ChoiceBox<Card> cardsChoiceBox;

    /**
     * Grille contenant devant contenir les numéros du carton séléctionné lors de l'ajout d'un carton à la partie
     */
    @FXML
    private GridPane addCardGrid;

    /**
     * Champ de saisie du nom du vendeur d'un carton à ajouter à la partie
     */
    @FXML
    private TextField sellerTextField;

    /**
     * Champ de saisie du nom de l'acheteur d'un carton à ajouter à la partie
     */
    @FXML
    private TextField buyerTextField;

    /**
     * Case à cocher déterminant si l'acheteur d'un carton à ajouter à la partie est présent ou non
     */
    @FXML
    private CheckBox buyerPresentCheckBox;

    /**
     * Champ de saisie du nom d'un nouveau partenaire
     */
    @FXML
    private TextField partnerNameTextField;

    /**
     * Bouton d'ajout d'un logo à un nouveau partenaire.
     */
    @FXML
    private Button addLogoButton;

    /**
     * Permet l'affichage du logo du partenaire en dessous du bouton d'ajout d'un logo
     */
    @FXML
    private ImageView partnerLogoViewer;

    /**
     * Conteneur des partenaires déjà ajoutés à la prochaine partie
     */
    @FXML
    private HBox partnersPane;

    /**
     * Conteneur des cartons ajoutés à la prochaine partie
     */
    @FXML
    private HBox cardsPane;

    /**
     * Champ contenant le numéro du lot à créer.
     */
    @FXML
    private TextField prizeNumberField;

    /**
     * Champ contenant le libellé du lot à créer.
     */
    @FXML
    private TextField prizeLabelField;

    /**
     * Menu déroulant contenant les partenaires lors de la création de lots.
     */
    @FXML
    private ChoiceBox<Partner> partnersChoiceBox;

    /**
     * Contient les lots ajoutés à la prochaine partie.
     */
    @FXML
    private HBox prizesPane;

    /**
     * Grille vide.
     */
    private int[][] emptyGrid;

    /**
     * Constructeur non paramétré de la classe {@link SettingsController}
     */
    public SettingsController() {
        cards = CardRepository.getInstance();
        games = GameRepository.getInstance();
        addedCards = new ArrayList<>();
        addedPartners = new ArrayList<>();
        addedPrizes = new ArrayList<>();
        emptyGrid = new int[3][5];

        for (int i = 0; i < emptyGrid.length; i++) {
            for (int j = 0; j < emptyGrid[i].length; j++) {
                emptyGrid[i][j] = 0;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Generate TextField input
        for (int column = 0; column < 5; column++) {
            for (int row = 0; row < 3; row++) {
                newCardGrid.add(new TextField(), column, row);
            }
        }

        try {
            reloadCardsChoiceBox();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Action du bouton "Créer le carton"
     */
	public void onCreateCard() {
	    int columnCount = 5;
	    int rowCount = 3;

        int grid[][] = new int[rowCount][columnCount];
	    for (int i = 0; i < rowCount; i++){
            for (int j = 0; j < columnCount; j++){
                TextField textField = (TextField) getNodeByRowColumnIndex(i, j, newCardGrid);
                int value = 0;
                if (textField != null) {
                    value = Integer.parseInt(textField.getText());
                    textField.clear();
                }
                grid[i][j] = value;
            }
        }

        int id =  Integer.parseInt(newCardId.getCharacters().toString());
        Card newCard = new Card(id, grid);

        cards.store(newCard);

        // Rechargement du menu déroulant des cartons
        try {
            reloadCardsChoiceBox();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        newCardId.clear();
    }

    /**
     * Getter d'un élément dans une grid en fonction de sa position
     *
     * @param row      Position horizontal du noeud
     * @param column   Position vertical du noeud
     * @param gridPane Grid
     *
     * @return L'élément souhaité
     */
    private Node getNodeByRowColumnIndex (int row, int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);
            if (rowIndex != null && columnIndex != null && rowIndex == row && columnIndex == column) {
                return node;
            }
        }
        return null;
    }

    /**
     * Action lors de la sélection d'un carton à ajouter à la partie
     */
    public void onSelectCard() {
        Card cardSelected = cardsChoiceBox.getValue();

        if (cardSelected == null) {
            return;
        }

        addCardGrid.getChildren().clear();

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 5; j++){
                int gridValue = cardSelected.getGrid()[i][j];
                // Use ""+int as a solution to write integer in a label
                addCardGrid.add(new Label(""+gridValue), j, i);
            }
        }
    }

    /**
     * Action du bouton "Ajouter le carton"
     */
    public void onAddCard() {
        Card cardSelected = cardsChoiceBox.getValue();

        // Set buyer, seller and if the buyer is present
        cardSelected.setBuyer(new Buyer(buyerTextField.getText(), buyerPresentCheckBox.isSelected()));
        cardSelected.setSeller(new Seller(sellerTextField.getText()));

        addedCards.add(cardSelected);

        try {
            reloadCardsChoiceBox();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        addCardGrid.getChildren().clear();
        buyerTextField.clear();
        buyerPresentCheckBox.setSelected(false);
        sellerTextField.clear();
        cardsPane.getChildren().add(new Label(String.valueOf(cardSelected.getId())));
    }

    /**
     * Action du bouton "Logo"
     */
    public void onAddLogo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un logo");

        //Set extension filter, only png file used
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            imagePath = file.toURI().toString();
            Image imageLogo = new Image(imagePath);
            partnerLogoViewer.setImage(imageLogo);
            addLogoButton.setText(file.getName());
        }
    }

    /**
     * Action du bouton "Ajouter le partenaire"
     */
    public void onAddPartner() {
        String partnersName = partnerNameTextField.getText();
        String pathLogoPartner = this.imagePath;

        Partner partner = new Partner(partnersName, pathLogoPartner);

        addedPartners.add(partner);

        // Clear the input
        partnerNameTextField.clear();
        partnerLogoViewer.setImage(null);
        addLogoButton.setText("Logo");
        partnersPane.getChildren().add(new Label(partner.getName()));

        reloadPartnersChoiceBox();
    }

    /**
     * Action du bouton "sauvegarde"
     */
    public void onSave() {
        InGameController controller = new InGameController(addedCards, addedPartners, addedPrizes);
        games.store(controller);
    }

    /**
     * Action lors du clic sur le bouton "Créer le lot".
     */
    public void onAddPrize() {
        Prize p = new Prize(Integer.parseInt(prizeNumberField.getText()), prizeLabelField.getText(), partnersChoiceBox.getValue());
        addedPrizes.add(p);
        prizesPane.getChildren().add(new Label(String.valueOf(p.getId())));

        prizeNumberField.clear();
        prizeLabelField.clear();
        partnersChoiceBox.setValue(null);
    }

    /**
     * Recharge la liste des partnaires lors de l'ajout de lots.
     */
    private void reloadPartnersChoiceBox() {
        partnersChoiceBox.getItems().clear();

        for (Partner p : addedPartners) {
            partnersChoiceBox.getItems().add(p);
        }
    }

    /**
     * Recharge la liste des cartons lors de l'ajout d'un carton à la partie.
     *
     * @throws IOException            Lorsqu'un des cartons stockés n'est pas accessible
     * @throws ClassNotFoundException Lorsqu'un des cartons stockés n'est pas instanciable
     */
    private void reloadCardsChoiceBox() throws IOException, ClassNotFoundException {
        List<Card> cards = this.cards.all();
        List<Integer> addedCardsIds = addedCards.stream().map(Card::getId).collect(Collectors.toList());

        cardsChoiceBox.getItems().clear();

        for (Card c : cards) {
            if (!addedCardsIds.contains(c.getId())) {
                cardsChoiceBox.getItems().add(c);
            }
        }
    }
}
