package su.medsoft.mis.er.terminal.responseMessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import su.medsoft.mis.er.terminal.dto.InsuranceBlankTypeDto;

import java.util.List;

public class InsuranceBlankTypesResponseMessage extends BaseResponseMessage {
    @JsonProperty("types")
    @SerializedName("types")
    private List<InsuranceBlankTypeDto> insuranceBlankTypeDtoList;

    public List<InsuranceBlankTypeDto> getInsuranceBlankTypeDtoList() {
        return insuranceBlankTypeDtoList;
    }

    public void setInsuranceBlankTypeDtoList(List<InsuranceBlankTypeDto> insuranceBlankTypeDtoList) {
        this.insuranceBlankTypeDtoList = insuranceBlankTypeDtoList;
    }
}
