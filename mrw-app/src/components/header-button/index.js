import React from 'react';
import { View, Platform } from 'react-native';
// TODO: Don't use non-public api
import TouchableItem from 'react-navigation/lib-rn/views/TouchableItem';
import Icon from 'react-native-vector-icons/Ionicons';
import styles from './styles';

const iconPrefix = Platform.OS === 'ios' ? 'ios' : 'md';

export default ({onPress, icon}) => (
  <TouchableItem
    onPress={onPress}
    delayPressIn={0}
    style={styles.centered}
  >
    <View style={styles.centered}>
      <Icon style={styles.icon} name={`${iconPrefix}-${icon}`}/>
    </View>
  </TouchableItem>
);
