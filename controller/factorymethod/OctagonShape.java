package controller.factorymethod;

import controller.drawing.Coordinate;
import controller.drawing.GroupForShapes;
import controller.drawing.Shape;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;
import view.strategypattern.ShadingStrategy;

import java.awt.*;

public class OctagonShape implements ShapeFrame, IEventCallback {

    Shape shape;
    ShadingStrategy shadingStrategy;

    OctagonShape(Shape shape, ShadingStrategy shadingStrategy) {
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
        int[] xPoints = new int[] { startX + width/4, startX + 3*width/4, endX, endX, startX + 3*width/4, startX + width/4, startX, startX };
        int[] yPoints = new int[] { startY, startY, startY + height/4, startY + 3*height/4, endY, endY, startY + 3*height/4, startY + height/4 };
        g.setColor(Shape.primaryColor);
        shadingStrategy.draw(g, new Polygon(xPoints, yPoints, 8));
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

