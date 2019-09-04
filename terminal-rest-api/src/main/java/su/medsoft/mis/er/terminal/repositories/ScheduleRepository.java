package su.medsoft.mis.er.terminal.repositories;

import su.medsoft.mis.er.terminal.database.QueryParam;
import su.medsoft.mis.er.terminal.dto.ScheduleDto;
import su.medsoft.mis.er.terminal.mappers.ScheduleMapper;
import su.medsoft.mis.er.terminal.services.DatabaseService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class ScheduleRepository {

    @Inject
    DatabaseService databaseService;

    public List<ScheduleDto> getSchedule(Long departmentId, long moId, String source) throws Exception {
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT DISTINCT er_schedule_day.id AS schedule_day_id, er_schedule_day.date, er_schedule_day.staff_id,\n" +
                        "       person.last_name || ' ' || person.first_name || CASE WHEN person.middle_name IS NULL THEN '' ELSE ' ' || person.middle_name END AS staff_fio,\n" +
                        "       dict_post.code AS post_code, dict_post.short_name AS post_name,\n" +
                        "       department.is_separate, department.id AS department_id, department.name AS department_name\n" +
                        "FROM er_schedule_day\n" +
                        "    INNER JOIN staff ON staff.id = er_schedule_day.staff_id\n" +
                        "    INNER JOIN person ON person.id = staff.person_id\n" +
                        "    INNER JOIN dict_post ON dict_post.id = staff.post_id\n" +
                        "    INNER JOIN department ON department.id = staff.department_id\n");

        if(source != null) {
            queryBuilder.append("INNER JOIN er_schedule_interval ON er_schedule_interval.schedule_day_id = er_schedule_day.id\n")
                    .append("INNER JOIN er_record_source_by_schedule_interval ON er_record_source_by_schedule_interval.er_schedule_interval_id = er_schedule_interval.id\n")
                    .append("INNER JOIN dict_er_record_source ON dict_er_record_source.id = er_record_source_by_schedule_interval.record_source_id\n");
        }

        queryBuilder.append(
                "WHERE er_schedule_day.date::date BETWEEN NOW()::date AND (NOW() + INTERVAL '9' DAY)::date\n" +
                        "    AND EXTRACT(DOW FROM er_schedule_day.date) <> 0\n" +
                        "    AND EXTRACT(DOW FROM er_schedule_day.date) <> 6\n" +
                        "    AND er_schedule_day.deleted = FALSE\n" +
                        "    AND er_schedule_day.activate_date <= NOW()\n" +
                        "    AND staff.deleted = FALSE\n" +
                        "    AND person.deleted = FALSE\n" +
                        "    AND department.deleted = FALSE\n" +
                        "    AND department.organization_id = ?\n");

        if(departmentId != null) {
            queryBuilder.append(String.format(" AND (department.id = %d OR department.separate_department_id = %d)\n", departmentId, departmentId));
        }

        if(source != null) {
            queryBuilder.append("AND er_schedule_interval.deleted = FALSE\n")
                    .append(String.format("AND dict_er_record_source.code = '%s'\n", source));
        }

        queryBuilder.append("ORDER BY er_schedule_day.date ASC\n");

        List<ScheduleDto> scheduleDtoList = databaseService.executeSelectQuery(queryBuilder.toString(),
                ScheduleMapper::schedule,
                QueryParam.getLong(moId));

        return scheduleDtoList;
    }
}
