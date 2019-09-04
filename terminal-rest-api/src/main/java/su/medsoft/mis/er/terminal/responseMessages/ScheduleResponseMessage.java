package su.medsoft.mis.er.terminal.responseMessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import su.medsoft.mis.er.terminal.dto.ScheduleDto;

import java.util.List;

public class ScheduleResponseMessage extends BaseResponseMessage {
    @JsonProperty("schedules")
    private List<ScheduleDto> scheduleDtoList;

    public List<ScheduleDto> getScheduleDtoList() {
        return scheduleDtoList;
    }

    public void setScheduleDtoList(List<ScheduleDto> scheduleDtoList) {
        this.scheduleDtoList = scheduleDtoList;
    }
}
