package telegram.bot.superGroupManagerbot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class GroupManagement {
	private boolean goupState = true;
	private boolean link = true;
	private boolean telLink = true;
	private boolean wellcome = true;
	private boolean warning = true;
	private boolean file = true;
	private boolean sticker = true;
	private boolean image = true;
	private boolean film = true;
	private boolean update = true;

	public void changeGroupState() {

		if (isGoupState()) {
			goupState = false;
		} else {
			goupState = true;
		}
	}

	public void changeLink() {
		if (isLink()) {
			link = false;
		} else {
			link = true;
		}
	}

	public void changeTelLink() {
		if (isTelLink()) {
			telLink = false;
		} else {
			telLink = true;
		}
	}


	public void changeWellcome() {
		if (isWellcome()) {
			wellcome = false;
		} else {
			wellcome = true;
		}
	}

	public void changeWarnning() {
		if (isWarning()) {
			warning = false;
		} else {
			warning = true;
		}
	}

	public void changeFile() {
		if (isFile()) {
			file = false;
		} else {
			file = true;
		}
	}

	public void changeSticker() {
		if (isSticker()) {
			sticker = false;
		} else {
			sticker = true;
		}
	}

	public void changeImage() {
		if (isImage()) {
			image = false;
		} else {
			image = true;
		}
	}

	public void changeFilm() {
		if (isFilm()) {
			film = false;
		} else {
			film = true;
		}
	}

	public void changeUpdate() {
		if (isUpdate()) {
			film = false;
		} else {
			film = true;
		}
	}

	public boolean isGoupState() {
		return goupState;
	}

	public boolean isLink() {
		return link;
	}


	public boolean isTelLink() {
		return telLink;
	}

	public boolean isWellcome() {
		return wellcome;
	}

	public boolean isWarning() {
		return warning;
	}

	public boolean isFile() {
		return file;
	}

	public boolean isSticker() {
		return sticker;
	}

	public boolean isImage() {
		return image;
	}

	public boolean isFilm() {
		return film;
	}

	private boolean isUpdate() {
		return update;
	}

	public List<List<InlineKeyboardButton>> allRowInlineList() {
		List<List<InlineKeyboardButton>> rowInlineList = new ArrayList<>();

		List<InlineKeyboardButton> rowInline = new ArrayList<>();
		rowInline.add(new InlineKeyboardButton().setText("کلمات ممنوعه")
				.setCallbackData("badWord"));

		rowInlineList.add(rowInline);

		rowInline = new ArrayList<>();
		rowInline.add(new InlineKeyboardButton().setText("حالت گروه")
				.setCallbackData("goupState"));
		rowInlineList.add(rowInline);

		rowInline = new ArrayList<>();
		rowInline.add(new InlineKeyboardButton().setText("قفل لینک")
				.setCallbackData("link"));
		rowInlineList.add(rowInline);

		if (isLink()) {
			rowInline = new ArrayList<>();
			
			String text = "";
			if (isTelLink()) {
				text="همه لینک ها";
				
			}else {
				
				text="لینک های تلگرام";
			}
			
			rowInline.add(new InlineKeyboardButton().setText(text)
					.setCallbackData("telLink"));
			rowInlineList.add(rowInline);
		}

		rowInline = new ArrayList<>();
		rowInline.add(new InlineKeyboardButton().setText("خوش آمد گویی")
				.setCallbackData("wellcome"));
		rowInlineList.add(rowInline);

		rowInline = new ArrayList<>();
		rowInline.add(new InlineKeyboardButton().setText("پیام اخطار")
				.setCallbackData("warning"));
		rowInlineList.add(rowInline);

		rowInline = new ArrayList<>();
		rowInline.add(new InlineKeyboardButton().setText("فایل")
				.setCallbackData("file"));
		rowInline.add(new InlineKeyboardButton().setText("استیکر")
				.setCallbackData("sticker"));
		rowInline.add(new InlineKeyboardButton().setText("عکس")
				.setCallbackData("image"));
		rowInline.add(new InlineKeyboardButton().setText("فیلم")
				.setCallbackData("film"));

		rowInlineList.add(rowInline);

		rowInline = new ArrayList<>();
		rowInline.add(new InlineKeyboardButton().setText(
				"آپدیت اطلاعت و ادمین ها").setCallbackData("update"));
		rowInlineList.add(rowInline);

		return rowInlineList;
	}

	public String getMessage() {
		String res = "==========================================";
		
		res+="\n";
		res += "گروه:";
		if (isGoupState()) {
			res += "روشن";
		} else {
			res += "خاموش";

		}
		res += "\n";
		res += "قفل لینک:";
		if (isLink()) {
			res += "روشن";
			if (isTelLink()) {
				res+="[لینک های تلگرام]";
			}else {
				res+="[همه لینک ها]";
			}

		} else {
			res += "خاموش";

		}

		res += "\n";
		res += "خوش آمد گویی:";
		if (isWellcome()) {
			res += "روشن";
		} else {
			res += "خاموش";

		}
		res += "\n";
		res += "پیام اخطار:";
		if (isWarning()) {
			res += "روشن";
		} else {
			res += "خاموش";

		}
		res += "\n";
		res += "فیلم:";
		if (isFilm()) {
			res += "روشن";
		} else {
			res += "خاموش";

		}
		res += "\n";
		res += "عکس:";
		if (isImage()) {
			res += "روشن";
		} else {
			res += "خاموش";

		}
		res += "\n";
		res += "استیکر:";
		if (isSticker()) {
			res += "روشن";
		} else {
			res += "خاموش";

		}
		res += "\n";
		res += "فایل:";
		if (isFile()) {
			res += "روشن";
		} else {
			res += "خاموش";

		}

		return res;
	}

	public void checkCallbackQuery(Update update) {
		// TODO Auto-generated method stub
		if (update.hasCallbackQuery()) {
			// Set variables
			CallbackQuery callbackQuery = update.getCallbackQuery();

			String call_data = callbackQuery.getData();

			if (call_data.equals("badWord")) {
				System.out.println("badWord");
			} else if (call_data.equals("goupState")) {
				System.out.println("goupState");
				changeGroupState();
			} else if (call_data.equals("link")) {
				System.out.println("link");
				changeLink();
			} else if (call_data.equals("telLink")) {
				System.out.println("telLink");
				changeTelLink();
			} else if (call_data.equals("wellcome")) {
				System.out.println("wellcome");
				changeWellcome();
			} else if (call_data.equals("warning")) {
				System.out.println("warning");
				changeWarnning();
			} else if (call_data.equals("file")) {
				System.out.println("file");
				changeFile();
			} else if (call_data.equals("sticker")) {
				System.out.println("sticker");
				changeSticker();
			} else if (call_data.equals("image")) {
				System.out.println("image");
				changeImage();
			} else if (call_data.equals("film")) {
				System.out.println("film");
				changeFilm();
			} else if (call_data.equals("update")) {
				System.out.println("11");
			}
		}
	}
}
