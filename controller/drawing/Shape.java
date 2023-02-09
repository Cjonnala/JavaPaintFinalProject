package controller.drawing;

import model.ShapeShadingType;
import model.ShapeType;
import model.persistence.ApplicationState;

import java.awt.*;

public class Shape {
    public Coordinate startCoordinate;
    public Coordinate endCoordinate;
    public Color pColor;
    public Color sColor;
    public ShapeShadingType shadingType;
    public ShapeType shapeType;
    ApplicationState appState;
    public boolean shapeSelected = false;
    public boolean undoPerformered = false;

    int deltaX;
    int deltaY;

    Shape(Coordinate startCoordinate, Coordinate endCoordinate, ApplicationState appState, Color pColor ,Color sColor, ShapeShadingType shadingType, ShapeType shapeType) {
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
        this.appState = appState;
        this.pColor = pColor;
        this.sColor = sColor;
        this.shadingType = shadingType;
        this.shapeType = shapeType;
    }




    public void undoMove(){
        this.setStartPoint(this.getStartPoint().x -deltaX, this.getStartPoint().y -deltaY);
        this.setEndPoint(this.getEndPoint().x -deltaX, this.getEndPoint().y -deltaY);
    }
    public void redoMove(){
        this.setStartPoint(this.getStartPoint().x +deltaX, this.getStartPoint().y +deltaY);
        this.setEndPoint(this.getEndPoint().x +deltaX, this.getEndPoint().y +deltaY);
    }

    public void move(int deltaX, int deltaY){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.setStartPoint((this.getStartPoint().x)+deltaX, this.getStartPoint().y +deltaY);
        this.setEndPoint(this.getEndPoint().x +deltaX, this.getEndPoint().y +deltaY);
    }


    public Coordinate getStartPoint() {
        return startCoordinate;
    }

    public Coordinate getEndPoint() {
        return endCoordinate;
    }

    public void setStartPoint(int x, int y) {
        this.startCoordinate.x = x;
        this.startCoordinate.y = y;
    }

    public void setEndPoint(int x, int y) {
        this.endCoordinate.x = x;
        this.endCoordinate.y = y;
    }

    public void shapeSelected(){
        shapeSelected = !shapeSelected;
    }
}
