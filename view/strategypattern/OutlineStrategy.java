package view.strategypattern;

import java.awt.*;

public class OutlineStrategy implements ShadingStrategy {
    @Override
    public void draw(Graphics2D g, Shape shape) {
        g.setStroke(new BasicStroke(5));
        g.draw(shape);

    }
}
