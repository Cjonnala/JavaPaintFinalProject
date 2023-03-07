package controller.factorymethod;

import controller.drawing.Coordinate;
import controller.drawing.GroupForShapes;
import controller.drawing.Shape;
import model.interfaces.ShapeFrame;
import view.strategypattern.ShadingStrategy;

import java.awt.*;

final class RectShape implements ShapeFrame {


    Shape shape;
    ShadingStrategy shadingStrategy;
    public RectShape(Shape shape, ShadingStrategy shadingStrategy){
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
        g.setColor(Shape.pColor);
        shadingStrategy.draw(g, new Rectangle(startX, startY, width, height));
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
    public GroupForShapes getGroup() {
        return null;
    }

    @Override
    public boolean isGroup() {
        return false;
    }

}
