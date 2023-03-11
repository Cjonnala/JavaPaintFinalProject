//Jonnala Chaitanya Lakshmi
//This class is created to implement the delete functionality for the selected shapes in the paint canvas application
package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.ListForShapes;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;

import java.util.ArrayList;

public class DeleteCommand implements IEventCallback, IUndoable {
    private final ListForShapes listForShapes;
    int deleteShapeCount;
    public DeleteCommand(ListForShapes listForShapes){
        this.listForShapes = listForShapes;
    }
    @Override
    public void run() {
        ArrayList<ShapeFrame> shapesList = listForShapes.getShapesList();
        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> listOfDeletedShapes = listForShapes.getListOfDeletedShapes();

        for (int i = listOfSelectedShapes.size() - 1; i >= 0; i--) {
            ShapeFrame shapeFrame = listOfSelectedShapes.get(i);
            shapesList.remove(shapeFrame);
            listOfDeletedShapes.add(shapeFrame);
            if (shapeFrame.gettheSize() == 0) {
                shapeFrame.gettheShape().selectedShape = false;
            }
            deleteShapeCount++;
        }
        listOfSelectedShapes.clear();
        listForShapes.drawerForShapesList(shapesList, listOfSelectedShapes);
        CommandHistory.add(this);
    }

//undo for the delete functionality
    @Override
    public void undo() {
        ArrayList<ShapeFrame> mainShapeList = listForShapes.getShapesList();
        ArrayList<ShapeFrame> mySelectedShapeList = listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> deletedShapeList = listForShapes.getListOfDeletedShapes();
        if (deleteShapeCount == 0) {
            deleteShapeCount = deletedShapeList.size();
        }
        for (int i = 0; i < deleteShapeCount && !deletedShapeList.isEmpty(); i++) {
            ShapeFrame finalShape = deletedShapeList.remove(deletedShapeList.size() - 1);
            mainShapeList.add(finalShape);
            mySelectedShapeList.add(finalShape);
            if(finalShape.isGroup()){
                finalShape.gettheGroup().selectedGroup=true;
            }
            else{
                finalShape.gettheShape().selectedShape=true;
            }
        }
        listForShapes.drawerForShapesList(mainShapeList, mySelectedShapeList);
    }
    //redo for the delete functionality
    @Override
    public void redo() {
        ArrayList<ShapeFrame> mainShapeList = listForShapes.getShapesList();
        ArrayList<ShapeFrame> selectedShapes = listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> listofDeletedShapes = listForShapes.getListOfDeletedShapes();
        for (int i = 0; i < deleteShapeCount && !listofDeletedShapes.isEmpty(); i++) {
            ShapeFrame finalShape = listofDeletedShapes.remove(listofDeletedShapes.size() - 1);
            mainShapeList.add(finalShape);
            if(finalShape.isGroup()){
                finalShape.gettheGroup().selectedGroup=true;
            }
            else{
                finalShape.gettheShape().selectedShape=true;
            }
        }
        selectedShapes.clear();
        listForShapes.drawerForShapesList(mainShapeList, selectedShapes);
        deleteShapeCount--;
    }
}


