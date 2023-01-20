package com.sdk.rest;

import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/insights")
public class InsightsRestController {

    @PostMapping(path = "/collect/{event_type}/{source_id}")
    public void track(@PathVariable("event_type") String eventType,
                        @PathVariable("source_id") String sourceId,
                        @RequestBody JsonObject jsonObject) {
        RestUtils.connectToInsights(eventType, sourceId, jsonObject);
    }
}
