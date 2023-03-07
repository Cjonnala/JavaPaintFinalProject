package controller.drawing;

import model.interfaces.ShapeFrame;

import java.awt.*;
import java.util.ArrayList;

public class GroupForShapes implements ShapeFrame {

    ShapeFrame shape;
    public ArrayList<ShapeFrame> groupedSubshapes;
    Graphics2D g;
    public boolean groupSelected;
    int newX, newY;

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

    public int getSize() {
        int size = 0;
        for (ShapeFrame ignored : groupedSubshapes) {
            size++;
        }
        return size;
    }


    public Point getMinXY() {
        int shapeStartX = 9999;
        int shapeStartY = 9999;
        for (ShapeFrame child : groupedSubshapes) {
            if (!child.isGroup()) {
                if (child.getShape().getMinXY().x < shapeStartX) {
                    shapeStartX = child.getShape().getMinXY().x;
                }
                if (child.getShape().getMinXY().y < shapeStartY) {
                    shapeStartY = child.getShape().getMinXY().y;

                }
            } else {
                if (child.getGroup().getMinXY().x < shapeStartX) {
                    shapeStartX = child.getGroup().getMinXY().x;
                }
                if (child.getGroup().getMinXY().y < shapeStartY) {
                    shapeStartY = child.getGroup().getMinXY().y;
                }
            }


        }
        return new Point(shapeStartX, shapeStartY);
    }

    public Point getMaxXY() {
        int shapeEndX = 0;
        int shapeEndY = 0;
        for (ShapeFrame child : groupedSubshapes) {
            if (!child.isGroup()) {
                if (child.getShape().getMaxXY().x > shapeEndX) {
                    shapeEndX = child.getShape().getMaxXY().x;
                }
                if (child.getShape().getMaxXY().y > shapeEndY) {
                    shapeEndY = child.getShape().getMaxXY().y;
                }
            } else {
                if (child.getGroup().getMaxXY().x > shapeEndX) {
                    shapeEndX = child.getGroup().getMaxXY().x;
                }
                if (child.getGroup().getMaxXY().y > shapeEndY) {
                    shapeEndY = child.getGroup().getMaxXY().y;
                }
            }

        }
        return new Point(shapeEndX, shapeEndY);
    }

    @Override
    public void drawChildren(Graphics2D g) {
        this.g = g;
        for (ShapeFrame s : groupedSubshapes) {
            if (s.getSize() > 0) {
                s.drawChildren(g);
            } else {
                s.draw(g);
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
    public Shape getShape() {
        return null;
    }

    @Override
    public GroupForShapes getGroup() {
        return this;
    }
}
