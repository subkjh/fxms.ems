package fxms.ems.vup.dpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fxms.bas.api.FxApi;
import fxms.bas.api.MoApi;
import fxms.bas.fxo.FxCfg;
import fxms.bas.fxo.FxmsUtil;
import fxms.bas.impl.api.MoApiDfo;
import fxms.bas.impl.dbo.all.FX_CF_INLO;
import fxms.bas.impl.dpo.FxDpo;
import fxms.bas.impl.dpo.FxFact;
import fxms.ems.bas.dbo.FE_ENG_BAS;
import fxms.ems.bas.dbo.FE_ENG_RT_BAS;
import fxms.ems.bas.dbo.FE_ENG_RT_PATH;
import fxms.ems.bas.dbo.FE_FAC_PIPE;
import fxms.ems.bas.dbo.FE_FAC_PIPE_PATH;
import fxms.ems.bas.mo.SensorMo;
import subkjh.dao.ClassDao;
import subkjh.dao.database.DBManager;
import subkjh.dao.util.FxTableMaker;

/**
 * 배관 정보를 이용하여 에너지 경로를 생성한다.
 * 
 * @author subkjh
 *
 */
public class MakeEnergyRouteDpo implements FxDpo<Void, Boolean> {

	class Data {
		final FE_FAC_PIPE pipe;
		final List<FE_FAC_PIPE_PATH> paths;

		Data(FE_FAC_PIPE pipe) {
			this.pipe = pipe;
			this.paths = new ArrayList<>();
		}

	}

	class RtData {
		final FE_ENG_RT_BAS rt;
		final List<FE_ENG_RT_PATH> paths;

		RtData(FE_ENG_RT_BAS rt, List<FE_ENG_RT_PATH> paths) {
			this.rt = rt;
			this.paths = paths;
		}
	}

	public static void main(String[] args) throws Exception {
		MoApi.api = new MoApiDfo();
		MakeEnergyRouteDpo dpo = new MakeEnergyRouteDpo();
		dpo.makeEnergrRoute();
	}

	private final Map<String, Data> datas;

	private final Map<String, FE_ENG_BAS> engMap;
	private final Map<Integer, FX_CF_INLO> inloMap;
	private Map<String, SensorMo> moMap;

	public MakeEnergyRouteDpo() {

		this.datas = new HashMap<>();
		this.engMap = new HashMap<>();
		this.inloMap = new HashMap<>();

	}

	private List<FE_ENG_RT_PATH> getPath(String engRtId, Data source, Data sink) {
		List<FE_ENG_RT_PATH> ret = new ArrayList<FE_ENG_RT_PATH>();
		FE_FAC_PIPE_PATH sinkPath;

		ret.add(makeRtPath(engRtId, source.pipe.getPipeId(), source.pipe.getPipeName()));

		SRC: for (FE_FAC_PIPE_PATH src : source.paths) {

			// SOURCE에서 SINK까지 가는 배관을 추가한다.
			if ("PIPE".equals(src.getLinkObjClCd())) {
				ret.add(makeRtPath(engRtId, src.getLinkObjId(), src.getLinkObjName()));

				// SINK에 연결고리를 찾으면 SINK에서 배관만 추출하여 추가한다.
				for (int i = 0; i < sink.paths.size(); i++) {
					sinkPath = sink.paths.get(i);
					if (FxApi.isSame(src.getLinkObjId(), sinkPath.getLinkObjId())) {
						for (int n = i + 1; n < sink.paths.size(); n++) {
							sinkPath = sink.paths.get(n);
							if ("PIPE".equals(sinkPath.getLinkObjClCd())) {
//								ret.add(makeRtPath(engRtId, src));				
							}
						}
						break SRC;
					}
				}
			}
		}

		ret.add(makeRtPath(engRtId, sink.pipe.getPipeId(), sink.pipe.getPipeName()));
		return ret;
	}

