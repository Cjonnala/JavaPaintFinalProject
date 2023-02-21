package controller.factorymethod;

import controller.drawing.Shape;
import model.interfaces.ShapeFrame;

public class TriangleShapeFactory implements ShapeFactory {
    public ShapeFrame createShapeFrame(Shape shape) {
        return new TriangleShape(shape);
    }
}