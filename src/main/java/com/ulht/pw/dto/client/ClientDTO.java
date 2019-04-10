package com.ulht.pw.dto.client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
public class ClientDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	private String lastName;
	private String firstName;
	private LocalDate dateOfBirth;
	private List<ContactDTO> contacts = new ArrayList<>();
	private List<AddressDTO> addresses = new ArrayList<>();
}
