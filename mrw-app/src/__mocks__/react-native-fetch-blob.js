export default {
  config(...args) {
    return this;
  },

  async fetch(method, url) {
    return {
      path() {
        return '/image.gif';
      }
    }
  },

  fs: {
    async scanFile(...args) {
    },

    dirs: {
      PictureDir: '/images',
    },
  }
};
