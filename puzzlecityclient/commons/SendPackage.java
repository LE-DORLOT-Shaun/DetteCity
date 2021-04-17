package commons;

import org.json.JSONException;
import org.json.JSONObject;

public class SendPackage {
	private JSONObject body;

	public SendPackage() {
		super();
		// TODO Auto-generated constructor stub
	}


	public JSONObject getBody() {
		return body;
	}

	public void setBody(JSONObject bd) {
		body = bd;
	}

	@Override
	public String toString() {
		JSONObject send = new JSONObject();
		try {
			send.put("body", this.body);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return send.toString() ;
	}

}
