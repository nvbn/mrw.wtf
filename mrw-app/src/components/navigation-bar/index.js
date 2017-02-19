import  React from 'react';
import {
  Text,
  View,
  TouchableHighlight,
  Navigator,
} from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';
import * as constants from '../../constants';
import * as routes from '../../routes';
import styles from './styles';

export const Title = ({title}) => (
  <View style={styles.centered}>
    <Text style={styles.titleText}>{title}</Text>
  </View>
);

export const LeftButton = ({type}, navigator) => {
  if (type === constants.ROUTE_ABOUT) {
    return (
      <TouchableHighlight
        onPress={() => navigator.pop()}
        style={styles.centered}
        underlayColor={constants.UNDERLAY_COLOR}
      >
        <Icon style={styles.icon} name="arrow-back"/>
      </TouchableHighlight>
    );
  } else {
    return (
      <TouchableHighlight
        onPress={() => navigator.push(routes.about)}
        style={styles.centered}
        underlayColor={constants.UNDERLAY_COLOR}
      >
        <Icon style={styles.icon} name="info"/>
      </TouchableHighlight>
    );
  }
};

export const RightButton = ({type, reaction, sharing, state, shareReaction}) => {
  if (type === constants.ROUTE_FIND_REACTION) {
    if (sharing) {
      return (
        <View style={styles.centered}>
          <Icon style={styles.icon} name="watch-later"/>
        </View>
      );
    } else if (state === constants.STATE_FOUND) {
      return (
        <TouchableHighlight
          onPress={() => shareReaction(reaction)}
          style={styles.centered}
          underlayColor={constants.UNDERLAY_COLOR}
        >
          <Icon style={styles.icon} name="share"/>
        </TouchableHighlight>
      );
    }
  }

  return null;
};

export default (routeMapper) => (
  <Navigator.NavigationBar routeMapper={routeMapper}
                           style={styles.bar}/>
);

