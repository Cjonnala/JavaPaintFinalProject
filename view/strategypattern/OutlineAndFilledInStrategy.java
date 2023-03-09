package view.strategypattern;

import java.awt.*;


public class OutlineAndFilledInStrategy implements ShadingStrategy {

    public void draw(Graphics2D g, Shape shape) {
        g.setColor(controller.drawing.Shape.getPrimaryColor());
        g.fill(shape);
        g.setColor(controller.drawing.Shape.getSecondaryColor());
        g.setStroke(new BasicStroke(8));
        g.draw(shape);
    }
}
