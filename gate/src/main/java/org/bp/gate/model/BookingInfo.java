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

package org.bp.gate.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * BookingInfo
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2020-12-06T08:44:40.322365400+01:00[Europe/Warsaw]")
public class BookingInfo {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("travelTimeByTrain")
  private Integer travelTimeByTrain = null;

  @JsonProperty("travelTimeByCar")
  private Integer travelTimeByCar = null;



  public BookingInfo() {
  }

  public BookingInfo(String id, Integer travelTimeByTrain, Integer travelTimeByCar) {
    this.id = id;
    this.travelTimeByTrain = travelTimeByTrain;
    this.travelTimeByCar = travelTimeByCar;
  }

  /**
   * Get id
   * @return id
   **/

  public BookingInfo id(String id) {
    this.id = id;
    return this;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  /**
   * Get cost
   * @return cost
   **/

  public BookingInfo travelTimeByTrain(Integer travelTimeByTrain) {
    this.travelTimeByTrain = travelTimeByTrain;
    return this;
  }

  public Integer getTravelTimeByTrain() {
    return travelTimeByTrain;
  }

  public void setTravelTimeByTrain(Integer travelTimeByTrain) {
    this.travelTimeByTrain = travelTimeByTrain;
  }

  /**
   * Get travelTimeByCar
   * @return travelTimeByCar
   **/

  public BookingInfo travelTimeByCar(Integer travelTimeByCar) {
    this.travelTimeByCar = travelTimeByCar;
    return this;
  }

  public Integer getTravelTimeByCar() {
    return travelTimeByCar;
  }

  public void setTravelTimeByCar(Integer travelTimeByCar) {
    this.travelTimeByCar = travelTimeByCar;
  }

  public boolean containsValue() {

    return !Objects.equals(this.id, null) ||
            !Objects.equals(this.travelTimeByTrain, null) ||
            !Objects.equals(this.travelTimeByCar, null);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BookingInfo bookingInfo = (BookingInfo) o;
    return Objects.equals(this.id, bookingInfo.id) &&
            Objects.equals(this.travelTimeByTrain, bookingInfo.travelTimeByTrain) &&
            Objects.equals(this.travelTimeByCar, bookingInfo.travelTimeByCar);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, travelTimeByTrain, travelTimeByCar);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BookingInfo {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    travelTimeByTrain: ").append(toIndentedString(travelTimeByTrain)).append("\n");
    sb.append("    travelTimeByCar: ").append(toIndentedString(travelTimeByCar)).append("\n");
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
