package controller.factorymethod;

import controller.drawing.Coordinate;
import controller.drawing.GroupForShapes;
import controller.drawing.Shape;
import model.interfaces.ShapeFrame;
import view.strategypattern.ShadingStrategy;

import java.awt.*;
import java.awt.geom.GeneralPath;

final class DropletShape implements ShapeFrame {

    Shape shape;
    ShadingStrategy shadingStrategy;

    public DropletShape(Shape shape, ShadingStrategy shadingStrategy) {
        this.shape = shape;
        this.shadingStrategy = shadingStrategy;
    }

    @Override
    public void draw(Graphics2D g) {
        int startX = Math.min(shape.startCoordinate.getX(), shape.endCoordinate.getX());
        int endX = Math.max(shape.startCoordinate.getX(), shape.endCoordinate.getX());
        int startY = Math.min(shape.startCoordinate.getY(), shape.endCoordinate.getY());
        int endY = Math.max(shape.startCoordinate.getY(), shape.endCoordinate.getY());

        int width = endX - startX;
        int height = endY - startY;
        double controlX = width / 2.0;
        double controlY = height / 2.0;
        double topX = startX + controlX;
        double topY = startY + controlY / 2.0;

        GeneralPath path = new GeneralPath();
        path.moveTo(topX, startY);
        path.curveTo(endX, startY, endX, topY, topX, endY);
        path.curveTo(startX, topY, startX, startY, topX, startY);

        g.setColor(Shape.primaryColor);
        shadingStrategy.draw(g, path);
    }

    @Override
    public Coordinate getStartCoordinate() {
        return shape.startCoordinate;
    }

    @Override
    public Coordinate getEndCoordinate() {
        return shape.endCoordinate;
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
    public void drawSubShape(Graphics2D g) {}

    @Override
    public GroupForShapes gettheGroup() {
        return null;
    }

    @Override
    public boolean isGroup() {
        return false;
    }

}