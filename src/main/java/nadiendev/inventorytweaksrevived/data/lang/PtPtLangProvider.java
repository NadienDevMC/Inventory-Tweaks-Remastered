package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class PtPtLangProvider extends InvTweaksLangProvider {
    public PtPtLangProvider(PackOutput output) {
        super(output, "pt_pt");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' ativado");
        add("invtweaks.loadconfig.refresh.error", "[1] Falha ao atualizar propriedades de ficheiro");
        add("invtweaks.loadconfig.invalidkeywords", "Palavras chave inválidas eliminadas");
        add("invtweaks.loadconfig.filemissing", "em falta, criando um padrão.");
        add("invtweaks.loadconfig.done", "Configuração carregada.");
        add("invtweaks.propsfile.loaded", "Propriedades do mod carregadas");
        add("invtweaks.propsfile.error", "[14] Falha ao criar o ficheiro de propriedades do mod");
        add("invtweaks.extract.find.error", "[15] O mod não funcionará porque %s não pôde ser encontrado!");
        add("invtweaks.extract.create.error", "[16] O mod não funcionará porque a criação de %s falhou!");
        add("invtweaks.sort.inventory.error", "[2] Falha ao ordenar inventário");
        add("invtweaks.sort.chest.error", "[3] Falha ao ordenar recipiente");
        add("invtweaks.sort.autorefill.error", "[4] Falha ao ativar a auto-recarga");
        add("invtweaks.sort.releaseitem.error", "[5] Falha ao libertar o item");
        add("invtweaks.shortcut.error", "[6] Falha ao acionar o atalho");
        add("invtweaks.pickup.error", "[7] Falha ao mover a pilha apanhada");
        add("invtweaks.button.settings.tooltip", "Configurar o inventário");
        add("invtweaks.button.chest1.tooltip", "Ordenação padrão");
        add("invtweaks.button.chest2.tooltip", "Ordenar por colunas");
        add("invtweaks.button.chest3.tooltip", "Ordenar por linhas");
        add("invtweaks.settings.title", "Configurar inventário e baús");
        add("invtweaks.settings.on", "Ligado");
        add("invtweaks.settings.off", "Desligado");
        add("invtweaks.settings.key", "Tecla de atalho para ordenar:");
        add("invtweaks.settings.disableci", "Desativar CI");
        add("invtweaks.settings.disableci.tooltip", "Em conflito com \"Convenient Inventory\"");
        add("invtweaks.settings.middleclick", "Clique do meio");
        add("invtweaks.settings.middleclick.tooltip", "Permite ordenar com\no botão do meio");
        add("invtweaks.settings.chestbuttons", "Botões de baú");
        add("invtweaks.settings.chestbuttons.tooltip", "Adiciona três botões\nnos baús para os ordenar");
        add("invtweaks.settings.shortcuts", "Atalhos");
        add("invtweaks.settings.shortcuts.tooltip", "Ativa várias teclas de atalho\npara mover os objectos");
        add("invtweaks.settings.autorefill", "Auto-recarga");
        add("invtweaks.settings.autorefill.tooltip", "Substitui ferramentas partidas & pilhas vazias\npor outras no teu inventário");
        add("invtweaks.settings.beforebreak", "Substituição amiga da reparação");
        add("invtweaks.settings.beforebreak.tooltip", "Substitui as ferramentas antes de elas se partirem");
        add("invtweaks.settings.moreoptions", "Mais opções...");
        add("invtweaks.settings.moreoptions.tooltip", "Ainda mais coisas fixes");
        add("invtweaks.settings.rulesfile", "Abrir o ficheiro de regras de classificação...");
        add("invtweaks.settings.rulesfile.error", "[8] Falha ao abrir o ficheiro de regras de classificação");
        add("invtweaks.settings.treefile", "Abrir o ficheiro da árvore de itens...");
        add("invtweaks.settings.treefile.error", "[9] Falha ao abrir o ficheiro da árvore");
        add("invtweaks.settings.onlinehelp", "Abrir a ajuda no navegador...");
        add("invtweaks.settings.onlinehelp.error", "[10] Falha ao abrir ajuda");
        add("invtweaks.help.bugsorting", "Não está a funcionar?");
        add("invtweaks.help.bugsorting.pt1", "Se parecer que o servidor 'cancela' o que este mod faz,");
        add("invtweaks.help.bugsorting.pt2", "é porque, talvêz, ele esteja a usar o plugin NoCheatPlus.");
        add("invtweaks.help.bugsorting.pt3", "Peça ao seu administrador para desactivar as seguintes opções:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" e \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Nota: Em servidores PvP, a auto-recarga e o equipamento automático");
        add("invtweaks.settings.pvpwarning.pt2", "são normalmente considerados batota!");
        add("invtweaks.settings.exit", "Concluído");
        add("invtweaks.settings.advanced.sortonpickup", "Ordenar ao apanhar");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Apenas SP");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Previne que tudo o que\napanhes encha a hotbar");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Apenas no modo de Um Jogador");
        add("invtweaks.settings.advanced.autoequip", "Equipar automaticamente armadura");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Equipa a melhor armadura\ndisponível enquanto ordenando");
        add("invtweaks.settings.advanced.sounds", "Ativar som");
        add("invtweaks.settings.advanced.sounds.tooltip", "Tocar som quando ordenando\nou quando a auto-recarga é ativada");
        add("invtweaks.settings.advanced.mappingsfile", "Abrir o ficheiro de mapeamento dos atalhos...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11]Falha ao abrir ficheiro de mapeamentos");
        add("invtweaks.settings.advanced.serverassist", "Movimento de Items num Servidor");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Habilita o uso da assistência do servidor para mover os items enquanto estão a ser ordenados.");
        add("invtweaks.help.shortcuts.title", "Ajuda para atalhos");
        add("invtweaks.help.shortcuts.click", "Cliqua em");
        add("invtweaks.help.shortcuts.or", "ou");
        add("invtweaks.help.shortcuts.onestack", "Mover uma pilha");
        add("invtweaks.help.shortcuts.oneitem", "Mover um item");
        add("invtweaks.help.shortcuts.allitems", "Mover todos os itens do mesmo tipo");
        add("invtweaks.help.shortcuts.everything", "Mover tudo");
        add("invtweaks.help.shortcuts.hotbar", "Mover para hotbar");
        add("invtweaks.help.shortcuts.emptyslot", "Mover para espaço vazio");
        add("invtweaks.help.shortcuts.rightclick", "(Qualquer atalho) + clique direito em vez de clique esquerdo");
        add("invtweaks.help.shortcuts.drop", "Largar");
        add("invtweaks.help.shortcuts.craftall", "Construir tudo para o inventário");
        add("invtweaks.help.shortcuts.craftone", "Construir para o inventário");
        add("invtweaks.help.shortcuts.selectconfig", "Seleciona a configuração de classificação");
        add("invtweaks.help.shortcuts.forward", "(Frente)");
        add("invtweaks.help.shortcuts.backward", "(Trás)");
        add("invtweaks.keyboardfix.error", "[12] Estou a ter problemas com o teclado");
        add("invtweaks.keyboardfix.recover", "Tudo voltou ao normal, desculpa-me por isso.");
        add("invtweaks.modcompat.ci.error", "[13] Falha ao gerir compatibilidade com \"Convenient Inventory\"");
        add("key.category.inventorytweaksrevived.invtweaks", "Inventory Tweaks");
        add("invtweaks.key.sort", "Organização do Inventário");
    }
}
