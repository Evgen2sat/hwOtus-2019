package su.medsoft.mis.er.terminal.external_services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import su.medsoft.mis.er.terminal.responseMessages.DocumentTypesResponseMessage;
import su.medsoft.mis.er.terminal.responseMessages.InsuranceBlankTypesResponseMessage;

public interface DictionaryServicesAPI {
    @Headers("Content-Type: application/json")
    @GET("/mis/api/dict/document_types")
    Call<DocumentTypesResponseMessage> getDocumentTypes();

    @Headers("Content-Type: application/json")
    @GET("/mis/api/dict/insurance_blank_types")
    Call<InsuranceBlankTypesResponseMessage> getInsuranceBlankTypes();
}
