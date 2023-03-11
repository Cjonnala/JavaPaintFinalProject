package controller.drawing;

import model.interfaces.ShapeFrame;
import model.persistence.ApplicationState;
import view.gui.PaintCanvas;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.util.ArrayList;

public class ListForShapes {

    public static ArrayList<ShapeFrame> shapesList = new ArrayList<>();
    public static ArrayList<ShapeFrame> listOfDeletedShapes = new ArrayList<>();
    public static ArrayList<ShapeFrame> listOfSelectedShapes = new ArrayList<>();
    public static ArrayList<ShapeFrame> listOfDeselectedShapes = new ArrayList<>();
    public static ArrayList<ShapeFrame> listOfCopiedShapes = new ArrayList<>();
    public static ArrayList<ShapeFrame> listOfPastedShapes = new ArrayList<>();
    public static ArrayList<ShapeFrame> undoRedoList = new ArrayList<>();
    public static ArrayList<ShapeFrame> listOfGroup = new ArrayList<>();

    private static PaintCanvas paintCanvas;
    public ApplicationState appState;


    public ListForShapes(PaintCanvasBase paintCanvas, ApplicationState appState) {
        ListForShapes.paintCanvas = (PaintCanvas) paintCanvas;
        this.appState = appState;
    }

    public void addShape(ShapeFrame shape) {
        shapesList.add(shape);
        drawerForShapesList(shapesList, listOfSelectedShapes);
    }


    public void drawerForShapesList(ArrayList<ShapeFrame> shapeList, ArrayList<ShapeFrame> selectedShapeList){

        Graphics2D g = paintCanvas.getGraphics2D();
        g.setColor(Color.white);
        g.fillRect(0,0,9999,9999);
        for (int i = 0, shapeListSize = shapeList.size(); i < shapeListSize; i++) {
            ShapeFrame s = shapeList.get(i);
            s.draw(g);
            if (s.gettheSize() > 0) s.drawSubShape(g);
        }
        if(selectedShapeList.size()>0){
            for (int i = 0, selectedShapeListSize = selectedShapeList.size(); i < selectedShapeListSize; i++) {
                ShapeFrame z = selectedShapeList.get(i);
                ShapeOutline shapeOutline = new ShapeOutline(paintCanvas);
                if (z.isGroup()) shapeOutline.outlineGroup(z);
                else shapeOutline.outlineShape(z);
            }
        }
    }

    public void removeShape() {
        ShapeFrame lastShape = shapesList.get(shapesList.size() - 1);
        lastShape.gettheShape().selectedShape = false;
        shapesList.remove(lastShape);
        listOfDeletedShapes.add(lastShape);
        drawerForShapesList(shapesList, listOfSelectedShapes);
    }

    public void redoShape() {
          if (listOfDeletedShapes.size() == 0) {
            ShapeFrame lastShape = shapesList.get(shapesList.size() - 1);
            if (lastShape.gettheShape().undoDone) {
                lastShape.gettheShape().redoMove();
                lastShape.gettheShape().undoDone = false;
                lastShape.gettheShape().shapeSelected();
                drawerForShapesList(shapesList, listOfSelectedShapes);
            }
        } else {
            addDeletedShapes();
        }
    }

    public void addDeletedShapes() {
        ShapeFrame d = listOfDeletedShapes.remove(listOfDeletedShapes.size() - 1);
        shapesList.add(d);
        d.gettheShape().selectedShape = true;

        drawerForShapesList(shapesList, listOfSelectedShapes);
    }


    public ArrayList<ShapeFrame> getListOfSelectedShapes() {return listOfSelectedShapes;
    }

    public ArrayList<ShapeFrame> getShapesList() {
        return shapesList;
    }

    public ArrayList<ShapeFrame> getListOfDeletedShapes() {
        return listOfDeletedShapes;
    }

    public ArrayList<ShapeFrame> getListOfCopiedShapes() {return listOfCopiedShapes;
    }

    public ArrayList<ShapeFrame> getListOfPastedShapes() {
        return listOfPastedShapes;
    }

    public ArrayList<ShapeFrame> getUndoRedoList() {
        return undoRedoList;
    }
    public ArrayList<ShapeFrame> getListOfGroup() { return listOfGroup;}
}