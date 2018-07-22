package application.controllers;

import application.models.Card;
import application.models.Partner;
import application.repositories.CardRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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
    }

    /**
     * Action lors de la sélection d'un carton à ajouter à la partie
     */
    public void onSelectCard() {
	    // TODO: Recharger l'ensemble des éléments représentant le carton séléctionné dans la page d'ajout d'un carton à la partie.
    }

    /**
     * Action du bouton "Ajouter le carton"
     */
    public void onAddCard() {
	    // TODO: Ajouter un carton dans la liste des cartons ajoutés et mettre à jour cardsChoiceBox.
    }

    /**
     * Action du bouton "Logo"
     */
    public void onAddLogo() {
        // TODO: Ouvrir l'explorateur pour ajouter un fichier et changer le label du bouton d'ajout d'un logo.
    }

    /**
     * Action du bouton "Ajouter le partenaire"
     */
    public void onAddPartner() {
        // TODO: Ajouter un partenaire à la prochaine partie.
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
