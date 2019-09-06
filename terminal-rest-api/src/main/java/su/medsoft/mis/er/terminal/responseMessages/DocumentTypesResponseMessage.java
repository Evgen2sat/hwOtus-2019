package su.medsoft.mis.er.terminal.responseMessages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import su.medsoft.mis.er.terminal.dto.DocumentTypeDto;

import java.util.List;

public class DocumentTypesResponseMessage extends BaseResponseMessage {
    @JsonProperty("types")
    @SerializedName("types")
    private List<DocumentTypeDto> documentTypeDtoList;

    public List<DocumentTypeDto> getDocumentTypeDtoList() {
        return documentTypeDtoList;
    }

    public void setDocumentTypeDtoList(List<DocumentTypeDto> documentTypeDtoList) {
        this.documentTypeDtoList = documentTypeDtoList;
    }
}
