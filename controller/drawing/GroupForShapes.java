package controller.drawing;

import model.interfaces.ShapeFrame;

import java.awt.*;
import java.util.ArrayList;

public class GroupForShapes implements ShapeFrame {

    ShapeFrame shape;
    Graphics2D g;
    int newX, newY;

    public boolean selectedGroup;
    public ArrayList<ShapeFrame> groupedSubshapes;

    public GroupForShapes() {
        groupedSubshapes = new ArrayList<>();
    }

    public ArrayList<ShapeFrame> getGroupedSubshapes() {
        return groupedSubshapes;
    }

    public void addChild(ShapeFrame shape) {
        groupedSubshapes.add(shape);
    }

    public ShapeFrame removeChild(int i) {
        ShapeFrame shapeFrame;
        shapeFrame = this.groupedSubshapes.remove(i);
        return shapeFrame;
    }

    public int gettheSize() {
        int size = 0;
        for (ShapeFrame ignored : groupedSubshapes) {
            size++;
        }
        return size;
    }


    public Point getMinimumCoordXY() {
        int startShapeX = 9999;
        int startShapeY = 9999;
        for (ShapeFrame child : groupedSubshapes) {
            if (!child.isGroup()) {
                if (child.gettheShape().getMinimumXY().x < startShapeX) {
                    startShapeX = child.gettheShape().getMinimumXY().x;
                }
                if (child.gettheShape().getMinimumXY().y < startShapeY) {
                    startShapeY = child.gettheShape().getMinimumXY().y;

                }
            } else {
                if (child.gettheGroup().getMinimumCoordXY().x < startShapeX) {
                    startShapeX = child.gettheGroup().getMinimumCoordXY().x;
                }
                if (child.gettheGroup().getMinimumCoordXY().y < startShapeY) {
                    startShapeY = child.gettheGroup().getMinimumCoordXY().y;
                }
            }


        }
        return new Point(startShapeX, startShapeY);
    }

    public Point getMaximumCoordXY() {
        int endShapeX = 0;
        int endShapeY = 0;
        for (ShapeFrame child : groupedSubshapes) {
            if (!child.isGroup()) {
                if (child.gettheShape().getMaximumXY().x > endShapeX) {
                    endShapeX = child.gettheShape().getMaximumXY().x;
                }
                if (child.gettheShape().getMaximumXY().y > endShapeY) {
                    endShapeY = child.gettheShape().getMaximumXY().y;
                }
            } else {
                if (child.gettheGroup().getMaximumCoordXY().x > endShapeX) {
                    endShapeX = child.gettheGroup().getMaximumCoordXY().x;
                }
                if (child.gettheGroup().getMaximumCoordXY().y > endShapeY) {
                    endShapeY = child.gettheGroup().getMaximumCoordXY().y;
                }
            }

        }
        return new Point(endShapeX, endShapeY);
    }

    @Override
    public void drawSubShape(Graphics2D g) {
        this.g = g;
        for (ShapeFrame shapeFrame : groupedSubshapes) {
            if (shapeFrame.gettheSize() > 0) {
                shapeFrame.drawSubShape(g);
            } else {
                shapeFrame.draw(g);
            }
        }
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public Coordinate getStartCoordinate() {
        return null;
    }

    @Override
    public Coordinate getEndCoordinate() {
        return null;
    }

    @Override
    public Shape gettheShape() {
        return null;
    }

    @Override
    public GroupForShapes gettheGroup() {
        return this;
    }

    public void moveGroupedSubShapes(int newCoordinateX, int newCoordinateY){
        this.newX = newCoordinateX;
        this.newY = newCoordinateY;
        for(ShapeFrame shapeFrame : groupedSubshapes){
            shapeFrame.gettheShape().setStartCoordinate((shapeFrame.gettheShape().getStartCoordinate().x)+newCoordinateX, (shapeFrame.gettheShape().getStartCoordinate().y)+newCoordinateY);
            shapeFrame.gettheShape().setEndCoordinate((shapeFrame.gettheShape().getEndCoordinate().x)+newCoordinateX, (shapeFrame.gettheShape().getEndCoordinate().y)+newCoordinateY);
        }
    }

    public void undoMovedGroupShapes(){
        for(ShapeFrame shapeFrame1 : groupedSubshapes){
            shapeFrame1.gettheShape().setStartCoordinate((shapeFrame1.gettheShape().getStartCoordinate().x)- newX, (shapeFrame1.gettheShape().getStartCoordinate().y)- newY);
            shapeFrame1.gettheShape().setEndCoordinate((shapeFrame1.gettheShape().getEndCoordinate().x)- newX, (shapeFrame1.gettheShape().getEndCoordinate().y)- newY);
        }
    }

    public void redoMovedGroupShapes() {
        for (ShapeFrame shapeFrame2 : groupedSubshapes) {
            shapeFrame2.gettheShape().setStartCoordinate((shapeFrame2.gettheShape().getStartCoordinate().x) + newX, (shapeFrame2.gettheShape().getStartCoordinate().y) + newY);
            shapeFrame2.gettheShape().setEndCoordinate((shapeFrame2.gettheShape().getEndCoordinate().x) + newX, (shapeFrame2.gettheShape().getEndCoordinate().y) + newY);
        }
    }
}
