package application.controllers;

import application.repositories.GameRepository;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.text.DocumentException;

/**
 * Contrôleur de la vue accueil.
 */
public class HomeController extends Controller {

    /**
     * Permet la récupération de parties de jeu du stockage interne.
     */
    private GameRepository games;

    /**
     * Crée un nouveau contrôleur pour la page d'accueil.
     */
    public HomeController() {
        games = GameRepository.getInstance();
    }

	/**
	 * Lancement du jeu de quine.
	 */
	public void onPlay() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Jouer automatiquement les cartons des absents ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        boolean playForAbsents = alert.getResult() == ButtonType.YES;

        try {
            Stage stage = new Stage();
            stage.setTitle("Quine - En jeu");

            FXMLLoader root = new FXMLLoader(getClass().getResource("../../ui/views/InGame.fxml"));

            InGameController controller = null;
            if (games.exists(1)) {
                controller = games.find(1);
                controller.setPlayForAbsents(playForAbsents);
            }

            root.setController(controller);
            root.setRoot(null);

            Scene scene = new Scene(root.load(), 1280, 1080);
            scene.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.A) {
                    ((InGameController)root.getController()).removeLastNumber();
                } else if (event.getCode() == KeyCode.R) {
                    ((InGameController)root.getController()).clear();
                }
                else if (event.getCode() == KeyCode.P) {
                	try {
						((InGameController) root.getController()).createPdf();
					} catch (FileNotFoundException | DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            });

            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Ouvertue du menu de configuration.
	 */
	public void onSettings() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Quine - Configuration");
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../ui/views/Settings.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
