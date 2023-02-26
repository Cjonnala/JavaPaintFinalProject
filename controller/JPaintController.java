package controller;

import controller.commands.*;
import controller.drawing.ListForShapes;
import model.interfaces.IApplicationState;
import view.EventName;
import view.interfaces.IUiModule;

public class JPaintController implements IJPaintController {
    private final IUiModule uiModule;
    private final IApplicationState applicationState;
    private final ListForShapes listForShapes;

    public JPaintController(IUiModule uiModule, IApplicationState applicationState, ListForShapes listForShapes) {
        this.uiModule = uiModule;
        this.applicationState = applicationState;
        this.listForShapes = listForShapes;
    }

    @Override
    public void setup() {
        setupEvents();
    }

    private void setupEvents() {
        uiModule.addEvent(EventName.CHOOSE_SHAPE, applicationState::setActiveShape);
        uiModule.addEvent(EventName.CHOOSE_PRIMARY_COLOR, applicationState::setActivePrimaryColor);
        uiModule.addEvent(EventName.CHOOSE_SECONDARY_COLOR, applicationState::setActiveSecondaryColor);
        uiModule.addEvent(EventName.CHOOSE_SHADING_TYPE, applicationState::setActiveShadingType);
        uiModule.addEvent(EventName.CHOOSE_MOUSE_MODE, applicationState::setActiveStartAndEndPointMode);
        uiModule.addEvent(EventName.UNDO, () -> new Undo().run());
        uiModule.addEvent(EventName.REDO, () -> new Redo().run());
        uiModule.addEvent(EventName.COPY, () -> new CopyCommand(listForShapes).run());
        uiModule.addEvent(EventName.PASTE, () -> new PasteCommand(listForShapes).run());
        uiModule.addEvent(EventName.DELETE, () -> new DeleteCommand(listForShapes).run());


    }

}
