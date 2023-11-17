package fxms.ems.cems;

import java.rmi.RemoteException;

import fxms.bas.event.NotiFilter;
import fxms.bas.fxo.service.FxServiceImpl;
import fxms.bas.mo.Mo;
import fxms.bas.vo.Alarm;

public class CemsServiceImpl extends FxServiceImpl implements CemsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4056899684439462062L;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FxServiceImpl.start(CemsServiceImpl.class.getSimpleName(), CemsServiceImpl.class, args);
	}

	public CemsServiceImpl(String name, int port) throws RemoteException, Exception {
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
