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

        if (listForShapes.getSelectedShapeList().size()>0){
            for(ShapeFrame shapeFrame : listForShapes.getSelectedShapeList()) {
                if (shapeFrame.getSize() > 0) {
                    shapeFrame.getGroup().moveGroupedSubShapes(newCoordinateX, newCoordinateY);
                } else {
                    shapeFrame.getShape().move(newCoordinateX, newCoordinateY);
                }
            }
            listForShapes.shapeListDrawer(listForShapes.getShapeList(),listForShapes.getSelectedShapeList());
            CommandHistory.add(this);
        }
    }

    @Override
    public void undo() {
        listForShapes.getSelectedShapeList();
        for(ShapeFrame shapeFrame1: listForShapes.getSelectedShapeList()) {
            if (shapeFrame1.getSize() > 0) {
                shapeFrame1.getGroup().undoMovedGroupShapes();
            } else {
                shapeFrame1.getShape().undoMove();
            }
        }
        listForShapes.shapeListDrawer(listForShapes.getShapeList(),listForShapes.getSelectedShapeList());
    }

    @Override
    public void redo() {
        listForShapes.getSelectedShapeList();
        for(ShapeFrame shapeFrame2: listForShapes.getSelectedShapeList()) {
            if (shapeFrame2.getSize() > 0) {
                shapeFrame2.getGroup().redoMovedGroupShapes();
            } else {
                shapeFrame2.getShape().redoMove();
            }
        }
        listForShapes.shapeListDrawer(listForShapes.getShapeList(),listForShapes.getSelectedShapeList());
    }
}