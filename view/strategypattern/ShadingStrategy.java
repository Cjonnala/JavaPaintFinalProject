package view.strategypattern;

import java.awt.*;

public interface ShadingStrategy {
    void draw(Graphics2D g, Shape shape);
}