package su.medsoft.mis.er.terminal.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.medsoft.mis.er.terminal.Application;
import su.medsoft.mis.er.terminal.dto.AggregateIntervalDto;
import su.medsoft.mis.er.terminal.dto.IntervalDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntervalMapper {
    private static final Logger LOG = LoggerFactory.getLogger(Application.getLoggerName());

    public static List<AggregateIntervalDto> aggregateInterval(ResultSet rs) {
        List<AggregateIntervalDto> result = new ArrayList<>();
        Map<Long, Map<String, AggregateIntervalDto>> mapByDayAndScheduleType = new HashMap<>();
        try {
            while (rs.next()) {
                String intervalTypeCode = rs.getString("type_code");
                long scheduleDayId = rs.getLong("schedule_day_id");

                IntervalDto item = getIntervalDto(rs);
                AggregateIntervalDto aggregateIntervalDto = getAggregateIntervalDto(rs, item);

                if(mapByDayAndScheduleType.containsKey(scheduleDayId)) {
                    Map<String, AggregateIntervalDto> mapByScheduleType = mapByDayAndScheduleType.get(scheduleDayId);
                    if(mapByScheduleType.containsKey(intervalTypeCode)) {
                        mapByScheduleType.get(intervalTypeCode).getIntervalDtoList().add(item);
                    } else {
                        mapByScheduleType.put(intervalTypeCode, aggregateIntervalDto);

                        mapByDayAndScheduleType.put(scheduleDayId, mapByScheduleType);
                    }
                } else {
                    Map<String, AggregateIntervalDto> mapByScheduleType = new HashMap<>();
                    mapByScheduleType.put(intervalTypeCode, aggregateIntervalDto);

                    mapByDayAndScheduleType.put(scheduleDayId, mapByScheduleType);
                }
            }

            for (Map.Entry<Long, Map<String, AggregateIntervalDto>> entries : mapByDayAndScheduleType.entrySet()) {
                for(Map.Entry<String, AggregateIntervalDto> item : entries.getValue().entrySet()) {
                    result.add(item.getValue());
                }
            }

            return result;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<AggregateIntervalDto> interval(ResultSet rs) {
        List<AggregateIntervalDto> result = new ArrayList<>();
        Map<Long, AggregateIntervalDto> mapIntervalsByDay = new HashMap<>();
        try {
            while (rs.next()) {
                IntervalDto intervalDto = getIntervalDto(rs);
                long scheduleDayId = rs.getLong("schedule_day_id");

                if(mapIntervalsByDay.containsKey(scheduleDayId)) {
                    mapIntervalsByDay.get(scheduleDayId).getIntervalDtoList().add(intervalDto);
                } else {
                    AggregateIntervalDto aggregateIntervalDto = new AggregateIntervalDto();
                    aggregateIntervalDto.setSchedule_day(scheduleDayId);
                    aggregateIntervalDto.setIntervalDtoList(new ArrayList<IntervalDto>() { {add(intervalDto);} });
                    mapIntervalsByDay.put(scheduleDayId, aggregateIntervalDto);
                }
            }

            for (Map.Entry<Long, AggregateIntervalDto> entry : mapIntervalsByDay.entrySet()) {
                result.add(entry.getValue());
            }

            return result;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    private static IntervalDto getIntervalDto(ResultSet rs) throws SQLException {
        IntervalDto item = new IntervalDto();
        item.setId(rs.getLong("id"));
        item.setTime_period(rs.getString("time_period"));
        item.setStatus(rs.getString("status"));
        item.setCabinet(rs.getString("cabinet"));
        return item;
    }

    private static AggregateIntervalDto getAggregateIntervalDto(ResultSet rs, IntervalDto item) throws SQLException {
        AggregateIntervalDto aggregateIntervalDto = new AggregateIntervalDto();
        aggregateIntervalDto.setSchedule_day(rs.getLong("schedule_day_id"));
        aggregateIntervalDto.setSchedule_type(rs.getString("type_code"));
        aggregateIntervalDto.setSchedule_name(rs.getString("type_name"));

        aggregateIntervalDto.setIntervalDtoList(new ArrayList<IntervalDto>() { {add(item);} });
        return aggregateIntervalDto;
    }
}
