package ch.dams333.report.listener;

import ch.dams333.report.Report;
import ch.dams333.report.objects.report.ReportOBJ;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Listener implements org.bukkit.event.Listener {
    Report main;
    public Listener(Report re) {
        this.main = re;
    }

    @EventHandler
    public void click(InventoryClickEvent e){

        Player p = (Player) e.getWhoClicked();
        ItemStack it = e.getCurrentItem();
        InventoryAction action = e.getAction();
        Inventory inv = e.getInventory();
        int slot = e.getSlot();

        if(inv.getName().split(" ").length > 3) {
            if (inv.getName().split(" ")[1].equals(">") && inv.getName().split(" ")[2].equals("Supprimer")) {

                int reportPerPage = 36;
                String reported = inv.getName().split(" ")[0];
                int page = 1;

                if (it.getType() == Material.REDSTONE_BLOCK) {

                    int start = (page - 1) * reportPerPage;
                    int end = start + (reportPerPage - 1);

                    Inventory inv2 = Bukkit.createInventory(null, 54, reported + " > page " + page);

                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta headM = (SkullMeta) head.getItemMeta();
                    headM.setOwningPlayer(Bukkit.getOfflinePlayer(reported));
                    headM.setDisplayName(ChatColor.GREEN + "Reports de " + ChatColor.DARK_GREEN + reported);
                    head.setItemMeta(headM);
                    inv2.setItem(4, head);

                    ItemStack book = new ItemStack(Material.BOOK);
                    ItemMeta bookM = book.getItemMeta();
                    bookM.setDisplayName(ChatColor.LIGHT_PURPLE + String.valueOf(main.listReports(reported).size()) + " reports trouvés");
                    book.setItemMeta(bookM);
                    inv2.setItem(0, book);

                    ItemStack delete = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta deleteM = delete.getItemMeta();
                    deleteM.setDisplayName(ChatColor.RED + "Supprimer tous les reports");
                    delete.setItemMeta(deleteM);
                    inv2.setItem(8, delete);

                    ItemStack arrow = new ItemStack(Material.ARROW);
                    ItemMeta arrowM = arrow.getItemMeta();
                    arrowM.setDisplayName(ChatColor.GRAY + "Page précédente");
                    arrow.setItemMeta(arrowM);
                    inv2.setItem(45, arrow);
                    arrowM.setDisplayName(ChatColor.GRAY + "Page suivante");
                    arrow.setItemMeta(arrowM);
                    inv2.setItem(53, arrow);

                    for (int i = 9; i < 45; i++) {

                        if ((main.listReports(reported).size() - 1) >= start) {
                            ReportOBJ report2 = main.listReports(reported).get(start);
                            String reporter = report2.getReporter();
                            String[] reason = report2.getReason().split(" ");
                            StringBuilder builder = new StringBuilder();
                            if (reason.length > 5) {
                                for (int x = 0; x < 6; x++) {
                                    builder.append(reason[x] + " ");
                                }
                                builder.append("...");
                            } else {
                                for (String part : reason) {
                                    builder.append(part + " ");
                                }
                            }
                            String preview = builder.toString();
                            ItemStack item = new ItemStack(Material.PAPER);
                            ItemMeta itemM = item.getItemMeta();
                            itemM.setDisplayName(preview);
                            itemM.setLore(Arrays.asList(ChatColor.GREEN + "Reporter: " + ChatColor.DARK_GREEN + reporter, ChatColor.LIGHT_PURPLE + "Clique gauche pour voir plus", ChatColor.RED + "Clique droit pour effacer"));
                            item.setItemMeta(itemM);
                            inv2.setItem(i, item);
                        } else {
                            break;
                        }

                        if (start < end) {
                            start++;
                        }
                    }

                    p.openInventory(inv2);

                } else if (it.getType() == Material.EMERALD_BLOCK) {

                    for (ReportOBJ reportOBJ : main.listReports(reported)) {
                        main.reports.remove(reportOBJ);
                    }
                    p.closeInventory();

                }

                e.setCancelled(true);
            }

            if (inv.getName().split(" ")[1].equals(">") && inv.getName().split(" ")[2].equals("Report")) {

                int report = (Integer.parseInt(inv.getName().split(" ")[3])) - 1;
                int reportPerPage = 36;
                String reported = inv.getName().split(" ")[0];
                int page = 1;

                if (it.getType() == Material.REDSTONE_BLOCK) {

                    main.reports.remove(main.reports.get(report));
                    int start = (page - 1) * reportPerPage;
                    int end = start + (reportPerPage - 1);

                    Inventory inv2 = Bukkit.createInventory(null, 54, reported + " > page " + page);

                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta headM = (SkullMeta) head.getItemMeta();
                    headM.setOwningPlayer(Bukkit.getOfflinePlayer(reported));
                    headM.setDisplayName(ChatColor.GREEN + "Reports de " + ChatColor.DARK_GREEN + reported);
                    head.setItemMeta(headM);
                    inv2.setItem(4, head);

                    ItemStack book = new ItemStack(Material.BOOK);
                    ItemMeta bookM = book.getItemMeta();
                    bookM.setDisplayName(ChatColor.LIGHT_PURPLE + String.valueOf(main.listReports(reported).size()) + " reports trouvés");
                    book.setItemMeta(bookM);
                    inv2.setItem(0, book);

                    ItemStack delete = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta deleteM = delete.getItemMeta();
                    deleteM.setDisplayName(ChatColor.RED + "Supprimer tous les reports");
                    delete.setItemMeta(deleteM);
                    inv2.setItem(8, delete);

                    ItemStack arrow = new ItemStack(Material.ARROW);
                    ItemMeta arrowM = arrow.getItemMeta();
                    arrowM.setDisplayName(ChatColor.GRAY + "Page précédente");
                    arrow.setItemMeta(arrowM);
                    inv2.setItem(45, arrow);
                    arrowM.setDisplayName(ChatColor.GRAY + "Page suivante");
                    arrow.setItemMeta(arrowM);
                    inv2.setItem(53, arrow);

                    for (int i = 9; i < 45; i++) {

                        if ((main.listReports(reported).size() - 1) >= start) {
                            ReportOBJ report2 = main.listReports(reported).get(start);
                            String reporter = report2.getReporter();
                            String[] reason = report2.getReason().split(" ");
                            StringBuilder builder = new StringBuilder();
                            if (reason.length > 5) {
                                for (int x = 0; x < 6; x++) {
                                    builder.append(reason[x] + " ");
                                }
                                builder.append("...");
                            } else {
                                for (String part : reason) {
                                    builder.append(part + " ");
                                }
                            }
                            String preview = builder.toString();
                            ItemStack item = new ItemStack(Material.PAPER);
                            ItemMeta itemM = item.getItemMeta();
                            itemM.setDisplayName(preview);
                            itemM.setLore(Arrays.asList(ChatColor.GREEN + "Reporter: " + ChatColor.DARK_GREEN + reporter, ChatColor.LIGHT_PURPLE + "Clique gauche pour voir plus", ChatColor.RED + "Clique droit pour effacer"));
                            item.setItemMeta(itemM);
                            inv2.setItem(i, item);
                        } else {
                            break;
                        }

                        if (start < end) {
                            start++;
                        }
                    }

                    p.openInventory(inv2);

                }

                if (it.getType() == Material.ARROW) {

                    int start = (page - 1) * reportPerPage;
                    int end = start + (reportPerPage - 1);

                    Inventory inv2 = Bukkit.createInventory(null, 54, reported + " > page " + page);

                    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta headM = (SkullMeta) head.getItemMeta();
                    headM.setOwningPlayer(Bukkit.getOfflinePlayer(reported));
                    headM.setDisplayName(ChatColor.GREEN + "Reports de " + ChatColor.DARK_GREEN + reported);
                    head.setItemMeta(headM);
                    inv2.setItem(4, head);

                    ItemStack book = new ItemStack(Material.BOOK);
                    ItemMeta bookM = book.getItemMeta();
                    bookM.setDisplayName(ChatColor.LIGHT_PURPLE + String.valueOf(main.listReports(reported).size()) + " reports trouvés");
                    book.setItemMeta(bookM);
                    inv2.setItem(0, book);

                    ItemStack delete = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta deleteM = delete.getItemMeta();
                    deleteM.setDisplayName(ChatColor.RED + "Supprimer tous les reports");
                    delete.setItemMeta(deleteM);
                    inv2.setItem(8, delete);

                    ItemStack arrow = new ItemStack(Material.ARROW);
                    ItemMeta arrowM = arrow.getItemMeta();
                    arrowM.setDisplayName(ChatColor.GRAY + "Page précédente");
                    arrow.setItemMeta(arrowM);
                    inv2.setItem(45, arrow);
                    arrowM.setDisplayName(ChatColor.GRAY + "Page suivante");
                    arrow.setItemMeta(arrowM);
                    inv2.setItem(53, arrow);

                    for (int i = 9; i < 45; i++) {

                        if ((main.listReports(reported).size() - 1) >= start) {
                            ReportOBJ report2 = main.listReports(reported).get(start);
                            String reporter = report2.getReporter();
                            String[] reason = report2.getReason().split(" ");
                            StringBuilder builder = new StringBuilder();
                            if (reason.length > 5) {
                                for (int x = 0; x < 6; x++) {
                                    builder.append(reason[x] + " ");
                                }
                                builder.append("...");
                            } else {
                                for (String part : reason) {
                                    builder.append(part + " ");
                                }
                            }
                            String preview = builder.toString();
                            ItemStack item = new ItemStack(Material.PAPER);
                            ItemMeta itemM = item.getItemMeta();
                            itemM.setDisplayName(preview);
                            itemM.setLore(Arrays.asList(ChatColor.GREEN + "Reporter: " + ChatColor.DARK_GREEN + reporter, ChatColor.LIGHT_PURPLE + "Clique gauche pour voir plus", ChatColor.RED + "Clique droit pour effacer"));
                            item.setItemMeta(itemM);
                            inv2.setItem(i, item);
                        } else {
                            break;
                        }

                        if (start < end) {
                            start++;
                        }
                    }

                    p.openInventory(inv2);

                }
                e.setCancelled(true);
            }

            if (inv.getName().split(" ")[1].equals(">") && inv.getName().split(" ")[2].equals("page")) {

                int page = Integer.parseInt(inv.getName().split(" ")[3]);
                int reportPerPage = 36;
                String reported = inv.getName().split(" ")[0];

                if (it.getType() == Material.REDSTONE_BLOCK) {

                    Inventory inv2 = Bukkit.createInventory(null, 54, reported + " > Supprimer les reports");

                    ItemStack report = new ItemStack(Material.PAPER);
                    ItemMeta reportM = report.getItemMeta();
                    reportM.setDisplayName(ChatColor.BOLD + "" + ChatColor.DARK_GREEN + "Voulez vous vraiment supprimer tous les reports ?");
                    report.setItemMeta(reportM);
                    inv2.setItem(4, report);

                    ItemStack annuler = new ItemStack(Material.REDSTONE_BLOCK);
                    ItemMeta annulerM = annuler.getItemMeta();
                    annulerM.setDisplayName(ChatColor.RED + "ANNULER");
                    annuler.setItemMeta(annulerM);

                    inv2.setItem(18, annuler);
                    inv2.setItem(19, annuler);
                    inv2.setItem(20, annuler);
                    inv2.setItem(27, annuler);
                    inv2.setItem(28, annuler);
                    inv2.setItem(29, annuler);
                    inv2.setItem(36, annuler);
                    inv2.setItem(37, annuler);
                    inv2.setItem(38, annuler);

                    ItemStack confirmer = new ItemStack(Material.EMERALD_BLOCK);
                    ItemMeta confirmerM = confirmer.getItemMeta();
                    confirmerM.setDisplayName(ChatColor.GREEN + "CONFIRMER");
                    confirmer.setItemMeta(confirmerM);

                    inv2.setItem(24, confirmer);
                    inv2.setItem(25, confirmer);
                    inv2.setItem(26, confirmer);
                    inv2.setItem(33, confirmer);
                    inv2.setItem(34, confirmer);
                    inv2.setItem(35, confirmer);
                    inv2.setItem(42, confirmer);
                    inv2.setItem(43, confirmer);
                    inv2.setItem(44, confirmer);

                    p.openInventory(inv2);

                }

                if (it.getType() == Material.PAPER) {

                    ReportOBJ report = main.reports.get(slot - 9 + ((page - 1) * reportPerPage));
                    if (action == InventoryAction.PICKUP_HALF) {
                        //EFFACER

                        main.reports.remove(report);

                        int start = (page - 1) * reportPerPage;
                        int end = start + (reportPerPage - 1);

                        Inventory inv2 = Bukkit.createInventory(null, 54, reported + " > page " + page);

                        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                        SkullMeta headM = (SkullMeta) head.getItemMeta();
                        headM.setOwningPlayer(Bukkit.getOfflinePlayer(reported));
                        headM.setDisplayName(ChatColor.GREEN + "Reports de " + ChatColor.DARK_GREEN + reported);
                        head.setItemMeta(headM);
                        inv2.setItem(4, head);

                        ItemStack book = new ItemStack(Material.BOOK);
                        ItemMeta bookM = book.getItemMeta();
                        bookM.setDisplayName(ChatColor.LIGHT_PURPLE + String.valueOf(main.listReports(reported).size()) + " reports trouvés");
                        book.setItemMeta(bookM);
                        inv2.setItem(0, book);

                        ItemStack delete = new ItemStack(Material.REDSTONE_BLOCK);
                        ItemMeta deleteM = delete.getItemMeta();
                        deleteM.setDisplayName(ChatColor.RED + "Supprimer tous les reports");
                        delete.setItemMeta(deleteM);
                        inv2.setItem(8, delete);

                        ItemStack arrow = new ItemStack(Material.ARROW);
                        ItemMeta arrowM = arrow.getItemMeta();
                        arrowM.setDisplayName(ChatColor.GRAY + "Page précédente");
                        arrow.setItemMeta(arrowM);
                        inv2.setItem(45, arrow);
                        arrowM.setDisplayName(ChatColor.GRAY + "Page suivante");
                        arrow.setItemMeta(arrowM);
                        inv2.setItem(53, arrow);

                        for (int i = 9; i < 45; i++) {

                            if ((main.listReports(reported).size() - 1) >= start) {
                                ReportOBJ report2 = main.listReports(reported).get(start);
                                String reporter = report2.getReporter();
                                String[] reason = report2.getReason().split(" ");
                                StringBuilder builder = new StringBuilder();
                                if (reason.length > 5) {
                                    for (int x = 0; x < 6; x++) {
                                        builder.append(reason[x] + " ");
                                    }
                                    builder.append("...");
                                } else {
                                    for (String part : reason) {
                                        builder.append(part + " ");
                                    }
                                }
                                String preview = builder.toString();
                                ItemStack item = new ItemStack(Material.PAPER);
                                ItemMeta itemM = item.getItemMeta();
                                itemM.setDisplayName(preview);
                                itemM.setLore(Arrays.asList(ChatColor.GREEN + "Reporter: " + ChatColor.DARK_GREEN + reporter, ChatColor.LIGHT_PURPLE + "Clique gauche pour voir plus", ChatColor.RED + "Clique droit pour effacer"));
                                item.setItemMeta(itemM);
                                inv2.setItem(i, item);
                            } else {
                                break;
                            }

                            if (start < end) {
                                start++;
                            }
                        }

                        p.openInventory(inv2);


                    } else if (action == InventoryAction.PICKUP_ALL) {
                        //OUVRIR

                        int reportNB = (slot - 9 + ((page - 1) * reportPerPage)) + 1;
                        Inventory inv2 = Bukkit.createInventory(null, 9, reported + " > Report " + reportNB);

                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy : HH:mm:ss");
                        String date = format.format(report.getDate());

                        ItemStack fleche = new ItemStack(Material.ARROW);
                        ItemMeta flecheM = fleche.getItemMeta();
                        flecheM.setDisplayName(ChatColor.GRAY + "Retour");
                        fleche.setItemMeta(flecheM);
                        inv2.setItem(0, fleche);

                        ItemStack horloge = new ItemStack(Material.WATCH);
                        ItemMeta horlogeM = horloge.getItemMeta();
                        horlogeM.setDisplayName(ChatColor.WHITE + "Report fait le");
                        horlogeM.setLore(Arrays.asList(ChatColor.GRAY + date));
                        horloge.setItemMeta(horlogeM);
                        inv2.setItem(2, horloge);

                        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                        SkullMeta headM = (SkullMeta) head.getItemMeta();
                        headM.setOwningPlayer(Bukkit.getOfflinePlayer(report.getReporter()));
                        headM.setDisplayName(ChatColor.GREEN + "Reporté par " + ChatColor.DARK_GREEN + report.getReporter());
                        head.setItemMeta(headM);
                        inv2.setItem(4, head);

                        ItemStack raison = new ItemStack(Material.PAPER);
                        ItemMeta raisonM = raison.getItemMeta();
                        raisonM.setDisplayName(ChatColor.GRAY + "Raison:");
                        raisonM.setLore(Arrays.asList(ChatColor.WHITE + report.getReason()));
                        raison.setItemMeta(raisonM);
                        inv2.setItem(6, raison);

                        ItemStack delete = new ItemStack(Material.REDSTONE_BLOCK);
                        ItemMeta deleteM = delete.getItemMeta();
                        deleteM.setDisplayName(ChatColor.RED + "Supprimer ce report");
                        delete.setItemMeta(deleteM);
                        inv2.setItem(8, delete);

                        p.openInventory(inv2);

                    }

                }

                if (it.getType() == Material.ARROW) {
                    if (it.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Page suivante")) {

                        int newPage = page + 1;
                        if (main.listReports(reported).size() > (page * reportPerPage)) {

                            page = newPage;

                            int start = (page - 1) * reportPerPage;
                            int end = start + (reportPerPage - 1);

                            Inventory inv2 = Bukkit.createInventory(null, 54, reported + " > page " + page);

                            ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                            SkullMeta headM = (SkullMeta) head.getItemMeta();
                            headM.setOwningPlayer(Bukkit.getOfflinePlayer(reported));
                            headM.setDisplayName(ChatColor.GREEN + "Reports de " + ChatColor.DARK_GREEN + reported);
                            head.setItemMeta(headM);
                            inv2.setItem(4, head);

                            ItemStack book = new ItemStack(Material.BOOK);
                            ItemMeta bookM = book.getItemMeta();
                            bookM.setDisplayName(ChatColor.LIGHT_PURPLE + String.valueOf(main.listReports(reported).size()) + " reports trouvés");
                            book.setItemMeta(bookM);
                            inv2.setItem(0, book);

                            ItemStack delete = new ItemStack(Material.REDSTONE_BLOCK);
                            ItemMeta deleteM = delete.getItemMeta();
                            deleteM.setDisplayName(ChatColor.RED + "Supprimer tous les reports");
                            delete.setItemMeta(deleteM);
                            inv2.setItem(8, delete);

                            ItemStack arrow = new ItemStack(Material.ARROW);
                            ItemMeta arrowM = arrow.getItemMeta();
                            arrowM.setDisplayName(ChatColor.GRAY + "Page précédente");
                            arrow.setItemMeta(arrowM);
                            inv2.setItem(45, arrow);
                            arrowM.setDisplayName(ChatColor.GRAY + "Page suivante");
                            arrow.setItemMeta(arrowM);
                            inv2.setItem(53, arrow);

                            for (int i = 9; i < 45; i++) {

                                if ((main.listReports(reported).size() - 1) >= start) {
                                    ReportOBJ report = main.listReports(reported).get(start);
                                    String reporter = report.getReporter();
                                    String[] reason = report.getReason().split(" ");
                                    StringBuilder builder = new StringBuilder();
                                    if (reason.length > 5) {
                                        for (int x = 0; x < 6; x++) {
                                            builder.append(reason[x] + " ");
                                        }
                                        builder.append("...");
                                    } else {
                                        for (String part : reason) {
                                            builder.append(part + " ");
                                        }
                                    }
                                    String preview = builder.toString();
                                    ItemStack item = new ItemStack(Material.PAPER);
                                    ItemMeta itemM = item.getItemMeta();
                                    itemM.setDisplayName(preview);
                                    itemM.setLore(Arrays.asList(ChatColor.GREEN + "Reporter: " + ChatColor.DARK_GREEN + reporter, ChatColor.LIGHT_PURPLE + "Clique gauche pour voir plus", ChatColor.RED + "Clique droit pour effacer"));
                                    item.setItemMeta(itemM);
                                    inv2.setItem(i, item);
                                } else {
                                    break;
                                }

                                if (start < end) {
                                    start++;
                                }
                            }

                            p.openInventory(inv2);

                        }

                    } else if (it.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Page précédente")) {

                        int newPage = page - 1;
                        if (newPage > 0) {

                            page = newPage;

                            int start = (page - 1) * reportPerPage;
                            int end = start + (reportPerPage - 1);

                            Inventory inv2 = Bukkit.createInventory(null, 54, reported + " > page " + page);

                            ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                            SkullMeta headM = (SkullMeta) head.getItemMeta();
                            headM.setOwningPlayer(Bukkit.getOfflinePlayer(reported));
                            headM.setDisplayName(ChatColor.GREEN + "Reports de " + ChatColor.DARK_GREEN + reported);
                            head.setItemMeta(headM);
                            inv2.setItem(4, head);

                            ItemStack book = new ItemStack(Material.BOOK);
                            ItemMeta bookM = book.getItemMeta();
                            bookM.setDisplayName(ChatColor.LIGHT_PURPLE + String.valueOf(main.listReports(reported).size()) + " reports trouvés");
                            book.setItemMeta(bookM);
                            inv2.setItem(0, book);

                            ItemStack delete = new ItemStack(Material.REDSTONE_BLOCK);
                            ItemMeta deleteM = delete.getItemMeta();
                            deleteM.setDisplayName(ChatColor.RED + "Supprimer tous les reports");
                            delete.setItemMeta(deleteM);
                            inv2.setItem(8, delete);

                            ItemStack arrow = new ItemStack(Material.ARROW);
                            ItemMeta arrowM = arrow.getItemMeta();
                            arrowM.setDisplayName(ChatColor.GRAY + "Page précédente");
                            arrow.setItemMeta(arrowM);
                            inv2.setItem(45, arrow);
                            arrowM.setDisplayName(ChatColor.GRAY + "Page suivante");
                            arrow.setItemMeta(arrowM);
                            inv2.setItem(53, arrow);

                            for (int i = 9; i < 45; i++) {

                                if ((main.listReports(reported).size() - 1) >= start) {
                                    ReportOBJ report = main.listReports(reported).get(start);
                                    String reporter = report.getReporter();
                                    String[] reason = report.getReason().split(" ");
                                    StringBuilder builder = new StringBuilder();
                                    if (reason.length > 5) {
                                        for (int x = 0; x < 6; x++) {
                                            builder.append(reason[x] + " ");
                                        }
                                        builder.append("...");
                                    } else {
                                        for (String part : reason) {
                                            builder.append(part + " ");
                                        }
                                    }
                                    String preview = builder.toString();
                                    ItemStack item = new ItemStack(Material.PAPER);
                                    ItemMeta itemM = item.getItemMeta();
                                    itemM.setDisplayName(preview);
                                    itemM.setLore(Arrays.asList(ChatColor.GREEN + "Reporter: " + ChatColor.DARK_GREEN + reporter, ChatColor.LIGHT_PURPLE + "Clique gauche pour voir plus", ChatColor.RED + "Clique droit pour effacer"));
                                    item.setItemMeta(itemM);
                                    inv2.setItem(i, item);
                                } else {
                                    break;
                                }

                                if (start < end) {
                                    start++;
                                }
                            }

                            p.openInventory(inv2);

                        }

                    }
                }

                e.setCancelled(true);
            }
        }

    }

}
