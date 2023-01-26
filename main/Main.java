package main;

import controller.IJPaintController;
import controller.JPaintController;
import controller.MouseHandler;
import controller.drawing.ListForShapes;
import model.persistence.ApplicationState;
import view.gui.Gui;
import view.gui.GuiWindow;
import view.gui.PaintCanvas;
import view.interfaces.IGuiWindow;
import view.interfaces.IUiModule;
import view.interfaces.PaintCanvasBase;

public class Main {
    public static void main(String[] args){
        PaintCanvasBase paintCanvas = new PaintCanvas();
        IGuiWindow guiWindow = new GuiWindow(paintCanvas);
        IUiModule uiModule = new Gui(guiWindow);
        ApplicationState appState = new ApplicationState(uiModule);
        ListForShapes listForShapes = new ListForShapes(paintCanvas,appState);
        IJPaintController controller = new JPaintController(uiModule, appState, listForShapes);
        controller.setup();
        paintCanvas.addMouseListener(new MouseHandler(appState,paintCanvas, listForShapes));
    }
}
