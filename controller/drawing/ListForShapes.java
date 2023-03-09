package controller.drawing;

import model.interfaces.ShapeFrame;
import model.persistence.ApplicationState;
import view.gui.PaintCanvas;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.util.ArrayList;

public class ListForShapes {

    public static ArrayList<ShapeFrame> shapeslist = new ArrayList<>();
    public static ArrayList<ShapeFrame> listofdeletedshapes = new ArrayList<>();
    public static ArrayList<ShapeFrame> listofselectedshapes = new ArrayList<>();
    public static ArrayList<ShapeFrame> listofdeselectedshapes = new ArrayList<>();
    public static ArrayList<ShapeFrame> listofcopiedshapes = new ArrayList<>();
    public static ArrayList<ShapeFrame> listofpastedshapes = new ArrayList<>();
    public static ArrayList<ShapeFrame> undoRedoList = new ArrayList<>();
    public static ArrayList<ShapeFrame> listofgroup = new ArrayList<>();

    private static PaintCanvas paintCanvas;
    public ApplicationState appState;


    public ListForShapes(PaintCanvasBase paintCanvas, ApplicationState appState) {
        ListForShapes.paintCanvas = (PaintCanvas) paintCanvas;
        this.appState = appState;
    }

    public void addShape(ShapeFrame shape) {
        shapeslist.add(shape);
        drawerForShapesList(shapeslist, listofselectedshapes);
    }


    public void drawerForShapesList(ArrayList<ShapeFrame> shapeList, ArrayList<ShapeFrame> selectedShapeList){

        Graphics2D g = paintCanvas.getGraphics2D();
        g.setColor(Color.white);
        g.fillRect(0,0,9999,9999);
        for (ShapeFrame s: shapeList){
            s.draw(g);
            if(s.gettheSize()>0){
                s.drawSubShape(g);
            }
        }
        if(selectedShapeList.size()>0){
            for (ShapeFrame z: selectedShapeList){
                ShapeOutline shapeOutline = new ShapeOutline(paintCanvas);
                if(z.isGroup()){
                    shapeOutline.outlineGroup(z);
                }
                else {
                    shapeOutline.outlineShape(z);
                }
            }
        }

//        CommandHistory.add(this);
    }

    public void removeShape() {
        if (shapeslist.size() == 0) {
            System.out.println("There's nothing in the list to remove!");
            return;
        }
        ShapeFrame lastShape = shapeslist.get(shapeslist.size() - 1);
        lastShape.gettheShape().selectedShape = false;
        shapeslist.remove(lastShape);
        listofdeletedshapes.add(lastShape);
        drawerForShapesList(shapeslist, listofselectedshapes);
    }

    public void redoShape() {
        if (listofdeletedshapes.size() == 0 && shapeslist.size() == 0) {
            System.out.println("There's nothing to redo!");
        } else if (listofdeletedshapes.size() == 0) {
            ShapeFrame lastShape = shapeslist.get(shapeslist.size() - 1);
            if (lastShape.gettheShape().undoDone) {
                lastShape.gettheShape().redoMove();
                lastShape.gettheShape().undoDone = false;
                lastShape.gettheShape().shapeSelected();
                drawerForShapesList(shapeslist, listofselectedshapes);
            }
        } else {
            addDeletedShapes();
        }
    }

    public void addDeletedShapes() {
        ShapeFrame d = listofdeletedshapes.remove(listofdeletedshapes.size() - 1);
        shapeslist.add(d);
        d.gettheShape().selectedShape = true;

        drawerForShapesList(shapeslist, listofselectedshapes);
    }


    public ArrayList<ShapeFrame> getListofSelectedShapes() {return listofselectedshapes;
    }

    public ArrayList<ShapeFrame> getShapesList() {
        return shapeslist;
    }

    public ArrayList<ShapeFrame> getListofDeletedShapes() {
        return listofdeletedshapes;
    }

    public ArrayList<ShapeFrame> getListofCopiedShapes() {return listofcopiedshapes;
    }

    public ArrayList<ShapeFrame> getListofPastedShapes() {
        return listofpastedshapes;
    }

    public ArrayList<ShapeFrame> getUndoRedoList() {
        return undoRedoList;
    }
    public ArrayList<ShapeFrame> getListofGroup() { return listofgroup;}
}