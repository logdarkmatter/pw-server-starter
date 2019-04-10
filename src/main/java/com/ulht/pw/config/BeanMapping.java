package com.ulht.pw.config;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;

/**
 * @author Pedro Perdigão
 */
public class BeanMapping {

	private MapperFactory mapperFactory;

	public BeanMapping(MapperFactory mapperFactory) {
		this.mapperFactory = mapperFactory;
		initMappers();
		initConverters();
	}

	private void initMappers() {
		/**
		 * EXAMPLE HOW TO USE
		 * this.mapperFactory.classMap(Department.class, DepartmentDTO.class).mapNulls(false).field("organization.id",
		 * "organizationId").byDefault().register();
		 */
	}

	private void initConverters() {
		ConverterFactory converterFactory = mapperFactory.getConverterFactory();
		/**
		 * EXAMPLE HOW TO USE
		 * converterFactory.registerConverter(new LanguageConverter());
		 */
	}
}
