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
import java.util.ArrayList;
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
        for (int column = 0; column < newCardGrid.getColumnCount(); column++){
            for (int row = 0; row < newCardGrid.getRowCount(); row++){
                newCardGrid.add(new TextField(), column, row);
            }
        }

        try {
            // Get all Card
            cardsChoiceBox = (ChoiceBox<Card>) cards.all();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        // TODO: Ajouter un carton au stockage interne et mettre à jour cardsChoiceBox.

	    int collumCount = newCardGrid.getColumnCount();
	    int rowCount = newCardGrid.getRowCount();

        int grid[][] = new int[rowCount][collumCount];
	    for (int i = 0; i < rowCount; i++){
            for (int j = 0; j < collumCount; j++){
                TextField textField = (TextField) getNodeByRowColumnIndex(i, j, newCardGrid);
                int value = Integer.parseInt(textField.getCharacters().toString());
                grid[i][j] = value;
            }
        }

        int id =  Integer.parseInt(newCardId.getCharacters().toString());
        Buyer buyer = new Buyer("name", true);
        Seller seller = new Seller("name");
        Card newCard = new Card(id, grid, buyer, seller);

        cards.store(newCard);
    }

    /**
     * Getter d'un noeud dans une grid en fonction de sa position
     * @param row position horizontal du noeud
     * @param column position vertical du noeud
     * @param gridPane grid
     * @return the node wished
     */
    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    /**
     * Action lors de la sélection d'un carton à ajouter à la partie
     */
    public void onSelectCard() {
	    // TODO: Recharger l'ensemble des éléments représentant le carton séléctionné dans la page d'ajout d'un carton à la partie.
        Card cardSelected = cardsChoiceBox.getValue();

        for (int column = 0; column < newCardGrid.getColumnCount(); column++){
            for (int row = 0; row < newCardGrid.getRowCount(); row++){
                int gridValue = cardSelected.getFilledGrid()[column][row];
                // Use ""+int as a solution to write integer in a label
                addCardGrid.add(new Label(""+gridValue), column, row);
            }
        }
    }

    /**
     * Action du bouton "Ajouter le carton"
     */
    public void onAddCard() {
	    // TODO: Ajouter un carton dans la liste des cartons ajoutés et mettre à jour cardsChoiceBox.
        Card cardSelected = cardsChoiceBox.getValue();

        // Set buyer, seller and if the buyer is present
        //cardSelected.setBuyer(buyerTextField);
        //cardSelected.setSeller(sellerTextField);
        //cardSelected.setIsPresent(buyerPresentCheckBox);
    }

    /**
     * Action du bouton "Logo"
     */
    public void onAddLogo() {
        // TODO: Ouvrir l'explorateur pour ajouter un fichier et changer le label du bouton d'ajout d'un logo.

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        //Set extension filter, only png file used
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                partnerLogoViewer.setImage(new Image(file.getAbsolutePath()));
                ImageIO.write(SwingFXUtils.fromFXImage(partnerLogoViewer.getImage(),null), "png", file);
            } catch (IOException ex) {
                // TODO : Do the catch IOException
            }
        }
    }

    /**
     * Action du bouton "Ajouter le partenaire"
     */
    public void onAddPartner() {
        // TODO: Ajouter un partenaire à la prochaine partie.
        String partnersName = partnerNameTextField.getText();
        String pathLogoPartner = partnerLogoViewer.getImage().getUrl();

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

        cardsChoiceBox.getItems().removeAll();

        for (Card c : cards) {
            if (!addedCardsIds.contains(c.getId())) {
                cardsChoiceBox.getItems().add(c);
            }
        }
    }
}
