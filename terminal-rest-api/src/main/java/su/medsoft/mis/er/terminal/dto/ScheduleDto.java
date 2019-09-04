package su.medsoft.mis.er.terminal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Информация о расписании
 */
public class ScheduleDto {
    @JsonProperty("schedule_day_id")
    private long schedule_day_id;

    @JsonProperty("date")
    private String date;

    @JsonProperty("staff_id")
    private long staff_id;

    @JsonProperty("staff_fio")
    private String staff_fio;

    @JsonProperty("post_code")
    private String post_code;

    @JsonProperty("post_name")
    private String post_name;

    @JsonProperty("separate_department")
    private boolean separate_department;

    @JsonProperty("department_id")
    private long department_id;

    @JsonProperty("department_name")
    private String department_name;

    @JsonProperty("intervals")
    private List<AggregateIntervalDto> aggregateIntervalDtoList;

    public long getSchedule_day_id() {
        return schedule_day_id;
    }

    public void setSchedule_day_id(long schedule_day_id) {
        this.schedule_day_id = schedule_day_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(long staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_fio() {
        return staff_fio;
    }

    public void setStaff_fio(String staff_fio) {
        this.staff_fio = staff_fio;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getPost_name() {
        return post_name;
    }

    public void setPost_name(String post_name) {
        this.post_name = post_name;
    }

    public long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(long department_id) {
        this.department_id = department_id;
    }

    public boolean isSeparate_department() {
        return separate_department;
    }

    public void setSeparate_department(boolean separate_department) {
        this.separate_department = separate_department;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public List<AggregateIntervalDto> getAggregateIntervalDtoList() {
        return aggregateIntervalDtoList;
    }

    public void setAggregateIntervalDtoList(List<AggregateIntervalDto> aggregateIntervalDtoList) {
        this.aggregateIntervalDtoList = aggregateIntervalDtoList;
    }
}

