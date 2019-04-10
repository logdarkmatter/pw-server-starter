package com.ulht.pw.dto.client;

import com.ulht.pw.domain.enums.ContactType;
import com.ulht.pw.dto.BaseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ContactDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	private ContactType contactType;
	private String contact;
}
