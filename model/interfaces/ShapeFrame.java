package model.interfaces;

import controller.drawing.Coordinate;
import controller.drawing.GroupForShapes;
import controller.drawing.Shape;

import java.awt.*;

public interface ShapeFrame {

    void draw(Graphics2D g);
    Coordinate getStartCoordinate();
    Coordinate getEndCoordinate();
    Shape gettheShape();
    int gettheSize();
    void drawSubShape(Graphics2D g);
    GroupForShapes gettheGroup();
    boolean isGroup();

}
