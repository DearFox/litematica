package fi.dy.masa.litematica.config;

import java.util.List;
import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

public class Hotkeys
{
    public static final ConfigHotkey ADD_SELECTION_BOX                  = new ConfigHotkey("addSelectionBox",                   "M,A",  "Добавьте новое поле выбора (позиция 1) здесь");
    public static final ConfigHotkey CLONE_SELECTION                    = new ConfigHotkey("cloneSelection",                    "",     "Быстрое клонирование текущего выделения области.\n" +
            "По сути, это просто создание схемы только в памяти,\n" +
            "а затем создает размещение этой схемы и выделяет ее,\n" +
            "а также переключает режим инструмента в режим \"Вставить\".");
    public static final ConfigHotkey DELETE_SELECTION_BOX               = new ConfigHotkey("deleteSelectionBox",                "",     "Удалить текущее выбранное поле");
    public static final ConfigHotkey EASY_PLACE_ACTIVATION              = new ConfigHotkey("easyPlaceUseKey",                   "BUTTON_2", KeybindSettings.PRESS_ALLOWEXTRA, "Когда включен режим easyPlaceMode, эта клавиша используется для размещения блоко");
    public static final ConfigHotkey EASY_PLACE_TOGGLE                  = new ConfigHotkey("easyPlaceToggle",                   "",     "Позволяет быстро включить/выключить Easy Place mode");
    public static final ConfigHotkey EXECUTE_OPERATION                  = new ConfigHotkey("executeOperation",                  "",     "Выполнение текущей операции выбранного инструмента с\n" +
            "Текущее выделение или размещение в режимах Fill, Replace,\n" +
            "Paste Schematic и т.д. и т.п.");
    public static final ConfigHotkey INVERT_GHOST_BLOCK_RENDER_STATE    = new ConfigHotkey("invertGhostBlockRenderState",       "",     "Инвертирует статус рендеринга схемы/призрачного блока\n" +
            "пока удерживается эта привязка клавиш");
    public static final ConfigHotkey INVERT_OVERLAY_RENDER_STATE        = new ConfigHotkey("invertOverlayRenderState",          "",     "Инвертирует статус рендеринга наложения, пока эта привязка удерживается нажатой");
    public static final ConfigHotkey LAYER_MODE_NEXT                    = new ConfigHotkey("layerModeNext",                     "M,PAGE_UP",    "Переключение режима рендеринга (все, слои) вперед");
    public static final ConfigHotkey LAYER_MODE_PREVIOUS                = new ConfigHotkey("layerModePrevious",                 "M,PAGE_DOWN",  "Переключите режим рендеринга (все, слои) в обратном направлении");
    public static final ConfigHotkey LAYER_NEXT                         = new ConfigHotkey("layerNext",                         "PAGE_UP",      "Переместите выделение слоя с рендерингом вверх");
    public static final ConfigHotkey LAYER_PREVIOUS                     = new ConfigHotkey("layerPrevious",                     "PAGE_DOWN",    "Переместите выделение слоя с рендерингом вниз");
    public static final ConfigHotkey LAYER_SET_HERE                     = new ConfigHotkey("layerSetHere",                      "",     "Установите слой рендеринга на текущую позицию игрока");
    public static final ConfigHotkey NUDGE_SELECTION_NEGATIVE           = new ConfigHotkey("nudgeSelectionNegative",            "",     "Сдвинуть текущее выделение в \"отрицательном\" направлении\n" +
            "Это практически то же самое, что и колесико мыши вниз\n" +
            "с нажатым модификатором Nudge");
    public static final ConfigHotkey NUDGE_SELECTION_POSITIVE           = new ConfigHotkey("nudgeSelectionPositive",            "",     "Сдвинуть текущее выделение в \"положительном\" направлении\n" +
            "Это практически то же самое, что и колесико мыши вверх\n" +
            "с нажатым модификатором Nudge");
    public static final ConfigHotkey MOVE_ENTIRE_SELECTION              = new ConfigHotkey("moveEntireSelection",               "",     "Переместите весь текущий выбор сюда");
    public static final ConfigHotkey OPEN_GUI_AREA_SETTINGS             = new ConfigHotkey("openGuiAreaSettings",               "KP_MULTIPLY", "Откройте графический интерфейс настроек области для текущей выбранной области");
    public static final ConfigHotkey OPEN_GUI_LOADED_SCHEMATICS         = new ConfigHotkey("openGuiLoadedSchematics",           "",     "Откройте графический интерфейс загруженной схемы");
    public static final ConfigHotkey OPEN_GUI_MAIN_MENU                 = new ConfigHotkey("openGuiMainMenu",                   "M",    KeybindSettings.RELEASE_EXCLUSIVE, "Откройте главное меню Litematica");
    public static final ConfigHotkey OPEN_GUI_MATERIAL_LIST             = new ConfigHotkey("openGuiMaterialList",               "M,L",  "Откройте графический интерфейс списка материалов для текущего\n" +
            "выбранного размещения схемы");
    public static final ConfigHotkey OPEN_GUI_PLACEMENT_SETTINGS        = new ConfigHotkey("openGuiPlacementSettings",          "KP_SUBTRACT", "Открыть графический интерфейс настроек размещения для текущего\n" +
            "выбранного размещения или субрегиона");
    public static final ConfigHotkey OPEN_GUI_SCHEMATIC_PLACEMENTS      = new ConfigHotkey("openGuiSchematicPlacements",        "M,P",  "Откройте графический интерфейс размещения схем");
    public static final ConfigHotkey OPEN_GUI_SCHEMATIC_PROJECTS        = new ConfigHotkey("openGuiSchematicProjects",          "",     "Откройте графический интерфейс Schematic Projects");
    public static final ConfigHotkey OPEN_GUI_SCHEMATIC_VERIFIER        = new ConfigHotkey("openGuiSchematicVerifier",          "M,V",  "Откройте графический интерфейс Schematic Verifier для текущего\n" +
            "выбранного размещения схемы");
    public static final ConfigHotkey OPEN_GUI_SELECTION_MANAGER         = new ConfigHotkey("openGuiSelectionManager",           "M,S",  "Откройте графический интерфейс менеджера выбора области");
    public static final ConfigHotkey OPEN_GUI_SETTINGS                  = new ConfigHotkey("openGuiSettings",                   "M,C",  "Откройте графический интерфейс конфигурации");
    public static final ConfigHotkey OPERATION_MODE_CHANGE_MODIFIER     = new ConfigHotkey("operationModeChangeModifier",       "LEFT_CONTROL", KeybindSettings.MODIFIER_INGAME, "Клавиша-модификатор для быстрого изменения режима работы.\n" +
            "Удерживайте ее и прокручивайте, удерживая \" tool item\", чтобы быстро переключить режим.");
    public static final ConfigHotkey PICK_BLOCK_FIRST                   = new ConfigHotkey("pickBlockFirst",                    "BUTTON_3",     KeybindSettings.PRESS_ALLOWEXTRA, "Ключ для выбора блока первый\n" +
            "схематический блок, прослеженный лучами до");
    public static final ConfigHotkey PICK_BLOCK_LAST                    = new ConfigHotkey("pickBlockLast",                     "",             KeybindSettings.MODIFIER_INGAME, "Ключ для выбора последнего блока схемы\n" +
            "к которому прослеживается луч, перед первым (возможным) клиентским миром\n" +
            "до которого прослеживается луч. В основном это позволяет получить\n" +
            "блок, который можно поместить напротив существующего блока.");
    public static final ConfigHotkey PICK_BLOCK_TOGGLE                  = new ConfigHotkey("pickBlockToggle",                   "M,BUTTON_3",   "Горячая клавиша для переключения опции переключения блока выбора в\n" +
            "Generic configs. Это предоставляется как быстрый способ включить\n" +
            "или отключить клавиши блока выбора, если они мешают.");
    public static final ConfigHotkey RENDER_INFO_OVERLAY                = new ConfigHotkey("renderInfoOverlay",                 "I",            KeybindSettings.PRESS_ALLOWEXTRA, "The key that enables rendering the block info overlay.\n" +
            "Use NONE for not requiring a key to be pressed.\n" +
            "Disable the similarly named option in the Visuals\n" +
            "configs to disable the overlay completely.");
    public static final ConfigHotkey RENDER_OVERLAY_THROUGH_BLOCKS      = new ConfigHotkey("renderOverlayThroughBlocks",        "RIGHT_CONTROL", KeybindSettings.PRESS_ALLOWEXTRA, "Горячая клавиша для разрешения рендеринга оверлеев через блоки.\n" +
            "Это просто более быстрый способ временно включить\n" +
            "то же самое, что делает опция 'schematicOverlayRenderThroughBlocks' в Visuals.");
    public static final ConfigHotkey RERENDER_SCHEMATIC                 = new ConfigHotkey("rerenderSchematic",                 "F3,M", "Горячая клавиша для обновления/перерисовки только схемы, вместо того, чтобы\n" +
            "необходимости обновлять и ванильный рельеф с помощью F3 + A");
    public static final ConfigHotkey SAVE_AREA_AS_IN_MEMORY_SCHEMATIC   = new ConfigHotkey("saveAreaAsInMemorySchematic",       "",     "Сохранить текущий выбор области как схему в памяти");
    public static final ConfigHotkey SAVE_AREA_AS_SCHEMATIC_TO_FILE     = new ConfigHotkey("saveAreaAsSchematicToFile",         "LEFT_CONTROL,LEFT_ALT,S",  "Сохранить текущий выбор области как схему в файл");
    public static final ConfigHotkey SCHEMATIC_EDIT_BREAK_ALL_EXCEPT    = new ConfigHotkey("schematicEditBreakAllExcept",       "", KeybindSettings.MODIFIER_INGAME, "Клавиша-модификатор для активации режима/функции \" ломать все, кроме\"\n" +
            "в режиме инструмента редактирования схемы.\n" +
            "В принципе, когда вы удерживаете эту клавишу и ударяете по блоку схемы,\n" +
            "все остальные блоки, кроме этого блока, будут удалены из схемы.");
    public static final ConfigHotkey SCHEMATIC_EDIT_BREAK_ALL           = new ConfigHotkey("schematicEditBreakPlaceAll",        "", KeybindSettings.MODIFIER_INGAME, "клавиша-модификатор для активации функции \"разбить все одинаковые блоки\"\n" +
            "в режиме инструмента редактирования схемы");
    public static final ConfigHotkey SCHEMATIC_EDIT_BREAK_DIRECTION     = new ConfigHotkey("schematicEditBreakPlaceDirection",  "", KeybindSettings.MODIFIER_INGAME, "Клавиша-модификатор для активации функции направленного/непрерывного\n" +
            "разрыв или место в режиме инструмента редактирования схемы");
    public static final ConfigHotkey SCHEMATIC_EDIT_REPLACE_ALL         = new ConfigHotkey("schematicEditReplaceAll",           "", KeybindSettings.MODIFIER_INGAME, "клавиша-модификатор для активации режима/функции замены \"заменить все идентичные\"\n" +
            "режим/функция замены в режиме инструмента редактирования схемы");
    public static final ConfigHotkey SCHEMATIC_EDIT_REPLACE_BLOCK       = new ConfigHotkey("schematicEditReplaceBlock",         "", KeybindSettings.MODIFIER_INGAME, "клавиша-модификатор для активации режима/функции замены \"заменить тип блока\"\n" +
            "режим/функция замены в режиме инструмента редактирования схемы");
    public static final ConfigHotkey SCHEMATIC_EDIT_REPLACE_DIRECTION   = new ConfigHotkey("schematicEditReplaceDirection",     "", KeybindSettings.MODIFIER_INGAME, "Клавиша-модификатор для активации режима/функции направленной/непрерывной\n" +
            "режим/функция замены в режиме инструмента редактирования схемы");
    public static final ConfigHotkey SCHEMATIC_VERSION_CYCLE_MODIFIER   = new ConfigHotkey("schematicVersionCycleModifier",     "",     KeybindSettings.MODIFIER_INGAME, "Клавиша-модификатор, которую нужно удерживать, чтобы иметь возможность использовать колесико мыши\n" +
            "для перебора версий схемы в режиме инструмента \" Version Control\".");
    public static final ConfigHotkey SCHEMATIC_VERSION_CYCLE_NEXT       = new ConfigHotkey("schematicVersionCycleNext",         "",     "Горячая клавиша для перехода к следующей версии схемы в режиме инструмента контроля версий");
    public static final ConfigHotkey SCHEMATIC_VERSION_CYCLE_PREVIOUS   = new ConfigHotkey("schematicVersionCyclePrevious",     "",     "Горячая клавиша для перехода к предыдущей версии схемы в режиме инструмента контроля версий");
    public static final ConfigHotkey SELECTION_GRAB_MODIFIER            = new ConfigHotkey("selectionGrabModifier",             "",     KeybindSettings.MODIFIER_INGAME, "Клавиша-модификатор, которую нужно удерживать, чтобы \"захватить\" поле выделения\n" +
            "или угол для перемещения курсора.");
    public static final ConfigHotkey SELECTION_GROW_HOTKEY              = new ConfigHotkey("selectionGrow",                     "",     "Горячая клавиша для автоматического увеличения поля выбора вокруг\n" +
            "любых смежных/диагонально соединенных блоков");
    public static final ConfigHotkey SELECTION_GROW_MODIFIER            = new ConfigHotkey("selectionGrowModifier",             "",     KeybindSettings.MODIFIER_INGAME, "Клавиша-модификатор, которую нужно удерживать для увеличения или уменьшения\n" +
            "поле выбора при прокрутке");
    public static final ConfigHotkey SELECTION_NUDGE_MODIFIER           = new ConfigHotkey("selectionNudgeModifier",            "LEFT_ALT", KeybindSettings.MODIFIER_INGAME, "Клавиша-модификатор, которую нужно удерживать при прокрутке\n" +
            "чтобы сдвинуть выбранную область или угол");
    public static final ConfigHotkey SELECTION_MODE_CYCLE               = new ConfigHotkey("selectionModeCycle",                "LEFT_CONTROL,M", "Измените режим между углами и кубоидом\n" +
            "в режиме выбора области");
    public static final ConfigHotkey SELECTION_SHRINK_HOTKEY            = new ConfigHotkey("selectionShrink",                   "",     "Горячая клавиша для уменьшения области выделения так, чтобы в ней\n" +
            "не осталось пустого пространства (пустых слоев) ни с одной стороны");
    public static final ConfigHotkey SET_AREA_ORIGIN                    = new ConfigHotkey("setAreaOrigin",                     "",     "Установить/переместить начальную точку текущего\n" +
            "выделение области на позицию игрока");
    public static final ConfigHotkey SET_SELECTION_BOX_POSITION_1       = new ConfigHotkey("setSelectionBoxPosition1",          "",     "Установить первую позицию текущего выбранного\n" +
            "на позицию игрока");
    public static final ConfigHotkey SET_SELECTION_BOX_POSITION_2       = new ConfigHotkey("setSelectionBoxPosition2",          "",     "Установите вторую позицию текущего выбранного\n" +
            "на позицию игрока");
    public static final ConfigHotkey TOGGLE_ALL_RENDERING               = new ConfigHotkey("toggleAllRendering",                "M,R",  "Включить/выключить все виды рендеринга", "All Rendering");
    public static final ConfigHotkey TOGGLE_AREA_SELECTION_RENDERING    = new ConfigHotkey("toggleAreaSelectionBoxesRendering", "",     "Включение/выключение рендеринга полей выбора области");
    public static final ConfigHotkey TOGGLE_SCHEMATIC_RENDERING         = new ConfigHotkey("toggleSchematicRendering",          "M,G",  "Включение/выключение визуализации схемы (блоки и наложение)");
    public static final ConfigHotkey TOGGLE_INFO_OVERLAY_RENDERING      = new ConfigHotkey("toggleInfoOverlayRendering",        "",     "Переключение отображения наложения информации (для информации о наведенном блоке)");
    public static final ConfigHotkey TOGGLE_OVERLAY_RENDERING           = new ConfigHotkey("toggleOverlayRendering",            "",     "Включение/выключение рендеринга наложения блока");
    public static final ConfigHotkey TOGGLE_OVERLAY_OUTLINE_RENDERING   = new ConfigHotkey("toggleOverlayOutlineRendering",     "",     "Включение/выключение рендеринга контура наложения блока");
    public static final ConfigHotkey TOGGLE_OVERLAY_SIDE_RENDERING      = new ConfigHotkey("toggleOverlaySideRendering",        "",     "Включение/выключение бокового рендеринга наложения блока");
    public static final ConfigHotkey TOGGLE_PLACEMENT_BOXES_RENDERING   = new ConfigHotkey("togglePlacementBoxesRendering",     "",     "Включить/выключить рендеринг блоков размещения схем");
    public static final ConfigHotkey TOGGLE_PLACEMENT_RESTRICTION       = new ConfigHotkey("togglePlacementRestriction",        "",     "Горячая клавиша для переключения режима ограничения размещения");
    public static final ConfigHotkey TOGGLE_SCHEMATIC_BLOCK_RENDERING   = new ConfigHotkey("toggleSchematicBlockRendering",     "",     "Включение/выключение рендеринга схемных блоков");
    public static final ConfigHotkey TOGGLE_SIGN_TEXT_PASTE             = new ConfigHotkey("toggleSignTextPaste",               "",     "Переключение значения конфигурации signTextPaste (в категории Generic)");
    public static final ConfigHotkey TOGGLE_TRANSLUCENT_RENDERING       = new ConfigHotkey("toggleTranslucentRendering",        "",     "Переключение полупрозрачного и непрозрачного рендеринга призрачных блоков");
    public static final ConfigHotkey TOGGLE_VERIFIER_OVERLAY_RENDERING  = new ConfigHotkey("toggleVerifierOverlayRendering",    "",     "Переключение рендеринга наложения Schematic Verifier");
    public static final ConfigHotkey TOOL_ENABLED_TOGGLE                = new ConfigHotkey("toolEnabledToggle",                 "M,T",  "Привязка клавиш для включения/выключения функциональности элемента \"инструмент\"");
    public static final ConfigHotkey TOOL_PLACE_CORNER_1                = new ConfigHotkey("toolPlaceCorner1",                  "BUTTON_1", KeybindSettings.PRESS_ALLOWEXTRA, "Кнопка, которую нужно использовать, удерживая \"tool\" предмет\n" +
            "для размещения основного/первого угла");
    public static final ConfigHotkey TOOL_PLACE_CORNER_2                = new ConfigHotkey("toolPlaceCorner2",                  "BUTTON_2", KeybindSettings.PRESS_ALLOWEXTRA, "Кнопка, которую нужно использовать, удерживая \"tool\" инструмент\n" +
            "для размещения второго угла");
    public static final ConfigHotkey TOOL_SELECT_ELEMENTS               = new ConfigHotkey("toolSelectElements",                "BUTTON_3", KeybindSettings.PRESS_ALLOWEXTRA, "Кнопка, которую следует использовать для выделения углов или квадратов\n" +
            "удерживая предмет\" tool\"");
    public static final ConfigHotkey TOOL_SELECT_MODIFIER_BLOCK_1       = new ConfigHotkey("toolSelectModifierBlock1",          "LEFT_ALT", KeybindSettings.MODIFIER_INGAME, "Клавиша-модификатор, которую нужно удерживать при использовании горячей клавиши 'toolSelectElements'\n" +
            "для выбора типа основного блока, используемого в некоторых режимах работы инструмента.");
    public static final ConfigHotkey TOOL_SELECT_MODIFIER_BLOCK_2       = new ConfigHotkey("toolSelectModifierBlock2",          "LEFT_SHIFT", KeybindSettings.MODIFIER_INGAME, "Клавиша-модификатор, которую нужно удерживать при использовании горячей клавиши 'toolSelectElements'\n" +
            "для выбора типа вторичного блока, используемого в некоторых режимах работы инструмента");
    public static final ConfigHotkey UNLOAD_CURRENT_SCHEMATIC           = new ConfigHotkey("unloadCurrentSchematic",            "",     "Выгружает схему текущего выбранного размещения и, таким образом, удаляет все созданные на ее основе размещения");

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            ADD_SELECTION_BOX,
            CLONE_SELECTION,
            DELETE_SELECTION_BOX,
            EASY_PLACE_ACTIVATION,
            EASY_PLACE_TOGGLE,
            EXECUTE_OPERATION,
            INVERT_GHOST_BLOCK_RENDER_STATE,
            INVERT_OVERLAY_RENDER_STATE,
            LAYER_MODE_NEXT,
            LAYER_MODE_PREVIOUS,
            LAYER_NEXT,
            LAYER_PREVIOUS,
            LAYER_SET_HERE,
            NUDGE_SELECTION_NEGATIVE,
            NUDGE_SELECTION_POSITIVE,
            MOVE_ENTIRE_SELECTION,
            OPEN_GUI_AREA_SETTINGS,
            OPEN_GUI_LOADED_SCHEMATICS,
            OPEN_GUI_MAIN_MENU,
            OPEN_GUI_MATERIAL_LIST,
            OPEN_GUI_PLACEMENT_SETTINGS,
            OPEN_GUI_SCHEMATIC_PLACEMENTS,
            OPEN_GUI_SCHEMATIC_PROJECTS,
            OPEN_GUI_SCHEMATIC_VERIFIER,
            OPEN_GUI_SELECTION_MANAGER,
            OPEN_GUI_SETTINGS,
            OPERATION_MODE_CHANGE_MODIFIER,
            PICK_BLOCK_FIRST,
            PICK_BLOCK_LAST,
            PICK_BLOCK_TOGGLE,
            RENDER_INFO_OVERLAY,
            RENDER_OVERLAY_THROUGH_BLOCKS,
            RERENDER_SCHEMATIC,
            SAVE_AREA_AS_IN_MEMORY_SCHEMATIC,
            SAVE_AREA_AS_SCHEMATIC_TO_FILE,
            SCHEMATIC_EDIT_BREAK_ALL,
            SCHEMATIC_EDIT_BREAK_ALL_EXCEPT,
            SCHEMATIC_EDIT_BREAK_DIRECTION,
            SCHEMATIC_EDIT_REPLACE_ALL,
            SCHEMATIC_EDIT_REPLACE_BLOCK,
            SCHEMATIC_EDIT_REPLACE_DIRECTION,
            SCHEMATIC_VERSION_CYCLE_MODIFIER,
            SCHEMATIC_VERSION_CYCLE_NEXT,
            SCHEMATIC_VERSION_CYCLE_PREVIOUS,
            SELECTION_GRAB_MODIFIER,
            SELECTION_GROW_HOTKEY,
            SELECTION_GROW_MODIFIER,
            SELECTION_NUDGE_MODIFIER,
            SELECTION_MODE_CYCLE,
            SELECTION_SHRINK_HOTKEY,
            SET_AREA_ORIGIN,
            SET_SELECTION_BOX_POSITION_1,
            SET_SELECTION_BOX_POSITION_2,
            TOGGLE_ALL_RENDERING,
            TOGGLE_AREA_SELECTION_RENDERING,
            TOGGLE_INFO_OVERLAY_RENDERING,
            TOGGLE_OVERLAY_RENDERING,
            TOGGLE_OVERLAY_OUTLINE_RENDERING,
            TOGGLE_OVERLAY_SIDE_RENDERING,
            TOGGLE_PLACEMENT_BOXES_RENDERING,
            TOGGLE_PLACEMENT_RESTRICTION,
            TOGGLE_SCHEMATIC_BLOCK_RENDERING,
            TOGGLE_SCHEMATIC_RENDERING,
            TOGGLE_SIGN_TEXT_PASTE,
            TOGGLE_TRANSLUCENT_RENDERING,
            TOGGLE_VERIFIER_OVERLAY_RENDERING,
            TOOL_ENABLED_TOGGLE,
            TOOL_PLACE_CORNER_1,
            TOOL_PLACE_CORNER_2,
            TOOL_SELECT_ELEMENTS,
            TOOL_SELECT_MODIFIER_BLOCK_1,
            TOOL_SELECT_MODIFIER_BLOCK_2,
            UNLOAD_CURRENT_SCHEMATIC
    );
}
