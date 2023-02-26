package controller.factorymethod;

import controller.drawing.Coordinate;
import controller.drawing.Shape;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;
import view.strategypattern.ShadingStrategy;

import java.awt.*;
import java.awt.geom.Ellipse2D;

final class EllipseShape implements ShapeFrame, IEventCallback {

    Shape shape;
    ShadingStrategy shadingStrategy;

    EllipseShape(Shape shape, ShadingStrategy shadingStrategy) {
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
        shadingStrategy.draw(g, new Ellipse2D.Double(startX, startY, width, height));

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
