package nadiendev.inventorytweaksrevived.data.lang;

import net.minecraft.data.PackOutput;

public class PtBrLangProvider extends InvTweaksLangProvider {
    public PtBrLangProvider(PackOutput output) {
        super(output, "pt_br");
    }

    @Override
    protected void addTranslations() {
        add("invtweaks.loadconfig.enabled", "'%s' ativado");
        add("invtweaks.loadconfig.refresh.error", "[1] Falha ao atualizar as propriedades de arquivo");
        add("invtweaks.loadconfig.invalidkeywords", "Palavras-chave inválidas encontradas");
        add("invtweaks.loadconfig.filemissing", "faltando, criando um padrão.");
        add("invtweaks.loadconfig.done", "Configuração carregada.");
        add("invtweaks.propsfile.loaded", "Propriedades do mod carregadas");
        add("invtweaks.propsfile.error", "[14] Falha ao criar o arquivo de propriedades do mod");
        add("invtweaks.extract.find.error", "[15] O mod não funcionará, porque %s não pode ser encontrado(a)!");
        add("invtweaks.extract.create.error", "[16] O mod não funcionará porque a criação %s falhou!");
        add("invtweaks.sort.inventory.error", "[2] Falha ao ordenar inventário");
        add("invtweaks.sort.chest.error", "[3] Falha ao ordenar recipiente");
        add("invtweaks.sort.autorefill.error", "[4] Falha ao acionar auto-recarga");
        add("invtweaks.sort.releaseitem.error", "[5] Falha ao liberar o item");
        add("invtweaks.shortcut.error", "[6] Falha ao acionar o atalho");
        add("invtweaks.pickup.error", "[7] Falha ao mover a pilha retirada");
        add("invtweaks.button.settings.tooltip", "Configurações de inventário");
        add("invtweaks.button.chest1.tooltip", "Organização Padrão");
        add("invtweaks.button.chest2.tooltip", "Organizar em colunas");
        add("invtweaks.button.chest3.tooltip", "Organizar em linhas");
        add("invtweaks.settings.title", "Ajustes de inventário e baús");
        add("invtweaks.settings.on", "Ligado");
        add("invtweaks.settings.off", "Desligado");
        add("invtweaks.settings.key", "Tecla de organização:");
        add("invtweaks.settings.disableci", "Desativar CI");
        add("invtweaks.settings.disableci.tooltip", "Em conflito com \"Convenient Inventory\"");
        add("invtweaks.settings.middleclick", "Clique do meio");
        add("invtweaks.settings.middleclick.tooltip", "Permite organizar usando\no clique do meio");
        add("invtweaks.settings.chestbuttons", "botões do baú");
        add("invtweaks.settings.chestbuttons.tooltip", "Adiciona três botões\naos baús para organizá-los");
        add("invtweaks.settings.shortcuts", "Atalhos");
        add("invtweaks.settings.shortcuts.tooltip", "Habilita vários atalhos\npara mover itens");
        add("invtweaks.settings.autorefill", "Auto-recarga");
        add("invtweaks.settings.autorefill.tooltip", "Substitui ferramentas quebradas e pilhas vazias\ncom os outros de seu inventário");
        add("invtweaks.settings.beforebreak", "Troca de itens pela durabilidade");
        add("invtweaks.settings.beforebreak.tooltip", "Troca de ferramentas pouco antes delas quebrarem");
        add("invtweaks.settings.moreoptions", "Mais opções...");
        add("invtweaks.settings.moreoptions.tooltip", "Ainda mais coisas legais");
        add("invtweaks.settings.rulesfile", "Abrir o arquivo de regras de organização...");
        add("invtweaks.settings.rulesfile.error", "[8] Falha ao abrir o arquivo de regras");
        add("invtweaks.settings.treefile", "Abra o arquivo de árvore de itens...");
        add("invtweaks.settings.treefile.error", "[9] Falha ao abrir o arquivo de árvore");
        add("invtweaks.settings.onlinehelp", "Abrir ajudar no navegador...");
        add("invtweaks.settings.onlinehelp.error", "[10] Falha ao abrir a ajuda");
        add("invtweaks.help.bugsorting", "Não está funcionando?");
        add("invtweaks.help.bugsorting.pt1", "Se o seu servidor parece \"cancelar\" o que este mod faz,");
        add("invtweaks.help.bugsorting.pt2", "A provável causa disto é que o seu servidor usa o plugin NoCheatPlus.");
        add("invtweaks.help.bugsorting.pt3", "Peça ao seu administrador para desabilitar as seguintes opções:");
        add("invtweaks.help.bugsorting.pt4", "\"inventory.fastclick\" e \"combined.improbable\"");
        add("invtweaks.settings.pvpwarning.pt1", "Nota: Em servidores PvP, as opções auto-recarga e auto-equipar armaduras");
        add("invtweaks.settings.pvpwarning.pt2", "são muitas vezes consideradas como trapaça!");
        add("invtweaks.settings.exit", "Feito");
        add("invtweaks.settings.advanced.sortonpickup", "Ordenar ao retirar");
        add("invtweaks.settings.advanced.sortonpickup.soloonly", "Somente SP");
        add("invtweaks.settings.advanced.sortonpickup.tooltip", "Evitar preencher a barra rápida com\ntudo o que você pega");
        add("invtweaks.settings.advanced.sortonpickup.tooltip.soloonly", "Apenas no modo single player");
        add("invtweaks.settings.advanced.autoequip", "Auto-equipar armadura");
        add("invtweaks.settings.advanced.autoequip.tooltip", "Equipar a melhor armadura\ndisponível quando ordenar");
        add("invtweaks.settings.advanced.sounds", "Ativar som");
        add("invtweaks.settings.advanced.sounds.tooltip", "Reproduzir sons ao ordenar\nou quando a auto-recarga é acionada");
        add("invtweaks.settings.advanced.mappingsfile", "Abra o arquivo de atalhos mapeados...");
        add("invtweaks.settings.advanced.mappingsfile.error", "[11] Falha ao abrir o arquivo de mapeamentos");
        add("invtweaks.settings.advanced.serverassist", "Movimento de Item do Servidor");
        add("invtweaks.settings.advanced.serverassist.tooltip", "Habilite a assistência do servidor para mover os itens durante a classificação.");
        add("invtweaks.help.shortcuts.title", "Atalhos de ajuda");
        add("invtweaks.help.shortcuts.click", "Clique");
        add("invtweaks.help.shortcuts.or", "ou");
        add("invtweaks.help.shortcuts.onestack", "Mover uma pilha");
        add("invtweaks.help.shortcuts.oneitem", "Mover um item");
        add("invtweaks.help.shortcuts.allitems", "Mover todos os itens do mesmo tipo");
        add("invtweaks.help.shortcuts.everything", "mover tudo");
        add("invtweaks.help.shortcuts.hotbar", "Mover para hotbar");
        add("invtweaks.help.shortcuts.emptyslot", "Mover para um slot vazio");
        add("invtweaks.help.shortcuts.rightclick", "(Qualquer atalho) + Direito em vez de clique esquerdo");
        add("invtweaks.help.shortcuts.drop", "Largar");
        add("invtweaks.help.shortcuts.craftall", "Todo o craft para o inventário");
        add("invtweaks.help.shortcuts.craftone", "Craft para o inventário");
        add("invtweaks.help.shortcuts.selectconfig", "Selecione configuração de ordenação");
        add("invtweaks.help.shortcuts.forward", "(Próximo)");
        add("invtweaks.help.shortcuts.backward", "(Para Trás)");
        add("invtweaks.keyboardfix.error", "[12] Estou tendo problemas com o teclado");
        add("invtweaks.keyboardfix.recover", "Ok reparado, desculpe por isso.");
        add("invtweaks.modcompat.ci.error", "[13] Falha para gerenciar compatibilidade \"Convenient Inventory\"");
        add("invtweaks.key.category", "Inventory Tweaks");
        add("invtweaks.key.sort", "Organização do Inventário");
    }
}
