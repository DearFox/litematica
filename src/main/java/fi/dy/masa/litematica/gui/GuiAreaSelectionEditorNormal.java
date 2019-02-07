package fi.dy.masa.litematica.gui;

import javax.annotation.Nullable;
import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.litematica.gui.GuiMainMenu.ButtonListenerChangeMenu;
import fi.dy.masa.litematica.gui.button.ButtonOnOff;
import fi.dy.masa.litematica.gui.widgets.WidgetListSelectionSubRegions;
import fi.dy.masa.litematica.gui.widgets.WidgetSelectionSubRegion;
import fi.dy.masa.litematica.selection.AreaSelection;
import fi.dy.masa.litematica.selection.Box;
import fi.dy.masa.litematica.selection.SelectionManager;
import fi.dy.masa.litematica.selection.SelectionMode;
import fi.dy.masa.litematica.util.PositionUtils;
import fi.dy.masa.litematica.util.PositionUtils.CoordinateType;
import fi.dy.masa.litematica.util.PositionUtils.Corner;
import fi.dy.masa.litematica.util.SchematicUtils;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.GuiTextFieldInteger;
import fi.dy.masa.malilib.gui.GuiTextInput;
import fi.dy.masa.malilib.gui.Message.MessageType;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.interfaces.ITextFieldListener;
import fi.dy.masa.malilib.gui.widgets.WidgetCheckBox;
import fi.dy.masa.malilib.interfaces.IStringConsumerFeedback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

