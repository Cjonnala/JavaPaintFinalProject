package controller;

import controller.commands.MoveCommand;
import controller.commands.SelectCommand;
import controller.drawing.ColorPalette;
import controller.drawing.Coordinate;
import controller.drawing.ListForShapes;
import controller.drawing.ShapeCreating;
import model.ShapeShadingType;
import model.ShapeType;
import model.persistence.ApplicationState;
import view.interfaces.IEventCallback;
import view.interfaces.PaintCanvasBase;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {

    ApplicationState appState;
    private final ListForShapes listForShapes;
    public Coordinate startCoordinate;
    public Coordinate endCoordinate;
    public PaintCanvasBase paintCanvas;

    public MouseHandler(ApplicationState appState, PaintCanvasBase paintCanvas, ListForShapes listForShapes){
        this.paintCanvas = paintCanvas;
        this.listForShapes = listForShapes;
        this.appState = appState;
    }



    @Override
    public void mousePressed(MouseEvent e) {
        startCoordinate = new Coordinate(e.getX(),e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.endCoordinate = new Coordinate(e.getX(), e.getY());

        ColorPalette primaryColorPalette = new ColorPalette(appState.getActivePrimaryColor());
        Color pColor = primaryColorPalette.getColor();

        ColorPalette secondaryColorPalette = new ColorPalette(appState.getActiveSecondaryColor());
        Color sColor = secondaryColorPalette.getColor();

        ShapeShadingType shadingType = appState.getActiveShapeShadingType();

        ShapeType shapeType = appState.getActiveShapeType();


        switch (appState.getActiveMouseMode()) {
            case DRAW -> {
                IEventCallback createShapeCommand = new ShapeCreating(appState, startCoordinate, endCoordinate, pColor, sColor, listForShapes, shadingType, shapeType);
                createShapeCommand.run();
            }
            case SELECT -> {
                IEventCallback selectShapeCommand = new SelectCommand(appState, startCoordinate, endCoordinate, listForShapes, paintCanvas);
                selectShapeCommand.run();
            }
            case MOVE -> {
                IEventCallback moveShapeCommand = new MoveCommand(appState, startCoordinate, endCoordinate, listForShapes);
                moveShapeCommand.run();
            }

        }

    }

}

