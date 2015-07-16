package ca.on.oicr.pinery.client;

import java.util.List;

public class InstrumentClient extends ResourceClient<InstrumentClient> {

	public InstrumentClient(PineryClient mainClient) {
		super(InstrumentClient.class, InstrumentClient[].class, mainClient);
	}
	
	public List<InstrumentClient> all() {
		return getResourceList("instruments");
	}
	
	public List<InstrumentClient> byModel(int instrumentModelId) {
		return getResourceList("instrumentmodel/"+instrumentModelId+"/instruments");
	}
	
	public InstrumentClient byId(int instrumentId) {
		return getResource("instrument/"+instrumentId);
	}

}
