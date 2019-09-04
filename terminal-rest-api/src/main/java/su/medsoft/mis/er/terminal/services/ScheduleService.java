package su.medsoft.mis.er.terminal.services;

import io.micronaut.http.HttpResponse;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.medsoft.mis.er.terminal.Application;
import su.medsoft.mis.er.terminal.dto.AggregateIntervalDto;
import su.medsoft.mis.er.terminal.dto.ScheduleDto;
import su.medsoft.mis.er.terminal.repositories.ScheduleRepository;
import su.medsoft.mis.er.terminal.responseMessages.ScheduleResponseMessage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ScheduleService {
    private static final Logger LOG = LoggerFactory.getLogger(Application.getLoggerName());

    @Inject
    private ScheduleRepository scheduleRepository;

    @Inject
    private IntervalService intervalService;

    public HttpResponse<Single<ScheduleResponseMessage>> getSchedule(Long departmentId, Boolean aggregate, long moId, String source) {

        ScheduleResponseMessage responseMessage = new ScheduleResponseMessage();

        List<ScheduleDto> scheduleDtoList = null;
        try {
            scheduleDtoList = scheduleRepository.getSchedule(departmentId, moId, source);

            if(!scheduleDtoList.isEmpty()) {
                List<Long> schedule_day_ids = scheduleDtoList.stream().map(ScheduleDto::getSchedule_day_id).collect(Collectors.toList());

                List<AggregateIntervalDto> aggregateIntervalDtoList = intervalService.getIntervals(schedule_day_ids, aggregate, source);

                for(AggregateIntervalDto aggregateIntervalDto : aggregateIntervalDtoList) {
                    ScheduleDto scheduleDto = scheduleDtoList.stream().filter(r -> r.getSchedule_day_id() == aggregateIntervalDto.getSchedule_day()).findAny().get();

                    if(scheduleDto.getAggregateIntervalDtoList() == null) {
                        scheduleDto.setAggregateIntervalDtoList(new ArrayList<>());
                    }
                    scheduleDto.getAggregateIntervalDtoList().add(aggregateIntervalDto);
                }
            }


            responseMessage.setScheduleDtoList(scheduleDtoList);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            responseMessage.setErrorDto(Application.getError(1, "Внутренняя ошибка сервера"));
            return Application.getResponse(responseMessage);
        }

        return Application.getResponse(responseMessage);
    }
}
