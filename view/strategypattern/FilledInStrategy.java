package view.strategypattern;

import java.awt.*;

public class FilledInStrategy implements ShadingStrategy {
    @Override
    public void draw(Graphics2D g, Shape shape) {
        g.fill(shape);
    }
}
