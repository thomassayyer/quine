package application.repositories;

import application.models.Model;
import application.models.Storable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe mère de tous les application.repositories
 */
public abstract class Repository<M extends Storable> {

    /**
     * Chemin d'accès au dossier du stockage interne
     */
    protected static final String basePath = "storage";

    /**
     * Retourne le dossier spécifique associée à l'entrepôt.
     *
     * @return Dossier de l'entrepôt
     */
    protected abstract String specificDir();

    /**
     * Stocke un objet dans un fichier binaire.
     *
     * @param object Objet à stocker
     *
     * @return Vrai si le fichier a été créé; faux sinon
     */
    public boolean store(M object) {
        File file = new File(basePath + "/" + specificDir() + "/" + object.getId() + ".object");

        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            object.beforeSerialization();
            stream.writeObject(object);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    /**
     * Supprime un objet du stockage interne.
     *
     * @param id Identifiant unique de l'objet à supprimer
     *
     * @return Vrai si le fichier a été supprimer; faux sinon
     */
    public boolean destroy(int id) {
        return new File(basePath + "/" + specificDir() + "/" + id + ".object").delete();
    }

    /**
     * Récupère et retourne un objet du stockage interne (en fonction du nom du fichier).
     *
     * @param filename Nom du fichier contenant l'objet à récupérer
     *
     * @return L'objet récupéré
     *
     * @throws IOException            Lorsque il est impossible d'accéder au fichier
     * @throws ClassNotFoundException Lorsque la classe à instancier n'est pas accessible
     */
    protected M find(String filename) throws IOException, ClassNotFoundException {
        File file = new File(basePath + "/" + specificDir() + "/" + filename);

        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
            return (M) stream.readObject();
        }
    }

    /**
     * Récupère et retourne un objet du stockage interne.
     *
     * @param id Identifiant unique de l'objet à récupérer
     *
     * @return L'objet récupéré
     *
     * @throws IOException            Lorsque il est impossible d'accéder au fichier
     * @throws ClassNotFoundException Lorsque la classe à instancier n'est pas accessible
     */
    public M find(int id) throws IOException, ClassNotFoundException {
        return find(id + ".object");
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
        File[] files = new File(basePath + "/" + specificDir()).listFiles();
        List<M> objects = new ArrayList<>();

        // Ternaire nécessaire pour éviter l'exception java.lang.NullPointerException
        for (File file : files != null ? files : new File[0]) {
            if (file.isFile()) {
                objects.add(find(file.getName()));
            }
        }

        return objects;
    }

    /**
     * Crée le répertoire spécifique au repository.
     */
    public boolean makeSpecificDir() {
        File directory = new File(basePath + "/" + specificDir());

        if (!directory.exists()) {
            return directory.mkdirs();
        }

        return true;
    }

    /**
     * Détermine si un objet existe dans le stockage interne.
     *
     * @param id ID de l'objet à trouver
     *
     * @return Vrai si l'objet a été trouvé; faux sinon
     */
    public boolean exists(int id) {
        return new File(basePath + "/" + specificDir() + "/" + id + ".object").exists();
    }
}
