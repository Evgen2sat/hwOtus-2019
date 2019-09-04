package su.medsoft.mis.er.terminal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Информация об интервале приема
 */
public class IntervalDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("time_period")
    private String time_period;

    @JsonProperty("cabinet")
    private String cabinet;

    @JsonProperty("status")
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTime_period() {
        return time_period;
    }

    public void setTime_period(String time_period) {
        this.time_period = time_period;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
