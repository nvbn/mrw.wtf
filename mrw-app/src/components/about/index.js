import React, { Component } from 'react';
import {
  View,
  Text,
  Linking,
} from 'react-native';
import styles from './styles';

class About extends Component {
  static navigationOptions = {
    title: 'About mrw.wtf',
  };

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.line}>
          <Text style={[styles.link, styles.text]} onPress={() => Linking.openURL('https://mrw.wtf/')}>
            mrw.wtf
          </Text>
          <Text style={styles.text}> is a service for searching reaction gifs.</Text>
        </View>
        <View style={styles.line}>
          <Text style={styles.text}>Powered by </Text>
          <Text style={[styles.link, styles.text]} onPress={() => Linking.openURL('https://www.reddit.com/dev/api/')}>
            reddit
          </Text>
          <Text style={styles.text}> and </Text>
          <Text style={[styles.link, styles.text]} onPress={() => Linking.openURL('https://api.imgur.com/')}>
            imgur
          </Text>
          <Text style={styles.text}> api.</Text>
        </View>
        <View style={styles.line}>
          <Text style={styles.text}>Source code available on </Text>
          <Text style={[styles.link, styles.text]} onPress={() => Linking.openURL('https://github.com/nvbn/mrw.wtf')}>
            github
          </Text>
          <Text style={styles.text}>.</Text>
        </View>
      </View>
    );
  }
}

export default About;
