package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class EsEsLangProvider extends InvTweaksLangProvider {
    public EsEsLangProvider(PackOutput output) {
        super(output, "es_es");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "\"%s\" activado");
        add("invtweaks.loadconfig.refresh.error", "[1] Error al actualizar las propiedades del archivo");
        add("invtweaks.loadconfig.invalidkeywords", "Palabras clave no válidas");
        add("invtweaks.loadconfig.filemissing", "no existe, creando uno predeterminado.");
        add("invtweaks.loadconfig.done", "Se ha cargado la configuración.");
        add("invtweaks.propsfile.loaded", "Se han cargado las propiedades del mod");
        add("invtweaks.propsfile.error", "[14] Error al crear el archivo de propiedades del mod");
        add("invtweaks.extract.find.error", "[15] ¡El mod no funciona, porque %s no se ha encontrado!");
        add("invtweaks.extract.create.error", "[16] ¡El mod no funciona, porque %s ha fallado durante la creación!");
        add("invtweaks.sort.inventory.error", "[2] Error al ordenar el inventario");
        add("invtweaks.sort.chest.error", "[3] Error al ordenar el contenedor");
        add("invtweaks.sort.autorefill.error", "[4] Error al activar la recarga automática");
        add("invtweaks.sort.releaseitem.error", "[5] Error al soltar el elemento objeto");
        add("invtweaks.shortcut.error", "[6] Error al activar el atajo");
        add("invtweaks.pickup.error", "[7] Error al mover la pila recogida");
        add("invtweaks.button.settings.tooltip", "Ajustes del inventario");
        add("invtweaks.button.chest1.tooltip", "Orden predeterminado");
        add("invtweaks.button.chest2.tooltip", "Ordenar por columnas");
        add("invtweaks.button.chest3.tooltip", "Ordenar por filas");
        add("invtweaks.button.nshape.tooltip", "Ordenar en forma de N");
        add("invtweaks.button.fill.tooltip", "Llenar: clic mueve un stack de un objeto que el contenedor ya tiene\nShift/Ctrl+clic: mover todo");
        add("invtweaks.button.empty.tooltip", "Vaciar: clic mueve un stack de un objeto que ya llevas\nShift/Ctrl+clic: mover todo");
        add("invtweaks.transfer.error", "[18] No se pudieron mover los objetos");
        add("invtweaks.settings.title", "Ajustes de inventario y cofres");
        add("invtweaks.settings.on", "ON");
        add("invtweaks.settings.off", "OFF");
        add("invtweaks.settings.key", "Orden:");
        add("invtweaks.settings.disableci", "Desactivar el inventario");
        add("invtweaks.settings.disableci.tooltip", "En conflicto con el inventario");
        add("invtweaks.settings.middleclick", "Clic central");
        add("invtweaks.settings.middleclick.tooltip", "Permite ordenar utilizando\nel clic central");
        add("invtweaks.settings.chestbuttons", "Botones del cofre");
        add("invtweaks.settings.chestbuttons.tooltip", "Añade tres botones\na los cofres para ordenarlos");
        add("invtweaks.settings.shortcuts", "Atajos");
        add("invtweaks.settings.shortcuts.tooltip", "Activa varios atajos\npara mover los objetos");
        add("invtweaks.settings.autorefill", "Recarga automática");
        add("invtweaks.settings.autorefill.tooltip", "Reemplaza las herramientas rotas y pilas vacías\npor otros objetos de tu inventario.");
        add("invtweaks.settings.beforebreak", "Recambiar objetos a punto de romperse");
        add("invtweaks.settings.beforebreak.tooltip", "Reemplaza las herramientas antes de que se rompan");
        add("invtweaks.settings.moreoptions", "Más opciones...");
        add("invtweaks.settings.moreoptions.tooltip", "Cosas aún más interesantes");
        add("invtweaks.settings.rulesfile", "Abrir el archivo de reglas de clasificación...");
        add("invtweaks.settings.rulesfile.error", "[8] Error al abrir el archivo de reglas");
        add("invtweaks.settings.treefile", "Abrir el archivo de árbol de objetos...");
        add("invtweaks.settings.treefile.error", "[9] Error al abrir el archivo de árbol");
        add("invtweaks.settings.onlinehelp", "Abrir la ayuda en el navegador...");
        add("invtweaks.settings.onlinehelp.error", "[10] Error al abrir el menú de ayuda");
        add("invtweaks.help.bugsorting", "¿No funciona?");
        add("invtweaks.help.bugsorting.pt1", "Si tu servidor parece \"cancelar\" lo que hace el mod,");
        add("invtweaks.help.bugsorting.pt2", "probablemente sea a causa de la extensión NoCheatPlus.");
        add("invtweaks.help.bugsorting.pt3", "Pregúntale a tu administrador si puede desactivar las siguientes opciones:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" y \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Nota: ¡En los servidores PvP las opciones de recarga automática y");
        add("invtweaks.settings.pvpwarning.pt2", "equiparte la armadura automáticamente pueden considerarse trampas!");
        add("invtweaks.settings.exit", "Hecho");
        add("invtweaks.settings.advanced.sortonpickup", "Ordenar al recoger");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Solo ordenar al recoger");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Evita llenar la barra de acceso rápido\ncon todos los objetos que recojas");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Solo en el modo un jugador");
        add("invtweaks.settings.advanced.autoequip", "Autoequipar armadura");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Te equipa la mejor armadura\ndisponible al ordenar");
        add("invtweaks.settings.advanced.sounds", "Activar sonido");
        add("invtweaks.settings.advanced.sounds.tooltip", "Reproduce sonidos al ordenar\no al activar la recarga automática");
        add("invtweaks.settings.advanced.mappingsfile", "Abrir el archivo de atajos de mapas...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Error al abrir el archivo de mapas");
        add("invtweaks.settings.advanced.serverassist", "Movimiento de objeto del servidor");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Activa el uso de asistencia del servidor para el movimiento de objetos al ordenar.");
        add("invtweaks.settings.advanced.transferhotbar", "Flechas usan hotbar");
        add("invtweaks.settings.advanced.transferhotbar.tooltip", "Llenar también toma objetos de la barra rápida,\ny vaciar también los pone en ella");
        add("invtweaks.settings.advanced.mousetweaks", "Mouse Tweaks");
        add("invtweaks.settings.advanced.mousetweaks.tooltip", "Shift+arrastrar mueve rápido cada pila, arrastrar con una pila junta los mismos objetos,\ny la rueda mueve un objeto entre los inventarios");
        add("invtweaks.help.shortcuts.title", "Ayuda con los atajos");
        add("invtweaks.help.shortcuts.click", "Clic");
        add("invtweaks.help.shortcuts.or", "o");
        add("invtweaks.help.shortcuts.onestack", "Mover una pila");
        add("invtweaks.help.shortcuts.oneitem", "Mover un objeto");
        add("invtweaks.help.shortcuts.allitems", "Mover todos los objetos del mismo tipo");
        add("invtweaks.help.shortcuts.everything", "Mover todo");
        add("invtweaks.help.shortcuts.hotbar", "Mover a la barra de acceso rápido");
        add("invtweaks.help.shortcuts.emptyslot", "Mover a un espacio vacío");
        add("invtweaks.help.shortcuts.rightclick", "(Cualquier atajo) + clic derecho en vez de izquierdo");
        add("invtweaks.help.shortcuts.drop", "Soltar");
        add("invtweaks.help.shortcuts.craftall", "Usar en fabricación todo el inventario");
        add("invtweaks.help.shortcuts.craftone", "Fabricar al inventario");
        add("invtweaks.help.shortcuts.selectconfig", "Selecciona una configuración de orden");
        add("invtweaks.help.shortcuts.forward", "(Hacia adelante)");
        add("invtweaks.help.shortcuts.backward", "(Hacia atrás)");
        add("invtweaks.keyboardfix.error", "[12] Estoy teniendo problemas con el teclado");
        add("invtweaks.keyboardfix.recover", "Ya está solucionado, perdón por las molestias.");
        add("invtweaks.modcompat.ci.error", "[13] Error con la compatibilidad del inventario");
        add("invtweaks.key.category", "Opciones de inventario");
        add("invtweaks.key.sort", "Clasificación de inventario");
    }
}
