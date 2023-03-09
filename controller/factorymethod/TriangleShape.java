package controller.factorymethod;

import controller.drawing.Coordinate;
import controller.drawing.GroupForShapes;
import controller.drawing.Shape;
import model.interfaces.ShapeFrame;
import view.strategypattern.ShadingStrategy;

import java.awt.*;
import java.awt.geom.Path2D;

final class TriangleShape implements ShapeFrame {

    Shape shape;
    ShadingStrategy shadingStrategy;

    public TriangleShape(Shape shape, ShadingStrategy shadingStrategy) {
        this.shape = shape;
        this.shadingStrategy = shadingStrategy;
    }

    @Override
    public void draw(Graphics2D g) {

        Coordinate newCoordinate = new Coordinate(shape.startCoordinate.x, shape.endCoordinate.y);

        int[] startArray = new int[3];
        int[] endArray = new int[3];

        startArray[0] = shape.startCoordinate.getX();
        startArray[1] = shape.endCoordinate.getX();
        startArray[2] = newCoordinate.getX();

        endArray[0] = shape.startCoordinate.getY();
        endArray[1] = shape.endCoordinate.getY();
        endArray[2] =  newCoordinate.getY();

        g.setColor(Shape.primaryColor);

        Path2D path = new Path2D.Double();
        path.moveTo(startArray[0], endArray[0]);
        path.lineTo(startArray[1], endArray[1]);
        path.lineTo(startArray[2], endArray[2]);
        path.closePath();

        shadingStrategy.draw(g, path);
    }

    @Override
    public Coordinate getStartCoordinate() {
        return null;
    }

    @Override
    public Coordinate getEndCoordinate() {
        return null;
    }

    @Override
    public Shape gettheShape() {
        return shape;
    }



    @Override
    public int gettheSize() {
        return 0;
    }

    @Override
    public void drawSubShape(Graphics2D g) {

    }

    @Override
    public GroupForShapes gettheGroup() {
        return null;
    }

    @Override
    public boolean isGroup() {
        return false;
    }


}