	private long getSensorMoNo(List<FE_FAC_PIPE_PATH> paths) {
		for (FE_FAC_PIPE_PATH path : paths) {
			if ("MO".equals(path.getLinkObjClCd())) {
				if (path.getLinkObjName().contains("유량계")) {
					SensorMo mo = this.moMap.get(path.getLinkObjId());
					if (mo != null) {
						return mo.getMoNo();
					}
				}
			}
		}

		return -1;
	}

	private List<Data> getSinks() {
		List<Data> list = new ArrayList<Data>();

		for (Data data : this.datas.values()) {
			if (data.pipe.getPipeClCd().equals("SINK")) {
				list.add(data);
			}
		}
		return list;
	}

	private List<Data> getSources() {
		List<Data> list = new ArrayList<Data>();

		for (Data data : this.datas.values()) {
			if (data.pipe.getPipeClCd().equals("SOURCE")) {
				list.add(data);
			} else if (data.pipe.getPipeClCd().equals("PUBLIC")) {
				list.add(data);
			}
		}

		return list;
	}

	private void init() throws Exception {

		List<FE_FAC_PIPE> list;
		List<FE_FAC_PIPE_PATH> paths;
		List<FE_ENG_BAS> engs;
		List<FX_CF_INLO> inlos;

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();
		try {
			tran.start();
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "AIR", "mngInloNo", 11000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "AIR", "mngInloNo", 12000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "AIR", "mngInloNo", 13000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "AIR", "mngInloNo", 14000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "STEAM", "mngInloNo", 11000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "STEAM", "mngInloNo", 12000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "STEAM", "mngInloNo", 13000));
//			list = tran.select(FE_FAC_PIPE.class, FxApi.makePara("engId", "STEAM", "mngInloNo", 14000));
			list = tran.select(FE_FAC_PIPE.class, null);
			paths = tran.select(FE_FAC_PIPE_PATH.class, null);
			engs = tran.select(FE_ENG_BAS.class, null);
			inlos = tran.select(FX_CF_INLO.class, null);

