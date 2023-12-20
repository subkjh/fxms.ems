package fxms.ems.cems;

import java.rmi.RemoteException;
import java.util.concurrent.LinkedBlockingQueue;

import fxms.bas.event.NotiFilter;
import fxms.bas.fxo.service.FxServiceImpl;
import fxms.bas.fxo.thread.FxThread;
import fxms.bas.mo.Mo;
import fxms.bas.vo.Alarm;
import fxms.ems.cems.dfo.CallCrawlerDpo;
import fxms.ems.cems.dto.NodeGwDto;
import subkjh.bas.co.log.Logger;

public class CemsServiceImpl extends FxServiceImpl implements CemsService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4056899684439462062L;

	private LinkedBlockingQueue<Long> queue;
	private FxThread thread = null;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FxServiceImpl.start(CemsServiceImpl.class.getSimpleName(), CemsServiceImpl.class, args);
	}

	public CemsServiceImpl(String name, int port) throws RemoteException, Exception {
		super(name, port);
		queue = new LinkedBlockingQueue<>();
	}

	@Override
	protected void onStarted() throws Exception {

		super.onStarted();

	}

	/**
	 * nodeId, namespaceIndex 조회 요청
	 * 
	 * @param moNo
	 */
	@Override
	public void addCrawler(NodeGwDto gw) throws Exception {

		Logger.logger.info("{}", gw);

		if (thread == null) {
			thread = new FxThread() {
				@Override
				protected void doWork() throws Exception {
					while (true) {
						Long moNo = queue.take();
						if (moNo != null) {
							try {
								new CallCrawlerDpo().run(null, moNo);
							} catch (Exception e) {
								Logger.logger.error(e);
							}
						}
					}
				}
			};
			thread.setName("OpcUaNodeIdSyncer");
			thread.start();
		}

		try {
			queue.put(gw.moNo);
		} catch (InterruptedException e) {
		}
	}

	@Override
	protected void onCycle(long mstime) {

		super.onCycle(mstime);

	}

	@Override
	public NotiFilter getNotiFilter() throws RemoteException, Exception {
		NotiFilter notiFilter = new NotiFilter();
		notiFilter.add(Mo.class);
		notiFilter.add(Alarm.class);
		return notiFilter;
	}
}
