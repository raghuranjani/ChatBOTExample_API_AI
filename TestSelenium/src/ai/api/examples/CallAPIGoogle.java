package ai.api.examples;

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.util.StringUtils;

public class CallAPIGoogle {
	public AIResponse callGoogle(String quote) {
		AIResponse response = null;
		try {
			AIConfiguration configuration = new AIConfiguration("16ac0817a0ea4875a5973731af7cc5f7");

			AIDataService dataService = new AIDataService(configuration);

			AIRequest request = new AIRequest(quote);

			response = dataService.request(request);
			System.out.println("response::"+response.toString());
			System.out.println("getResult::"+response.getResult().toString());
			System.out.println("getparameters::"+response.getResult().getParameters());
			System.out.println("getparameters::"+response.getResult().getMetadata().getIntentId());

			if (response.getStatus().getCode() == 200) {
				System.out.println(response.getResult().getFulfillment().getSpeech());
				String searchEngine = "";
				String searchParam = "";
				if(null != response.getResult().getParameters().get("search-engine")){
					searchEngine = response.getResult().getParameters().get("search-engine").toString();
					searchEngine = searchEngine.replace("\"", "");
				}else if(null != response.getResult().getParameters().get("service")){
					searchEngine = response.getResult().getParameters().get("service").toString();
					searchEngine = searchEngine.replace("\"", "");
				}

				if(null != response.getResult().getParameters().get("q")){
					searchParam = response.getResult().getParameters().get("q").toString();
					searchParam = searchParam.replace("\"", "");
				}
				System.out.println("searchEngine::"+searchEngine);
				System.out.println("searchParam::"+searchParam);
				if(!StringUtils.isEmpty(searchParam)){
					TextClientApplication.clickscreen(searchEngine,searchParam);
				}
			} else {
				System.err.println(response.getStatus().getErrorDetails());
			}
		} catch (AIServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
		

	}
}
