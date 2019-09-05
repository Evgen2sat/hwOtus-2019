package su.medsoft.mis.er.terminal.external_services;

import retrofit2.Call;
import retrofit2.http.*;
import su.medsoft.mis.er.terminal.dto.InsuranceDto;
import su.medsoft.mis.er.terminal.dto.MpiPatientDto;
import su.medsoft.mis.er.terminal.dto.PatientDto;

import java.util.List;

public interface PatientServicesAPI {
    @Headers("Content-Type: application/json")
    @POST("/mis/api/patient_search/search/info")
    Call<PatientDto> getPatientInfo(@Body MpiPatientDto mpiPatientDto);

    @Headers("Content-Type: application/json")
    @POST("/mis/api/patient_search/search/insurance")
    Call<List<MpiPatientDto>> getPatientByInsuranceNumber(@Body InsuranceDto insuranceDto, @Query("inMPI") boolean inMPI);

    @Headers("Content-Type: application/json")
    @GET("/mis/api/patient_search/search/enp")
    Call<List<MpiPatientDto>> getPatientByENP(@Query("enp") String enp, @Query("inMPI") boolean inMPI);
}
