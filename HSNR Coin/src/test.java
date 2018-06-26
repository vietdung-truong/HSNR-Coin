import org.json.*;

public class test {
	static String from = "David";
	static String to = "test";
	static String amount = "15";

	static String jsonResponse = String.format("{\"from\":\"%s\",\"to\":\"%s\",\"amount\":\"%s\"}", from, to, amount);

	public static void main(String[] args) {
		System.out.println(jsonResponse);

		JSONObject jsonObject = new JSONObject(jsonResponse);
		JSONArray data = jsonObject.getJSONArray("from");
		for (int i = 0; i < data.length(); i++) {

			JSONObject o = (JSONObject) data.get(i);
			String from2 = o.getString("from");
			String to2 = o.getString("to");
			int amount2 = o.getInt("amount");
			
			System.out.println(from2);
			System.out.println(to2);
			System.out.println(amount2);
		}
	}
}
