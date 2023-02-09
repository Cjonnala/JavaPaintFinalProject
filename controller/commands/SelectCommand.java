package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.Coordinate;
import controller.drawing.ListForShapes;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import model.persistence.ApplicationState;
import view.interfaces.IEventCallback;
import view.interfaces.PaintCanvasBase;

import java.util.ArrayList;

public class SelectCommand implements IEventCallback, IUndoable {

    PaintCanvasBase paintCanvas;
    private final Coordinate startCoordinate;
    private final Coordinate endCoordinate;
    private final ListForShapes listForShapes;
    ApplicationState appState;

    public SelectCommand(ApplicationState appState, Coordinate startCoordinate, Coordinate endCoordinate, ListForShapes listForShapes, PaintCanvasBase paintCanvas){
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
        this.listForShapes = listForShapes;
        this.appState = appState;
        this.paintCanvas = paintCanvas;
    }

    @Override
    public void run() {
        ArrayList<ShapeFrame> myShapeList = listForShapes.getShapeList();
        ArrayList<ShapeFrame> mySelectedShapeList = listForShapes.getSelectedShapeList();
        mySelectedShapeList.clear();

        int clickStartX = Math.min(this.startCoordinate.getX(), this.endCoordinate.getX());
        int clickEndX = Math.max(this.startCoordinate.getX(), this.endCoordinate.getX());
        int clickStartY = Math.min(this.startCoordinate.getY(), this.endCoordinate.getY());
        int clickEndY = Math.max(this.startCoordinate.getY(), this.endCoordinate.getY());
        int w = clickEndX - clickStartX;
        int l = clickEndY - clickStartY;

        for (ShapeFrame s : myShapeList) {

            int shapeStartX = Math.min(s.getShape().getStartPoint().getX(), s.getShape().getEndPoint().getX());
            int shapeEndX = Math.max(s.getShape().getStartPoint().getX(), s.getShape().getEndPoint().getX());
            int shapeStartY = Math.min(s.getShape().getStartPoint().getY(), s.getShape().getEndPoint().getY());
            int shapeEndY = Math.max(s.getShape().getStartPoint().getY(), s.getShape().getEndPoint().getY());

            int shapeWidth = shapeEndX - shapeStartX;
            int shapeHeight = shapeEndY - shapeStartY;

            if (shapeStartX + shapeWidth > clickStartX
                    && shapeStartY + shapeHeight > clickStartY
                    && clickStartX + w > shapeStartX
                    && clickStartY + l > shapeStartY) {
                s.getShape().shapeSelected();
                mySelectedShapeList.add(s);

            }
        }
        listForShapes.shapeListDrawer(myShapeList);
        CommandHistory.add(this);

    }

    @Override
    public void undo() {
        ArrayList<ShapeFrame> selectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> deselectedShapeList = ListForShapes.deselectedShapeList;
        for(ShapeFrame d: selectedShapeList){
            deselectedShapeList.add(d);
            d.getShape().shapeSelected=false;
        }
        selectedShapeList.clear();
        listForShapes.shapeListDrawer(listForShapes.getShapeList());
    }

    @Override
    public void redo() {
        ArrayList<ShapeFrame> selectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> deselectedShapeList = ListForShapes.deselectedShapeList;
        for(ShapeFrame d: deselectedShapeList){
            selectedShapeList.add(d);
            d.getShape().shapeSelected=true;
        }
        deselectedShapeList.clear();
        listForShapes.shapeListDrawer(listForShapes.getShapeList());

    }
}
