package model.interfaces;

import controller.drawing.Coordinate;
import controller.drawing.GroupForShapes;
import controller.drawing.Shape;

import java.awt.*;

public interface ShapeFrame {

    void draw(Graphics2D g);
    Coordinate getStartCoordinate();
    Coordinate getEndCoordinate();
    Shape getShape();
    int getSize();
    void drawChildren(Graphics2D g);
    GroupForShapes getGroup();
    boolean isGroup();

}
