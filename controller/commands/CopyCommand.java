//Jonnala Chaitanya Lakshmi
//This class is created to implement the copy functionality in the paint canvas application
package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.ListForShapes;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;

import java.util.ArrayList;

public class CopyCommand implements IEventCallback, IUndoable {

    public ListForShapes listForShapes;

    public CopyCommand(ListForShapes listForShapes){
        this.listForShapes = listForShapes;
    }
    @Override
    public void run() {

        ArrayList<ShapeFrame> listofSelectedShapes = listForShapes.getListofSelectedShapes();
        ArrayList<ShapeFrame> listofCopiedShapes = listForShapes.getListofCopiedShapes();
        listofCopiedShapes.clear();
        listForShapes.getListofPastedShapes().clear();

        listofCopiedShapes.addAll(listofSelectedShapes);

        CommandHistory.add(this);
    }
//undo for the copy command
    @Override
    public void undo() {
        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListofSelectedShapes();
        ArrayList<ShapeFrame> listOfCopiedShapes = listForShapes.getListofCopiedShapes();

        listOfSelectedShapes.addAll(listOfCopiedShapes);
        listOfCopiedShapes.clear();
    }
//redo for the copy command
    @Override
    public void redo() {
        ArrayList<ShapeFrame> listofSelectedShapes = listForShapes.getListofSelectedShapes();
        ArrayList<ShapeFrame> listofCopiedShapes = listForShapes.getListofCopiedShapes();

        listofCopiedShapes.addAll(listofSelectedShapes);
    }
}