public class GuiAreaSelectionEditorNormal extends GuiListBase<String, WidgetSelectionSubRegion, WidgetListSelectionSubRegions>
                                          implements ISelectionListener<String>
{
    protected final AreaSelection selection;
    protected GuiTextFieldGeneric textFieldSelectionName;
    protected WidgetCheckBox checkBoxOrigin;
    protected WidgetCheckBox checkBoxCorner1;
    protected WidgetCheckBox checkBoxCorner2;
    protected int xNext;
    protected int yNext;
    protected int xOrigin;
    @Nullable protected String selectionId;

    public GuiAreaSelectionEditorNormal(AreaSelection selection)
    {
        super(8, 116);

        this.selection = selection;
        this.selectionId = DataManager.getSelectionManager().getCurrentSelectionId();
        this.title = I18n.format("litematica.gui.title.area_editor_normal");
        this.useTitleHierarchy = false;
    }

    public void setSelectionId(String selectionId)
    {
        this.selectionId = selectionId;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.createSelectionEditFields();
        this.addSubRegionFields(this.xOrigin, this.yNext);
        this.updateCheckBoxes();
    }

    protected void createSelectionEditFields()
    {
        int xLeft = 12;
        int x = xLeft - 2;
        int y = 24;

        x += this.createButton(x, y, -1, ButtonListener.Type.CHANGE_MODE) + 4;

        boolean currentlyOn = this.selection.getExplicitOrigin() != null;
        x += this.createButtonOnOff(x, y, -1, currentlyOn, ButtonListener.Type.TOGGLE_ORIGIN_ENABLED) + 4;

        this.xOrigin = x + 20;
        x = xLeft;
        y += 20;

        this.addLabel(x, y, -1, 16, 0xFFFFFFFF, I18n.format("litematica.gui.label.area_editor.selection_name"));
        y += 13;

        int width = 202;
        this.textFieldSelectionName = new GuiTextFieldGeneric(x, y + 2, width, 16, this.mc.fontRenderer);
        this.textFieldSelectionName.setText(this.selection.getName());
        this.addTextField(this.textFieldSelectionName, new TextFieldListenerDummy());
        x += width + 4;
        x += this.createButton(x, y, -1, ButtonListener.Type.SET_SELECTION_NAME) + 10;
        y += 20;

        this.yNext = y;
    }

    protected int addSubRegionFields(int x, int y)
    {
        int width = 68;
        int xSave = 10;
        int ySave = y + 4;
        int xOrigin = x;
        int yOrigin = 6;

        xSave += this.createButton(xSave, ySave, -1, ButtonListener.Type.CREATE_SUB_REGION) + 4;
        xSave += this.createButton(xSave, ySave, -1, ButtonListener.Type.CREATE_SCHEMATIC) + 4;

        // Manual Origin defined
        if (this.selection.getExplicitOrigin() != null)
        {
            this.createCoordinateInputs(xOrigin, yOrigin, width, Corner.NONE);
        }

        x = 12;
        y += 16;
        String str = String.valueOf(this.selection.getAllSubRegionNames().size());
        this.addLabel(x, this.getListY() - 12, -1, 16, 0xFFFFFFFF, TextFormatting.BOLD + I18n.format("litematica.gui.label.area_editor.sub_regions", str));

        ButtonGeneric button;

        y = this.height - 26;
        ButtonListenerChangeMenu.ButtonType type = ButtonListenerChangeMenu.ButtonType.AREA_SELECTION_BROWSER;
        String label = I18n.format(type.getLabelKey());
        button = new ButtonGeneric(0, x, y, -1, 20, label, type.getIcon());
        this.addButton(button, new ButtonListenerChangeMenu(type, this.getParent()));

        type = ButtonListenerChangeMenu.ButtonType.MAIN_MENU;
        label = I18n.format(type.getLabelKey());
        int buttonWidth = this.fontRenderer.getStringWidth(label) + 10;
        x = this.width - buttonWidth - 10;
        button = new ButtonGeneric(0, x, y, buttonWidth, 20, label);
        this.addButton(button, new ButtonListenerChangeMenu(type, this.getParent()));

        return y;
    }

    protected void renameSubRegion()
    {
    }

    protected void createOrigin()
    {
        BlockPos origin = new BlockPos(this.mc.player);
        this.selection.setExplicitOrigin(origin);
    }

    protected void createCoordinateInputs(int x, int y, int width, Corner corner)
    {
        String label = "";
        WidgetCheckBox widget = null;

        switch (corner)
        {
            case CORNER_1:
                label = I18n.format("litematica.gui.label.area_editor.corner_1");
                widget = new WidgetCheckBox(x, y + 3, this.zLevel, Icons.CHECKBOX_UNSELECTED, Icons.CHECKBOX_SELECTED, label, this.mc);
                this.checkBoxCorner1 = widget;
                break;
            case CORNER_2:
                label = I18n.format("litematica.gui.label.area_editor.corner_2");
                widget = new WidgetCheckBox(x, y + 3, this.zLevel, Icons.CHECKBOX_UNSELECTED, Icons.CHECKBOX_SELECTED, label, this.mc);
                this.checkBoxCorner2 = widget;
                break;
            case NONE:
                label = I18n.format("litematica.gui.label.area_editor.origin");
                widget = new WidgetCheckBox(x, y + 3, this.zLevel, Icons.CHECKBOX_UNSELECTED, Icons.CHECKBOX_SELECTED, label, this.mc);
                this.checkBoxOrigin = widget;
                break;
        }

        if (widget != null)
        {
            widget.setListener(new CheckBoxListener(corner, this));
            this.addWidget(widget);
        }
        y += 14;

        this.createCoordinateInput(x, y, width, CoordinateType.X, corner);
        y += 20;

        this.createCoordinateInput(x, y, width, CoordinateType.Y, corner);
        y += 20;

        this.createCoordinateInput(x, y, width, CoordinateType.Z, corner);
        y += 22;

        this.createButton(x + 10, y, -1, corner, ButtonListener.Type.MOVE_TO_PLAYER);
    }

    protected void createCoordinateInput(int x, int y, int width, CoordinateType coordType, Corner corner)
    {
        String label = coordType.name() + ":";
        this.addLabel(x, y, 20, 20, 0xFFFFFFFF, label);
        int offset = 12;

        y += 2;
        BlockPos pos = corner == Corner.NONE ? this.selection.getEffectiveOrigin() : this.getBox().getPosition(corner);
        String text = "";
        ButtonListener.Type type = null;

        switch (coordType)
        {
            case X:
                text = String.valueOf(pos.getX());
                type = ButtonListener.Type.NUDGE_COORD_X;
                break;
            case Y:
                text = String.valueOf(pos.getY());
                type = ButtonListener.Type.NUDGE_COORD_Y;
                break;
            case Z:
                text = String.valueOf(pos.getZ());
                type = ButtonListener.Type.NUDGE_COORD_Z;
                break;
        }

        GuiTextFieldInteger textField = new GuiTextFieldInteger(x + offset, y, width, 16, this.mc.fontRenderer);
        TextFieldListener listener = new TextFieldListener(coordType, corner, this);
        textField.setText(text);
        this.addTextField(textField, listener);

        this.createCoordinateButton(x + offset + width + 4, y, corner, coordType, type);
    }

    protected int createButtonOnOff(int x, int y, int width, boolean isCurrentlyOn, ButtonListener.Type type)
    {
        ButtonOnOff button = ButtonOnOff.create(x, y, width, false, type.getTranslationKey(), isCurrentlyOn);
        this.addButton(button, new ButtonListener(type, null, null, this));
        return button.getButtonWidth();
    }

    protected int createButton(int x, int y, int width, ButtonListener.Type type)
    {
        return this.createButton(x, y, width, null, type);
    }

    protected int createButton(int x, int y, int width, @Nullable Corner corner, ButtonListener.Type type)
    {
        String label;

        if (type == ButtonListener.Type.CHANGE_MODE)
        {
            SelectionMode mode = DataManager.getSelectionManager().getSelectionMode();
            label = type.getDisplayName(mode.getDisplayName());
        }
        else
        {
            label = type.getDisplayName();
        }

        if (width == -1)
        {
            width = this.mc.fontRenderer.getStringWidth(label) + 10;
        }

        ButtonGeneric button = new ButtonGeneric(0, x, y, width, 20, label);
        ButtonListener listener = new ButtonListener(type, corner, null, this);
        this.addButton(button, listener);

        if (type == ButtonListener.Type.CREATE_SCHEMATIC)
        {
            button.setHoverStrings("litematica.gui.button.hover.area_editor.shift_for_in_memory");
        }

        return width;
    }

    protected void createCoordinateButton(int x, int y, Corner corner, CoordinateType coordType, ButtonListener.Type type)
    {
        String hover = I18n.format("litematica.gui.button.hover.plus_minus_tip_ctrl_alt_shift");
        ButtonGeneric button = new ButtonGeneric(0, x, y, Icons.BUTTON_PLUS_MINUS_16, hover);
        ButtonListener listener = new ButtonListener(type, corner, coordType, this);
        this.addButton(button, listener);
    }

    protected void updateCheckBoxes()
    {
        if (this.checkBoxOrigin != null)
        {
            this.checkBoxOrigin.setChecked(this.selection.isOriginSelected(), false);
        }

        if (this.checkBoxCorner1 != null)
        {
            boolean checked = this.selection.getSelectedSubRegionBox() != null && this.selection.getSelectedSubRegionBox().getSelectedCorner() == Corner.CORNER_1;
            this.checkBoxCorner1.setChecked(checked, false);
        }

        if (this.checkBoxCorner2 != null)
        {
            boolean checked = this.selection.getSelectedSubRegionBox() != null && this.selection.getSelectedSubRegionBox().getSelectedCorner() == Corner.CORNER_2;
            this.checkBoxCorner2.setChecked(checked, false);
        }
    }

    @Nullable
    protected Box getBox()
    {
        return null;
    }

    protected void updatePosition(String numberString, Corner corner, CoordinateType type)
    {
        try
        {
            int value = Integer.parseInt(numberString);
            this.selection.setCoordinate(this.getBox(), corner, type, value);
        }
        catch (NumberFormatException e)
        {
        }
    }

    protected void moveCoordinate(int amount, Corner corner, CoordinateType type)
    {
        int oldValue = 0;

        if (corner == Corner.NONE)
        {
            oldValue = PositionUtils.getCoordinate(this.selection.getEffectiveOrigin(), type);
        }
        else if (this.getBox() != null)
        {
            oldValue = this.getBox().getCoordinate(corner, type);
        }

        this.selection.setCoordinate(this.getBox(), corner, type, oldValue + amount);
    }

    protected void renameSelection()
    {
        String oldSelectionName = this.selection.getName();
        String oldBoxName = this.selection.getCurrentSubRegionBoxName();
        String newName = this.textFieldSelectionName.getText();

        if (oldSelectionName.equals(oldBoxName))
        {
            this.selection.renameSubRegionBox(oldBoxName, newName);
        }

        if (this.selectionId != null)
        {
            this.renameSelectionFile(this.selectionId, newName);
        }
    }

    protected void renameSelectionFile(String selectionId, String newName)
    {
        DataManager.getSelectionManager().renameSelection(selectionId, newName, this);
    }

    protected static class ButtonListener implements IButtonActionListener<ButtonGeneric>
    {
        private final GuiAreaSelectionEditorNormal parent;
        private final Type type;
        @Nullable private final Corner corner;
        @Nullable private final CoordinateType coordinateType;

        public ButtonListener(Type type, @Nullable Corner corner, @Nullable CoordinateType coordinateType, GuiAreaSelectionEditorNormal parent)
        {
            this.type = type;
            this.corner = corner;
            this.coordinateType = coordinateType;
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ButtonGeneric control)
        {
        }

        @Override
        public void actionPerformedWithButton(ButtonGeneric control, int mouseButton)
        {
            int amount = mouseButton == 1 ? -1 : 1;
            if (GuiScreen.isCtrlKeyDown()) { amount *= 100; }
            if (GuiScreen.isShiftKeyDown()) { amount *= 10; }
            if (GuiScreen.isAltKeyDown()) { amount *= 5; }

            this.parent.setNextMessageType(MessageType.ERROR);

            switch (this.type)
            {
                case NUDGE_COORD_X:
                    this.parent.moveCoordinate(amount, this.corner, this.coordinateType);
                    break;

                case NUDGE_COORD_Y:
                    this.parent.moveCoordinate(amount, this.corner, this.coordinateType);
                    break;

                case NUDGE_COORD_Z:
                    this.parent.moveCoordinate(amount, this.corner, this.coordinateType);
                    break;

                case CHANGE_MODE:
                    SelectionManager manager = DataManager.getSelectionManager();
                    manager.setMode(manager.getSelectionMode().cycle(true));
                    manager.openEditGui(null);
                    break;

                case CREATE_SCHEMATIC:
                    SchematicUtils.saveSchematic(GuiScreen.isShiftKeyDown());
                    break;

                case CREATE_SUB_REGION:
                    GuiTextInput gui = new GuiTextInput(512, "litematica.gui.title.area_editor.sub_region_name", "", null, new SubRegionCreator(this.parent));
                    gui.setParent(this.parent);
                    Minecraft.getMinecraft().displayGuiScreen(gui);
                    break;

                case SET_SELECTION_NAME:
                {
                    this.parent.renameSelection();
                    break;
                }

                case SET_BOX_NAME:
                {
                    this.parent.renameSubRegion();
                    break;
                }

                case MOVE_TO_PLAYER:
                    if (this.parent.mc.player != null)
                    {
                        BlockPos pos = new BlockPos(this.parent.mc.player);

                        if (this.corner == Corner.NONE)
                        {
                            this.parent.selection.setExplicitOrigin(pos);
                        }
                        else
                        {
                            this.parent.selection.setSelectedSubRegionCornerPos(pos, this.corner);
                        }
                    }
                    break;

                case TOGGLE_ORIGIN_ENABLED:
                    BlockPos origin = this.parent.selection.getExplicitOrigin();

                    if (origin == null)
                    {
                        this.parent.createOrigin();
                    }
                    else
                    {
                        this.parent.selection.setExplicitOrigin(null);
                    }
                    break;
            }

            this.parent.initGui(); // Re-create buttons/text fields
        }

        public enum Type
        {
            SET_SELECTION_NAME      ("litematica.gui.button.area_editor.set_selection_name"),
            SET_BOX_NAME            ("litematica.gui.button.area_editor.set_box_name"),
            TOGGLE_ORIGIN_ENABLED   ("litematica.gui.button.area_editor.origin_enabled"),
            CREATE_SUB_REGION       ("litematica.gui.button.area_editor.create_sub_region"),
            CREATE_SCHEMATIC        ("litematica.gui.button.area_editor.create_schematic"),
            CHANGE_MODE             ("litematica.gui.button.area_editor.change_mode"),
            MOVE_TO_PLAYER          ("litematica.gui.button.move_to_player"),
            NUDGE_COORD_X           (""),
            NUDGE_COORD_Y           (""),
            NUDGE_COORD_Z           ("");

            private final String translationKey;
            @Nullable private final String hoverText;

            private Type(String translationKey)
            {
                this(translationKey, null);
            }

            private Type(String translationKey, @Nullable String hoverText)
            {
                this.translationKey = translationKey;
                this.hoverText = hoverText;
            }

            public String getTranslationKey()
            {
                return this.translationKey;
            }

            public String getDisplayName(Object... args)
            {
                return I18n.format(this.translationKey, args);
            }
        }
    }

    protected static class TextFieldListener implements ITextFieldListener<GuiTextField>
    {
        private final GuiAreaSelectionEditorNormal parent;
        private final CoordinateType type;
        private final Corner corner;

        public TextFieldListener(CoordinateType type, Corner corner, GuiAreaSelectionEditorNormal parent)
        {
            this.type = type;
            this.corner = corner;
            this.parent = parent;
        }

        @Override
        public boolean onTextChange(GuiTextField textField)
        {
            this.parent.updatePosition(textField.getText(), this.corner, this.type);
            return false;
        }
    }

    public static class TextFieldListenerDummy implements ITextFieldListener<GuiTextField>
    {
        @Override
        public boolean onTextChange(GuiTextField textField)
        {
            return false;
        }
    }

    protected static class SubRegionCreator implements IStringConsumerFeedback
    {
        private final GuiAreaSelectionEditorNormal gui;

        private SubRegionCreator(GuiAreaSelectionEditorNormal gui)
        {
            this.gui = gui;
        }

        @Override
        public boolean setString(String string)
        {
            return DataManager.getSelectionManager().createNewSubRegionIfDoesntExist(string, this.gui.mc, this.gui);
        }
    }

    protected static class CheckBoxListener implements ISelectionListener<WidgetCheckBox>
    {
        private final GuiAreaSelectionEditorNormal gui;
        private final Corner corner;

        public CheckBoxListener(Corner corner, GuiAreaSelectionEditorNormal gui)
        {
            this.gui = gui;
            this.corner = corner;
        }

        @Override
        public void onSelectionChange(WidgetCheckBox entry)
        {
            if (entry.isChecked())
            {
                // Origin
                if (this.corner == Corner.NONE)
                {
                    this.gui.selection.setOriginSelected(true);
                    this.gui.selection.clearCurrentSelectedCorner();
                }
                else
                {
                    this.gui.selection.setOriginSelected(false);
                    this.gui.selection.setCurrentSelectedCorner(this.corner);
                }
            }
            else
            {
                // Origin
                if (this.corner == Corner.NONE)
                {
                    this.gui.selection.setOriginSelected(false);
                }
                else
                {
                    this.gui.selection.clearCurrentSelectedCorner();
                }
            }

            this.gui.updateCheckBoxes();
        }
    }

    @Override
    protected int getBrowserWidth()
    {
        return this.width - 20;
    }

    @Override
    protected int getBrowserHeight()
    {
        return this.height - 146;
    }

    @Override
    protected ISelectionListener<String> getSelectionListener()
    {
        return this;
    }

    @Override
    public void onSelectionChange(String entry)
    {
        if (entry != null && entry.equals(this.selection.getCurrentSubRegionBoxName()))
        {
            this.selection.setSelectedSubRegionBox(null);
        }
        else
        {
            this.selection.setSelectedSubRegionBox(entry);
        }
    }

    @Override
    protected WidgetListSelectionSubRegions createListWidget(int listX, int listY)
    {
        return new WidgetListSelectionSubRegions(listX, listY,
                this.getBrowserWidth(), this.getBrowserHeight(), this.selection, this);
    }
}
