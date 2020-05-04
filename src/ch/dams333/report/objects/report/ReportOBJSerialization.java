package ch.dams333.report.objects.report;

import ch.dams333.report.Report;
import org.bukkit.configuration.ConfigurationSection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportOBJSerialization {
    Report main;
    public ReportOBJSerialization(Report re) {
        this.main = re;
        format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    }

    private SimpleDateFormat format;

    public void serialize(){

        for (String s : main.getConfig().getKeys(false)){
            main.getConfig().set(s, null);
        }

        for(ReportOBJ report : main.reports){

            ConfigurationSection sec = main.getConfig().createSection(report.getUuid().toString());
            sec.set("reporter", report.getReporter());
            sec.set("reported", report.getReported());
            sec.set("date", format.format(report.getDate()));
            sec.set("reason", report.getReason());

        }

        main.saveConfig();

    }

    public void deSerialize(){
        for(String key : main.getConfig().getKeys(false)){
            ConfigurationSection sec = main.getConfig().getConfigurationSection(key);
            String reporter = sec.getString("reporter");
            String reported = sec.getString("reported");
            Date date = null;
            try {
                date = format.parse(sec.getString("date"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String reason = sec.getString("reason");
            ReportOBJ report = new ReportOBJ(reported, reporter, date, reason);
            main.reports.add(report);
        }
    }

}
