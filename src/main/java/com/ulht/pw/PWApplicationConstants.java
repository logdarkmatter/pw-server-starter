package com.ulht.pw;

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
}
