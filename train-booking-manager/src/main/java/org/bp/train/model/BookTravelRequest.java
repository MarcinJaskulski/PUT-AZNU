/*
 * Travel mircro service
 * Micro service to book a travel
 *
 * OpenAPI spec version: 1.0.0
 * Contact: supportm@bp.org
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package org.bp.train.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * BookTravelRequest
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2020-12-06T08:44:40.322365400+01:00[Europe/Warsaw]")
public class BookTravelRequest {

  @JsonProperty("numberOfPerson")
  private Integer numberOfPerson = null;

  @JsonProperty("travelByTrainForm")
  private Point travelByTrainForm = null;

  @JsonProperty("transportHub")
  private Point transportHub = null;

  @JsonProperty("travelByCarTo")
  private Point travelByCarTo = null;

  /**
   * @param numberOfPerson
   * @return
   */

  public BookTravelRequest numberOfPerson(Integer numberOfPerson) {
    this.numberOfPerson = numberOfPerson;
    return this;
  }

  public Integer getNumberOfPerson() {
    return numberOfPerson;
  }

  public void setNumberOfPerson(Integer numberOfPerson) {
    this.numberOfPerson = numberOfPerson;
  }

  /**
   * @param travelByTrainForm
   * @return
   */

  public BookTravelRequest travelByTrainForm(Point travelByTrainForm) {
    this.travelByTrainForm = travelByTrainForm;
    return this;
  }

  public Point getTravelByTrainForm() {
    return travelByTrainForm;
  }

  public void setTravelByTrainForm(Point travelByTrainForm) {
    this.travelByTrainForm = travelByTrainForm;
  }

  /**
   * @param transportHub
   * @return
   */

  public BookTravelRequest transportHub(Point transportHub) {
    this.transportHub = transportHub;
    return this;
  }

  public Point getTransportHub() {
    return transportHub;
  }

  public void setTransportHub(Point transportHub) {
    this.transportHub = transportHub;
  }

  /**
   * @param travelByCarTo
   * @return
   */

  public BookTravelRequest numberOfPerson(Point travelByCarTo) {
    this.travelByCarTo = travelByCarTo;
    return this;
  }

  public Point getTravelByCarTo() {
    return travelByCarTo;
  }

  public void setTravelByCarTo(Point travelByCarTo) {
    this.travelByCarTo = travelByCarTo;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookTravelRequest bookTravelRequest = (BookTravelRequest) o;
    return Objects.equals(this.numberOfPerson, bookTravelRequest.numberOfPerson) &&
            Objects.equals(this.travelByTrainForm, bookTravelRequest.travelByTrainForm) &&
            Objects.equals(this.transportHub, bookTravelRequest.transportHub) &&
            Objects.equals(this.travelByCarTo, bookTravelRequest.travelByCarTo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberOfPerson, travelByTrainForm, transportHub, travelByCarTo);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BookTravelRequest {\n");

    sb.append("    numberOfPerson: ").append(toIndentedString(numberOfPerson)).append("\n");
    sb.append("    travelByTrainForm: ").append(toIndentedString(travelByTrainForm)).append("\n");
    sb.append("    transportHub: ").append(toIndentedString(transportHub)).append("\n");
    sb.append("    travelByCarTo: ").append(toIndentedString(travelByCarTo)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}