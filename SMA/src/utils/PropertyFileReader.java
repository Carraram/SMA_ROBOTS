package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Lecteur de fichiers de propriétés
 * Les fichiers properties doivent se trouver dans le répertoire resources
 */
public class PropertyFileReader {
    private Properties properties;
    
    /**
     * Crée un PropertyFileReader
     */
    public PropertyFileReader() {
        properties = new Properties();
    }
     
    /**
     * Crée un PropertyFileReader et ouvre un fichier properties
     * @param nomFichier Nom du fichier properties
     * @throws IOException Erreur lors de la lecture du fichier
     */
    public PropertyFileReader(String nomFichier) throws IOException {
        properties = new Properties();
        ouvrirFichier(nomFichier);
    }
    
    /**
     * Ouvre un fichier properties
     * @param nomFichier Nom du fichier properties
     * @throws IOException Erreur lors de la lecture du fichier
     */
    public void ouvrirFichier(String nomFichier) throws IOException {
        assert nomFichier != null;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(nomFichier);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("Le fichier de propriétés " + nomFichier + " n'a pas été trouvé dans le classpath");
        }
    }
    
    /**
     * Renvoie la valeur d'une propriété de nom donné sous forme de chaîne de caractères
     * @param nomPropriete Nom de la propriété
     * @return Valeur de la propriété
     */
    public String getProprieteString(String nomPropriete) {
        assert properties != null;
        return properties.getProperty(nomPropriete);
    }
    
    /**
     * Renvoie la valeur d'une propriété sous forme d'entier
     * @param nomPropriete Nom de la propriété
     * @return Valeur entière de la propriété
     * @throws NumberFormatException La valeur de la propriété ne correspond pas à un entier
     */
    public int getProprieteInt(String nomPropriete) throws NumberFormatException {
        assert properties != null;
        return Integer.parseInt(properties.getProperty(nomPropriete));
    }
}
