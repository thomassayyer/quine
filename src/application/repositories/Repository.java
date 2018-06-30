package application.repositories;

import application.models.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe mère de tous les repositories
 */
public abstract class Repository<M extends Model> {

    /**
     * Chemin d'accès au dossier du stockage interne
     */
    protected final String basePath = System.getProperty("user.dir") + "/storage";

    /**
     * Retourne le dossier spécifique associée à l'entrepôt.
     *
     * @return Dossier de l'entrepôt
     */
    protected abstract String specificDirectory();

    /**
     * Stocke un objet dans un fichier binaire.
     *
     * @param object   Objet à stocker
     * @param filename Nom du fichier à stocker
     *
     * @return Vrai si fichier a été créé; faux sinon
     */
    public boolean store(M object, String filename) {
        File file = new File(basePath + "/" + specificDirectory() + "/" + filename);

        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(object);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Supprime un fichier binaire.
     *
     * @param filename Nom du fichier à supprimer
     *
     * @return Vrai si le fichier a été supprimer; faux sinon
     */
    public boolean destroy(String filename) {
        return new File(basePath + "/" + specificDirectory() + "/" + filename).delete();
    }

    /**
     * Récupère et retourne un objet du stockage interne.
     *
     * @param filename Nom du fichier à récupérer
     *
     * @return L'objet récupéré
     *
     * @throws IOException            Lorsque il est impossible d'accéder au fichier
     * @throws ClassNotFoundException Lorsque la classe à instancier n'est pas accessible
     */
    public M find(String filename) throws IOException, ClassNotFoundException {
        File file = new File(basePath + "/" + specificDirectory() + "/" + filename);
        M object = null;

        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
            object = (M)stream.readObject();
        }

        return object;
    }

    /**
     * Récupère et retourne l'ensemble des modèles stockés dans le dossier spécifique à l'entrepôt.
     *
     * @return L'ensemble des modèles de l'entrepôt.
     *
     * @throws IOException            Lorsqu'il est impossible d'accéder à un fichier.
     * @throws ClassNotFoundException Lorsqu'il est impossible d'instancier un objet.
     */
    public List<M> all() throws IOException, ClassNotFoundException {
        File[] files = new File(basePath + "/" + specificDirectory()).listFiles();
        List<M> objects = new ArrayList<>();

        // Ternaire nécessaire pour éviter l'exception java.lang.NullPointerException
        for (File file : files != null ? files : new File[0]) {
            if (file.isFile()) {
                objects.add(find(file.getName()));
            }
        }

        return objects;
    }
}
