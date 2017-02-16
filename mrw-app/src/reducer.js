import * as constants from './constants';

const initialState = {
  reaction: undefined,
  query: '',
  sharing: false,
};

export default (previousState = initialState, action) => {
  switch (action.type) {
    case constants.ACTION_CHANGE_QUERY:
      return {
        ...previousState,
        query: action.query,
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
    default:
      return previousState;
  }
};
