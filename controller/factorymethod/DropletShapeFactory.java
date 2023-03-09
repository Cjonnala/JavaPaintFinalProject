package controller.factorymethod;

import controller.drawing.Shape;
import model.interfaces.ShapeFrame;
import view.strategypattern.FilledInStrategy;
import view.strategypattern.OutlineAndFilledInStrategy;
import view.strategypattern.OutlineStrategy;
import view.strategypattern.ShadingStrategy;

public class DropletShapeFactory implements ShapeFactory {
    public ShapeFrame createShapeFrame(Shape shape) {
        ShadingStrategy shadingStrategy = null;

        switch (shape.shadingType) {
            case FILLED_IN -> shadingStrategy = new FilledInStrategy();
            case OUTLINE -> shadingStrategy = new OutlineStrategy();
            case OUTLINE_AND_FILLED_IN -> shadingStrategy = new OutlineAndFilledInStrategy();
        }

        return new DropletShape(shape, shadingStrategy);
    }
}
