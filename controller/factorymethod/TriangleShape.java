package controller.factorymethod;

import controller.drawing.Coordinate;
import controller.drawing.Shape;
import model.interfaces.ShapeFrame;

import java.awt.*;

final class TriangleShape implements ShapeFrame {

    controller.drawing.Shape shape;

    TriangleShape(controller.drawing.Shape shape) {
        this.shape = shape;
    }

    @Override
    public void draw(Graphics2D g) {
        int[] xPoints = {shape.startCoordinate.getX(), shape.endCoordinate.getX(), shape.startCoordinate.getX()};
        int[] yPoints = {shape.startCoordinate.getY(), shape.startCoordinate.getY(), shape.endCoordinate.getY()};

        g.setColor(shape.pColor);

        switch (shape.shadingType) {
            case FILLED_IN -> {
                g.fillPolygon(xPoints, yPoints, 3);
                g.setStroke(new BasicStroke(8));
            }
            case OUTLINE -> {
                g.drawPolygon(xPoints, yPoints, 3);
                g.setStroke(new BasicStroke(8));
            }
            case OUTLINE_AND_FILLED_IN -> {
                g.setColor(shape.sColor);
                g.drawPolygon(xPoints, yPoints, 3);
                g.setStroke(new BasicStroke(8));
                g.setColor(shape.pColor);
                g.setStroke(new BasicStroke(8));
                g.fillPolygon(xPoints, yPoints, 3);
            }
        }

    }

    @Override
    public Coordinate getStartPoint() {
        return null;
    }

    @Override
    public Coordinate getEndPoint() {
        return null;
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void drawChildren(Graphics2D g) {

    }
}