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
        style={styles.button}
        underlayColor={constants.UNDERLAY_COLOR}
      >
        <View style={styles.centered}>
          <Icon style={styles.icon} name="arrow-back"/>
        </View>
      </TouchableHighlight>
    );
  }
};

export const RightButton = ({type, navigator, reaction, sharing, shareReaction}) => {
  console.log(sharing);
  if (type === constants.ROUTE_FIND_REACTION) {
    if(sharing) {
      return (
        <View style={styles.button}>
          <View style={styles.centered}>
            <Icon style={styles.icon} name="watch-later"/>
          </View>
        </View>
      );
    } else if (reaction && reaction.url) {
      return (
        <TouchableHighlight
          onPress={() => shareReaction(reaction)}
          style={styles.button}
          underlayColor={constants.UNDERLAY_COLOR}
        >
          <View style={styles.centered}>
            <Icon style={styles.icon} name="share"/>
          </View>
        </TouchableHighlight>
      );
    } else {
      return (
        <TouchableHighlight
          onPress={() => navigator.push(routes.about)}
          style={styles.button}
          underlayColor={constants.UNDERLAY_COLOR}
        >
          <View style={styles.centered}>
            <Icon style={styles.icon} name="info"/>
          </View>
        </TouchableHighlight>
      );
    }
  }

  return (<View />);
};

export default (routeMapper) => (
  <Navigator.NavigationBar routeMapper={routeMapper}
                           style={styles.bar}/>
);

