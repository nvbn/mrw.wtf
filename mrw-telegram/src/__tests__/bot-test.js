import request from 'request';
import MrwWtfBot from '../bot';

describe('Bot', () => {
  it('Send greeting on `/start`', () => {
    const bot = new MrwWtfBot();
    bot.run();
    bot.bot.simulateReceivingText({chat: {id: 0}}, '/start');

    const [chatId, text] = bot.bot.sendMessage.mock.calls[0];
    expect(chatId).toBe(0);
    expect(text).toMatch(/Hi, I'm/);
  });

  it('Send =( smile on `/mrw` when reaction not found', () => {
    const bot = new MrwWtfBot();
    bot.run();

    request.mockImplementation(
      (url, callback) => callback(null, {}, JSON.stringify([])));

    bot.bot.simulateReceivingText({chat: {id: 0}}, '/mrw lost my key');

    const [chatId, text] = bot.bot.sendMessage.mock.calls[0];
    expect(chatId).toBe(0);
    expect(text).toMatch(/=\(/);
  });

  it('Send =( smile on error', () => {
    const bot = new MrwWtfBot();
    bot.run();

    request.mockImplementation(
      (url, callback) => callback(null, {}, JSON.stringify([])));

    bot.bot.simulateReceivingText({chat: {id: 0}}, '/mrw lost my key');

    const [chatId, text] = bot.bot.sendMessage.mock.calls[0];
    expect(chatId).toBe(0);
    expect(text).toMatch(/=\(/);
  });

  it('Send first reaction if available', () => {
    const bot = new MrwWtfBot();
    bot.run();

    const response = '[{"title":"MRW I quit my shitty office job","url":"http://i.imgur.com/lmr0gjK.gif","name":"t3_4dgozu","sentiment":"sadness"}]';
    request.mockImplementation(
      (url, callback) => callback(null, {}, response));

    bot.bot.simulateReceivingText({chat: {id: 0}}, '/mrw lost my key');

    const [chatId, text] = bot.bot.sendVideo.mock.calls[0];
    expect(chatId).toBe(0);
    expect(text).toBe('http://i.imgur.com/lmr0gjK.gif');
  });

  it("Uses `can't find my command` as a query if query not present", () => {
    const bot = new MrwWtfBot();
    bot.run();

    const response = '[{"title":"MRW I quit my shitty office job","url":"http://i.imgur.com/lmr0gjK.gif","name":"t3_4dgozu","sentiment":"sadness"}]';
    request.mockImplementation(
      (url, callback) => callback(null, {}, response));

    bot.bot.simulateReceivingText({chat: {id: 0}}, '/mrw');

    const [chatId, text] = bot.bot.sendVideo.mock.calls[0];
    expect(chatId).toBe(0);
    expect(text).toBe('http://i.imgur.com/lmr0gjK.gif');
  });
});
