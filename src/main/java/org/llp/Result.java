
package org.llp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "category",
    "client_record_id",
    "center",
    "eRelations",
    "reference_number",
    "expiration_date",
    "abstract",
    "title",
    "innovator",
    "contact",
    "publication",
    "concepts",
    "serial_number",
    "_id",
    "patent_number",
    "id",
    "trl"
})
public class Result {

    @JsonProperty("category")
    private String category;
    @JsonProperty("client_record_id")
    private String clientRecordId;
    @JsonProperty("center")
    private String center;
    @JsonProperty("eRelations")
    private List<Object> eRelations = new ArrayList<Object>();
    @JsonProperty("reference_number")
    private String referenceNumber;
    @JsonProperty("expiration_date")
    private String expirationDate;
    @JsonProperty("abstract")
    private String _abstract;
    @JsonProperty("title")
    private String title;
    @JsonProperty("innovator")
    private List<Innovator> innovator = new ArrayList<Innovator>();
    @JsonProperty("contact")
    private Contact contact;
    @JsonProperty("publication")
    private Object publication;
    @JsonProperty("concepts")
    private Concepts concepts;
    @JsonProperty("serial_number")
    private String serialNumber;
    @JsonProperty("_id")
    private String Id;
    @JsonProperty("patent_number")
    private String patentNumber;
    @JsonProperty("id")
    private String id;
    @JsonProperty("trl")
    private String trl;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The category
     */
    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The clientRecordId
     */
    @JsonProperty("client_record_id")
    public String getClientRecordId() {
        return clientRecordId;
    }

    /**
     * 
     * @param clientRecordId
     *     The client_record_id
     */
    @JsonProperty("client_record_id")
    public void setClientRecordId(String clientRecordId) {
        this.clientRecordId = clientRecordId;
    }

    /**
     * 
     * @return
     *     The center
     */
    @JsonProperty("center")
    public String getCenter() {
        return center;
    }

    /**
     * 
     * @param center
     *     The center
     */
    @JsonProperty("center")
    public void setCenter(String center) {
        this.center = center;
    }

    /**
     * 
     * @return
     *     The eRelations
     */
    @JsonProperty("eRelations")
    public List<Object> getERelations() {
        return eRelations;
    }

    /**
     * 
     * @param eRelations
     *     The eRelations
     */
    @JsonProperty("eRelations")
    public void setERelations(List<Object> eRelations) {
        this.eRelations = eRelations;
    }

    /**
     * 
     * @return
     *     The referenceNumber
     */
    @JsonProperty("reference_number")
    public String getReferenceNumber() {
        return referenceNumber;
    }

    /**
     * 
     * @param referenceNumber
     *     The reference_number
     */
    @JsonProperty("reference_number")
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    /**
     * 
     * @return
     *     The expirationDate
     */
    @JsonProperty("expiration_date")
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * 
     * @param expirationDate
     *     The expiration_date
     */
    @JsonProperty("expiration_date")
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * 
     * @return
     *     The _abstract
     */
    @JsonProperty("abstract")
    public String getAbstract() {
        return _abstract;
    }

    /**
     * 
     * @param _abstract
     *     The abstract
     */
    @JsonProperty("abstract")
    public void setAbstract(String _abstract) {
        this._abstract = _abstract;
    }

    /**
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The innovator
     */
    @JsonProperty("innovator")
    public List<Innovator> getInnovator() {
        return innovator;
    }

    /**
     * 
     * @param innovator
     *     The innovator
     */
    @JsonProperty("innovator")
    public void setInnovator(List<Innovator> innovator) {
        this.innovator = innovator;
    }

    /**
     * 
     * @return
     *     The contact
     */
    @JsonProperty("contact")
    public Contact getContact() {
        return contact;
    }

    /**
     * 
     * @param contact
     *     The contact
     */
    @JsonProperty("contact")
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * 
     * @return
     *     The publication
     */
    @JsonProperty("publication")
    public Object getPublication() {
        return publication;
    }

    /**
     * 
     * @param publication
     *     The publication
     */
    @JsonProperty("publication")
    public void setPublication(Object publication) {
        this.publication = publication;
    }

    /**
     * 
     * @return
     *     The concepts
     */
    @JsonProperty("concepts")
    public Concepts getConcepts() {
        return concepts;
    }

    /**
     * 
     * @param concepts
     *     The concepts
     */
    @JsonProperty("concepts")
    public void setConcepts(Concepts concepts) {
        this.concepts = concepts;
    }

    /**
     * 
     * @return
     *     The serialNumber
     */
    @JsonProperty("serial_number")
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * 
     * @param serialNumber
     *     The serial_number
     */
    @JsonProperty("serial_number")
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * 
     * @return
     *     The Id
     */
    @JsonProperty("_id")
    public String getId() {
        return Id;
    }

    /**
     * 
     * @param Id
     *     The _id
     */
    @JsonProperty("_id")
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * 
     * @return
     *     The patentNumber
     */
    @JsonProperty("patent_number")
    public String getPatentNumber() {
        return patentNumber;
    }

    /**
     * 
     * @param patentNumber
     *     The patent_number
     */
    @JsonProperty("patent_number")
    public void setPatentNumber(String patentNumber) {
        this.patentNumber = patentNumber;
    }


    /**
     * 
     * @return
     *     The trl
     */
    @JsonProperty("trl")
    public String getTrl() {
        return trl;
    }

    /**
     * 
     * @param trl
     *     The trl
     */
    @JsonProperty("trl")
    public void setTrl(String trl) {
        this.trl = trl;
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
