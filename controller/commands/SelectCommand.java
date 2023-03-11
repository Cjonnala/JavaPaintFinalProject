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
        ArrayList<ShapeFrame> shapesList = listForShapes.getShapesList();
        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
        listOfSelectedShapes.clear();

        int clickStartX = Math.min(this.startCoordinate.getX(), this.endCoordinate.getX());
        int clickEndX = Math.max(this.startCoordinate.getX(), this.endCoordinate.getX());
        int clickStartY = Math.min(this.startCoordinate.getY(), this.endCoordinate.getY());
        int clickEndY = Math.max(this.startCoordinate.getY(), this.endCoordinate.getY());
        int w = clickEndX - clickStartX;
        int l = clickEndY - clickStartY;

        for (int i = 0; i < shapesList.size(); i++) {
            ShapeFrame s = shapesList.get(i);
            if (s.isGroup()) {
                int groupWidth = s.gettheGroup().getMaximumCordXY().x - s.gettheGroup().getMinimumCoordXY().x;
                int groupHeight = s.gettheGroup().getMaximumCordXY().x - s.gettheGroup().getMinimumCoordXY().x;

                if (s.gettheGroup().getMinimumCoordXY().x + groupWidth > clickStartX
                        && s.gettheGroup().getMinimumCoordXY().y + groupHeight > clickStartY
                        && clickStartX + w > s.gettheGroup().getMinimumCoordXY().x
                        && clickStartY + l > s.gettheGroup().getMinimumCoordXY().y) {
                    s.gettheGroup().selectedGroup = true;
                    listOfSelectedShapes.add(s);

                }
            } else {
                int shapeStartX = Math.min(s.gettheShape().getStartCoordinate().getX(), s.gettheShape().getEndCoordinate().getX());
                int shapeEndX = Math.max(s.gettheShape().getStartCoordinate().getX(), s.gettheShape().getEndCoordinate().getX());
                int shapeStartY = Math.min(s.gettheShape().getStartCoordinate().getY(), s.gettheShape().getEndCoordinate().getY());
                int shapeEndY = Math.max(s.gettheShape().getStartCoordinate().getY(), s.gettheShape().getEndCoordinate().getY());

                int shapeWidth = shapeEndX - shapeStartX;
                int shapeHeight = shapeEndY - shapeStartY;

                if (shapeStartX + shapeWidth > clickStartX
                        && shapeStartY + shapeHeight > clickStartY
                        && clickStartX + w > shapeStartX
                        && clickStartY + l > shapeStartY) {
                    s.gettheShape().shapeSelected();
                    listOfSelectedShapes.add(s);

                }
            }
            listForShapes.drawerForShapesList(shapesList, listOfSelectedShapes);
            CommandHistory.add(this);

        }
    }

    @Override
    public void undo() {
        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> listOfDeselectedShapes = ListForShapes.listOfDeselectedShapes;
        for (int i = 0; i < listOfSelectedShapes.size(); i++) {
            ShapeFrame selectedShape = listOfSelectedShapes.get(i);
            listOfDeselectedShapes.add(selectedShape);
            if (selectedShape.isGroup()) selectedShape.gettheGroup().selectedGroup = false;
            else selectedShape.gettheShape().selectedShape = false;
        }
        listOfSelectedShapes.clear();
        listForShapes.drawerForShapesList(listForShapes.getShapesList(),listForShapes.getListOfSelectedShapes());
    }

    @Override
    public void redo() {
        ArrayList<ShapeFrame> listOfDeselectedShapes = ListForShapes.listOfDeselectedShapes;
        for (int i = 0; i < listOfDeselectedShapes.size(); i++) {
            ShapeFrame deselectedShape = listOfDeselectedShapes.get(i);
            if (deselectedShape.isGroup()) deselectedShape.gettheGroup().selectedGroup = true;
            else deselectedShape.gettheShape().selectedShape = true;
        }
        listOfDeselectedShapes.clear();
        listForShapes.drawerForShapesList(listForShapes.getShapesList(),listForShapes.getListOfSelectedShapes());

    }
}
