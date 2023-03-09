package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.Coordinate;
import controller.drawing.ListForShapes;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import model.persistence.ApplicationState;
import view.interfaces.IEventCallback;

public class MoveCommand implements IEventCallback, IUndoable {

    private final ListForShapes listForShapes;
      ApplicationState appState;

    private final int newCoordinateX, newCoordinateY;

    public MoveCommand(ApplicationState appState, Coordinate startCoordinate, Coordinate endCoordinate, ListForShapes listForShapes){
        this.listForShapes = listForShapes;
        this.appState = appState;
        newCoordinateX = endCoordinate.getX()- startCoordinate.getX();
        newCoordinateY = endCoordinate.getY()- startCoordinate.getY();
    }

    @Override
    public void run() {

        if (listForShapes.getListofSelectedShapes().size()>0){
            for(ShapeFrame shapeFrame : listForShapes.getListofSelectedShapes()) {
                if (shapeFrame.gettheSize() > 0) {
                    shapeFrame.gettheGroup().moveGroupedSubShapes(newCoordinateX, newCoordinateY);
                } else {
                    shapeFrame.gettheShape().move(newCoordinateX, newCoordinateY);
                }
            }
            listForShapes.drawerForShapesList(listForShapes.getShapesList(),listForShapes.getListofSelectedShapes());
            CommandHistory.add(this);
        }
    }

    @Override
    public void undo() {
        listForShapes.getListofSelectedShapes();
        for(ShapeFrame shapeFrame1: listForShapes.getListofSelectedShapes()) {
            if (shapeFrame1.gettheSize() > 0) {
                shapeFrame1.gettheGroup().undoMovedGroupShapes();
            } else {
                shapeFrame1.gettheShape().undoMove();
            }
        }
        listForShapes.drawerForShapesList(listForShapes.getShapesList(),listForShapes.getListofSelectedShapes());
    }

    @Override
    public void redo() {
        listForShapes.getListofSelectedShapes();
        for(ShapeFrame shapeFrame2: listForShapes.getListofSelectedShapes()) {
            if (shapeFrame2.gettheSize() > 0) {
                shapeFrame2.gettheGroup().redoMovedGroupShapes();
            } else {
                shapeFrame2.gettheShape().redoMove();
            }
        }
        listForShapes.drawerForShapesList(listForShapes.getShapesList(),listForShapes.getListofSelectedShapes());
    }
}