package controller.factorymethod;

import controller.drawing.Shape;
import model.interfaces.ShapeFrame;

public class RectShapeFactory implements ShapeFactory {
    public ShapeFrame createShapeFrame(Shape shape) {
        return new RectShape(shape);
    }
}
