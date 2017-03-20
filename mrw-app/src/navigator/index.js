import { StackNavigator } from 'react-navigation';
import FindReaction from '../containers/FindReaction';
import * as constants from '../constants';
import About from '../components/about';
import navigationOptions from './navigationOptions';


export default StackNavigator({
  [constants.ROUTE_FIND_REACTION]: {
    screen: FindReaction,
  },
  [constants.ROUTE_ABOUT]: {
    screen: About,
  },
}, {
  initialRouteName: constants.ROUTE_FIND_REACTION,
  initialRouteParams: {sharing: constants.SHARING_NOT_AVAILABLE},
  navigationOptions,
});
