import repositories.CardRepository;
import repositories.PrizeRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Représente le point d'entrée de l'application. La méthode Main.main(String[] args) est la première méthode appelée.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ui/views/home.fxml"));
        primaryStage.setTitle("Quine - Accueil");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    /**
     * Effectue les opérations pré-requises pour le bon fonctionnement de l'application.
     */
    private static void boot() {
        if (!CardRepository.getInstance().makeSpecificDir()) {
            System.out.println("[Warning] Impossible de créer le répertoire de stockage des cartons.");
        }
        if (!PrizeRepository.getInstance().makeSpecificDir()) {
            System.out.println("[Warning] Impossible de créer le répertoire de stockage des lots.");
        }
    }

    /**
     * Démarre l'application.
     *
     * @param args Devrait toujours être null.
     */
    public static void main(String[] args) {
        boot();
        launch(args);
    }
}
