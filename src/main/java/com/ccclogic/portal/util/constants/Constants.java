package com.ccclogic.portal.util.constants;

public final class Constants {

    public static class DATASOURCE {
        public static String DEFAULT_SCHEMA = "default";
        public static String DEFAULT_MYSQL_SCHEMA = "mysql";
        public static String METADATA_SCHEMA = "metadata";
        public static String CC_SCHEMA_PREFIX = "cc_";

    }

    public static class FILEPATH{
        public static String TIMEZONE_PATH = "/js/timezone.js";
        public static String CREATE_USER = "/template/create_user_template.html";
        public static String UPDATE_PASSWORD = "/template/update_user_password_template.html";
    }

    public static class REGEX_VALIDATION {
        public static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        public static final String NAME_PATTERN = "^[^\\\\/,':`~!\\[\\]@#$%^&*()+=}{|\";<>?]*$";
        public static String FORWARD_URI_PATTERN = "^[/-_@a-zA-Z0-9.:+]{3,}";
        public static final String USERNAMEALIAS_PATTERN = "^[^~`!@#$%^&*()+=|\\}{\\[\\]\"':;?/><]{3,}";
        public static final String USER_TYPE_PATTERN = "AGENT|SERVICE_NODE";
        public static final String PROJECT_NAME_PATTERN = "^[^\\\\/,':`~!\\[\\]@#$%^&*()+=}{|\";<>?]*$";
        public static final String PHONENUMBERURI_PATTERN = "^[a-zA-Z0-9/\\.?&\\+:\\-_@]*$";
        public static final String ALPHA_NUMERIC = "^[-\\w\\s]+$";
        public static final String MASK_NAME = "^[^`~!@#$%^&*()+=}{|\";<>?]*$";

    }
    public static class URL_CONTEXT{
        public static String DS_CONTEXT = "/ds";
    }

    public static class RESULT_CODES{
        public static final Long BUSINESS_CATEGORY = 2L;
        public static final Long SYSTEM_CATEGORY = 1L;
    }

    public static class DEFAULT_CONSTANTS {
        public static final String FILE_PATH_RESOURCE_MEDIA = "cc_{ccid}/resources/media";
    }
}
