package controller.factorymethod;

import controller.drawing.Coordinate;
import controller.drawing.GroupForShapes;
import controller.drawing.Shape;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;
import view.strategypattern.ShadingStrategy;

import java.awt.*;

public class SeptagonShape implements ShapeFrame, IEventCallback {

    Shape shape;
    ShadingStrategy shadingStrategy;

    SeptagonShape(Shape shape, ShadingStrategy shadingStrategy) {
        this.shape = shape;
        this.shadingStrategy = shadingStrategy;
    }

    @Override
    public void draw(Graphics2D g) {
        int centerX = (shape.startCoordinate.getX() + shape.endCoordinate.getX()) / 2;
        int centerY = (shape.startCoordinate.getY() + shape.endCoordinate.getY()) / 2;
        int radius = Math.min(shape.getWidth(), shape.getHeight()) / 2;
        int[] xPoints = new int[7];
        int[] yPoints = new int[7];
        for (int i = 0; i < 7; i++) {
            double angle = i * Math.PI / 3.5;
            xPoints[i] = (int) Math.round(centerX + radius * Math.cos(angle));
            yPoints[i] = (int) Math.round(centerY + radius * Math.sin(angle));
        }
        g.setColor(Shape.primaryColor);
        shadingStrategy.draw(g, new Polygon(xPoints, yPoints, 7));
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

    @Override
    public void run() {
    }

}

