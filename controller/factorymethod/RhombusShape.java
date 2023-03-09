package controller.factorymethod;

import controller.drawing.Coordinate;
import controller.drawing.GroupForShapes;
import controller.drawing.Shape;
import model.interfaces.ShapeFrame;
import view.strategypattern.ShadingStrategy;

import java.awt.*;

final class RhombusShape implements ShapeFrame {

    Shape shape;
    ShadingStrategy shadingStrategy;
    public RhombusShape(Shape shape, ShadingStrategy shadingStrategy){
        this.shape = shape;
        this.shadingStrategy = shadingStrategy;
    }

    @Override
    public void draw(Graphics2D g) {

        int startX = Math.min(shape.startCoordinate.getX(), shape.endCoordinate.getX());
        int endX = Math.max(shape.startCoordinate.getX(), shape.endCoordinate.getX());
        int startY = Math.min(shape.startCoordinate.getY(), shape.endCoordinate.getY());
        int endY = Math.max(shape.startCoordinate.getY(), shape.endCoordinate.getY());

        int[] xPoints = new int[] { startX + (endX - startX) / 2, endX, startX + (endX - startX) / 2, startX };
        int[] yPoints = new int[] { startY, startY + (endY - startY) / 2, endY, startY + (endY - startY) / 2 };
        g.setColor(Shape.primaryColor);
        shadingStrategy.draw(g, new Polygon(xPoints, yPoints, 4));
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