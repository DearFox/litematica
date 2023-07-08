package fi.dy.masa.litematica.config;

import java.io.File;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.HudAlignment;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.config.options.ConfigOptionList;
import fi.dy.masa.malilib.config.options.ConfigString;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.MessageOutputType;
import fi.dy.masa.litematica.Reference;
import fi.dy.masa.litematica.data.DataManager;
import fi.dy.masa.litematica.selection.CornerSelectionMode;
import fi.dy.masa.litematica.util.BlockInfoAlignment;
import fi.dy.masa.litematica.util.EasyPlaceProtocol;
import fi.dy.masa.litematica.util.InventoryUtils;
import fi.dy.masa.litematica.util.PasteNbtBehavior;
import fi.dy.masa.litematica.util.ReplaceBehavior;

public class Configs implements IConfigHandler
{
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

    public static class Generic
    {
        public static final ConfigOptionList    EASY_PLACE_PROTOCOL         = new ConfigOptionList("easyPlaceProtocolVersion", EasyPlaceProtocol.AUTO, "Тип используемого \"протокола точного размещения\".\n- Auto: Использует v3 в одиночной игре, и по умолчанию Slabs-only в многопользовательской,\n  если только на сервере не установлен мод Carpet, посылающий \"carpet:hello\n  пакет, в этом случае на сервере используется v2.\n- Версия 3: Пока поддерживается только самой Litematica (в одиночной игре).\n- Версия 2: Совместима с серверами с модом Carpet\n  (либо QuickCarpet от skyrising и DeadlyMC,\n  либо CarpetExtra в дополнение к FabricCarpet.\n  И в обоих случаях правило ковра 'accurateBlockPlacement' необходимо\n  должно быть включено на сервере).\n- Только плиты: Исправляет только верхние перекрытия. Совместимо с серверами Paper.\n- None: Не изменяет координаты.");
        public static final ConfigOptionList    PASTE_NBT_BEHAVIOR          = new ConfigOptionList("pasteNbtRestoreBehavior", PasteNbtBehavior.NONE, "Пытаются ли восстановить данные NBT блоков или нет,\n" +
                "и какой метод для этого используется.\n" +
                "- Place & Data Modify попытается поместить \"NBT-выбранный\" блок\n" +
                "  рядом с игроком, а затем использовать команду data modify\n" +
                "  чтобы перенести данные NBT в блок, выбранный для setblock'ed.\n" +
                "- Place & Clone попытается разместить блок \"NBT-picked\"\n" +
                "  рядом с игроком, а затем клонировать его в конечное место.\n" +
                "- Teleport & Place попытается телепортировать игрока поблизости, а затем\n" +
                "  непосредственно поместить выбранный NBT элемент в нужное место.\n" +
                "Обратите внимание, что метод Teleport & Place в настоящее время работает некорректно/не работает вообще.\n" +
                "Рекомендуемый метод - §ePlace & Data Modify§r, однако для того, чтобы он работал\n" +
                "вам, вероятно, потребуется уменьшить значение параметра pasteCommandLimit до 1 за тик и увеличить\n" +
                "интервал pasteCommandInterval до 1-4 тиков или что-то в этом роде.\n" +
                "Таким образом, вы должны использовать это только для вставки важных блоков, которым нужны данные,\n" +
                "например, создав схему только инвентаря,\n" +
                "и затем вставьте ее с поведением замены, установленным на None.");
        public static final ConfigOptionList    PASTE_REPLACE_BEHAVIOR      = new ConfigOptionList("pasteReplaceBehavior", ReplaceBehavior.NONE, "Поведение при замене существующих блоков\nв режиме инструмента Вставить схему");
        public static final ConfigOptionList    PLACEMENT_REPLACE_BEHAVIOR  = new ConfigOptionList("placementReplaceBehavior", ReplaceBehavior.ALL, "Поведение замены блоков при добавлении блоков\n" +
                "в мир схемы.\n" +
                "\n" +
                "Это позволяет использовать перекрывающиеся размещения без того, чтобы\n" +
                "более позднее размещение всегда заменяет более раннее размещение воздухом.\n" +
                "С другой стороны, некоторые блоки, такие как блоки света, считаются\n" +
                "воздухом, поэтому для их размещения необходимо использовать поведение замены \"All\".\n" +
                "чтобы они вообще были размещены.");
        public static final ConfigOptionList    PLACEMENT_RESTRICTION_WARN  = new ConfigOptionList("placementRestrictionWarn", MessageOutputType.ACTIONBAR, "Выбор типа предупреждающего сообщения, которое будет показано (если таковое имеется)\n" +
                "когда режим Easy Place или Placement Restriction препятствуют размещению блока");
        public static final ConfigOptionList    SELECTION_CORNERS_MODE      = new ConfigOptionList("selectionCornersMode", CornerSelectionMode.CORNERS, "Режим углов выделения области для использования (Углы или Развернуть)");

        public static final ConfigBoolean       CUSTOM_SCHEMATIC_BASE_DIRECTORY_ENABLED = new ConfigBoolean("customSchematicBaseDirectoryEnabled", false, "Если включено, то каталог, заданный в 'customSchematicBaseDirectory'\n" +
                "будет использоваться в качестве корневого/базового каталога схем,\n" +
                "вместо обычного каталога '.minecraft/schematics/'.");
        public static final ConfigString        CUSTOM_SCHEMATIC_BASE_DIRECTORY         = new ConfigString( "customSchematicBaseDirectory", DataManager.getDefaultBaseSchematicDirectory().getAbsolutePath(), "Корневой/базовый каталог схем для использования,\n" +
                "если включено 'customSchematicBaseDirectoryEnabled'");

