export default {
  async fetch(method, url) {
    return {
      async base64() {
        return 'image-in-base-64';
      }
    }
  }
};
