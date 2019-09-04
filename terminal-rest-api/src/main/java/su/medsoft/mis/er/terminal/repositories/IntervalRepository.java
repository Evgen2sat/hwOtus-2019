package su.medsoft.mis.er.terminal.repositories;

import su.medsoft.mis.er.terminal.dto.AggregateIntervalDto;
import su.medsoft.mis.er.terminal.mappers.IntervalMapper;
import su.medsoft.mis.er.terminal.services.DatabaseService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class IntervalRepository {

    @Inject
    private DatabaseService databaseService;

    public List<AggregateIntervalDto> getIntervals(List<Long> schedule_day_ids, Boolean aggregate, String source) throws Exception {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT er_schedule_interval.id, er_schedule_interval.schedule_day_id,\n" +
                        "       er_schedule_interval.begin_date::TIME || ' - ' || (er_schedule_interval.end_date + INTERVAL '1' SECOND)::TIME AS time_period,\n" +
                        "       dict_er_schedule_type.code AS type_code, dict_er_schedule_type.name AS type_name, CASE WHEN COUNT(er_slot.id) = COUNT(er_appointment.id) THEN 'busy' ELSE 'free' END AS status,\n" +
                        "       er_schedule_day.room AS cabinet\n" +
                        "FROM er_schedule_interval\n" +
                        "    INNER JOIN er_slot ON er_slot.schedule_interval_id = er_schedule_interval.id\n" +
                        "    INNER JOIN dict_er_schedule_type ON dict_er_schedule_type.id = er_schedule_interval.schedule_type_id\n" +
                        "    INNER JOIN er_schedule_day ON er_schedule_day.id = er_schedule_interval.schedule_day_id\n" +
                        "    LEFT JOIN er_appointment ON er_appointment.slot_id = er_slot.id AND er_appointment.deleted = FALSE\n");

        if(source != null) {
            queryBuilder.append("INNER JOIN er_record_source_by_schedule_interval ON er_record_source_by_schedule_interval.er_schedule_interval_id = er_schedule_interval.id\n")
                    .append("INNER JOIN dict_er_record_source ON dict_er_record_source.id = er_record_source_by_schedule_interval.record_source_id\n");
        }

        queryBuilder.append(
                        String.format("WHERE er_schedule_interval.schedule_day_id IN (%s)\n", String.join(",", schedule_day_ids.stream().map(r -> r.toString()).collect(Collectors.toList()))) +
                        "    AND er_schedule_interval.deleted = FALSE\n" +
                        "    AND er_slot.deleted = FALSE\n");

        if(source != null) {
            queryBuilder.append(String.format("AND dict_er_record_source.code = '%s'\n", source));
        }

        queryBuilder.append(
                        "GROUP BY er_schedule_interval.id, er_schedule_interval.schedule_day_id,\n" +
                        "       er_schedule_interval.begin_date, er_schedule_interval.end_date + INTERVAL '1' SECOND,\n" +
                        "       dict_er_schedule_type.code, dict_er_schedule_type.name, er_schedule_day.room");

        if(aggregate != null && aggregate) {
            return databaseService.executeSelectQueryWithAllItemsWithoutParams(queryBuilder.toString(), IntervalMapper::aggregateInterval);
        } else {
            return databaseService.executeSelectQueryWithAllItemsWithoutParams(queryBuilder.toString(), IntervalMapper::interval);
        }
    }
}
