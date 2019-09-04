package su.medsoft.mis.er.terminal.services;

import su.medsoft.mis.er.terminal.dto.AggregateIntervalDto;
import su.medsoft.mis.er.terminal.repositories.IntervalRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class IntervalService {

    @Inject
    private IntervalRepository intervalRepository;

    public List<AggregateIntervalDto> getIntervals(List<Long> schedule_day_ids, Boolean aggregate, String source) throws Exception {
        return intervalRepository.getIntervals(schedule_day_ids, aggregate, source);
    }
}
