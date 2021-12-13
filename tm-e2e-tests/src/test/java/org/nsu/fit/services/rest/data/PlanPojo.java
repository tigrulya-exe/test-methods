package org.nsu.fit.services.rest.data;

import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlanPojo {
    private UUID id;

    private String name;

    private String details;

    private int fee;
}
