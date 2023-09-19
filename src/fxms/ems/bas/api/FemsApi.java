package fxms.ems.bas.api;

import fxms.bas.api.FxApi;
import fxms.bas.vo.PsKind;
import subkjh.bas.co.log.LOG_LEVEL;

public class FemsApi extends FxApi {

	public static PsKind kind1M = new PsKind("1M", "1M", 0, "1 minutes");
	public static PsKind kind5M = new PsKind("5M", "5M", 0, "5 minutes");
	public static PsKind kind15M = new PsKind("15M", "15M", 0, "15 minutes");
	public static PsKind kind1H = new PsKind("1H", "1H", 0, "1 hour");
	public static PsKind kind1D = new PsKind("1D", "1D", 0, "1 day");
	public static PsKind kindMonthly = new PsKind("Monthly", "Monthly", 0, "1 month");

	@Override
	public String getState(LOG_LEVEL arg0) {
		return null;
	}

	@Override
	public void onCreated() throws Exception {

	}

	@Override
	protected void reload(Enum<?> arg0) throws Exception {

	}

}
