package org.nsu.fit.services.rest.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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