			init(list, paths, engs, inlos);

		} catch (Exception e) {
			throw e;
		} finally {
			tran.stop();
		}

	}

	private void init(List<FE_FAC_PIPE> pipes, List<FE_FAC_PIPE_PATH> paths, List<FE_ENG_BAS> engs,
			List<FX_CF_INLO> inlos) {

		for (FE_FAC_PIPE pipe : pipes) {
			this.datas.put(pipe.getPipeId(), new Data(pipe));
		}

		for (FE_FAC_PIPE_PATH path : paths) {
			Data data = this.datas.get(path.getPipeId());
			if (data != null) {
				data.paths.add(path);
			}
		}

		for (FE_ENG_BAS eng : engs) {
			this.engMap.put(eng.getEngId(), eng);
		}

		for (FX_CF_INLO inlo : inlos) {
			this.inloMap.put(inlo.getInloNo(), inlo);
		}

	}

	private void initMos() throws Exception {
		List<SensorMo> list = MoApi.getApi().getMoList(null, SensorMo.class);
		this.moMap = MoApi.toMoTidMap(list);
	}

	private boolean isLinked(Data source, Data sink) {

		return true;

		// 일반공장이 소스인 경우 다른 공장과 연결 포인트를 찾을 수 없다.
		// 모든 연결포인트는 공용에만 존재한다.
//		for (FE_FAC_PIPE_PATH src : source.paths) {
//			if ("PIPE".equals(src.getLinkObjClCd())) {
//				for (FE_FAC_PIPE_PATH sp : sink.paths) {
//					if (FxApi.isSame(src.getLinkObjId(), sp.getLinkObjId())) {
//						return true;
//					}
//				}
//			}
//		}
//		return false;
	}

	private List<RtData> make() {

		List<RtData> ret = new ArrayList<RtData>();

		FE_FAC_PIPE srcPipe, snkPipe;

		for (Data source : getSources()) {

			srcPipe = source.pipe;

			for (Data sink : getSinks()) {

				snkPipe = sink.pipe;

//				System.out.println(FxmsUtil.toJson(source));
//				System.out.println(FxmsUtil.toJson(sink));

				if (srcPipe.getEngId().equals(snkPipe.getEngId())
						&& srcPipe.getMngInloNo().intValue() == snkPipe.getMngInloNo().intValue()
						&& srcPipe.getLinkInloNo().intValue() != snkPipe.getLinkInloNo().intValue()) {

					if (isLinked(source, sink)) {

						FE_ENG_BAS eng = engMap.get(srcPipe.getEngId());
						FX_CF_INLO srcInlo = inloMap.get(srcPipe.getLinkInloNo());
						FX_CF_INLO sinkInlo = inloMap.get(snkPipe.getLinkInloNo());
						FX_CF_INLO complex = inloMap.get(srcPipe.getMngInloNo());

						String rtId = eng.getEngTid() + "-" + srcInlo.getInloTid() + "-" + sinkInlo.getInloTid();
						FE_ENG_RT_BAS rt = new FE_ENG_RT_BAS();
						rt.setEngId(eng.getEngId());
						rt.setEngRtDescr(
								"(" + eng.getEngName() + ") " + srcInlo.getInloName() + " - " + sinkInlo.getInloName());
						rt.setEngRtId(rtId);
						rt.setFnshInloNo(sinkInlo.getInloNo());
						rt.setFnshInloName(sinkInlo.getInloName());
						rt.setStrtInloNo(srcInlo.getInloNo());
						rt.setStrtInloName(srcInlo.getInloName());
						rt.setInloNo(complex.getInloNo());
						rt.setInloName(complex.getInloName());

						rt.setSensorMoNo(getSensorMoNo(sink.paths));

						RtData rtData = new RtData(rt, getPath(rtId, source, sink));
						ret.add(rtData);
						print(rtData);
					}
				}
			}
		}

		return ret;
	}

	private FE_ENG_RT_PATH makeRtPath(String engRtId, String pipeId, String pipeDescr) {
		FE_ENG_RT_PATH ret = new FE_ENG_RT_PATH();
		ret.setEngRtId(engRtId);
		ret.setPipeDescr(pipeDescr);
		ret.setPipeId(pipeId);
		return ret;
	}

	private void print(RtData data) {
		System.out.println(data.rt.getEngRtId() + " " + data.rt.getEngRtDescr());
		for (int i = 0; i < data.paths.size(); i++) {
			System.out.println("\t" + i + "." + FxmsUtil.toJson(data.paths.get(i)));
		}
	}

	private void save(List<RtData> list) throws Exception {

		ClassDao tran = DBManager.getMgr().getDataBase(FxCfg.DB_CONFIG).createClassDao();
		FE_ENG_RT_BAS rt;
		try {
			tran.start();
			for (RtData data : list) {

				int index = 1;

				rt = tran.selectOne(FE_ENG_RT_BAS.class, FxApi.makePara("engRtId", data.rt.getEngRtId()));

				FxTableMaker.initRegChg(0, data.rt);
				if (rt != null) {
					tran.updateOfClass(FE_ENG_RT_BAS.class, data.rt);
				} else {
					tran.insertOfClass(FE_ENG_RT_BAS.class, data.rt);
				}

				tran.deleteOfClass(FE_ENG_RT_PATH.class, FxApi.makePara("engRtId", data.rt.getEngRtId()));
				for (FE_ENG_RT_PATH path : data.paths) {
					path.setRtSeq(index++);
					FxTableMaker.initRegChg(0, path);
				}
				tran.insertOfClass(FE_ENG_RT_PATH.class, data.paths);

			}

			tran.commit();

		} catch (Exception e) {

			tran.rollback();
			throw e;

		} finally {
			tran.stop();
		}

	}

	public void makeEnergrRoute() throws Exception {

		init(); // 데이터 설정

		initMos();

		List<RtData> list = make(); // 경로 생성

		save(list); // 경로 기록

	}

	@Override
	public Boolean run(FxFact fact, Void data) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
