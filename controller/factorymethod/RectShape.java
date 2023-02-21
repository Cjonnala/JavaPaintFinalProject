package controller.factorymethod;

import controller.drawing.Coordinate;
import controller.drawing.Shape;
import model.interfaces.ShapeFrame;

import java.awt.*;

final class RectShape implements ShapeFrame {

    controller.drawing.Shape shape;

    RectShape(controller.drawing.Shape shape){
        this.shape = shape;
    }

    @Override
    public void draw(Graphics2D g) {

        int startX = Math.min(shape.startCoordinate.getX(), shape.endCoordinate.getX());
        int endX = Math.max(shape.startCoordinate.getX(), shape.endCoordinate.getX());
        int startY = Math.min(shape.startCoordinate.getY(), shape.endCoordinate.getY());
        int endY = Math.max(shape.startCoordinate.getY(), shape.endCoordinate.getY());

        int width = endX - startX;
        int height = endY - startY;

        g.setColor(shape.pColor);

        switch (shape.shadingType) {
            case FILLED_IN -> {
                g.fillRect(startX, startY, width, height);
                g.setColor(shape.pColor);
                g.setStroke(new BasicStroke(8));
            }
            case OUTLINE -> {
                g.setStroke(new BasicStroke(8));
                g.setColor(shape.pColor);
                g.drawRect(startX, startY, width, height);
            }
            case OUTLINE_AND_FILLED_IN -> {
                g.setColor(shape.pColor);
                g.fillRect(startX, startY, width, height);
                g.setColor(shape.sColor);
                g.setStroke(new BasicStroke(8));
                g.drawRect(startX, startY, width, height);
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
