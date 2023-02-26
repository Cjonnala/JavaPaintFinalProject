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

        ArrayList<ShapeFrame> selectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> copiedlist = listForShapes.getCopiedShapeList();
        copiedlist.clear();
        listForShapes.getPasteShapeList().clear();

        copiedlist.addAll(selectedShapeList);
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        ArrayList<ShapeFrame> selectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> copiedlist = listForShapes.getCopiedShapeList();

        selectedShapeList.addAll(copiedlist);
        copiedlist.clear();
    }

    @Override
    public void redo() {
        ArrayList<ShapeFrame> selectedShapeList = listForShapes.getSelectedShapeList();
        ArrayList<ShapeFrame> copiedlist = listForShapes.getCopiedShapeList();

        copiedlist.addAll(selectedShapeList);
    }
}
