package su.medsoft.mis.er.terminal.mappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.medsoft.mis.er.terminal.Application;
import su.medsoft.mis.er.terminal.dto.ScheduleDto;

import java.sql.ResultSet;

public class ScheduleMapper {

    private static final Logger LOG = LoggerFactory.getLogger(Application.getLoggerName());

    public static ScheduleDto schedule(ResultSet rs) {
        ScheduleDto result;
        try {
            result = new ScheduleDto();
            result.setSchedule_day_id(rs.getLong("schedule_day_id"));
            result.setDate(rs.getDate("date").toString());
            result.setStaff_id(rs.getLong("staff_id"));
            result.setStaff_fio(rs.getString("staff_fio"));
            result.setPost_code(rs.getString("post_code"));
            result.setPost_name(rs.getString("post_name"));
            result.setSeparate_department(rs.getBoolean("is_separate"));
            result.setDepartment_id(rs.getLong("department_id"));
            result.setDepartment_name(rs.getString("department_name"));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }


        return result;
    }
}
