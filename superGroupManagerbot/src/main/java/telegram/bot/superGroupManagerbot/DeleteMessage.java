package telegram.bot.superGroupManagerbot;

import milk.telegram.bot.TelegramBot;
import milk.telegram.method.editor.Editor;
import milk.telegram.type.Identifier;
import milk.telegram.type.Usernamed;
import milk.telegram.type.chat.Channel;
import milk.telegram.type.message.Message;
import milk.telegram.type.message.TextMessage;
import milk.telegram.type.reply.ReplyMarkup;

import org.json.JSONObject;

public class DeleteMessage extends Editor {

	protected String chat_id;
	// protected String inline_id;

	protected long message_id = -1;

	// protected String text;
	// protected String parse_mode = null;
	//
	protected JSONObject reply_markup = null;

	// private boolean disable_web_page_preview = false;

	public DeleteMessage(TelegramBot bot) {
		super(bot);
	}

	// public String getText(){
	// return text;
	// }
	//
	public String getChatId() {
		return chat_id;
	}

	public long getMessageId() {
		return message_id;
	}

	// public String getInlineId(){
	// return inline_id;
	// }

	// public String getParseMode(){
	// return parse_mode;
	// }

	public JSONObject getReplyMarkup() {
		return reply_markup;
	}

	// public boolean isDisableWebPagePreview(){
	// return disable_web_page_preview;
	// }

	public DeleteMessage setChatId(Object chat_id) {
		if (chat_id instanceof Identifier) {
			chat_id = chat_id instanceof Channel ? "@"
					+ ((Usernamed) chat_id).getUsername()
					: ((Identifier) chat_id).getId();
		}

		if (chat_id instanceof String) {
			this.chat_id = (String) chat_id;
		} else if (chat_id instanceof Number) {
			this.chat_id = ((Number) chat_id).longValue() + "";
		}
		return this;
	}

	public DeleteMessage setMessageId(Object message_id) {
		if (message_id instanceof Message) {
			this.message_id = ((Message) message_id).getId();
			this.chat_id = ((Message) message_id).getChat().getId() + "";
		} else if (message_id instanceof Number) {
			this.message_id = ((Number) message_id).longValue();
		}
		return this;
	}

	// public DeleteMessage setReplyMarkup(ReplyMarkup reply_markup){
	// this.reply_markup = reply_markup;
	// return this;
	// }
	//
	// public DeleteMessage setText(String text){
	// this.text = text;
	// return this;
	// }
	//
	// public DeleteMessage setInlineId(String inline_id){
	// this.inline_id = inline_id;
	// return this;
	// }
	//
	// public DeleteMessage setParseMode(String parse_mode){
	// this.parse_mode = parse_mode;
	// return this;
	// }
	//
	// public DeleteMessage setDisableWebPagePreview(boolean value){
	// this.disable_web_page_preview = value;
	// return this;
	// }

	public Boolean send() {
		JSONObject object = new JSONObject();
		// object.put("text", text);
		// if(inline_id != null){
		// object.put("inline_message_id", inline_id);
		// }else{
		object.put("chat_id", chat_id);
		object.put("message_id", message_id);
		// }
		// object.put("disable_web_page_preview", disable_web_page_preview);

		// if(parse_mode != null) object.put("parse_mode", parse_mode);

		JSONObject updateResponse = bot.updateResponse("deleteMessage", object);

		if (updateResponse != null && updateResponse.has("\"result\":true")) {
			return true;
		} else {
			return false;
		}

	}
}
