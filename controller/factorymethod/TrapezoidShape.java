package controller.factorymethod;

import controller.drawing.Coordinate;
import controller.drawing.GroupForShapes;
import controller.drawing.Shape;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;
import view.strategypattern.ShadingStrategy;

import java.awt.*;

public class TrapezoidShape implements ShapeFrame, IEventCallback {

    Shape shape;
    ShadingStrategy shadingStrategy;

    TrapezoidShape(Shape shape, ShadingStrategy shadingStrategy) {
        this.shape = shape;
        this.shadingStrategy = shadingStrategy;
    }

    @Override
    public void draw(Graphics2D g) {
        int x1 = shape.startCoordinate.getX();
        int x2 = shape.endCoordinate.getX();
        int y1 = shape.startCoordinate.getY();
        int y2 = shape.endCoordinate.getY();
        int height = Math.abs(y2 - y1);
        int width = Math.abs(x2 - x1);
        int topWidth = (int) (width - ((-38.0) / height) * shape.getWidth());
        int x3 = x1 + (width - topWidth) / 38;
        int x4 = x3 + topWidth;

        int[] xPoints = {x1, x3, x4, x2};
        int[] yPoints = {y1, y2, y2, y1};

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

