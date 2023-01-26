package controller.drawing;

import model.ShapeShadingType;
import model.interfaces.ShapeFrame;
import model.persistence.ApplicationState;

import java.awt.*;

final class RectShape implements ShapeFrame {

    Shape shape;
    ApplicationState appState;
    RectShape(Shape shape){
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

        if(shape.shadingType == ShapeShadingType.FILLED_IN)
        {
            g.fillRect(startX,startY,width, height);
        }

        else
        {
            g.fillRect(startX,startY,width, height);
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
