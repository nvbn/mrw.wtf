import React from 'react';
import { View } from 'react-native';
// TODO: Don't use non-public api
import TouchableItem from 'react-navigation/lib-rn/views/TouchableItem';
import Icon from 'react-native-vector-icons/MaterialIcons';
import styles from './styles';

export default ({onPress, icon}) => (
  <TouchableItem
    onPress={onPress}
    delayPressIn={0}
    style={styles.centered}
  >
    <View style={styles.centered}>
      <Icon style={styles.icon} name={icon}/>
    </View>
  </TouchableItem>
);
