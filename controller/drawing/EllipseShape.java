package controller.drawing;

import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;

import java.awt.*;

final class EllipseShape implements ShapeFrame, IEventCallback {

    Shape shape;

    EllipseShape(Shape shape) {
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
                g.fillOval(startX, startY, width, height);
                g.setColor(shape.pColor);
                g.setStroke(new BasicStroke(8));
            }
            case OUTLINE -> {
                g.drawOval(startX, startY, width, height);
                g.setStroke(new BasicStroke(8));
            }
            case OUTLINE_AND_FILLED_IN -> {
                g.setColor(shape.pColor);
                g.fillOval(startX, startY, width, height);
                g.setColor(shape.sColor);
                g.setStroke(new BasicStroke(8));
                g.drawOval(startX, startY, width, height);
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

    @Override
    public void run() {

    }
}
