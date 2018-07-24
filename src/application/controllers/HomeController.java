package application.controllers;

import application.repositories.GameRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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
        try {
            Stage stage = new Stage();
            stage.setTitle("Quine - En jeu");

            FXMLLoader root = FXMLLoader.load(getClass().getResource("../../ui/views/InGame.fxml"));
            root.setController(games.find(1));
            root.setRoot(null);

            stage.setScene(new Scene(root.load(), 1280, 1080));
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
