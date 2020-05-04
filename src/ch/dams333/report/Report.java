package ch.dams333.report;

import ch.dams333.report.commands.ListCommand;
import ch.dams333.report.commands.ReportCommand;
import ch.dams333.report.listener.Listener;
import ch.dams333.report.objects.report.ReportOBJ;
import ch.dams333.report.objects.report.ReportOBJSerialization;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Report extends JavaPlugin {

    public List<ReportOBJ> reports = new ArrayList<>();
    ReportOBJSerialization reportOBJSerialization;

    @EventHandler
    public void onEnable(){

        reportOBJSerialization = new ReportOBJSerialization(this);
        reportOBJSerialization.deSerialize();
        getCommand("report").setExecutor(new ReportCommand(this));
        getCommand("list").setExecutor(new ListCommand(this));
        getServer().getPluginManager().registerEvents(new Listener(this), this);

    }

    @EventHandler
    public void onDisable(){
        reportOBJSerialization.serialize();
    }

    public List<ReportOBJ> listReports(String p){
        List<ReportOBJ> reportsReturn = new ArrayList<>();
        for(ReportOBJ report : reports){
            if(report.getReported().equals(p)){
                reportsReturn.add(report);
            }
        }
        return reportsReturn;
    }

}
