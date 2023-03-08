package controller.drawing;

import model.ShapeShadingType;
import model.ShapeType;
import model.persistence.ApplicationState;

import java.awt.*;

public class Shape {
    public Coordinate startCoordinate;
    public Coordinate endCoordinate;
    public static Color pColor;
    public static Color sColor;
    public ShapeShadingType shadingType;
    public ShapeType shapeType;
    public ApplicationState appState;
    public boolean shapeSelected = false;
    public boolean undoPerformered = false;
    public boolean shapePasted = false;

    int deltaX;
    int deltaY;

    Shape(Coordinate startCoordinate, Coordinate endCoordinate, ApplicationState appState, Color pColor ,Color sColor, ShapeShadingType shadingType, ShapeType shapeType) {
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
        this.appState = appState;
        Shape.pColor = pColor;
        Shape.sColor = sColor;
        this.shadingType = shadingType;
        this.shapeType = shapeType;
    }




    public void undoMove(){
        this.setStartCoordinate(this.getStartCoordinate().x -deltaX, this.getStartCoordinate().y -deltaY);
        this.setEndCoordinate(this.getEndCoordinate().x -deltaX, this.getEndCoordinate().y -deltaY);
    }
    public void redoMove(){
        this.setStartCoordinate(this.getStartCoordinate().x +deltaX, this.getStartCoordinate().y +deltaY);
        this.setEndCoordinate(this.getEndCoordinate().x +deltaX, this.getEndCoordinate().y +deltaY);
    }

    public void move(int deltaX, int deltaY){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.setStartCoordinate((this.getStartCoordinate().x)+deltaX, this.getStartCoordinate().y +deltaY);
        this.setEndCoordinate(this.getEndCoordinate().x +deltaX, this.getEndCoordinate().y +deltaY);
    }


    public Coordinate getStartCoordinate() {
        return startCoordinate;
    }

    public Coordinate getEndCoordinate() {
        return endCoordinate;
    }

    public void setStartCoordinate(int x, int y) {
        this.startCoordinate.x = x;
        this.startCoordinate.y = y;
    }

    public void setEndCoordinate(int x, int y) {
        this.endCoordinate.x = x;
        this.endCoordinate.y = y;
    }

    public void shapeSelected(){
        shapeSelected = !shapeSelected;
    }
    public ShapeType getShapeType() {
        return shapeType;
    }

    public Coordinate getMinXY(){
        int mouseStartX = Math.min(this.startCoordinate.getX(), this.endCoordinate.getX());
        int mouseStartY = Math.min(this.startCoordinate.getY(), this.endCoordinate.getY());

        return new Coordinate(mouseStartX,mouseStartY);
    }

    public int getWidth(){
        int startX = Math.min(this.startCoordinate.getX(), this.endCoordinate.getX());
        int endX = Math.max(this.startCoordinate.getX(), this.endCoordinate.getX());
        return endX - startX;
    }

    public int getHeight(){
        int startY = Math.min(this.startCoordinate.getY(), this.endCoordinate.getY());
        int endY = Math.max(this.startCoordinate.getY(), this.endCoordinate.getY());
        return endY - startY;

    }

    public Coordinate getMaximumCoordXY(){
        int mouseEndX = Math.max(this.startCoordinate.getX(), this.endCoordinate.getX());
        int mouseEndY = Math.max(this.startCoordinate.getY(), this.endCoordinate.getY());

        return new Coordinate(mouseEndX,mouseEndY);
    }
    public static Color getpColor(){
        return pColor;
    }

    public static Color getsColor(){
        return sColor;
    }

    public ShapeShadingType getShadingType() {
        return shadingType;
    }
}

