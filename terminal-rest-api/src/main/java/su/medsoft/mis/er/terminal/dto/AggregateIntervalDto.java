package su.medsoft.mis.er.terminal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AggregateIntervalDto {
    @JsonProperty("schedule_name")
    private String schedule_name;

    @JsonProperty("schedule_type")
    private String schedule_type;

    @JsonProperty("schedule_day")
    private long schedule_day;

    @JsonProperty("interval")
    private List<IntervalDto> intervalDtoList;

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public String getSchedule_type() {
        return schedule_type;
    }

    public void setSchedule_type(String schedule_type) {
        this.schedule_type = schedule_type;
    }

    public List<IntervalDto> getIntervalDtoList() {
        return intervalDtoList;
    }

    public void setIntervalDtoList(List<IntervalDto> intervalDtoList) {
        this.intervalDtoList = intervalDtoList;
    }

    public long getSchedule_day() {
        return schedule_day;
    }

    public void setSchedule_day(long schedule_day) {
        this.schedule_day = schedule_day;
    }
}
