package controller.factorymethod;

import controller.drawing.Coordinate;
import controller.drawing.GroupForShapes;
import controller.drawing.Shape;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;
import view.strategypattern.ShadingStrategy;

import java.awt.*;

public class SquareShape implements ShapeFrame, IEventCallback {

    Shape shape;
    ShadingStrategy shadingStrategy;

    SquareShape(Shape shape, ShadingStrategy shadingStrategy) {
        this.shape = shape;
        this.shadingStrategy = shadingStrategy;
    }

    @Override
    public void draw(Graphics2D g) {
        int x1 = shape.startCoordinate.getX();
        int x2 = shape.endCoordinate.getX();
        int y1 = shape.startCoordinate.getY();
        int y2 = shape.endCoordinate.getY();
        int side = Math.min(Math.abs(x2 - x1), Math.abs(y2 - y1));

        int[] xPoints = {x1, x1 + side, x1 + side, x1};
        int[] yPoints = {y1, y1, y1 + side, y1 + side};

        g.setColor(Shape.primaryColor);
        shadingStrategy.draw(g, new Polygon(xPoints, yPoints, 4));
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

