package application.repositories;

import application.models.Model;

import java.io.*;

/**
 * Classe mère de tous les repositories
 */
public abstract class Repository {

    /**
     * Chemin d'accès au dossier du stockage interne
     */
    private final String basePath = "/storage";

    /**
     * Stocke un objet dans un fichier binaire.
     *
     * @param object   Objet à stocker
     * @param filename Nom du fichier à stocker
     *
     * @throws IOException Lorsque la création du fichier est imposssible
     */
    public void store(Model object, String filename) throws IOException {
        File file = new File(basePath + "/" + specificDirectory() + "/" + filename);

        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(object);
        }
    }

    /**
     * Retourne le dossier spécifique associée à l'entrepôt.
     *
     * @return Dossier de l'entrepôt
     */
    protected abstract String specificDirectory();
}
