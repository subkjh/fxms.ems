package fxms.ems.flare;

import java.rmi.RemoteException;

import fxms.bas.event.NotiFilter;
import fxms.bas.fxo.service.FxServiceImpl;
import fxms.bas.fxo.service.MoServiceImpl;
import fxms.bas.mo.Mo;
import fxms.bas.vo.Alarm;
import fxms.ems.vup.VupService;

public class FlareServiceImpl extends MoServiceImpl implements VupService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2466143371388367407L;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FxServiceImpl.start(VupService.class.getSimpleName(), FlareServiceImpl.class, args);
	}

	public FlareServiceImpl(String name, int port) throws RemoteException, Exception {
		super(name, port);
	}

	@Override
	protected void onStarted() throws Exception {
		super.onStarted();
	}

	@Override
	public NotiFilter getNotiFilter() throws RemoteException, Exception {
		NotiFilter notiFilter = new NotiFilter();
		notiFilter.add(Mo.class);
		notiFilter.add(Alarm.class);
		return notiFilter;
	}
}