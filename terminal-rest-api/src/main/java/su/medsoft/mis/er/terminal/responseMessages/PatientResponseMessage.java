package su.medsoft.mis.er.terminal.responseMessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import su.medsoft.mis.er.terminal.dto.PatientDto;

public class PatientResponseMessage extends BaseResponseMessage {
    @JsonProperty("patient")
    private PatientDto patientDto;

    public PatientDto getPatientDto() {
        return patientDto;
    }

    public void setPatientDto(PatientDto patientDto) {
        this.patientDto = patientDto;
    }
}
