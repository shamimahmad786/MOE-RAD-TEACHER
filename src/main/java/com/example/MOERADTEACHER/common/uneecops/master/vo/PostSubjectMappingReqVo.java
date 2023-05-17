package com.example.MOERADTEACHER.common.uneecops.master.vo;

import javax.validation.constraints.NotNull;
import lombok.Data;
@Data
public class PostSubjectMappingReqVo {
	
	@NotNull(message = "please enter post id")
	private int postId;
	@NotNull(message = "please enter subject id")
	private int subjectId;
	
	
	
	
}
