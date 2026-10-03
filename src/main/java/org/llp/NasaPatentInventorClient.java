package org.llp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.HtmlUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Finds inventor names for a NASA patent search:
 * NASA Technology Transfer search -> NASA case ids -> US patent numbers (NASA case page) -> inventors (Google Patents).
 */
class NasaPatentInventorClient {

    private static final Logger Log = LoggerFactory.getLogger(NasaPatentInventorClient.class);

    static final String NASA_SEARCH_URL = "https://technology.nasa.gov/api/api/patent/{search}";
    static final String NASA_CASE_URL = "https://technology.nasa.gov/patent/{caseId}";
    static final String GOOGLE_PATENT_URL = "https://patents.google.com/patent/US{patentNumber}/en";

    private static final int CASE_ID_COLUMN = 1;
    private static final Pattern PATENT_NUMBER =
        Pattern.compile("<span class=\"patent_number\">\\s*<a [^>]*>\\s*([0-9,]+)\\s*</a>");
    private static final Pattern INVENTOR =
        Pattern.compile("<meta name=\"DC\\.contributor\" content=\"([^\"]*)\" scheme=\"inventor\"");

    private final RestClient restClient;

    NasaPatentInventorClient() {
        this(RestClient.builder().requestFactory(timeoutRequestFactory()));
    }

    NasaPatentInventorClient(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    List<String> findInventorNames(String search, int limit) {
        Set<String> names = new LinkedHashSet<>();
        for (String caseId : findCaseIds(search, limit)) {
            for (String patentNumber : findPatentNumbers(caseId)) {
                names.addAll(findInventors(patentNumber));
            }
        }
        return new ArrayList<>(names);
    }

    List<String> findCaseIds(String search, int limit) {
        TechTransferResponse response = restClient.get()
            .uri(NASA_SEARCH_URL, search)
            .retrieve()
            .body(TechTransferResponse.class);
        List<String> caseIds = parseCaseIds(response, limit);
        Log.info("NASA search [search={}, limit={}] returned [caseIds={}]", search, limit, caseIds);
        return caseIds;
    }

    List<String> findPatentNumbers(String caseId) {
        List<String> patentNumbers = parsePatentNumbers(fetchHtml(NASA_CASE_URL, caseId));
        Log.info("NASA case [caseId={}] has [patentNumbers={}]", caseId, patentNumbers);
        return patentNumbers;
    }

    List<String> findInventors(String patentNumber) {
        List<String> inventors = parseInventors(fetchHtml(GOOGLE_PATENT_URL, patentNumber));
        Log.info("Patent [patentNumber={}] has [inventors={}]", patentNumber, inventors);
        return inventors;
    }

    private String fetchHtml(String uriTemplate, String variable) {
        try {
            return restClient.get().uri(uriTemplate, variable).retrieve().body(String.class);
        } catch (RestClientException e) {
            Log.warn("Skipping [uri={}, variable={}]: {}", uriTemplate, variable, e.getMessage());
            return null;
        }
    }

    static List<String> parseCaseIds(TechTransferResponse response, int limit) {
        if (response == null || response.results() == null) {
            return Collections.emptyList();
        }
        List<String> caseIds = new ArrayList<>();
        for (List<Object> row : response.results()) {
            if (caseIds.size() >= limit) {
                break;
            }
            if (row != null && row.size() > CASE_ID_COLUMN && row.get(CASE_ID_COLUMN) instanceof String caseId
                && !caseId.isBlank()) {
                caseIds.add(caseId);
            }
        }
        return caseIds;
    }

    static List<String> parsePatentNumbers(String html) {
        List<String> patentNumbers = new ArrayList<>();
        if (html == null) {
            return patentNumbers;
        }
        Matcher matcher = PATENT_NUMBER.matcher(html);
        while (matcher.find()) {
            String patentNumber = matcher.group(1).replace(",", "");
            if (!patentNumbers.contains(patentNumber)) {
                patentNumbers.add(patentNumber);
            }
        }
        return patentNumbers;
    }

    static List<String> parseInventors(String html) {
        List<String> inventors = new ArrayList<>();
        if (html == null) {
            return inventors;
        }
        Matcher matcher = INVENTOR.matcher(html);
        while (matcher.find()) {
            String name = HtmlUtils.htmlUnescape(matcher.group(1)).trim();
            if (!name.isEmpty() && !inventors.contains(name)) {
                inventors.add(name);
            }
        }
        return inventors;
    }

    private static SimpleClientHttpRequestFactory timeoutRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(30));
        return factory;
    }

    /** NASA returns each result as a positional array; column 1 is the NASA case id (e.g. "MFS-TOPS-93"). */
    @JsonIgnoreProperties(ignoreUnknown = true)
    record TechTransferResponse(List<List<Object>> results) {
    }
}