        public static final ConfigBoolean       AREAS_PER_WORLD             = new ConfigBoolean("areaSelectionsPerWorld", true, "Используйте корневые каталоги для каждого мира или сервера для выбора области.\n" +
                "§6NOTE: Не выключайте эту опцию во время прямой трансляции,\n" +
                "§6так как тогда браузер выбора области будет показывать IP сервера\n" +
                "§6в виджете навигации, а также в имени/пути текущего выбора\n" +
                "§6пока вы не измените текущий каталог и выборку снова");
        public static final ConfigBoolean       BETTER_RENDER_ORDER         = new ConfigBoolean("betterRenderOrder", true, "Если включено, то рендеринг схемы выполняется\n" +
                "путем внедрения различных вызовов рендеринга в ванильный\n" +
                "код рендеринга. Это должно привести к лучшему полупрозрачному блоку\n" +
                "рендеринг/упорядочивание и схематические блоки не будут рендериться\n" +
                "через блоки/террейн клиентского мира.\n" +
                "Если рендеринг не работает (например, с Optifine),\n" +
                "попробуйте отключить эту опцию.");
        public static final ConfigBoolean       CHANGE_SELECTED_CORNER      = new ConfigBoolean("changeSelectedCornerOnMove", true, "Если true, то выделенный угол при выделении области\n" +
                "всегда устанавливается на последний перемещенный угол,\n" +
                "при использовании горячих клавиш set corner");
        public static final ConfigBoolean       CLONE_AT_ORIGINAL_POS       = new ConfigBoolean("cloneAtOriginalPosition", false, "Если эта функция включена, то при использовании горячей клавиши \" Clone Selection\" будет создано\n" +
                "размещение в исходной позиции выбора области,\n" +
                "а не в текущей позиции игрока");
        public static final ConfigBoolean       COMMAND_DISABLE_FEEDBACK    = new ConfigBoolean("commandDisableFeedback", true, "Если включено, то обратная связь по команде автоматически отключается\n" +
                "а затем снова включается для многопользовательских операций Paste, Fill и Delete\n" +
                "(которые используют команды /setblock и /fill) путем отключения, а затем\n" +
                "повторного включения игрового правила sendCommandFeedback, когда задание завершено");
        public static final ConfigInteger       COMMAND_FILL_MAX_VOLUME     = new ConfigInteger("commandFillMaxVolume", 32768, 256, 10000000, "Максимальный размер/объем каждой отдельной ячейки\n" +
                "который может быть заполнен с помощью командных операций Заполнить/Удалить\n" +
                "операции. Большие области/объемы будут разбиты на несколько команд.\n" +
                "Сначала все области также разбиваются на отдельные ячейки.");
        public static final ConfigBoolean       COMMAND_FILL_NO_CHUNK_CLAMP = new ConfigBoolean("commandFillNoChunkClamp", false, "Отключает деление объемов заполнения (в режимах Fill, Replace и Delete)\n" +
                "на ячейки для каждого куска");
        public static final ConfigInteger       COMMAND_LIMIT               = new ConfigInteger("commandLimitPerTick", 24, 1, 256, "Максимальное количество команд, отправляемых за один игровой такт,\n" +
                "при использовании функций Paste, Fill и Delete на сервере,\n" +
                "где будут использоваться команды setblock и fill.\n" +
                "Обратите внимание, что функция Paste может превысить этот показатель на несколько команд\n" +
                "при использовании функции восстановления NBT, которая требует две дополнительные команды для каждого блока.");
        public static final ConfigString        COMMAND_NAME_CLONE          = new ConfigString( "commandNameClone", "clone", "Имя команды клонирования, используемое при использовании\n" +
                "функциональность творческого режима на серверах на основе команд.\n" +
                "В настоящее время это имя используется только функцией Paste, если для параметра восстановления NBT\n" +
                "установлено значение 'Place & Clone'.");
        public static final ConfigString        COMMAND_NAME_FILL           = new ConfigString( "commandNameFill", "fill", "Имя команды заполнения, используемое при использовании\n" +
                "функциональность творческого режима на серверах на основе команд");
        public static final ConfigString        COMMAND_NAME_SETBLOCK       = new ConfigString( "commandNameSetblock", "setblock", "Имя команды setblock для использования при использовании\n" +
                "функциональность творческого режима на серверах,\n" +
                "а именно функции \" Paste Schematic in World\".");
        public static final ConfigString        COMMAND_NAME_SUMMON         = new ConfigString( "commandNameSummon", "summon", "Имя команды спавна, используемое при использовании\n" +
                "функциональность творческого режима на серверах,\n" +
                "а именно функция \" Paste Schematic in World\".");
        public static final ConfigInteger       COMMAND_TASK_INTERVAL       = new ConfigInteger("commandTaskInterval", 1, 1, 1000, "Интервал в игровых тиках, с которым задания Paste, Fill и Delete\n" +
                "выполняются с интервалом. Конфигурация commandLimitPerTick устанавливает максимальное\n" +
                "количество команд, отправляемых за одно выполнение, а эта конфигурация\n" +
                "задает интервал в игровых тиках перед следующим выполнением.");
        public static final ConfigBoolean       COMMAND_USE_WORLDEDIT       = new ConfigBoolean("commandUseWorldEdit", false, "Если включено, то вместо использования настроенных команд setblock и fill,\n" +
                "используются команды World Edit //pos1, //pos2 и //set.\n" +
                "Обратите внимание, что использование команд World Edit примерно в 3 раза медленнее.\n" +
                "чем использование ванильных команд из-за ограничения команд на тик,\n" +
                "и WE, требующих несколько команд на блок или область (//pos1 //pos2 //set).\n" +
                "§6ПРЕДУПРЕЖДЕНИЕ: Опция поведения \" paste replace\" НЕ РАБОТАЕТ, если использовать\n" +
                "§6команды редактирования мира и заполнения объемов вместо отдельных команд setblock!\n" +
                "Поэтому рекомендуется использовать ванильные команды, если у вас есть разрешение на их выполнение.\n" +
                "Еще одна вещь, которая может заставить вас предпочесть команды WE в некоторых случаях\n" +
                "это то, что они могут предотвратить обновление блоков, если на сервере не установлен\n" +
                "мод Carpet и, следовательно, правило '/carpet fillUpdates false' доступно.");
        public static final ConfigBoolean       DEBUG_LOGGING               = new ConfigBoolean("debugLogging", false, "Включает некоторые сообщения журнала отладки в игровой консоли,\n" +
                "для отладки некоторых проблем или сбоев.");
        public static final ConfigBoolean       EASY_PLACE_FIRST            = new ConfigBoolean("easyPlaceFirst", true, "Это заставит режим \"Легкое размещение\" разместить первый/ближайший блок\n" +
                "на который вы смотрите, вместо самого дальнего/самого нижнего блока.\n" +
                "Установка этого значения в false позволяет размещать несколько слоев \"одновременно\",\n" +
                "так как самые дальние блоки будут размещены до того, как более близкие блоки перекроют линию видимости.");
        public static final ConfigBoolean       EASY_PLACE_HOLD_ENABLED     = new ConfigBoolean("easyPlaceHoldEnabled", true, "Если эта функция включена, то вы можете удерживать нажатой клавишу use\n" +
                "и смотреть на различные блоки схемы, чтобы разместить их,\n" +
                "без необходимости щелкать по каждому блоку отдельно.");
        public static final ConfigBoolean       EASY_PLACE_MODE             = new ConfigBoolean("easyPlaceMode", false, "Если эта функция включена, то простая попытка использовать элемент/разместить блок\n" +
                "на схематических блоках поместит этот блок в данную позицию");
        public static final ConfigBoolean       EASY_PLACE_SP_HANDLING      = new ConfigBoolean("easyPlaceSinglePlayerHandling", true, "Если эта функция включена, то Litematica сама обрабатывает так называемый\n" +
                "\"Carpet mod Accurate Block Placement Protocol\" в одиночной игре.\n" +
                "Рекомендуется оставить эту опцию включенной, если вы\n" +
                "собираетесь использовать Easy Place в одиночной игре.");
        public static final ConfigInteger       EASY_PLACE_SWAP_INTERVAL    = new ConfigInteger("easyPlaceSwapInterval", 0, 0, 10000, "Интервал в миллисекундах, который режим Easy Place ожидает\n" +
                "после переключения слотов инвентаря и размещения блока.\n" +
                "Полезно для того, чтобы избежать неправильного размещения блоков при высоком пинге.");
        public static final ConfigBoolean       EASY_PLACE_VANILLA_REACH    = new ConfigBoolean("easyPlaceVanillaReach", false, "Если включено, уменьшает расстояние досягаемости с 6 до 4,5\n" +
                "чтобы серверы не отклоняли размещение дальних блоков.");
        public static final ConfigBoolean       EXECUTE_REQUIRE_TOOL        = new ConfigBoolean("executeRequireHoldingTool", true, "Требуется удерживать включенный элемент инструмента\n" +
                "для работы горячей клавиши executeOperation");
        public static final ConfigBoolean       FIX_CHEST_MIRROR            = new ConfigBoolean("fixChestMirror", true, "Включить исправление неработающего кода зеркала сундука в ваниле");
        public static final ConfigBoolean       FIX_RAIL_ROTATION           = new ConfigBoolean("fixRailRotation", true, "Если true, то будет применено исправление ошибки ванильных рельсов,\n" +
                "когда 180-градусные повороты прямых рельсов север-юг и\n" +
                "восточно-западных рельсов вращаются на 90 градусов против часовой стрелки >_>");
        public static final ConfigBoolean       GENERATE_LOWERCASE_NAMES    = new ConfigBoolean("generateLowercaseNames", false, "Если эта опция включена, то по умолчанию предлагаемые имена схем\n" +
                "будут строчными и с использованием подчеркивания вместо пробелов.");
        public static final ConfigBoolean       HIGHLIGHT_BLOCK_IN_INV      = new ConfigBoolean("highlightBlockInInventory", false, "Если включена, выделяет элемент (включая содержащие его блоки Шулькера)\n" +
                "рассматриваемого блока в схеме");
        public static final ConfigBoolean       ITEM_USE_PACKET_CHECK_BYPASS= new ConfigBoolean("itemUsePacketCheckBypass", true, "Обход новой проверки расстояния/координат, которая была добавлена в 1.18.2.\n" +
                "\n" +
                "Эта проверка нарушает \"протокол точного размещения\" и приводит к тому, что\n" +
                "любые блоки, размещенные с запросом на поворот (или другое свойство), становятся блоками-призраками.\n" +
                "\n" +
                "В принципе, нет никакой необходимости отключать эту проверку.\n" +
                "Эта проверка даже не существовала до версии 1.18.2.");
        public static final ConfigBoolean       LAYER_MODE_DYNAMIC          = new ConfigBoolean("layerModeFollowsPlayer", false, "Если true, то слой рендеринга следует за игроком.\n" +
                "Примечание: В настоящее время этот параметр сворачивает диапазоны типа Layer Range, к сожалению.");
        public static final ConfigBoolean       LOAD_ENTIRE_SCHEMATICS      = new ConfigBoolean("loadEntireSchematics", false, "Если true, то всегда загружается вся схема сразу.\n" +
                "Если false, то загружается только та часть, которая находится в пределах расстояния просмотра клиента.");
        public static final ConfigBoolean       PASTE_ALWAYS_USE_FILL       = new ConfigBoolean("pasteAlwaysUseFill", false, "Это заставляет использовать команду fill (вместо setblock) даже для одиночных блоков");
        public static final ConfigBoolean       PASTE_IGNORE_BE_ENTIRELY    = new ConfigBoolean("pasteIgnoreBlockEntitiesEntirely", false, "Если включено, то блочные сущности не будут вставляться вообще\n" +
                "через командную вставку в многопользовательском режиме.\n" +
                "Это позволяет упростить вставку в два прохода, если вы\n" +
                "хотите использовать опцию NBT-restore для инвентаря и т.д. во втором проходе,\n" +
                "что обычно требует гораздо меньшей скорости вставки/команды.");
        public static final ConfigBoolean       PASTE_IGNORE_BE_IN_FILL     = new ConfigBoolean("pasteIgnoreBlockEntitiesFromFill", true, "Если включено, то все блочные сущности игнорируются из команд заливки\n" +
                "при вставке. Это позволяет вставлять их по отдельности,\n" +
                "что необходимо, если используется опция восстановления NBT.");
        public static final ConfigBoolean       PASTE_IGNORE_CMD_LIMIT      = new ConfigBoolean("pasteIgnoreCommandLimitWithNbtRestore", true, "Если включено, то ограничение команды игнорируется при вставке\n" +
                "блоков с блочной сущностью с включенной опцией восстановления NBT.\n" +
                "Похоже, это каким-то образом устраняет проблему, при которой восстановление NBT\n" +
                "в противном случае не срабатывало для многих блоков с низким количеством команд.");
        public static final ConfigBoolean       PASTE_IGNORE_ENTITIES       = new ConfigBoolean("pasteIgnoreEntities", false, "Если включено, то функция \"Paste\" не будет вставлять сущности.");
        public static final ConfigBoolean       PASTE_IGNORE_INVENTORY      = new ConfigBoolean("pasteIgnoreInventories", false, "Не вставляйте содержимое инвентаря при вставке схемы");
        public static final ConfigBoolean       PASTE_TO_MCFUNCTION         = new ConfigBoolean("pasteToMcFunctionFiles", false, "Если включено, то вместо того, чтобы в действительности вставлять схемы в мир,\n" +
                "они записываются как команды setblock в текстовые файлы.");
        public static final ConfigBoolean       PASTE_USE_FILL_COMMAND      = new ConfigBoolean("pasteUseFillCommand", true, "Если включено, то вместо использования только отдельных команд /setblock,\n" +
                "операция Paste на основе команд (которая используется на серверах)\n" +
                "будет также пытаться использовать команды /fill для любых непрерывных областей одного и того же блока.\n" +
                "Это не имеет эффекта в одиночной игре, так как мод устанавливает блоки непосредственно\n" +
                "в интегрированном мире сервера и не использует команды вообще.");
        public static final ConfigBoolean       PASTE_USING_COMMANDS_IN_SP  = new ConfigBoolean("pasteUsingCommandsInSp", false, "Это временное обходное решение для использования командной вставки\n" +
                "также в одиночной игре, что позволяет использовать ограниченный слой рендеринга\n" +
                "пастинга в одиночной игре, который в настоящее время не работает с\n" +
                "прямым доступом к миру, который обычно используется в одиночной игре.\n" +
                "\n" +
                "Обратите внимание, что при этом будут действовать все те же ограничения на восстановление данных NBT.\n" +
                "ограничения, которые обычно имеет многопользовательская склейка.");
        public static final ConfigBoolean       PICK_BLOCK_AVOID_DAMAGEABLE = new ConfigBoolean("pickBlockAvoidDamageable", true, "Позволяет избежать замены повреждаемых элементов в горячей панели");
        public static final ConfigBoolean       PICK_BLOCK_AVOID_TOOLS      = new ConfigBoolean("pickBlockAvoidTools", false, "Избегает замены любых элементов инструментов на горячей панели.\n" +
                "\n" +
                "Это означает любые элементы, которые расширяют ванильный класс ToolItem.");
        public static final ConfigBoolean       PICK_BLOCK_ENABLED          = new ConfigBoolean("pickBlockEnabled", true, "Включает горячие клавиши выбора блоков мира схем.\n" +
                "Существует также горячая клавиша для переключения этой опции для переключения этих горячих клавиш... o.o", "Pick Block Hotkeys");
        public static final ConfigBoolean       PICK_BLOCK_SHULKERS         = new ConfigBoolean("pickBlockShulkers", false, "Если включено, то если необходимый предмет для блока кирки\n" +
                "не находится непосредственно в инвентаре игрока, но есть\n" +
                "Шалкер бокс, в котором он находится, то Шалкер бокс\n" +
                "будет переключен на руку игрока.");
        public static final ConfigString        PICK_BLOCKABLE_SLOTS        = new ConfigString( "pickBlockableSlots", "1,2,3,4,5", "Слоты горячей панели, которые разрешено\n" +
                "использовать для блока выбора схемы");
        public static final ConfigBoolean       PLACEMENT_RESTRICTION       = new ConfigBoolean("placementRestriction", false, "Когда эта функция включена, клавиша использования может быть использована только\n" +
                "при удержании правильного элемента для целевой позиции,\n" +
                "и в целевой позиции должен быть отсутствующий блок в схеме", "Placement Restriction");
        public static final ConfigBoolean       RENDER_MATERIALS_IN_GUI     = new ConfigBoolean("renderMaterialListInGuis", true, "Должен ли список материалов\n" +
                "отображаться в графическом интерфейсе");
        public static final ConfigBoolean       RENDER_THREAD_NO_TIMEOUT    = new ConfigBoolean("renderThreadNoTimeout", true, "Снимает таймаут с рабочих потоков рендеринга.\n" +
                "Если вы получаете очень заикающийся рендеринг при передвижении\n" +
                "или при работе с большими схемами, попробуйте отключить это. Однако это сделает\n" +
                "в некоторых случаях рендеринг схем станет намного медленнее.");
        public static final ConfigBoolean       SIGN_TEXT_PASTE             = new ConfigBoolean("signTextPaste", true, "Автоматическая установка текста в графических интерфейсах знаков на основе схемы");
        public static final ConfigString        TOOL_ITEM                   = new ConfigString( "toolItem", "minecraft:stick", "Предмет для использования в качестве \"инструмента\" для выделения и т.д.");
        public static final ConfigBoolean       TOOL_ITEM_ENABLED           = new ConfigBoolean("toolItemEnabled", true, "Если это так, то элемент \"инструмент\" можно использовать для управления выделениями и т.д.", "Tool Item Enabled");
        public static final ConfigBoolean       UNHIDE_SCHEMATIC_PROJECTS   = new ConfigBoolean("unhideSchematicVCS", false, "Снимает скрытие кнопки меню Schematic VCS (Version Control System),\n" +
                "и включает горячую клавишу и функциональность VCS в целом.\n" +
                "(Ранее эта функция называлась Schematic Projects).\n" +
                "\n" +
                "В целом, вам не следует использовать эту функцию,\n" +
                "если только вы действительно не знаете, как она работает и что делает.\n" +
                "Она несколько меняет принципы выбора, размещения и вставки областей,\n" +
                "в частности, операция удаления области при вставке.\n" +
                "\n" +
                "В основном, эта функция предназначена для §6итеративного проектирования на месте,\n" +
                "и позволяет легче создавать несколько версий/снимков\n" +
                "одной и той же сборки, а также переключаться между версиями, удаляя то, что есть\n" +
                "в мире сначала, а затем вставляя на его место следующую версию.");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                AREAS_PER_WORLD,
                //BETTER_RENDER_ORDER,
                CHANGE_SELECTED_CORNER,
                CLONE_AT_ORIGINAL_POS,
                COMMAND_DISABLE_FEEDBACK,
                COMMAND_FILL_NO_CHUNK_CLAMP,
                COMMAND_USE_WORLDEDIT,
                CUSTOM_SCHEMATIC_BASE_DIRECTORY_ENABLED,
                DEBUG_LOGGING,
                EASY_PLACE_FIRST,
                EASY_PLACE_HOLD_ENABLED,
                EASY_PLACE_MODE,
                EASY_PLACE_SP_HANDLING,
                EASY_PLACE_PROTOCOL,
                EASY_PLACE_VANILLA_REACH,
                EXECUTE_REQUIRE_TOOL,
                FIX_CHEST_MIRROR,
                FIX_RAIL_ROTATION,
                GENERATE_LOWERCASE_NAMES,
                HIGHLIGHT_BLOCK_IN_INV,
                ITEM_USE_PACKET_CHECK_BYPASS,
                LAYER_MODE_DYNAMIC,
                //LOAD_ENTIRE_SCHEMATICS,
                PASTE_ALWAYS_USE_FILL,
                PASTE_IGNORE_BE_ENTIRELY,
                PASTE_IGNORE_BE_IN_FILL,
                PASTE_IGNORE_CMD_LIMIT,
                PASTE_IGNORE_ENTITIES,
                PASTE_IGNORE_INVENTORY,
                PASTE_NBT_BEHAVIOR,
                PASTE_TO_MCFUNCTION,
                PASTE_USE_FILL_COMMAND,
                PASTE_USING_COMMANDS_IN_SP,
                PICK_BLOCK_AVOID_DAMAGEABLE,
                PICK_BLOCK_AVOID_TOOLS,
                PICK_BLOCK_ENABLED,
                PICK_BLOCK_SHULKERS,
                PLACEMENT_REPLACE_BEHAVIOR,
                PLACEMENT_RESTRICTION,
                PLACEMENT_RESTRICTION_WARN,
                RENDER_MATERIALS_IN_GUI,
                RENDER_THREAD_NO_TIMEOUT,
                SIGN_TEXT_PASTE,
                TOOL_ITEM_ENABLED,
                UNHIDE_SCHEMATIC_PROJECTS,

