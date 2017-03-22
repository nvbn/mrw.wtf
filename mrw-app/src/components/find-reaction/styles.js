import { StyleSheet, Platform } from 'react-native';

export default StyleSheet.create({
  container: {
    flex: 1,
    padding: 5,
    backgroundColor: '#ffffff',
    ...Platform.select({
      ios: {
        paddingTop: 20,
      },
    }),
  },
  reaction: Platform.select({
    ios: {
      marginTop: 20,
    },
    android: {
      marginTop: 5,
    },
  }),
});
