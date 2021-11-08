package org.nsu.fit.tm_backend.database.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionPojo {
    @JsonProperty("id")
    public UUID id;

    @JsonProperty("customer_id")
    public UUID customerId;

    @JsonProperty("plan_id")
    public UUID planId;

    @JsonProperty("plan_name")
    public String planName;

    @JsonProperty("plan_details")
    public String planDetails;

    @JsonProperty("plan_fee")
    public Integer planFee;
}
