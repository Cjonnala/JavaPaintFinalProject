package controller.commands;

import controller.drawing.CommandHistory;
import controller.drawing.GroupForShapes;
import controller.drawing.ListForShapes;
import model.interfaces.IUndoable;
import model.interfaces.ShapeFrame;
import view.interfaces.IEventCallback;
import java.util.ArrayList;

public class UngroupCommand implements IEventCallback, IUndoable {

    ListForShapes listForShapes;

    public UngroupCommand(ListForShapes listForShapes) {
        this.listForShapes = listForShapes;
    }

    @Override
    public void run() {
        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapesList();
        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();
        ArrayList<ShapeFrame> shapesGroupList = listForShapes.getListOfGroup();

        ShapeFrame shapeFrame1 = shapesGroupList.get(shapesGroupList.size() - 1);

        if (shapesGroupList.size() == 1) {
            ArrayList<ShapeFrame> groupedSubShapes = shapeFrame1.gettheGroup().groupedSubShapes;
            for (int i = 0; i < groupedSubShapes.size(); i++) {
                ShapeFrame shapeFrame = groupedSubShapes.get(i);
                masterShapeList.add(shapeFrame);
                listOfSelectedShapes.add(shapeFrame);
                shapeFrame.gettheShape().selectedShape = true;
            }
        } else {


            ArrayList<ShapeFrame> groupedSubShapes = shapeFrame1.gettheGroup().groupedSubShapes;
            for (int i = 0; i < groupedSubShapes.size(); i++) {
                ShapeFrame shapeFrame2 = groupedSubShapes.get(i);
                masterShapeList.add(shapeFrame2);
                listOfSelectedShapes.add(shapeFrame2);
                if (!shapeFrame2.isGroup()) shapeFrame2.gettheShape().selectedShape = true;
                else shapeFrame2.gettheGroup().selectedGroup = true;
            }
        }
        shapeFrame1.gettheGroup().selectedGroup = false;

        shapesGroupList.remove(shapeFrame1);
        masterShapeList.remove(shapeFrame1);
        listOfSelectedShapes.remove(shapeFrame1);
        undoRedoList.add(shapeFrame1);
        listForShapes.drawerForShapesList(masterShapeList, listOfSelectedShapes);
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapesList();
        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> shapesGroupList = listForShapes.getListOfGroup();

        GroupForShapes shapeGroup = new GroupForShapes();

        for (int i = 0; i < listOfSelectedShapes.size(); i++) {
            ShapeFrame shapeFrame2 = listOfSelectedShapes.get(i);
            masterShapeList.remove(shapeFrame2);
            shapeGroup.addSubShape(shapeFrame2);
            if (!shapeFrame2.isGroup()) shapeFrame2.gettheShape().selectedShape = false;
            else shapeFrame2.gettheGroup().selectedGroup = false;
        }

        masterShapeList.add(shapeGroup);
        listOfSelectedShapes.clear();
        listOfSelectedShapes.add(shapeGroup);
        shapesGroupList.add(shapeGroup);
        shapeGroup.selectedGroup = true;
        listForShapes.drawerForShapesList(masterShapeList, listOfSelectedShapes);
    }

    @Override
    public void redo() {

        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapesList();
        ArrayList<ShapeFrame> listOfSelectedShapes = listForShapes.getListOfSelectedShapes();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();
        ArrayList<ShapeFrame> shapesGroupList = listForShapes.getListOfGroup();

        ShapeFrame outerShapeGroup = shapesGroupList.get(shapesGroupList.size() - 1);

        if (shapesGroupList.size() == 0) System.out.println("there's nothing here!");
        else if (shapesGroupList.size() == 1) {
            ArrayList<ShapeFrame> groupedSubshapes = outerShapeGroup.gettheGroup().groupedSubShapes;
            for (int i = 0; i < groupedSubshapes.size(); i++) {
                ShapeFrame shapeFrame3 = groupedSubshapes.get(i);
                masterShapeList.add(shapeFrame3);
                listOfSelectedShapes.add(shapeFrame3);
                shapeFrame3.gettheShape().selectedShape = true;
            }
            outerShapeGroup.gettheGroup().selectedGroup = false;
        } else {
            ArrayList<ShapeFrame> groupedSubshapes = outerShapeGroup.gettheGroup().groupedSubShapes;
            for (int i = 0; i < groupedSubshapes.size(); i++) {
                ShapeFrame s = groupedSubshapes.get(i);
                masterShapeList.add(s);
                listOfSelectedShapes.add(s);
                if (!s.isGroup()) s.gettheShape().selectedShape = true;
                else s.gettheGroup().selectedGroup = true;
            }
            outerShapeGroup.gettheGroup().selectedGroup = false;
        }

        shapesGroupList.remove(outerShapeGroup);
        masterShapeList.remove(outerShapeGroup);
        listOfSelectedShapes.remove(outerShapeGroup);
        undoRedoList.add(outerShapeGroup);

        listForShapes.drawerForShapesList(masterShapeList, listOfSelectedShapes);
    }
}
