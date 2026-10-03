package org.llp;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.json.JsonMapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatentsTest {

    @Test
    void deserializesNasaResponseAndExtractsInventorNames() {
        String json = "{\"count\":1,\"results\":[{\"title\":\"Thermometer\",\"unknown_field\":\"x\","
            + "\"innovator\":[{\"fname\":\"Graham\",\"lname\":\"Bell\"},{\"fname\":\"Nicola\",\"lname\":\"Tesla\"}],"
            + "\"contact\":{\"email\":\"a@b.c\"},\"concepts\":{}}]}";

        Patents patents = JsonMapper.builder().build().readValue(json, Patents.class);

        assertEquals(Integer.valueOf(1), patents.getCount());
        assertEquals(Arrays.asList("Graham Bell", "Nicola Tesla"), patents.getInventorsFirstLastNames());
    }
}
