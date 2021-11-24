package reactive.extension.basement.request;

public final class RequestMessageDefinition {

    public static class ProduceValues {
        private String messageId;

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }
    }
}
