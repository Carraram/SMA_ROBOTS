package sma.common.services.interfaces;

import sma.common.pojo.exceptions.PersistedObjectNotFoundException;

/**
 * Service de persistance
 */
public interface IPersistence {
    /**
     * Persiste un objet
     * @param objectToSave Objet à persister
     * @param objectName Nom donné pour retrouver l'objet
     */
    void saveObject(Object objectToSave, String objectName);
    
    /**
     * Recrée un objet persisté
     * @param objectName Nom de l'objet à recréer
     * @return Objet recréé
     * @throws PersistedObjectNotFoundException Objet à charger inexistant
     */
    Object loadObject(String objectName) throws PersistedObjectNotFoundException;
}
