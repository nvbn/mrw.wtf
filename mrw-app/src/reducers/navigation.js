import { NavigationActions } from 'react-navigation';
import last from 'lodash/last';
import * as constants from '../constants';
import Navigator from '../navigator';

export default (state, action) => {
  const defaultHandler = () => Navigator.router.getStateForAction(action, state) || state;

  if (!state) {
    return defaultHandler();
  }

  const key = last(state.routes).key;

  const setParams = (params) => Navigator.router.getStateForAction(
    NavigationActions.setParams({
      params,
      key: key,
    }), state,
  );

  switch (action.type) {
    case constants.ACTION_CHANGE_QUERY:
      return setParams({sharing: constants.SHARING_NOT_AVAILABLE});
    case constants.ACTION_CHANGE_STATE:
      if (action.state === constants.STATE_FOUND) {
        return setParams({sharing: constants.SHARING_AVAILABLE});
      } else {
        return defaultHandler();
      }
    case constants.ACTION_START_SHARING_REACTION:
      return setParams({sharing: constants.SHARING_NOT_AVAILABLE});
    case constants.ACTION_REACTION_SHARED:
      return setParams({sharing: constants.SHARING_AVAILABLE});
    default:
      return defaultHandler();
  }
};
