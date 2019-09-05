package su.medsoft.mis.er.terminal.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import io.reactivex.Single;
import su.medsoft.mis.er.terminal.responseMessages.PatientResponseMessage;
import su.medsoft.mis.er.terminal.services.PatientService;

import javax.inject.Inject;

@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
@Controller("${endpoints.all.path}/patients")
public class PatientController {
    @Inject
    private PatientService patientService;

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Single<PatientResponseMessage>> getPatientByInsuranceAndBirthDate(@QueryValue("npolis") String npolis, @QueryValue("birthDate") String birthDate) {
        return patientService.getPatientByInsuranceAndBirthDate(npolis, birthDate);
    }
}
