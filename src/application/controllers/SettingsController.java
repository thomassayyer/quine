package application.controllers;

import application.models.Buyer;
import application.models.Card;
import application.models.Partner;
import application.models.Seller;
import application.repositories.CardRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Contrôleur de la page de configuration.
 */
public class SettingsController extends Controller implements Initializable {

    /**
     * Permet la récupération de cartons du stockage interne
     */
    private CardRepository cards;

    /**
     * Liste des cartons ajoutés à la prochaine partie
     */
    private List<Card> addedCards;

    /**
     * Liste des partenaires ajoutés à la prochaine partie.
     */
    private List<Partner> addedPartners;

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
    private TitledPane partnersPane;

    /**
     * Constructeur non paramétré de la classe {@link SettingsController}
     */
    public SettingsController() {
        cards = CardRepository.getInstance();
        addedCards = new ArrayList<>();
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
            partnerLogoViewer.setImage(new Image(file.getAbsolutePath()));
            addLogoButton.setText(file.getName());
        }
    }

    /**
     * Action du bouton "Ajouter le partenaire"
     */
    public void onAddPartner() {
        String partnersName = partnerNameTextField.getText();
        String pathLogoPartner = null;

        try {
            pathLogoPartner = new File("../../ui/images/" + addLogoButton.getText()).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Partner partner = new Partner(partnersName, pathLogoPartner);

        addedPartners.add(partner);

        // Clear the input
        partnerNameTextField.clear();
        partnerLogoViewer.setImage(null);
    }

    /**
     * Action du bouton "sauvegarde"
     */
    public void onSave() {
        // TODO: Configurer le contrôleur de la page "En jeu".
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
