import * as constants from '../constants';

const initialState = {
  reaction: {},
  query: '',
  sharing: false,
  state: constants.STATE_EMPTY_QUERY,
};

export default (previousState = initialState, action) => {
  switch (action.type) {
    case constants.ACTION_CHANGE_QUERY:
      return {
        ...previousState,
        query: action.query,
        reaction: {},
      };
    case constants.ACTION_REACTION_FETCHED:
      return {
        ...previousState,
        reaction: action.reaction,
      };
    case constants.ACTION_START_SHARING_REACTION:
      return {
        ...previousState,
        sharing: true,
      };
    case constants.ACTION_REACTION_SHARED:
      return {
        ...previousState,
        sharing: false,
      };
    case constants.ACTION_CHANGE_STATE:
      return {
        ...previousState,
        state: action.state,
      };
    default:
      return previousState;
  }
};
