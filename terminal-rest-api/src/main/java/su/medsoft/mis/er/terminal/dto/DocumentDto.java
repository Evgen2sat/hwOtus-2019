package su.medsoft.mis.er.terminal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class DocumentDto {
    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("type")
    @SerializedName("type")
    private DocumentTypeDto type;

    @JsonProperty("serie")
    @SerializedName("seria")
    private String seria;

    @JsonProperty("number")
    @SerializedName("number")
    private String number;

    @JsonProperty("issueDate")
    @SerializedName("issueDate")
    private String issueDate;

    @JsonProperty("issuer")
    @SerializedName("issuer")
    private String issuer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentTypeDto getType() {
        return type;
    }

    public void setType(DocumentTypeDto type) {
        this.type = type;
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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
