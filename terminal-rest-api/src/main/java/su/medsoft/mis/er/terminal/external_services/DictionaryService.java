package su.medsoft.mis.er.terminal.external_services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.micronaut.context.annotation.Value;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import su.medsoft.mis.er.terminal.responseMessages.DocumentTypesResponseMessage;
import su.medsoft.mis.er.terminal.responseMessages.InsuranceBlankTypesResponseMessage;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Singleton
public class DictionaryService {
    private final DictionaryServicesAPI api;

    public DictionaryService(@Value("${external-services.dictionary.url}") String baseUrl) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        api = retrofit.create(DictionaryServicesAPI.class);
    }

    public InsuranceBlankTypesResponseMessage getInsuranceBlankTypes() throws IOException {
        return api.getInsuranceBlankTypes().execute().body();
    }

    public DocumentTypesResponseMessage getDocumentTypes() throws IOException {
        return api.getDocumentTypes().execute().body();
    }
}
