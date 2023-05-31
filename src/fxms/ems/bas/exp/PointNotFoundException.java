package fxms.ems.bas.exp;

import fxms.bas.exp.NotFoundException;
import subkjh.bas.co.lang.Lang;

public class PointNotFoundException extends NotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5074655623867968821L;

	public PointNotFoundException(String pointPid) {
		super("point", pointPid, Lang.get("This is an unregistered control point.", pointPid)); // 등록되지 않은 관제점입니다.
	}

}
