package com.ulht.pw.controller.vm;

import ch.qos.logback.classic.Logger;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * View Model object for storing a Logback logger.
 */
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoggerVM {

	private String name;

	private String level;

	public LoggerVM(Logger logger) {
		this.name = logger.getName();
		this.level = logger.getEffectiveLevel().toString();
	}
}
