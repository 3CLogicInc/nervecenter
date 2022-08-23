package com.ccclogic.portal.util.errorcodes;

public interface IntegrationDesignerErrorCodes {
    String invalidTriggerId = "invalid_trigger_id";
    String triggerNotFound = "trigger_not_found";


    String matchingRuleNotFound = "matching_rule_not_found";
    String invalidMatchingRuleId = "matching_rule_trigger_id";

    String recordProducerNotFound = "record_producer_not_found";
    String invalidRecordProducerId = "invalid_record_producer_id";

    String nameRequired = "name_required";
    String descRequired = "description_required";
    String activeRequired = "active_required";
    String crmRequired = "crm_required";
    String eventRequired = "event_required";
    String matchJsonRequired = "matchJson_required";
}
