package su.medsoft.mis.er.terminal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class InsuranceDto {
    @JsonProperty("serie")
    @SerializedName("seria")
    private String seria;

    @JsonProperty("number")
    @SerializedName("number")
    private String number;

    @JsonProperty("type")
    @SerializedName("type")
    private InsuranceTypeDto type;

    public InsuranceDto() { }

    public InsuranceDto(String seria, String number) {
        this.seria = seria;
        this.number = number;
    }

    public String getSeria() {
        return seria;
    }

    public void setSeria(String seria) {
        this.seria = seria;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public InsuranceTypeDto getType() {
        return type;
    }

    public void setType(InsuranceTypeDto type) {
        this.type = type;
    }
}
