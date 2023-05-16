package com.aircondition.refrigerationsys.Model;

import javax.persistence.*;

@Entity
public class AirCon {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String clientName;
		private String clientLocation;
		private String equipmentLocation;
		private String phoneNumber;

		private String email;
		private String workToDo;
		private String workExplanation;
	@Lob
	@Column(name = "EquipmentImage", columnDefinition = "LONGBLOB")
		private byte[] equipmentPhoto;

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientLocation() {
		return clientLocation;
	}

	public void setClientLocation(String clientLocation) {
		this.clientLocation = clientLocation;
	}

	public String getEquipmentLocation() {
		return equipmentLocation;
	}

	public void setEquipmentLocation(String equipmentLocation) {
		this.equipmentLocation = equipmentLocation;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getWorkToDo() {
		return workToDo;
	}

	public void setWorkToDo(String workToDo) {
		this.workToDo = workToDo;
	}

	public String getWorkExplanation() {
		return workExplanation;
	}

	public void setWorkExplanation(String workExplanation) {
		this.workExplanation = workExplanation;
	}

	public byte[] getEquipmentPhoto() {
		return equipmentPhoto;
	}

	public void setEquipmentPhoto(byte[] equipmentPhoto) {
		this.equipmentPhoto = equipmentPhoto;
	}
}


