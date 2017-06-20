package telegram.bot.superGroupManagerbot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import milk.telegram.bot.TelegramBot;
import milk.telegram.method.getter.ChatAdministratorsGetter;
import milk.telegram.method.getter.UserProfilePhotosGetter;
import milk.telegram.type.file.photo.UserProfilePhotos;
import milk.telegram.type.message.TextMessage;
import milk.telegram.type.user.ChatMember;

import org.telegram.telegrambots.api.interfaces.BotApiObject;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.MessageEntity;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class MyTelegramBot extends TelegramLongPollingBot {
	int updateCounter = 0;

	TelegramBot bot = new TelegramBot(getBotToken());
	ArrayList<String> badWord = new ArrayList<String>();
	ArrayList<ChatMember> admin = new ArrayList<ChatMember>();
	GroupManagement groupManagement = new GroupManagement();

	public MyTelegramBot() {

	}

	public void onUpdateReceived(Update update) {

//		 superGroup(update);
		// lesson6(update);
		
//		System.out.println(update.toString());
//		List<MessageEntity> entities = update.getChannelPost().getEntities();
//		for (MessageEntity messageEntity : entities) {
//			System.out.println(messageEntity.toString());
////			messageEntity.
//		}

		example(update);
	}

	private void example(Update update) {
		// TODO Auto-generated method stub

		if (update.hasMessage()) {
			
			Message message = update.getMessage();
			Long chat_id = message.getChatId();

			if (updateCounter < 1) {
				Long chatId = message.getChatId();
				updateAdmin(chatId);
				updateCounter++;
			}

			if (message.hasText()) {
				String text = message.getText();

				if (text.equals("مدیریت گروه")) {
					Integer id = message.getFrom().getId();
					for (ChatMember chatMember : admin) {
						Integer id2 = chatMember.getUser().getId();
						if ((id2 + "").equals(id + "")) {

							SendMessage msg = new SendMessage().setChatId(
									chat_id).setText(
									groupManagement.getMessage());

							InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

							markupInline.setKeyboard(groupManagement
									.allRowInlineList());
							msg.setReplyMarkup(markupInline);
							try {
								sendMessage(msg);
							} catch (TelegramApiException e) {
								e.printStackTrace();
							}

						}

					}
				}

			}

			if (groupManagement.isWellcome()) {

				List<User> newChatMembers = update.getMessage()
						.getNewChatMembers();
				User leftChatMember = update.getMessage().getLeftChatMember();
				String text1 = "";
				if (newChatMembers != null) {

					for (Iterator iterator = newChatMembers.iterator(); iterator
							.hasNext();) {
						User user = (User) iterator.next();
						text1 += "your wellcome " + user.getFirstName();
						text1 += user.getLastName() != null ? user
								.getLastName() : "";
					}
				}
				if (leftChatMember != null) {

					text1 += "goodbye " + leftChatMember.getFirstName();

					text1 += leftChatMember.getLastName() != null ? leftChatMember
							.getLastName() : "";

				}
				SendMessage msg = new SendMessage().setChatId(chat_id)
						.setText(text1)
						.setReplyToMessageId(message.getMessageId());

				try {
					sendMessage(msg);
				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} else if (update.hasCallbackQuery()) {
			// Set variables

			groupManagement.checkCallbackQuery(update);
			CallbackQuery callbackQuery = update.getCallbackQuery();

			String call_data = callbackQuery.getData();
			long message_id = callbackQuery.getMessage().getMessageId();
			long chat_id = callbackQuery.getMessage().getChatId();

			InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

			markupInline.setKeyboard(groupManagement.allRowInlineList());

			String answer = groupManagement.getMessage();
			EditMessageText new_message = new EditMessageText()
					.setChatId(chat_id).setMessageId((int) message_id)
					.setText(answer).setReplyMarkup(markupInline);
			try {

				editMessageText(new_message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}

	}

	private void superGroup(Update update) {
		// TODO Auto-generated method stub
		if (update.hasMessage()) {
			Message message = update.getMessage();

			if (updateCounter < 1) {
				System.out.println("  2222222222  ");
				Long chatId = message.getChatId();
				updateAdmin(chatId);
				updateCounter++;
			}

			if (message.hasText()) {
				String text = message.getText();
				User from = message.getFrom();
				Integer id = from.getId();

				for (ChatMember chatMember : admin) {
					Integer id2 = chatMember.getUser().getId();

					if ((id + "").equals(id2 + "")) {
						if (text.startsWith(">AddBadWord ")) {
							String substring = text.substring(12);
							badWord.add(substring);
							System.out.println(substring);
						}
						if (text.startsWith(">updateAdmin")) {
							Long chatId = message.getChatId();
							updateAdmin(chatId);
						}
						if (text.startsWith(">AdminList")) {
							String ad = "";
							for (ChatMember chatMember2 : admin) {
								milk.telegram.type.user.User user = chatMember2
										.getUser();
								ad += user.getFullName() + " " + user.getId()
										+ "\n";
							}

							SendMessage sendMessage = new SendMessage();
							sendMessage.setChatId(message.getChatId());
							sendMessage.setReplyToMessageId(message
									.getMessageId());
							sendMessage.setText(ad);
							try {
								sendMessage(sendMessage);
							} catch (TelegramApiException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}

					for (String string : badWord) {
						if (text.contains(string)) {
							DeleteMessage deleteMessage = new DeleteMessage(bot);

							deleteMessage.setChatId(message.getChatId());
							deleteMessage.setMessageId(message.getMessageId());

							deleteMessage.send();

						}
					}
				}

			}

		}

	}

	private void updateAdmin(Comparable<Long> chatId) {
		// TODO Auto-generated method stub
		ChatAdministratorsGetter administratorsGetter = new ChatAdministratorsGetter(
				bot);

		administratorsGetter.setChatId(chatId);
		ArrayList<ChatMember> send = administratorsGetter.send();
		for (ChatMember chatMember : send) {
			admin.add(chatMember);

		}

	}

	private void getUserProfilePhotos(Update update) {

		if (update.hasMessage()) {
			Message message = update.getMessage();
			try {
				if (message.getForwardFromChat().isChannelChat()) {
					SendMessage sendMessage = new SendMessage();
					String str = "(فقط فرد , نه  کانالی یا  هر چیز دیگر ) ";

					sendMessage.setChatId(message.getChatId());

					sendMessage.setReplyToMessageId(message.getMessageId());
					sendMessage.setText(str);

					try {
						sendMessage(sendMessage);
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}

				}

			} catch (Exception e) {

			}
			if (message.getText().equals("/start")) {
				SendMessage sendMessage = new SendMessage();
				String str = "در پاسخ به افرادی (فقط فرد , نه  کانالی یا  هر چیز دیگر ) که به ربات فوروارد میکننید /prof را بنویسید";

				sendMessage.setChatId(message.getChatId());

				sendMessage.setReplyToMessageId(message.getMessageId());
				sendMessage.setText(str);

				try {
					sendMessage(sendMessage);
				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (message.isReply()) {

				Message replyToMessage = message.getReplyToMessage();
				User forwardFrom = replyToMessage.getForwardFrom();

				if (forwardFrom != null) {
					if (message.getText().equals("/prof")) {
						try {

							TelegramBot telegramBot = new TelegramBot(
									BotConf.getToken());

							UserProfilePhotosGetter photosGetter = new UserProfilePhotosGetter(
									telegramBot).setUserId(forwardFrom.getId());

							UserProfilePhotos send = photosGetter.send();

							ArrayList<ArrayList<milk.telegram.type.file.photo.PhotoSize>> photos = send
									.getPhotos();

							for (int i = 0; i < photos.size(); i++) {

								milk.telegram.type.file.photo.PhotoSize orElse = photos
										.get(i).stream().sequential()
										.findFirst().orElse(null);

								String id = orElse.getId();

								int height = orElse.getHeight();
								int width = orElse.getWidth();
								Integer size = orElse.getSize();

								SendPhoto msg = new SendPhoto()
										.setChatId(
												update.getMessage().getChatId())
										.setPhoto(id)
										.setCaption(
												"size: " + size
														+ " byte\nwidth: "
														+ width + "\nheight: "
														+ height);

								try {
									sendPhoto(msg); // Call method to send
													// the
													// photo
								} catch (TelegramApiException e) {
									e.printStackTrace();
								}

							}

						} catch (Exception e) {
						}

					}
				}
			}
		}
	}

	public String getBotUsername() {
		// TODO Auto-generated method stub
		return BotConf.getUserName();
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return BotConf.getToken();
	}

	private void lesson6(Update update) {
		// TODO Auto-generated method stub
		// We check if the update has a message and the message has text
		if (update.hasMessage() && update.getMessage().hasText()) {
			String message_text = update.getMessage().getText();
			long chat_id = update.getMessage().getChatId();
			if (update.getMessage().getText().equals("/start")) {

				SendMessage message = new SendMessage() // Create a message
														// object object
						.setChatId(chat_id).setText("You send /start");

				InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

				List<List<InlineKeyboardButton>> rowInlineList = new ArrayList<>();
				List<InlineKeyboardButton> rowInline = new ArrayList<>();

				rowInline.add(new InlineKeyboardButton().setText(
						"Update message text").setCallbackData(
						"update_msg_text"));
				// Set the keyboard to the markup
				rowInlineList.add(rowInline);
				// Add it to the message
				markupInline.setKeyboard(rowInlineList);
				message.setReplyMarkup(markupInline).enableNotification();
				try {
					sendMessage(message); // Sending our message object to user
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else {

			}

		} else if (update.hasCallbackQuery()) {
			// Set variables
			CallbackQuery callbackQuery = update.getCallbackQuery();

			String call_data = callbackQuery.getData();
			long message_id = callbackQuery.getMessage().getMessageId();
			long chat_id = callbackQuery.getMessage().getChatId();

			if (call_data.equals("update_msg_text")) {
				String answer = "Updated message text";
				EditMessageText new_message = new EditMessageText()
						.setChatId(chat_id).setMessageId((int) message_id)
						.setText(answer);
				try {

					editMessageText(new_message);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void lesson2(Update update) {
		// TODO Auto-generated method stub
		// We check if the update has a message and the message has text
		if (update.hasMessage()) {
			Message message = update.getMessage();
			long chat_id = message.getChatId();

			if (message.hasText()) {
				// Set variables
				String message_text = message.getText();
				if (message_text.equals("/start")) {
					SendMessage msg = new SendMessage() // Create a message
							// object object
							.setChatId(chat_id).setText(message_text);
					try {
						sendMessage(msg); // Sending our message object to
						// user
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				} else if (message_text.equals("/pic")) {
					SendPhoto msg = new SendPhoto()
							.setChatId(chat_id)
							.setPhoto(
									"AgADAgAD6qcxGwnPsUgOp7-MvnQ8GecvSw0ABGvTl7ObQNPNX7UEAAEC")
							.setCaption("Photo");
					try {
						sendPhoto(msg); // Call method to send the photo
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				} else if (message_text.equals("/markup")) {
					SendMessage msg = new SendMessage()
							// Create a message
							// object object
							.setChatId(chat_id)
							.setText("Here is your keyboard");
					// Create ReplyKeyboardMarkup object
					ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
					// Create the keyboard (list of keyboard rows)
					List<KeyboardRow> keyboard = new ArrayList<>();
					// Create a keyboard row
					KeyboardRow row = new KeyboardRow();
					// Set each button, you can also use KeyboardButton objects
					// if
					// you need something else than text
					row.add("Row 1 Button 1");
					row.add("Row 1 Button 2");
					row.add("Row 1 Button 3");
					// Add the first row to the keyboard
					keyboard.add(row);
					// Create another keyboard row
					row = new KeyboardRow();
					// Set each button for the second line
					row.add("Row 2 Button 1");
					row.add("Row 2 Button 2");
					row.add("Row 2 Button 3");
					// Add the second row to the keyboard
					keyboard.add(row);
					// Set the keyboard to the markup
					keyboardMarkup.setKeyboard(keyboard);
					// Add it to the message
					msg.setReplyMarkup(keyboardMarkup);

					try {
						sendMessage(msg); // Sending our message object to
						// user

					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				} else if (message_text.equals("Row 1 Button 1")) {
					SendPhoto msg = new SendPhoto()
							.setChatId(chat_id)
							.setPhoto(
									"AgADAgAD6qcxGwnPsUgOp7-MvnQ8GecvSw0ABGvTl7ObQNPNX7UEAAEC")
							.setCaption("Photo");

					try {
						sendPhoto(msg); // Call method to send the photo
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				} else if (message_text.equals("/hide")) {
					SendMessage msg = new SendMessage().setChatId(chat_id)
							.setText("Keyboard hidden");
					ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
					msg.setReplyMarkup(keyboardMarkup);
					try {
						sendMessage(msg); // Call method to send the photo
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				} else {
					SendMessage msg = new SendMessage() // Create a message
							// object object
							.setChatId(chat_id).setText("Unknown command");
					try {
						sendMessage(msg); // Sending our message object to
						// user
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
				}
			} else if (message.hasPhoto()) {
				// Message contains photo
				// Set variables

				List<PhotoSize> photos = message.getPhoto();
				PhotoSize orElse = photos
						.stream()
						.sorted(Comparator.comparing(PhotoSize::getFileSize)
								.reversed()).findFirst().orElse(null);

				String f_id = orElse.getFileId();

				int f_width = orElse.getWidth();
				int f_height = orElse.getHeight();

				String caption = "file_id: " + f_id + "\nwidth: "
						+ Integer.toString(f_width) + "\nheight: "
						+ Integer.toString(f_height);
				SendPhoto msg = new SendPhoto().setChatId(chat_id)
						.setPhoto(f_id).setCaption(caption);
				try {
					sendPhoto(msg); // Call method to send the message
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
