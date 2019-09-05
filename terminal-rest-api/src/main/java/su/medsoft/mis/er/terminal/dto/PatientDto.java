package su.medsoft.mis.er.terminal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class PatientDto {
    @JsonProperty("id")
    @SerializedName("id")
    private long id;

    @JsonProperty("last_name")
    @SerializedName("lastName")
    private String last_name;

    @JsonProperty("first_name")
    @SerializedName("firstName")
    private String first_name;

    @JsonProperty("middle_name")
    @SerializedName("middleName")
    private String middle_name;

    @JsonProperty("birth_date")
    @SerializedName("birthdate")
    private String birth_date;

    @JsonProperty("gender")
    @SerializedName("gender")
    private String gender;

    @JsonProperty("snils")
    @SerializedName("snils")
    private String snils;

    @JsonProperty("enp")
    @SerializedName("enp")
    private String enp;

    @JsonProperty("not_confirmed")
    @SerializedName("notConfirmed")
    private boolean not_confirmed;

    @JsonProperty("comment")
    @SerializedName("comment")
    private String comment;

    @JsonProperty("person_info")
    @SerializedName("personInfo")
    private PersonDto personDto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
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

    public boolean isNot_confirmed() {
        return not_confirmed;
    }

    public void setNot_confirmed(boolean not_confirmed) {
        this.not_confirmed = not_confirmed;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PersonDto getPersonDto() {
        return personDto;
    }

    public void setPersonDto(PersonDto personDto) {
        this.personDto = personDto;
    }
}
