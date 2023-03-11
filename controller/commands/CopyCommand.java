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

    /**
     * @param listForShapes
     *
     */
    public CopyCommand(ListForShapes listForShapes){
        this.listForShapes = listForShapes;
    }
    @Override
    public void run() {

        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> listOfCopiedShapes = listForShapes.getListOfCopiedShapes();
        listOfCopiedShapes.clear();
        listForShapes.getListOfPastedShapes().clear();

        listOfCopiedShapes.addAll(listOfSelectedShapes);

        CommandHistory.add(this);
    }
//undo for the copy command
    @Override
    public void undo() {
        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> listOfCopiedShapes = listForShapes.getListOfCopiedShapes();

        listOfSelectedShapes.addAll(listOfCopiedShapes);
        listOfCopiedShapes.clear();
    }
//redo for the copy command
    @Override
    public void redo() {
        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> listOfCopiedShapes = listForShapes.getListOfCopiedShapes();

        listOfCopiedShapes.addAll(listOfSelectedShapes);
    }
}
