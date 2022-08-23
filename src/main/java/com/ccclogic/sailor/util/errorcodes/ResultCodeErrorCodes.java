package com.ccclogic.sailor.util.errorcodes;

public interface ResultCodeErrorCodes {

    String notFound = "resultCode_notfound";

    String invalidResultCodeId = "invalid_resultCode_id";
    String resultCodeNotAssigned = "result_code_not_assigned";

    String displayValueExists = "display_value_exists";
    String displayValueEmpty="display_value_empty";

    String alreadyExists= "value_already_exists";

    String invalidCategoryProvided = "invalid_category_provided";
    String callcenterResultCodeCannotBeNull = "empty_callcenter_resultcode";
    String maxTriesExceeded = "max_tries_exceeds_campaign_max_tries";
    String invalid_tag_id = "invalid_tag_id";

}
