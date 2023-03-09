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
        ArrayList<ShapeFrame> listofSelectedShapes = listForShapes.getListofSelectedShapes();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();
        ArrayList<ShapeFrame> shapesGroupList = listForShapes.getListofGroup();

        ShapeFrame shapeFrame1 = shapesGroupList.get(shapesGroupList.size() - 1);

        if (shapesGroupList.size() == 0) {
            System.out.println("there's nothing here!");
        } else if (shapesGroupList.size() == 1) {
            for (ShapeFrame shapeFrame : shapeFrame1.gettheGroup().groupedSubshapes) {
                masterShapeList.add(shapeFrame);
                listofSelectedShapes.add(shapeFrame);
                shapeFrame.gettheShape().selectedShape = true;
            }
            shapeFrame1.gettheGroup().selectedGroup = false;
        } else {

            for (ShapeFrame shapeFrame2 : shapeFrame1.gettheGroup().groupedSubshapes) {
                masterShapeList.add(shapeFrame2);
                listofSelectedShapes.add(shapeFrame2);
                if (!shapeFrame2.isGroup()) {
                    shapeFrame2.gettheShape().selectedShape = true;
                } else {
                    shapeFrame2.gettheGroup().selectedGroup = true;
                }
            }
            shapeFrame1.gettheGroup().selectedGroup = false;
        }

        shapesGroupList.remove(shapeFrame1);
        masterShapeList.remove(shapeFrame1);
        listofSelectedShapes.remove(shapeFrame1);
        undoRedoList.add(shapeFrame1);
        listForShapes.drawerForShapesList(masterShapeList, listofSelectedShapes);
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapesList();
        ArrayList<ShapeFrame> listofSelectedShapes = listForShapes.getListofSelectedShapes();
        ArrayList<ShapeFrame> shapesGroupList = listForShapes.getListofGroup();

        GroupForShapes shapeGroup = new GroupForShapes();

        for (ShapeFrame shapeFrame2 : listofSelectedShapes) {
            masterShapeList.remove(shapeFrame2);
            shapeGroup.addChild(shapeFrame2);
            if (!shapeFrame2.isGroup()) {
                shapeFrame2.gettheShape().selectedShape = false;
            } else {
                shapeFrame2.gettheGroup().selectedGroup = false;
            }
        }

        masterShapeList.add(shapeGroup);
        listofSelectedShapes.clear();
        listofSelectedShapes.add(shapeGroup);
        shapesGroupList.add(shapeGroup);
        shapeGroup.selectedGroup = true;
        listForShapes.drawerForShapesList(masterShapeList, listofSelectedShapes);
    }

    @Override
    public void redo() {

        ArrayList<ShapeFrame> masterShapeList = listForShapes.getShapesList();
        ArrayList<ShapeFrame> listofSelectedShapes = listForShapes.getListofSelectedShapes();
        ArrayList<ShapeFrame> undoRedoList = listForShapes.getUndoRedoList();
        ArrayList<ShapeFrame> shapesGroupList = listForShapes.getListofGroup();

        ShapeFrame outerShapeGroup = shapesGroupList.get(shapesGroupList.size() - 1);

        if (shapesGroupList.size() == 0) {
            System.out.println("there's nothing here!");
        } else if (shapesGroupList.size() == 1) {
            for (ShapeFrame shapeFrame3 : outerShapeGroup.gettheGroup().groupedSubshapes) {
                masterShapeList.add(shapeFrame3);
                listofSelectedShapes.add(shapeFrame3);
                shapeFrame3.gettheShape().selectedShape = true;
            }
            outerShapeGroup.gettheGroup().selectedGroup = false;
        } else {
            for (ShapeFrame s : outerShapeGroup.gettheGroup().groupedSubshapes) {
                masterShapeList.add(s);
                listofSelectedShapes.add(s);
                if (!s.isGroup()) {
                    s.gettheShape().selectedShape = true;
                } else {
                    s.gettheGroup().selectedGroup = true;
                }
            }
            outerShapeGroup.gettheGroup().selectedGroup = false;
        }

        shapesGroupList.remove(outerShapeGroup);
        masterShapeList.remove(outerShapeGroup);
        listofSelectedShapes.remove(outerShapeGroup);
        undoRedoList.add(outerShapeGroup);

        listForShapes.drawerForShapesList(masterShapeList, listofSelectedShapes);
    }
}
