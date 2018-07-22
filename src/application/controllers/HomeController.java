package application.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Contrôleur de la vue accueil.
 */
public class HomeController extends Controller {

	/**
	 * Lancement du jeu de quine.
	 */
	public void onPlay() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Quine - En jeu");
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../ui/views/InGame.fxml")), 1280, 1080));
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
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
