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

import java.util.Objects;

/**
 * BookTravelRequest
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2020-12-06T08:44:40.322365400+01:00[Europe/Warsaw]")
public class MessageManagerRequest {

  @JsonProperty("message")
  private String message = null;



  public MessageManagerRequest paymentCard(String paymentCard) {
    this.message = paymentCard;
    return this;
  }

   /**
   * Get paymentCard
   * @return paymentCard
  **/

  public String getPaymentCard() {
    return message;
  }

  public void setPaymentCard(String paymentCard) {
    this.message = paymentCard;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageManagerRequest bookTravelRequest = (MessageManagerRequest) o;
    return Objects.equals(this.message, bookTravelRequest.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BookTravelRequest {\n");
    sb.append("    paymentCard: ").append(toIndentedString(message)).append("\n");
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