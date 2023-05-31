package fxms.ems.vup;

import java.rmi.RemoteException;

import fxms.bas.event.NotiFilter;
import fxms.bas.fxo.service.FxServiceImpl;
import fxms.bas.mo.Mo;
import fxms.bas.vo.Alarm;

public class VupServiceImpl extends FxServiceImpl implements VupService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8978200873633112817L;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FxServiceImpl.start(VupServiceImpl.class.getSimpleName(), VupServiceImpl.class, args);
	}

	public VupServiceImpl(String name, int port) throws RemoteException, Exception {
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
