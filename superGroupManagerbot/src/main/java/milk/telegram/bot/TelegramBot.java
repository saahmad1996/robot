package milk.telegram.bot;

import milk.telegram.handler.Handler;
import milk.telegram.update.Update;
import milk.telegram.type.user.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends Thread {

	public static final String BASE_URL = "https://api.telegram.org/bot%s/%s";

	private String token = "373255648:AAGhUN5t7wdGPu8c0FJo4YVv8riVoxifsC4";

	private int lastId = 0;
	private int limit = 100;
	private int timeout = 1500;

	private User me;

	private Handler handler;

	public TelegramBot(String token) {
		this(token, null);
	}

	public TelegramBot(String token, Handler handler) {
		this(token, handler, 1000);
	}

	public TelegramBot(String token, Handler handler, int timeout) {
		this.setToken(token);
		this.setHandler(handler);
		this.setTimeout(timeout);
	}

	public final JSONObject updateResponse(String key, JSONObject object) {
		try {
			URL url = new URL(String.format(BASE_URL, this.token, key));
			URLConnection connection = url.openConnection();
			connection.setDoInput(true);
			connection.setConnectTimeout(this.timeout);
			connection.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");

			if (object != null && object.length() > 0) {
				connection.setDoOutput(true);
				OutputStream stream = connection.getOutputStream();
				stream.write(object.toString().getBytes(StandardCharsets.UTF_8));
			}

			return new JSONObject(new JSONTokener(new InputStreamReader(
					connection.getInputStream(), StandardCharsets.UTF_8)));
		} catch (IOException ex) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void run() {
		while (true) {
			if (this.isInterrupted() || this.token.length() < 45
					|| this.handler == null)
				break;

			try {
				JSONObject k = new JSONObject();
				k.put("limit", this.getLimit());
				if (this.lastId > 0)
					k.put("offset", this.lastId + 1);

				JSONObject update = this.updateResponse("getUpdates", k);
				if (update == null) {
					continue;
				}

				JSONArray array = update.optJSONArray("result");
				if (array == null) {
					continue;
				}

				List<Update> list = new ArrayList<Update>();
				for (int i = 0; i < array.length(); i++) {
					Update kkk = Update.create(array.optJSONObject(i));
					if (kkk == null) {
						continue;
					}
					list.add(kkk);
					this.lastId = kkk.getId();
				}
				this.handler.update(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public User getMe() {
		if (this.me == null) {
			this.me = User.create(updateResponse("getMe", null));
		}
		return this.me;
	}

	public int getLimit() {
		return limit;
	}

	public int getTimeout() {
		return this.timeout;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		if (token.length() != 45) {
			return;
		}
		this.me = null;
		this.token = token;
	}

	public void setLimit(int value) {
		this.limit = Math.max(1, Math.min(100, value));
	}

	public void setTimeout(int time) {
		this.timeout = Math.max(time, 500);
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}