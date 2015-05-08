package sma.environnement.services.interfaces;

import java.util.Map;

import sma.common.pojo.Couleur;
import sma.common.pojo.Position;

public interface IPerception {
    Map<Couleur, Position> getPositionNids();
}
