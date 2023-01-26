package controller.drawing;

import model.ShapeColor;

import java.awt.*;

public class ColorPalette {

    private ShapeColor colorEnum;
    private java.awt.Color color;

    public ColorPalette(ShapeColor colorEnum){
        this.colorEnum = colorEnum;

        switch(colorEnum){
            case BLACK:
                this.color = Color.BLACK;
                break;
            case BLUE:
                this.color = Color.BLUE;
                break;
            case CYAN:
                this.color = Color.CYAN;
                break;
            case DARK_GRAY:
                this.color = Color.darkGray;
                break;
            case GRAY:
                this.color = Color.GRAY;
                break;
            case GREEN:
                this.color = Color.GREEN;
                break;
            case LIGHT_GRAY:
                this.color = Color.LIGHT_GRAY;
                break;
            case MAGENTA:
                this.color = Color.MAGENTA;
                break;
            case ORANGE:
                this.color = Color.ORANGE;
                break;
            case RED:
                this.color = Color.red;
                break;
            case PINK:
                this.color = Color.pink;
                break;
            case WHITE:
                this.color = Color.white;
                break;
            case YELLOW:
                this.color = Color.yellow;
                break;
        }

    }
    public Color getColor(){
        return this.color;
    }
    public Color setColor(){
        return Color.RED;
    }
}
