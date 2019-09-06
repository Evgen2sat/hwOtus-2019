package su.medsoft.mis.er.terminal.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import io.reactivex.Single;
import su.medsoft.mis.er.terminal.responseMessages.DocumentTypesResponseMessage;
import su.medsoft.mis.er.terminal.responseMessages.InsuranceBlankTypesResponseMessage;
import su.medsoft.mis.er.terminal.services.DictionaryService;

import javax.inject.Inject;

@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
@Controller("${endpoints.all.path}/dictionary")
public class DictionaryController {
    @Inject
    private DictionaryService dictionaryService;

    @Get("/document_types")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Single<DocumentTypesResponseMessage>> getDocumentTypes() {
        return dictionaryService.getDocumentTypes();
    }

    @Get("/insurance_blank_types")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Single<InsuranceBlankTypesResponseMessage>> getInsuranceBlankTypes() {
        return dictionaryService.getInsuranceBlankTypes();
    }
}
