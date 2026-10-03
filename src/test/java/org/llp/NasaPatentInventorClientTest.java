package org.llp;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class NasaPatentInventorClientTest {

    private static final String NASA_SEARCH_JSON = "{\"results\":["
        + "[\"61ea84da\",\"MFS-TOPS-93\",\"Title A\",\"Abstract\",\"MFS-TOPS-93\",\"Propulsion\",\"\",\"\",\"\",\"MSFC\",\"img\",\"\",13.5],"
        + "[\"LEW-TOPS-38\",\"LEW-TOPS-38\",\"Title B\",\"Abstract\",\"LEW-TOPS-38\",\"Mechanical\",\"\",\"\",\"\",\"GRC\",\"img\",\"\",12.9],"
        + "[\"TOP2-300\",\"TOP2-300\",\"Title C\",\"Abstract\",\"TOP2-300\",\"Sensors\",\"\",\"\",\"\",\"ARC\",\"img\",\"\",11.0]"
        + "],\"count\":3,\"total\":3,\"perpage\":10,\"page\":0}";

    private static final String MFS_CASE_HTML = "<label class=\"label\">Patent(s)</label>\n"
        + "<span class=\"patent_number\"><a href=\"https://ppubs.uspto.gov/pubwebapp/external.html?q=(11333105).pn.\" target=\"_blank\">11,333,105</a></span>";

    private static final String LEW_CASE_HTML =
        "<span class=\"patent_number\"><a href=\"x\" target=\"_blank\">7,086,648</a></span>"
            + "<span class=\"patent_number\"><a href=\"y\" target=\"_blank\">9,541,148</a></span>";

    private static String googlePatentHtml(String... inventors) {
        StringBuilder html = new StringBuilder("<html><head>");
        for (String inventor : inventors) {
            html.append("<meta name=\"DC.contributor\" content=\"").append(inventor).append("\" scheme=\"inventor\">\n");
        }
        html.append("<meta name=\"DC.contributor\" content=\"NASA\" scheme=\"assignee\"></head></html>");
        return html.toString();
    }

    @Test
    void findsInventorsForTopNasaCasesAcrossAllTheirPatents() {
        RestClient.Builder builder = RestClient.builder();
        MockRestServiceServer server = MockRestServiceServer.bindTo(builder).build();

        server.expect(requestTo("https://technology.nasa.gov/api/api/patent/rocket%20engine"))
            .andRespond(withSuccess(NASA_SEARCH_JSON, MediaType.APPLICATION_JSON));
        server.expect(requestTo("https://technology.nasa.gov/patent/MFS-TOPS-93"))
            .andRespond(withSuccess(MFS_CASE_HTML, MediaType.TEXT_HTML));
        server.expect(requestTo("https://patents.google.com/patent/US11333105/en"))
            .andRespond(withSuccess(googlePatentHtml("Paul R. Gradl", "Omar R. Mireles"), MediaType.TEXT_HTML));
        server.expect(requestTo("https://technology.nasa.gov/patent/LEW-TOPS-38"))
            .andRespond(withSuccess(LEW_CASE_HTML, MediaType.TEXT_HTML));
        server.expect(requestTo("https://patents.google.com/patent/US7086648/en"))
            .andRespond(withSuccess(googlePatentHtml("Bruce M. Steinetz"), MediaType.TEXT_HTML));
        server.expect(requestTo("https://patents.google.com/patent/US9541148/en"))
            .andRespond(withStatus(HttpStatus.NOT_FOUND));

        List<String> inventors = new NasaPatentInventorClient(builder).findInventorNames("rocket engine", 2);

        assertEquals(Arrays.asList("Paul R. Gradl", "Omar R. Mireles", "Bruce M. Steinetz"), inventors);
        server.verify();
    }

    @Test
    void parsesPatentNumbersWithoutCommasAndDeduplicates() {
        assertEquals(Arrays.asList("7086648", "9541148"),
            NasaPatentInventorClient.parsePatentNumbers(LEW_CASE_HTML + LEW_CASE_HTML));
        assertEquals(Collections.emptyList(), NasaPatentInventorClient.parsePatentNumbers("<html>no patents</html>"));
        assertEquals(Collections.emptyList(), NasaPatentInventorClient.parsePatentNumbers(null));
    }

    @Test
    void parsesOnlyInventorsAndUnescapesHtml() {
        assertEquals(Arrays.asList("Jing Li", "Kevin O'Brien"),
            NasaPatentInventorClient.parseInventors(googlePatentHtml("Jing Li", "Kevin O&#39;Brien", "Jing Li")));
    }
}
