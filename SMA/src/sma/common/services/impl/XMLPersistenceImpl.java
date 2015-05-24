package sma.common.services.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.XStream;

import sma.common.pojo.PersistedObjectNotFoundException;
import sma.common.services.interfaces.IPersistence;
import components.control.Persistence;

public class XMLPersistenceImpl extends Persistence {
    
    private XStream xstream;
    private final String saveDirectory = "resources/persistence";
    
    /**
     * Constructeur simple
     */
    public XMLPersistenceImpl() {
        xstream = new XStream();
    }
    
    /**
     * Constructeur avec utilisation d'alias associés aux types d'objets
     * @param serializationAlias Map reliant les classes à leur nom de sérialisation
     */
    public XMLPersistenceImpl(Map<Class<?>, String> serializationAlias) {
        xstream = new XStream();
        for (Entry<Class<?>, String> entry : serializationAlias.entrySet()) {
            xstream.alias(entry.getValue(), entry.getKey());
        }
    }

    @Override
    protected IPersistence make_persistenceService() {
        return new IPersistence() {
            
            @Override
            public void saveObject(Object objectToSave, String objectName) {
                try {
                    OutputStream output = new FileOutputStream(saveDirectory + objectName + "-persisted.xml");
                    //System.out.println(xstream.toXML(objectToSave));
                    xstream.toXML(objectToSave, output);
                } catch (FileNotFoundException e) {
                    System.err.println("Impossible de sauvegarder l'état de l'objet " + objectName);
                    e.printStackTrace();
                }
                
            }
            
            @Override
            public Object loadObject(String objectName) throws PersistedObjectNotFoundException {
                InputStream inputstream;
                try {
                    inputstream = new FileInputStream(saveDirectory + objectName + "-persisted.xml");
                    return xstream.fromXML(inputstream);
                } catch (FileNotFoundException e) {
                    throw new PersistedObjectNotFoundException("Impossible de charger l'état de l'objet " + objectName);
                }
            }
        };
    }

}