                PASTE_REPLACE_BEHAVIOR,
                SELECTION_CORNERS_MODE,

                COMMAND_FILL_MAX_VOLUME,
                COMMAND_LIMIT,
                COMMAND_NAME_CLONE,
                COMMAND_NAME_FILL,
                COMMAND_NAME_SETBLOCK,
                COMMAND_NAME_SUMMON,
                COMMAND_TASK_INTERVAL,
                CUSTOM_SCHEMATIC_BASE_DIRECTORY,
                EASY_PLACE_SWAP_INTERVAL,
                PICK_BLOCKABLE_SLOTS,
                TOOL_ITEM
        );
    }

    public static class Visuals
    {
        public static final ConfigBoolean       ENABLE_AREA_SELECTION_RENDERING     = new ConfigBoolean("enableAreaSelectionBoxesRendering", true, "Включить рендеринг полей выбора области", "Area Selection Boxes Rendering");
        public static final ConfigBoolean       ENABLE_PLACEMENT_BOXES_RENDERING    = new ConfigBoolean("enablePlacementBoxesRendering", true, "Включить рендеринг блоков размещения схем", "Schematic Placement Boxes Rendering");
        public static final ConfigBoolean       ENABLE_RENDERING                    = new ConfigBoolean("enableRendering", true, "Опция переключения основного рендеринга. Включает/выключает рендеринг ВСЕХ модов.", "All Rendering");
        public static final ConfigBoolean       ENABLE_SCHEMATIC_BLOCKS             = new ConfigBoolean("enableSchematicBlocksRendering",  true, "Включает рендеринг блоков схемы.\n" +
                "Отключение этой функции позволяет видеть только цветовое наложение", "Schematic Blocks Rendering");
        public static final ConfigBoolean       ENABLE_SCHEMATIC_OVERLAY            = new ConfigBoolean("enableSchematicOverlay",  true, "Основная опция переключения для схемы\n" +
                "рендеринг наложения блоков", "Schematic Overlay Rendering");
        public static final ConfigBoolean       ENABLE_SCHEMATIC_RENDERING          = new ConfigBoolean("enableSchematicRendering", true, "Включить рендеринг схемы и наложения", "Schematic Rendering");
        public static final ConfigDouble        GHOST_BLOCK_ALPHA                   = new ConfigDouble( "ghostBlockAlpha", 0.5, 0, 1, "Альфа-значение блоков-призраков,\n" +
                "при рендеринге их как полупрозрачных.\n" +
                "§6Примечание: §7Вам также необходимо отдельно включить полупрозрачный рендеринг,\n" +
                "используя опцию 'renderBlocksAsTranslucent'!");
        public static final ConfigBoolean       IGNORE_EXISTING_FLUIDS              = new ConfigBoolean("ignoreExistingFluids", false, "Если включено, то любые блоки жидкости игнорируются как \"лишние блоки\"\n" +
                "и как \"неправильные блоки\", т.е. там, где в схеме есть воздушные или другие блоки.\n" +
                "В принципе, это делает строительство под водой гораздо менее раздражающим.\n" +
                "Примечание: Скорее всего, вы также захотите включить опцию 'renderCollidingSchematicBlocks'\n" +
                "одновременно, чтобы блоки рендерились внутри жидкостей.");
        public static final ConfigBoolean       OVERLAY_REDUCED_INNER_SIDES         = new ConfigBoolean("overlayReducedInnerSides", false, "Если включено, то смежные/касающиеся внутренние стороны\n" +
                "для накладок блоков удаляются/не отображаются");
        public static final ConfigDouble        PLACEMENT_BOX_SIDE_ALPHA            = new ConfigDouble( "placementBoxSideAlpha", 0.2, 0, 1, "Альфа-значение стороны боксов субрегиона");
        public static final ConfigBoolean       RENDER_AREA_SELECTION_BOX_SIDES     = new ConfigBoolean("renderAreaSelectionBoxSides", true, "Если включено, то поля выбора области будут\n" +
                "отображаться их боковые квадратики");
        public static final ConfigBoolean       RENDER_BLOCKS_AS_TRANSLUCENT        = new ConfigBoolean("renderBlocksAsTranslucent", false, "Если включено, то схемы отображаются\n" +
                "с использованием полупрозрачных \"призрачных блоков\"", "Translucent Schematic Block Rendering");
        public static final ConfigBoolean       RENDER_COLLIDING_SCHEMATIC_BLOCKS   = new ConfigBoolean("renderCollidingSchematicBlocks", false, "Если включено, то блоки в схемах отображаются\n" +
                "также если в мире клиента уже есть (неправильный) блок.\n" +
                "Вероятно, это полезно в основном при попытке построить\n" +
                "где на пути есть слои снега или воды.");
        public static final ConfigBoolean       RENDER_ERROR_MARKER_CONNECTIONS     = new ConfigBoolean("renderErrorMarkerConnections", false, "Рендеринг соединительных линий между последующими углами хиллайтбокса верификатора.\n" +
                "Это была ошибка рендеринга, с которой столкнулись некоторые люди, но, по крайней мере, некоторым игрокам\n" +
                "понравился и они попросили оставить его, так что эта опция \"восстанавливает\" его.");
        public static final ConfigBoolean       RENDER_ERROR_MARKER_SIDES           = new ConfigBoolean("renderErrorMarkerSides", true, "Если включено, то маркеры ошибок в верификаторе схем\n" +
                "будут иметь (полупрозрачные) стороны, а не только контур");
        public static final ConfigBoolean       RENDER_PLACEMENT_BOX_SIDES          = new ConfigBoolean("renderPlacementBoxSides", false, "Если эта опция включена, то помещенные в схему ячейки субрегионов\n" +
                "будут отрисованы их боковые квадраты");
        public static final ConfigBoolean       RENDER_PLACEMENT_ENCLOSING_BOX      = new ConfigBoolean("renderPlacementEnclosingBox", true, "Если включено, то вокруг\n" +
                "всех субрегионов в схеме (размещение)");
        public static final ConfigBoolean       RENDER_PLACEMENT_ENCLOSING_BOX_SIDES= new ConfigBoolean("renderPlacementEnclosingBoxSides", false, "Если включено, то окружающая рамка вокруг\n" +
                "вокруг размещения схемы будут отображаться ее боковые квадратики");
        public static final ConfigBoolean       RENDER_TRANSLUCENT_INNER_SIDES      = new ConfigBoolean("renderTranslucentBlockInnerSides", false, "Если включено, то стороны модели также отображаются\n" +
                "для внутренних сторон в полупрозрачном режиме");
        public static final ConfigBoolean       SCHEMATIC_OVERLAY_ENABLE_OUTLINES   = new ConfigBoolean("schematicOverlayEnableOutlines",  true, "Включает рендеринг контура проволочного каркаса для\n" +
                "наложения блока схемы", "Schematic Overlay Outlines");
        public static final ConfigBoolean       SCHEMATIC_OVERLAY_ENABLE_SIDES      = new ConfigBoolean("schematicOverlayEnableSides",     true, "Включает рендеринг полупрозрачных коробок/сторон для\n" +
                "наложения блока схемы", "Schematic Overlay Sides");
        public static final ConfigBoolean       SCHEMATIC_OVERLAY_MODEL_OUTLINE     = new ConfigBoolean("schematicOverlayModelOutline",    true, "Если включено, то при наложении схемы будут использоваться\n" +
                "квадраты/вертикали блочной модели вместо\n" +
                "традиционного наложения полного блока");
        public static final ConfigBoolean       SCHEMATIC_OVERLAY_MODEL_SIDES       = new ConfigBoolean("schematicOverlayModelSides",      true, "Если включено, то при наложении схемы будут использоваться\n" +
                "квадраты/вертикали блочной модели вместо\n" +
                "традиционного наложения полного блока");
        public static final ConfigDouble        SCHEMATIC_OVERLAY_OUTLINE_WIDTH     = new ConfigDouble( "schematicOverlayOutlineWidth",  1.0, 0, 64, "Ширина линии контуров блока (модели)");
        public static final ConfigDouble        SCHEMATIC_OVERLAY_OUTLINE_WIDTH_THROUGH = new ConfigDouble("schematicOverlayOutlineWidthThrough",  3.0, 0, 64, "Ширина линии контуров блока (модели),\n" +
                "когда наложение визуализируется через блоки");
        public static final ConfigBoolean       SCHEMATIC_OVERLAY_RENDER_THROUGH    = new ConfigBoolean("schematicOverlayRenderThroughBlocks", false, "Если включено, то схематическое наложение будет отображаться\n" +
                "через блоки. Это может быть полезно только после того, как вы\n" +
                " закончили и хотите увидеть все ошибки более легко.");
        public static final ConfigBoolean       SCHEMATIC_OVERLAY_TYPE_EXTRA        = new ConfigBoolean("schematicOverlayTypeExtra",       true, "Включает наложение схемы для дополнительных блоков");
        public static final ConfigBoolean       SCHEMATIC_OVERLAY_TYPE_MISSING      = new ConfigBoolean("schematicOverlayTypeMissing",     true, "Включает наложение схемы для отсутствующих блоков");
        public static final ConfigBoolean       SCHEMATIC_OVERLAY_TYPE_WRONG_BLOCK  = new ConfigBoolean("schematicOverlayTypeWrongBlock",  true, "Включает наложение схемы для неправильных блоков");
        public static final ConfigBoolean       SCHEMATIC_OVERLAY_TYPE_WRONG_STATE  = new ConfigBoolean("schematicOverlayTypeWrongState",  true, "Enables the schematic overlay for wrong states");
        public static final ConfigBoolean       SCHEMATIC_VERIFIER_BLOCK_MODELS     = new ConfigBoolean("schematicVerifierUseBlockModels", false, "Заставляет использовать модели блоков для всего в Schematic Verifier\n" +
                "списка результатов. Обычно модели элементов используются для всего, что\n" +
                "имеет элемент, а модели блоков используются только для блоков.\n" +
                "у которых нет элемента, плюс для цветочных горшков, чтобы увидеть содержащийся в них элемент.");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                ENABLE_RENDERING,
                ENABLE_SCHEMATIC_RENDERING,

                ENABLE_AREA_SELECTION_RENDERING,
                ENABLE_PLACEMENT_BOXES_RENDERING,
                ENABLE_SCHEMATIC_BLOCKS,
                ENABLE_SCHEMATIC_OVERLAY,
                IGNORE_EXISTING_FLUIDS,
                OVERLAY_REDUCED_INNER_SIDES,
                RENDER_AREA_SELECTION_BOX_SIDES,
                RENDER_BLOCKS_AS_TRANSLUCENT,
                RENDER_COLLIDING_SCHEMATIC_BLOCKS,
                RENDER_ERROR_MARKER_CONNECTIONS,
                RENDER_ERROR_MARKER_SIDES,
                RENDER_PLACEMENT_BOX_SIDES,
                RENDER_PLACEMENT_ENCLOSING_BOX,
                RENDER_PLACEMENT_ENCLOSING_BOX_SIDES,
                RENDER_TRANSLUCENT_INNER_SIDES,
                SCHEMATIC_OVERLAY_ENABLE_OUTLINES,
                SCHEMATIC_OVERLAY_ENABLE_SIDES,
                SCHEMATIC_OVERLAY_MODEL_OUTLINE,
                SCHEMATIC_OVERLAY_MODEL_SIDES,
                SCHEMATIC_OVERLAY_RENDER_THROUGH,
                SCHEMATIC_OVERLAY_TYPE_EXTRA,
                SCHEMATIC_OVERLAY_TYPE_MISSING,
                SCHEMATIC_OVERLAY_TYPE_WRONG_BLOCK,
                SCHEMATIC_OVERLAY_TYPE_WRONG_STATE,
                SCHEMATIC_VERIFIER_BLOCK_MODELS,

                GHOST_BLOCK_ALPHA,
                PLACEMENT_BOX_SIDE_ALPHA,
                SCHEMATIC_OVERLAY_OUTLINE_WIDTH,
                SCHEMATIC_OVERLAY_OUTLINE_WIDTH_THROUGH
        );
    }

    public static class InfoOverlays
    {
        public static final ConfigOptionList    BLOCK_INFO_LINES_ALIGNMENT          = new ConfigOptionList("blockInfoLinesAlignment", HudAlignment.TOP_RIGHT, "Выравнивание наложения информационных линий блока");
        public static final ConfigOptionList    BLOCK_INFO_OVERLAY_ALIGNMENT        = new ConfigOptionList("blockInfoOverlayAlignment", BlockInfoAlignment.TOP_CENTER, "Выравнивание информационной накладки блока");
        public static final ConfigOptionList    INFO_HUD_ALIGNMENT                  = new ConfigOptionList("infoHudAlignment", HudAlignment.BOTTOM_RIGHT, "Выравнивание \"Info HUD\",\n" +
                "используемого для списка материалов, позиций несоответствия верификатора схем и т.д.");
        public static final ConfigOptionList    TOOL_HUD_ALIGNMENT                  = new ConfigOptionList("toolHudAlignment", HudAlignment.BOTTOM_LEFT, "Выравнивание \" tool HUD\", при удержании настроенного \"инструмента\"");

        public static final ConfigBoolean       BLOCK_INFO_LINES_ENABLED            = new ConfigBoolean("blockInfoLinesEnabled", true, "Если включено, то наложение информации о блоке в стиле MiniHUD\nбудет отображаться для просматриваемого блока");
        public static final ConfigDouble        BLOCK_INFO_LINES_FONT_SCALE         = new ConfigDouble( "blockInfoLinesFontScale", 0.5, 0, 10, "Масштаб шрифта для информационных строк блока");
        public static final ConfigInteger       BLOCK_INFO_LINES_OFFSET_X           = new ConfigInteger("blockInfoLinesOffsetX", 4, 0, 2000, "Смещение x информационных строк блока от выбранного края");
        public static final ConfigInteger       BLOCK_INFO_LINES_OFFSET_Y           = new ConfigInteger("blockInfoLinesOffsetY", 4, 0, 2000, "Смещение по оси y информационных строк блока от выбранного края");
        public static final ConfigInteger       BLOCK_INFO_OVERLAY_OFFSET_Y         = new ConfigInteger("blockInfoOverlayOffsetY", 6, -2000, 2000, "Смещение по оси y наложения информации о блоке от выбранного края");
        public static final ConfigBoolean       BLOCK_INFO_OVERLAY_ENABLED          = new ConfigBoolean("blockInfoOverlayEnabled", true, "Включите рендеринг наложения информации о блоке, чтобы показать информацию\n" +
                "о просматриваемом блоке или маркере ошибки верификатора,\n" +
                "удерживая клавишу 'renderInfoOverlay'", "Block Info Overlay Rendering");
        public static final ConfigInteger       INFO_HUD_MAX_LINES                  = new ConfigInteger("infoHudMaxLines", 10, 1, 128, "Максимальное количество информационных строк, отображаемых на HUD одновременно");
        public static final ConfigInteger       INFO_HUD_OFFSET_X                   = new ConfigInteger("infoHudOffsetX", 1, 0, 32000, "Смещение по оси X информационного HUD от края экрана");
        public static final ConfigInteger       INFO_HUD_OFFSET_Y                   = new ConfigInteger("infoHudOffsetY", 1, 0, 32000, "Смещение по Y информационного HUD от края экрана");
        public static final ConfigDouble        INFO_HUD_SCALE                      = new ConfigDouble( "infoHudScale", 1, 0.1, 4, "Коэффициент масштабирования для общего текста Info HUD");
        public static final ConfigBoolean       INFO_OVERLAYS_TARGET_FLUIDS         = new ConfigBoolean("infoOverlaysTargetFluids", false, "Если эта функция включена, наложение информации о блоке и линии информации о блоке\n" +
                "смогут проводить лучи к блокам жидкости вместо того, чтобы проходить через них");
        public static final ConfigInteger       MATERIAL_LIST_HUD_MAX_LINES         = new ConfigInteger("materialListHudMaxLines", 10, 1, 128, "Максимальное количество элементов для отображения на\n" +
                "HUD информации списка материалов одновременно");
        public static final ConfigDouble        MATERIAL_LIST_HUD_SCALE             = new ConfigDouble( "materialListHudScale", 1, 0.1, 4, "Масштабный коэффициент для информационного HUD списка материалов");
        public static final ConfigBoolean       STATUS_INFO_HUD                     = new ConfigBoolean("statusInfoHud", false, "Включите рендеринг HUD-информации о состоянии,\n" +
                "который отображает несколько битов информации о состоянии, таких как\n" +
                "текущий режим слоя и состояние включенного рендеринга");
        public static final ConfigBoolean       STATUS_INFO_HUD_AUTO                = new ConfigBoolean("statusInfoHudAuto", true, "Разрешить автоматическое кратковременное включение HUD информации о статусе \"при необходимости\",\n" +
                "например, при создании размещения и отключении рендеринга");
        public static final ConfigInteger       TOOL_HUD_OFFSET_X                   = new ConfigInteger("toolHudOffsetX", 1, 0, 32000, "Смещение по оси X информационного HUD от края экрана");
        public static final ConfigInteger       TOOL_HUD_OFFSET_Y                   = new ConfigInteger("toolHudOffsetY", 1, 0, 32000, "Смещение по оси X информационного HUD от края экрана");
        public static final ConfigDouble        TOOL_HUD_SCALE                      = new ConfigDouble( "toolHudScale", 1, 0.1, 4, "Коэффициент масштабирования для текста Tool HUD");
        public static final ConfigDouble        VERIFIER_ERROR_HILIGHT_ALPHA        = new ConfigDouble( "verifierErrorHilightAlpha", 0.2, 0, 1, "Альфа-значение сторон поля маркера ошибок");
        public static final ConfigInteger       VERIFIER_ERROR_HILIGHT_MAX_POSITIONS= new ConfigInteger("verifierErrorHilightMaxPositions", 1000, 1, 1000000, "Максимальное количество несовпадающих позиций для отображения\n" +
                "одновременно в оверлее Schematic Verifier.");
        public static final ConfigBoolean       VERIFIER_OVERLAY_ENABLED            = new ConfigBoolean("verifierOverlayEnabled", true, "Включить рендеринг наложения маркеров Schematic Verifier", "Verifier Overlay Rendering");
        public static final ConfigBoolean       WARN_DISABLED_RENDERING             = new ConfigBoolean("warnDisabledRendering", true, "Должно ли предупреждающее сообщение о том, что вы находитесь в режиме слоя\n" +
                "или о том, что некоторые опции рендеринга отключены\n" +
                "отображаться при загрузке новой схемы\n" +
                "или при создании нового размещения");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                BLOCK_INFO_LINES_ENABLED,
                BLOCK_INFO_OVERLAY_ENABLED,
                INFO_OVERLAYS_TARGET_FLUIDS,
                STATUS_INFO_HUD,
                STATUS_INFO_HUD_AUTO,
                VERIFIER_OVERLAY_ENABLED,
                WARN_DISABLED_RENDERING,

                BLOCK_INFO_LINES_ALIGNMENT,
                BLOCK_INFO_OVERLAY_ALIGNMENT,
                INFO_HUD_ALIGNMENT,
                TOOL_HUD_ALIGNMENT,

                BLOCK_INFO_LINES_OFFSET_X,
                BLOCK_INFO_LINES_OFFSET_Y,
                BLOCK_INFO_LINES_FONT_SCALE,
                BLOCK_INFO_OVERLAY_OFFSET_Y,
                INFO_HUD_MAX_LINES,
                INFO_HUD_OFFSET_X,
                INFO_HUD_OFFSET_Y,
                INFO_HUD_SCALE,
                MATERIAL_LIST_HUD_MAX_LINES,
                MATERIAL_LIST_HUD_SCALE,
                TOOL_HUD_OFFSET_X,
                TOOL_HUD_OFFSET_Y,
                TOOL_HUD_SCALE,
                VERIFIER_ERROR_HILIGHT_ALPHA,
                VERIFIER_ERROR_HILIGHT_MAX_POSITIONS
        );
    }

    public static class Colors
    {
        public static final ConfigColor AREA_SELECTION_BOX_SIDE_COLOR       = new ConfigColor("areaSelectionBoxSideColor",          "#30FFFFFF", "Цвет полей выделения области, когда они не выделены");
        public static final ConfigColor HIGHTLIGHT_BLOCK_IN_INV_COLOR       = new ConfigColor("hightlightBlockInInventoryColor",    "#30FF30FF", "Цвет подсветки для элемента просматриваемого блока");
        public static final ConfigColor MATERIAL_LIST_HUD_ITEM_COUNTS       = new ConfigColor("materialListHudItemCountsColor",     "#FFFFAA00", "Цвет текста количества элементов в списке материалов в информации HUD");
        public static final ConfigColor REBUILD_BREAK_OVERLAY_COLOR         = new ConfigColor("schematicRebuildBreakPlaceOverlayColor", "#4C33CC33", "Цвет наложения селектора разбиения или размещения блоков в режиме Schematic Rebuild");
        public static final ConfigColor REBUILD_BREAK_EXCEPT_OVERLAY_COLOR  = new ConfigColor("schematicRebuildBreakExceptPlaceOverlayColor", "#4CF03030", "Цвет накладки режима Schematic Rebuild, разбивающей все блоки, кроме накладки целевого селектора");
        public static final ConfigColor REBUILD_REPLACE_OVERLAY_COLOR       = new ConfigColor("schematicRebuildReplaceOverlayColor","#4CF0A010", "Цвет наложения селектора замены в режиме Schematic Rebuild");
        public static final ConfigColor SCHEMATIC_OVERLAY_COLOR_EXTRA       = new ConfigColor("schematicOverlayColorExtra",         "#4CFF4CE6", "Цвет наложения блоков для дополнительных блоков");
        public static final ConfigColor SCHEMATIC_OVERLAY_COLOR_MISSING     = new ConfigColor("schematicOverlayColorMissing",       "#2C33B3E6", "Цвет наложения блоков для отсутствующих блоков");
        public static final ConfigColor SCHEMATIC_OVERLAY_COLOR_WRONG_BLOCK = new ConfigColor("schematicOverlayColorWrongBlock",    "#4CFF3333", "Цвет наложения блоков для неправильных блоков");
        public static final ConfigColor SCHEMATIC_OVERLAY_COLOR_WRONG_STATE = new ConfigColor("schematicOverlayColorWrongState",    "#4CFF9010", "Цвет наложения блоков для неправильных состояний блоков");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
                AREA_SELECTION_BOX_SIDE_COLOR,
                HIGHTLIGHT_BLOCK_IN_INV_COLOR,
                MATERIAL_LIST_HUD_ITEM_COUNTS,
                REBUILD_BREAK_OVERLAY_COLOR,
                REBUILD_BREAK_EXCEPT_OVERLAY_COLOR,
                REBUILD_REPLACE_OVERLAY_COLOR,
                SCHEMATIC_OVERLAY_COLOR_EXTRA,
                SCHEMATIC_OVERLAY_COLOR_MISSING,
                SCHEMATIC_OVERLAY_COLOR_WRONG_BLOCK,
                SCHEMATIC_OVERLAY_COLOR_WRONG_STATE
        );
    }

    public static void loadFromFile()
    {
        File configFile = new File(FileUtils.getConfigDirectory(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead())
        {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject())
            {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "Colors", Colors.OPTIONS);
                ConfigUtils.readConfigBase(root, "Generic", Generic.OPTIONS);
                ConfigUtils.readConfigBase(root, "Hotkeys", Hotkeys.HOTKEY_LIST);
                ConfigUtils.readConfigBase(root, "InfoOverlays", InfoOverlays.OPTIONS);
                ConfigUtils.readConfigBase(root, "Visuals", Visuals.OPTIONS);
            }
        }

        DataManager.setToolItem(Generic.TOOL_ITEM.getStringValue());
        InventoryUtils.setPickBlockableSlots(Generic.PICK_BLOCKABLE_SLOTS.getStringValue());
    }

    public static void saveToFile()
    {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs())
        {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "Colors", Colors.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Generic", Generic.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Hotkeys", Hotkeys.HOTKEY_LIST);
            ConfigUtils.writeConfigBase(root, "InfoOverlays", InfoOverlays.OPTIONS);
            ConfigUtils.writeConfigBase(root, "Visuals", Visuals.OPTIONS);

            JsonUtils.writeJsonToFile(root, new File(dir, CONFIG_FILE_NAME));
        }
    }

    @Override
    public void load()
    {
        loadFromFile();
    }

    @Override
    public void save()
    {
        saveToFile();
    }
}
