package com.znh.model;
/**
 * 客户性质
 * @author Sean
 *
 */
public class CustomCharacter {
	
	private String customCharacterId;					// UUID
	private String customCharacterContent;		// 客户性质
	public String getCustomCharacterId() {
		return customCharacterId;
	}
	public void setCustomCharacterId(String customCharacterId) {
		this.customCharacterId = customCharacterId;
	}
	public String getCustomCharacterContent() {
		return customCharacterContent;
	}
	public void setCustomCharacterContent(String customCharacterContent) {
		this.customCharacterContent = customCharacterContent;
	}
	@Override
	public String toString() {
		return "CustomCharacter [customCharacterId=" + customCharacterId + ", customCharacterContent="
				+ customCharacterContent + "]";
	}
}
