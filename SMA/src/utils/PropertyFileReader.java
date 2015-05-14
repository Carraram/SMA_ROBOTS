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
     * @param filename Nom du fichier properties
     * @throws IOException Erreur lors de la lecture du fichier
     */
    public PropertyFileReader(String filename) throws IOException {
        properties = new Properties();
        openFile(filename);
    }
    
    /**
     * Ouvre un fichier properties
     * @param filename Nom du fichier properties
     * @throws IOException Erreur lors de la lecture du fichier
     */
    public void openFile(String filename) throws IOException {
        assert filename != null;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("Le fichier de propriétés " + filename + " n'a pas été trouvé dans le classpath");
        }
    }
    
    /**
     * Renvoie la valeur d'une propriété de nom donné sous forme de chaîne de caractères
     * @param propertyName Nom de la propriété
     * @return Valeur de la propriété
     */
    public String getPropertyAsString(String propertyName) {
        assert properties != null;
        return properties.getProperty(propertyName);
    }
    
    /**
     * Renvoie la valeur d'une propriété sous forme d'entier
     * @param propertyName Nom de la propriété
     * @return Valeur entière de la propriété
     * @throws NumberFormatException La valeur de la propriété ne correspond pas à un entier
     */
    public int getPropertyAsInt(String propertyName) throws NumberFormatException {
        assert properties != null;
        return Integer.parseInt(properties.getProperty(propertyName));
    }
}
