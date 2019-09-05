package su.medsoft.mis.er.terminal.services;

import io.micronaut.http.HttpResponse;
import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.medsoft.mis.er.terminal.Application;
import su.medsoft.mis.er.terminal.dto.InsuranceDto;
import su.medsoft.mis.er.terminal.dto.MpiPatientDto;
import su.medsoft.mis.er.terminal.dto.PatientDto;
import su.medsoft.mis.er.terminal.responseMessages.PatientResponseMessage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class PatientService {
    private static final Logger LOG = LoggerFactory.getLogger(Application.getLoggerName());

    @Inject
    private su.medsoft.mis.er.terminal.external_services.PatientService patientService;

    public HttpResponse<Single<PatientResponseMessage>> getPatientByInsuranceAndBirthDate(String npolis, String birthDate) {
        PatientResponseMessage responseMessage = new PatientResponseMessage();
        InsuranceDto insuranceDto = new InsuranceDto(null, npolis);

        try {
            List<MpiPatientDto> mpiPatientDtos = patientService.getPatientByENP(npolis);

            if(mpiPatientDtos.isEmpty()) {
                mpiPatientDtos = patientService.getPatientByInsuranceNumber(insuranceDto);
            }

            if(mpiPatientDtos.isEmpty()) {
                responseMessage.setErrorDto(Application.getError(2, "Пациент не найден"));
                return Application.getResponse(responseMessage);
            }

            List<MpiPatientDto> mpiPatientDtosByBirthDate = mpiPatientDtos.stream().filter(r -> r.getBirthdate().equals(birthDate)).collect(Collectors.toList());

            if(mpiPatientDtosByBirthDate.size() > 1) {
                responseMessage.setErrorDto(Application.getError(3, "Найдено несколько пациентов"));
                return Application.getResponse(responseMessage);
            } else if(mpiPatientDtosByBirthDate.isEmpty()) {
                responseMessage.setErrorDto(Application.getError(2, "Пациент не найден"));
                return Application.getResponse(responseMessage);
            }

            PatientDto patientDto = patientService.getPatientInfo(mpiPatientDtosByBirthDate.get(0));
            responseMessage.setPatientDto(patientDto);

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            responseMessage.setErrorDto(Application.getError(1, "Внутренняя ошибка сервера"));
        }

        return Application.getResponse(responseMessage);
    }
}
