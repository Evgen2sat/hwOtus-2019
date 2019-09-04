package su.medsoft.mis.er.terminal.responseMessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import su.medsoft.mis.er.terminal.dto.ErrorDto;

public abstract class BaseResponseMessage {
    @JsonProperty("error")
    @SerializedName("error")
    private ErrorDto errorDto;

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
}
