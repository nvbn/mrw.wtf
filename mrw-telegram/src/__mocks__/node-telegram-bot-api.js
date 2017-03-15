class TelegramBot {
  constructor() {
    this.handlers = [];
    this.sendVideo = jest.fn();
    this.sendMessage = jest.fn();
  }

  onText(regexp, handler) {
    this.handlers.push([regexp, handler]);
  }

  simulateReceivingText(request, text) {
    for (const [regexp, handler] of this.handlers) {
      if (text.match(regexp)) {
        handler(request, text.match(regexp).slice(1));
        return;
      }
    }
  }
}

export default TelegramBot;
