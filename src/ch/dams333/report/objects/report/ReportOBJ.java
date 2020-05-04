package ch.dams333.report.objects.report;

import java.util.Date;
import java.util.UUID;

public class ReportOBJ {

    private UUID uuid;
    private String reported;
    private String reporter;
    private Date date;
    private String reason;

    public UUID getUuid() {
        return uuid;
    }

    public String getReported() {
        return reported;
    }

    public String getReporter() {
        return reporter;
    }

    public Date getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public ReportOBJ(String reported, String reporter, Date date, String reason) {
        this.reported = reported;
        this.reporter = reporter;
        this.date = date;
        this.reason = reason;
        this.uuid = UUID.randomUUID();
    }
}
