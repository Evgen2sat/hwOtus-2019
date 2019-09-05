package su.medsoft.mis.er.terminal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class MpiPatientDto {
    @JsonProperty("lastName")
    @SerializedName("lastName")
    private String lastName;

    @JsonProperty("firstName")
    @SerializedName("firstName")
    private String firstName;

    @JsonProperty("middleName")
    @SerializedName("middleName")
    private String middleName;

    @JsonProperty("birthdate")
    @SerializedName("birthdate")
    private String birthdate;

    @JsonProperty("gender")
    @SerializedName("gender")
    private String gender;

    @JsonProperty("snils")
    @SerializedName("snils")
    private String snils;

    @JsonProperty("enp")
    @SerializedName("enp")
    private String enp;

    @JsonProperty("mpiId")
    @SerializedName("mpiId")
    private Long mpiId;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSnils() {
        return snils;
    }

    public void setSnils(String snils) {
        this.snils = snils;
    }

    public String getEnp() {
        return enp;
    }

    public void setEnp(String enp) {
        this.enp = enp;
    }

    public Long getMpiId() {
        return mpiId;
    }

    public void setMpiId(Long mpiId) {
        this.mpiId = mpiId;
    }

    public static MpiPatientDto convertFromPatientDto (PatientDto patientDto) {
        MpiPatientDto mpiPatientDto = new MpiPatientDto();
        mpiPatientDto.setBirthdate(patientDto.getBirth_date());
        mpiPatientDto.setFirstName(patientDto.getFirst_name());
        mpiPatientDto.setGender(patientDto.getGender());
        mpiPatientDto.setLastName(patientDto.getLast_name());
        mpiPatientDto.setMiddleName(patientDto.getMiddle_name());
        mpiPatientDto.setSnils(patientDto.getSnils());

        return mpiPatientDto;
    }
}
