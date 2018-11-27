package com.lingyun.common.support.code;

/**
 * 错误返回码
 */
public enum ErrorCode {


	USER_ERROR("-5","诉求用户为空"),
	UNSUPPORTED_TYPE("-4","不支持的类型"),
	PARAM_ERROR("-3","参数错误"),
	INTERNAL_ERROR("-2","表单数据为空"),
	SERVER_BUSY("-1","服务器忙，请稍后再试。"),
	SUCCESS("0","success"),
	FAILURE("1","failure"),
	SAVE_FAIL("2","保存失败"),
	PERMISSION_DENIED("004","没有权限"),
	DEPARTMENT_ERROR("1000","通过部门id查询的部门集合为空"),
	APPEAL_ERROR("1001","通过用户id查询的诉求集合为空"),
	APPEAL_HAD_SEND("1001","诉求已经send，不属于本部门了"),
	PERSONAL_ERROR("1002","通过员工id查询的员工集合为空"),
	APPEAL_HAD_HANDLED("1003","诉求已经提交处理，不属于本部门了"),
	APPEAL_HAD_DELAY("1004","诉求已经延时处理，不属于本部门了"),
	UPLOAD_FILE_EMPTY("1005","上传文件为空"),
	UPLOAD_IMAGE_COMPRESS_FAIL("1006","图片压缩失败"),
	NULL_EXCEPTION("4000","传输的内容为空"),
	RECORD_NULL("10000","数据为空"),
	NOT_LOGGIN("41000","没有登录"),
	USER_FROZEN("41001","用户被冻结"),
	NOT_AUTHORIZED("42000","未授权"),
	JSON_ERROR("47001","JSON/XML parse error");


	private String code; //error code
	private String message;  //error message
	private ErrorCode(String code,String message){
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
