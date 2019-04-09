package com.ulht.pw;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PWApplicationConstants {

	/** Root package for the application. Used for Spring annotation configuration, etc. */
	public static final String APPLICATION_ROOT_PACKAGE = "com.ulht.pw";

	/** Location of BeanConfig class */
	public static final String ANNOTATION_CONFIG_PACKAGE = "com.ulht.pw.config";

	/** Location of Loggable Dispatcher */
	public static final String LOGGING_DISPATCHER_PACKAGE = "com.ulht.pw.web.servlet";

	/** Location of Spring-Data JPA repositories */
	public static final String DAO_PACKAGE = "com.ulht.pw.repository";

	/** Location of JPA entities */
	public static final String JPA_ENTITIES_PACKAGE = "com.ulht.pw.domain";

	/** Regex for acceptable logins */
	public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

	public static final String SYSTEM_ACCOUNT = "system";
	public static final String ANONYMOUS_USER = "anonymoususer";
	public static final String DEFAULT_LANGUAGE = "pt-pt";
}
