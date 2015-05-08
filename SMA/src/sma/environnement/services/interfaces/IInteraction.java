package sma.environnement.services.interfaces;

import sma.common.pojo.CaseNonVideException;
import sma.common.pojo.Position;
import sma.common.pojo.PositionInvalideException;

public interface IInteraction extends IDepot {
    /**
     * Déplacement d'une case vers une de ses voisines
     * @param positionInitiale Position de départ
     * @param nouvellePosition Position d'arrivée
     * @throws CaseNonVideException Case d'arrivée déjà occupée
     * @throws PositionInvalideException Position d'arrivée en dehors de la grille
     */
    void seDeplacer(Position positionInitiale, Position nouvellePosition) throws CaseNonVideException, PositionInvalideException;
}
