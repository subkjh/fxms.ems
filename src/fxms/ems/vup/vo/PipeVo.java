package fxms.ems.vup.vo;

import java.util.Map;

public class PipeVo extends VupVo {

	private Map<String, Object> pipe;

	public PipeVo(Map<String, Object> pipe) {
		this.pipe = pipe;
	}

	public void addPath(Map<String, Object> path) {
		addList(this.pipe, "path_list", path);
	}

	public String getPipeId() {
		return this.pipe.get("pipe_id").toString();
	}

	public String getComplexId() {
		return this.pipe.get("complex_pid").toString();
	}
}
