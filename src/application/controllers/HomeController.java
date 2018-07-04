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
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getResource("ui/views/inGame.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Quine - En jeu");
            stage.setScene(new Scene(root, 300, 275));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Ouvertue du menu de configuration.
	 */
	public void onSettings() {
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getResource("ui/views/settings.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Quine - Configuration");
            stage.setScene(new Scene(root, 300, 275));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
