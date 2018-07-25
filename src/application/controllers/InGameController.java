package application.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.javafx.collections.MappingChange.Map;

import application.models.Card;
import application.models.Partner;
import application.models.Prize;
import application.models.Seller;
import application.repositories.CardRepository;
import application.repositories.PrizeRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TitledPane;

/**
 * Contrôleur de la page "En jeu".
 */
public class InGameController extends Controller implements Initializable {

    @FXML
	private TitledPane winnersCardsPane;
	
    // TODO: Réfléchir aux méthodes à implémenter.

	// Liste des cartons des joueurs absents
	private List<Card> absentBuyerCard;
	


	// Liste des nombres
	private LinkedList<Integer> numbers;

	// Liste des partenaires
	private List<Partner> partners;

	// Liste des partenaires
	private List<Prize> prizes;

	private CardRepository cardRepository;
	private PrizeRepository prizeRepository;

	/**
	 * Initialise le jeu
	 *
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void init() throws ClassNotFoundException, IOException {
		this.absentBuyerCard = cardRepository.absents();
	}

	/**
	 * Renseigne le num�ro sorti dans la liste
	 *
	 * @param number
	 */
	private void chooseNumber(int number) {
		numbers.add(number);
		this.fillAbsentBuyerCard(number);

	}

    /**
     * Onglet "Ajout d'un carton sans joueur"
     */
    @FXML
    private Tab addCardTab;

    /**
     * Onglet "Partenaires de la partie"
     */
    @FXML
    private Tab managePartnersTab;

    /**
     * Action du bouton sauvegarde
     *
     * @param controller Contrôleur de la page "En jeu"
     */
    public void onSave(InGameController controller) {
        // TODO: Configurer le contrôleur de la page "En jeu".
    }


	// Work in progress

	private String type;
	private static final String CARTON_PLEIN = "Carton Plein";
	private static final String LIGNE_SIMPLE = "Ligne Simple";

	private void changeType(String type) {
		switch (type) {
			case CARTON_PLEIN:
				this.type = CARTON_PLEIN;
				break;
			case LIGNE_SIMPLE:
				this.type = LIGNE_SIMPLE;
				break;
			default:
				// TODO : exception
				break;
		}
	}

	private void fillAbsentBuyerCard(int number) {
		for (Card card : absentBuyerCard) {
			card.fill(number);
		}
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
	    // TODO : Générer la grille de nombres (l'ID de a grille est "gridPane")
    }
    
    private void writeInPDF(Document document) throws DocumentException {
    	 HashMap<String, Integer> map = new HashMap<String, Integer>();
    	 PdfPTable table = new PdfPTable(2);
    	 PdfPCell sellerColumn = new PdfPCell();
    	 PdfPCell cardNumber = new PdfPCell();
    	 sellerColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
    	 cardNumber.setHorizontalAlignment(Element.ALIGN_CENTER);
    	 table.addCell(sellerColumn);
    	 table.addCell(sellerColumn);
    	 table.setHeaderRows(1);
    	 for(Card card : absentBuyerCard) {
    		map.put(card.getSeller().getName(), card.getId());
    	 }  	 
    	 document.add(table);
    }
    
    private void createPdf() throws FileNotFoundException, DocumentException {
    	Document document = new Document();
    	 PdfWriter.getInstance(document, new FileOutputStream("test"));
    	 document.open();
    	 this.writeInPDF(document);
    	 document.close();
    }
    // TODO: Pop-up Carton absent gagnant.
}
