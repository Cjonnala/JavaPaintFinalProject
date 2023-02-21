package controller.factorymethod;

import controller.drawing.Shape;
import model.interfaces.ShapeFrame;

public class EllipseShapeFactory implements ShapeFactory {
    public ShapeFrame createShapeFrame(Shape shape) {
        return new EllipseShape(shape);
    }
}
