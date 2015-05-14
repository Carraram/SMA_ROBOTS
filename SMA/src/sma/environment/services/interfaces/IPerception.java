package sma.environment.services.interfaces;

import java.util.Map;

import sma.common.pojo.Colors;
import sma.common.pojo.Position;

public interface IPerception {
    Map<Colors, Position> getNests();
}
