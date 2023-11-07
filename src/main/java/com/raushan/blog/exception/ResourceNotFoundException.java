package com.raushan.blog.exception;





public class ResourceNotFoundException extends RuntimeException{
	

	/**
	 * 
	 */

	String resourcrName;
	String fieldName;
	long fieldValue;
	
	public ResourceNotFoundException(String resourcrName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourcrName, fieldName, fieldValue));
		this.resourcrName = resourcrName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	@Override
	public String toString() {
		return "ResourceNotFoundException [resourcrName=" + resourcrName + ", fieldName=" + fieldName + ", fieldValue="
				+ fieldValue + "]";
	}

	public String getResourcrName() {
		return resourcrName;
	}

	public void setResourcrName(String resourcrName) {
		this.resourcrName = resourcrName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public long getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(long fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	
	
	
	
	
	

}
