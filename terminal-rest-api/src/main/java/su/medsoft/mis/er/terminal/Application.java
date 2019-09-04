package su.medsoft.mis.er.terminal;

import io.micronaut.http.HttpResponse;
import io.micronaut.runtime.Micronaut;
import io.reactivex.Single;
import su.medsoft.mis.er.terminal.dto.ErrorDto;
import su.medsoft.mis.er.terminal.responseMessages.BaseResponseMessage;

public class Application {

    private static final String LOGGER_NAME = "terminal-api-logger";

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

    public static String getLoggerName() {
        return LOGGER_NAME;
    }

    public static <T extends BaseResponseMessage> HttpResponse<Single<T>> getResponse(T responseMessage) {
        if(responseMessage.getErrorDto() != null) {
            return HttpResponse.badRequest(Single.just(responseMessage));
        } else {
            return HttpResponse.ok(Single.just(responseMessage));
        }
    }

    public static ErrorDto getError(int code, String message) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(code);
        errorDto.setMessage(message);

        return errorDto;
    }
}