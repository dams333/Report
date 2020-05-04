package ch.dams333.report.commands;

import ch.dams333.report.Report;
import ch.dams333.report.objects.report.ReportOBJ;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class ListCommand implements CommandExecutor {
    Report main;
    public ListCommand(Report re) {
        this.main = re;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 1){
                String reported = args[0];
                if(Bukkit.getOfflinePlayer(reported) != null) {

                    Inventory inv = Bukkit.createInventory(null, 54, reported + " > page 1");

                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
                    SkullMeta headM = (SkullMeta) head.getItemMeta();
                    headM.setOwningPlayer(Bukkit.getOfflinePlayer(reported));
                    headM.setDisplayName(ChatColor.GREEN + "Reports de " + ChatColor.DARK_GREEN + reported);
                    head.setItemMeta(headM);
                    inv.setItem(4, head);

                    ItemStack book = new ItemStack(Material.BOOK);
                    ItemMeta bookM = book.getItemMeta();
                    bookM.setDisplayName(ChatColor.LIGHT_PURPLE + String.valueOf(main.listReports(reported).size()) + " reports trouvés");
                    book.setItemMeta(bookM);
                    inv.setItem(0, book);

                    ItemStack delete = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta deleteM = delete.getItemMeta();
                    deleteM.setDisplayName(ChatColor.RED + "Supprimer tous les reports");
                    delete.setItemMeta(deleteM);
                    inv.setItem(8, delete);

                    ItemStack arrow = new ItemStack(Material.ARROW);
                    ItemMeta arrowM = arrow.getItemMeta();
                    arrowM.setDisplayName(ChatColor.GRAY + "Page précédente");
                    arrow.setItemMeta(arrowM);
                    inv.setItem(45, arrow);
                    arrowM.setDisplayName(ChatColor.GRAY + "Page suivante");
                    arrow.setItemMeta(arrowM);
                    inv.setItem(53, arrow);

                    int page = 1;
                    int reportPerPage = 36;

                    int start = (page - 1) * reportPerPage;
                    int end = start + (reportPerPage - 1);

                    for(int i = 9; i < 45; i++){

                        if((main.listReports(reported).size() - 1) >= start){
                            ReportOBJ report = main.listReports(reported).get(start);
                            String reporter = report.getReporter();
                            String[] reason = report.getReason().split(" ");
                            StringBuilder builder = new StringBuilder();
                            if(reason.length > 5){
                                for(int x = 0; x < 6; x++){
                                    builder.append(reason[x] + " ");
                                }
                                builder.append("...");
                            }else {
                                for (String part : reason) {
                                    builder.append(part + " ");
                                }
                            }
                            String preview = builder.toString();
                            ItemStack it = new ItemStack(Material.PAPER);
                            ItemMeta itM = it.getItemMeta();
                            itM.setDisplayName(preview);
                            itM.setLore(Arrays.asList(ChatColor.GREEN + "Reporter: " + ChatColor.DARK_GREEN + reporter, ChatColor.LIGHT_PURPLE + "Clique gauche pour voir plus", ChatColor.RED + "Clique droit pour effacer"));
                            it.setItemMeta(itM);
                            inv.setItem(i, it);
                        }else{
                            break;
                        }

                        if(start < end){
                            start++;
                        }
                    }

                    p.openInventory(inv);

                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Le joueur n'existe pas");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "/list [player]");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Veuillez rejoindre le serveur");
        return false;
    }
}
