package com.ccclogic.sailor.util.errorcodes;

public interface MediaResourceErrorCodes {

    String nameBlank = "ivr_name_blank";
    String holdNotFound = "no_hold_media_found_with_id";
    String maxHoldNotFound = "no_max_hold_media_found";
    String hangupHoldNotFound = "no_hangup_media_found";
    String noMediaFound = "no_media_found";
    String noVirtualHoldFound = "no_virtual_media_found";
    String noVirtualConfirmFound = "no_virtual_confirm_media_found";
    String noBusyMediaFound = "no_busy_media_found";
    String duplicateFileName = "duplicate_file_name_provided";
    String invalidMediaResourceId = "invalid_media_resource_id";
    String invalidFileName = "invalid_file_name";
    String invalidFileFormat = "invalid_file_format";
    String defaultMediaDelete = "default_media_delete";
    String mediaInUse = "media_in_use";
}
