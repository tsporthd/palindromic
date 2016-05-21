
package org.llp;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "1",
    "0",
    "2"
})
public class Concepts {

    @JsonProperty("1")
    private String _1;
    @JsonProperty("0")
    private String _0;
    @JsonProperty("2")
    private String _2;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The _1
     */
    @JsonProperty("1")
    public String get1() {
        return _1;
    }

    /**
     * 
     * @param _1
     *     The 1
     */
    @JsonProperty("1")
    public void set1(String _1) {
        this._1 = _1;
    }

    /**
     * 
     * @return
     *     The _0
     */
    @JsonProperty("0")
    public String get0() {
        return _0;
    }

    /**
     * 
     * @param _0
     *     The 0
     */
    @JsonProperty("0")
    public void set0(String _0) {
        this._0 = _0;
    }

    /**
     * 
     * @return
     *     The _2
     */
    @JsonProperty("2")
    public String get2() {
        return _2;
    }

    /**
     * 
     * @param _2
     *     The 2
     */
    @JsonProperty("2")
    public void set2(String _2) {
        this._2 = _2;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
