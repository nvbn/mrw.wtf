import request from 'request';
import TelegramBot from 'node-telegram-bot-api';
import * as conf from './conf';

class MrwWtfBot {
  constructor() {
    this.bot = new TelegramBot(conf.TELEGRAM_TOKEN, {polling: true});
  }

  getReactions(query, callback) {
    request(`${conf.MRW_WTF_API}/api/v1/search/?query=${query}`, (error, response, body) => {
      if (error) {
        console.error("Can't fetch reaction", error);
        callback([]);
      } else {
        callback(JSON.parse(body));
      }
    });
  }

  onRequest(chat, query) {
    this.getReactions(query, (reactions) => {
      if (!reactions.length) {
        this.bot.sendMessage(chat.id, '=(');
      } else {
        this.bot.sendVideo(chat.id, reactions[0].url);
      }
    });
  }

  sendGreeting(chat) {
    this.bot.sendMessage(
      chat.id, "Hi, I'm https://mrw.wtf/ bot!\nUse me like:\n/mrw I found a new nice bot");
  }

  sendHelp(chat) {
    this.bot.sendMessage(
      chat.id, "I'm https://mrw.wtf/ bot!\nUse me like:\n/mrw I found a new nice bot");
  }

  run() {
    this.bot.onText(
      /^\/mrw (.*)$/i, ({chat}, [query]) => this.onRequest(chat, query));
    this.bot.onText(
      /^\/mrw$/i, ({chat}) => this.onRequest(chat, "can't find my command"));
    this.bot.onText(
      /^\/start$/i, ({chat}) => this.sendGreeting(chat));
    this.bot.onText(
      /^\/help$/i, ({chat}) => this.sendHelp(chat));
  }
}

export default MrwWtfBot;
