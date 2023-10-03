package at.htlleonding.chatbot_ai.screens;

public class Message {
    private String message;
    private boolean isMine;

    private int messageHistoryId;

    public Message(String message, boolean isMine, int messageHistoryId) {
        this.message = message;
        this.isMine = isMine;
        this.messageHistoryId = messageHistoryId;
    }

    public String getMessage() {
        return message;
    }

    public boolean isMine() {
        return isMine;
    }

    public int getMessageHistoryId() {
        return messageHistoryId;
    }


}

