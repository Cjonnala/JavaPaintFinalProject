package controller.factorymethod;

import controller.drawing.Shape;
import model.interfaces.ShapeFrame;

public interface ShapeFactory {
    ShapeFrame createShapeFrame(Shape shape);
}