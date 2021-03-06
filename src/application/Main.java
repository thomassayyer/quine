package application;

import application.repositories.CardRepository;
import application.repositories.GameRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Représente le point d'entrée de l'application. La méthode {@link Main#main(String[])} est la première méthode appelée.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/Home.fxml"));
        primaryStage.setTitle("Quine - Accueil");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * Effectue les pré-requis au bon fonctionnement de l'application.
     */
    private static void boot() {
        CardRepository.getInstance().makeSpecificDir();
        GameRepository.getInstance().makeSpecificDir();
    }

    /**
     * Il s'agit de la première méthode appelée lors de l'exécution de l'application.
     *
     * @param args Devrait toujours être null.
     */
    public static void main(String[] args) {
        boot();
        launch(args);
    }
}
