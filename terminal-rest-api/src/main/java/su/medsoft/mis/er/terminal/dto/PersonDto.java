package su.medsoft.mis.er.terminal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonDto {
    @JsonProperty("insurance")
    @SerializedName("insuranceList")
    private List<InsuranceDto> insuranceList;

    @JsonProperty("document")
    @SerializedName("documentList")
    private List<DocumentDto> documentDtoList;

    @JsonProperty("phone")
    @SerializedName("phoneList")
    private List<PhoneDto> phoneDtoList;

    public List<InsuranceDto> getInsuranceList() {
        return insuranceList;
    }

    public void setInsuranceList(List<InsuranceDto> insuranceList) {
        this.insuranceList = insuranceList;
    }

    public List<DocumentDto> getDocumentDtoList() {
        return documentDtoList;
    }

    public void setDocumentDtoList(List<DocumentDto> documentDtoList) {
        this.documentDtoList = documentDtoList;
    }

    public List<PhoneDto> getPhoneDtoList() {
        return phoneDtoList;
    }

    public void setPhoneDtoList(List<PhoneDto> phoneDtoList) {
        this.phoneDtoList = phoneDtoList;
    }
}
