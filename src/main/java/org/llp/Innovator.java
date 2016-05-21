
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
    "lname",
    "mname",
    "company",
    "order",
    "fname"
})
public class Innovator {

    @JsonProperty("lname")
    private String lname;
    @JsonProperty("mname")
    private String mname;
    @JsonProperty("company")
    private String company;
    @JsonProperty("order")
    private String order;
    @JsonProperty("fname")
    private String fname;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The lname
     */
    @JsonProperty("lname")
    public String getLname() {
        return lname;
    }

    /**
     * 
     * @param lname
     *     The lname
     */
    @JsonProperty("lname")
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * 
     * @return
     *     The mname
     */
    @JsonProperty("mname")
    public String getMname() {
        return mname;
    }

    /**
     * 
     * @param mname
     *     The mname
     */
    @JsonProperty("mname")
    public void setMname(String mname) {
        this.mname = mname;
    }

    /**
     * 
     * @return
     *     The company
     */
    @JsonProperty("company")
    public String getCompany() {
        return company;
    }

    /**
     * 
     * @param company
     *     The company
     */
    @JsonProperty("company")
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 
     * @return
     *     The order
     */
    @JsonProperty("order")
    public String getOrder() {
        return order;
    }

    /**
     * 
     * @param order
     *     The order
     */
    @JsonProperty("order")
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * 
     * @return
     *     The fname
     */
    @JsonProperty("fname")
    public String getFname() {
        return fname;
    }


    public String getFirstNameLastName(){
        StringBuilder builder = new StringBuilder();

        if ( fname != null ){
            builder.append(fname);
            builder.append(" ");
        }

        if ( lname != null ){
            builder.append(lname);
        }

        return builder.toString();
    }

    /**
     * 
     * @param fname
     *     The fname
     */
    @JsonProperty("fname")
    public void setFname(String fname) {
        this.fname = fname;
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
