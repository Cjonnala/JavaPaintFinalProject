package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.Coordinate;
import controller.drawing.ListForShapes;
import controller.drawing.ShapeCreating;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;

import java.util.ArrayList;

public class PasteCommand implements IEventCallback, IUndoable {
    private final ListForShapes listForShapes;
    public int pasteCount;
    public PasteCommand(ListForShapes listForShapes){
        this.listForShapes = listForShapes;
    }
    @Override
    public void run() {
        pasteCount =0;
        ArrayList<ShapeFrame> listOfCopiedShapes = listForShapes.getListOfCopiedShapes();
        ArrayList<ShapeFrame> listOfPastedShapes = listForShapes.getListOfPastedShapes();
        for(ShapeFrame shapeFrame: listOfPastedShapes){
            shapeFrame.gettheShape().pastedShape =false;
        }
        for(ShapeFrame shapeFrame: listOfCopiedShapes) {
            if (!shapeFrame.isGroup()) {
                int x1 = shapeFrame.gettheShape().getStartCoordinate().x - 100;
                int y1 = shapeFrame.gettheShape().getStartCoordinate().y - 100;
                int x2 = shapeFrame.gettheShape().getEndCoordinate().x - 100;
                int y2 = shapeFrame.gettheShape().getEndCoordinate().y - 100;
                Coordinate coordinateX = new Coordinate(x1, y1);
                Coordinate coordinateY = new Coordinate(x2, y2);
                IEventCallback createShapeCommand = new ShapeCreating(shapeFrame.gettheShape().appState, coordinateX, coordinateY, shapeFrame.gettheShape().getPrimaryColor(), shapeFrame.gettheShape().getSecondaryColor(), listForShapes, shapeFrame.gettheShape().getShadingType(), shapeFrame.gettheShape().getShapeType());
                createShapeCommand.run();
                pasteCount++;
            } else {
                for (ShapeFrame shapeFrame1 : shapeFrame.gettheGroup().groupedSubShapes) {
                    int i1 = shapeFrame1.gettheShape().getStartCoordinate().x - 100;
                    int j1 = shapeFrame1.gettheShape().getStartCoordinate().y - 100;
                    int i2 = shapeFrame1.gettheShape().getEndCoordinate().x - 100;
                    int j2 = shapeFrame1.gettheShape().getEndCoordinate().y - 100;
                    Coordinate coordinateX1 = new Coordinate(i1, j1);
                    Coordinate coordinateX2 = new Coordinate(i2, j2);
                    IEventCallback shapeCreating = new ShapeCreating(shapeFrame1.gettheShape().appState, coordinateX1, coordinateX2, shapeFrame1.gettheShape().getPrimaryColor(), shapeFrame1.gettheShape().getSecondaryColor(), listForShapes, shapeFrame1.gettheShape().getShadingType(), shapeFrame1.gettheShape().getShapeType());
                    shapeCreating.run();
                    pasteCount++;
                }
            }
        }

        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapesList();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();

        if(masterShapeList.size() == 0) {
            return;
        }

        while(pasteCount !=0){
            ShapeFrame lastShape = masterShapeList.get(masterShapeList.size()-1);
            masterShapeList.remove(lastShape);
            undoRedoList.add(lastShape);
            listForShapes.drawerForShapesList(masterShapeList, listForShapes.getListOfSelectedShapes());
            pasteCount--;
        }
    }

    @Override
    public void redo() {

        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapesList();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();

        if(masterShapeList.size() == 0) {
        }
        else{

            while(undoRedoList.size()!=0) {
                ShapeFrame finalShape = undoRedoList.get(undoRedoList.size() - 1);
                undoRedoList.remove(finalShape);
                masterShapeList.add(finalShape);
                listForShapes.drawerForShapesList(masterShapeList, listForShapes.getListOfSelectedShapes());
                pasteCount++;
            }
        }
    }
}

