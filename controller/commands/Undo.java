package controller.commands;

import controller.drawing.CommandHistory;
import view.interfaces.IEventCallback;

public class Undo implements IEventCallback {
    @Override
    public void run() {
        CommandHistory.undo();
    }
}