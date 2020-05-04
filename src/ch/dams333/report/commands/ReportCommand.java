package ch.dams333.report.commands;

import ch.dams333.report.Report;
import ch.dams333.report.objects.report.ReportOBJ;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;

public class ReportCommand implements CommandExecutor {
    Report main;
    public ReportCommand(Report re) {
        this.main = re;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(args.length >= 2) {
            String pseudo = args[0];
            if(Bukkit.getPlayer(pseudo) != null){
                Player reported = Bukkit.getPlayer(pseudo);
                if(sender instanceof Player){
                    Player reporter = (Player) sender;
                    StringBuilder builder = new StringBuilder();
                    for(String arg : args){
                        if(!arg.equals(args[0])){
                            builder.append(arg + " ");
                        }
                    }
                    String reason = builder.toString();
                    Date date = new Date();
                    for(ReportOBJ reports : main.listReports(reported.getName())){
                        if(reports.getReporter().equals(reporter.getName())){
                            Date rDate = reports.getDate();
                            long diff = date.getTime() - rDate.getTime();
                            if(diff < 86400000){
                                sender.sendMessage(ChatColor.RED + "vous avez déjà report ce joueur il y a moins de 24 heures");
                                return true;
                            }
                        }
                    }
                    ReportOBJ report = new ReportOBJ(reported.getName(), reporter.getName(), date, reason);
                    main.reports.add(report);
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Vous avez bien report " + reported.getName());
                    for(Player p : Bukkit.getOnlinePlayers()){
                        if(p.hasPermission("report.modo")){
                            p.sendMessage(ChatColor.LIGHT_PURPLE + reporter.getName() + " a report " + reported.getName());
                        }
                    }
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "vous n'etes meme pas connecté sur le serveur");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "Vous n'allez pas accuser un joueur qui n'est pas connecté");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "/report [joueur] [raison]");
        return false;
    }
}
