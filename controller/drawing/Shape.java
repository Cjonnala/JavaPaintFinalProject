package controller.drawing;

import model.ShapeShadingType;
import model.ShapeType;
import model.persistence.ApplicationState;

import java.awt.*;

public class Shape {
    public ShapeType shapeType;

    public Coordinate startCoordinate;

    public Coordinate endCoordinate;
    public ApplicationState appState;

    public static Color primaryColor;
    public static Color secondaryColor;

    public boolean selectedShape = false;
    public boolean undoDone = false;
    public ShapeShadingType shadingType;
    public boolean pastedShape = false;

    int deltaX;
    int deltaY;

    Shape(Coordinate startCoordinate, Coordinate endCoordinate, ApplicationState appState, Color primaryColor, Color secondaryColor, ShapeShadingType shadingType, ShapeType shapeType) {
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
        this.appState = appState;
        Shape.primaryColor = primaryColor;
        Shape.secondaryColor = secondaryColor;
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

    public Coordinate getEndCoordinate() {return endCoordinate;}

    public void setStartCoordinate(int x, int y) {
        this.startCoordinate.x = x;
        this.startCoordinate.y = y;
    }

    public void setEndCoordinate(int x, int y) {
        this.endCoordinate.x = x;
        this.endCoordinate.y = y;
    }

    public void shapeSelected(){
        selectedShape = !selectedShape;
    }
    public ShapeType getShapeType() {
        return shapeType;
    }

    public Coordinate getMinimumXY(){
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

    public Coordinate getMaximumXY(){
        int mouseEndX = Math.max(this.startCoordinate.getX(), this.endCoordinate.getX());
        int mouseEndY = Math.max(this.startCoordinate.getY(), this.endCoordinate.getY());

        return new Coordinate(mouseEndX,mouseEndY);
    }
    public static Color getPrimaryColor(){
        return primaryColor;
    }

    public static Color getSecondaryColor(){
        return secondaryColor;
    }

    public ShapeShadingType getShadingType() {
        return shadingType;
    }
}

