import { Image } from 'react-native';
import Share from 'react-native-share';
import RNFetchBlob from 'react-native-fetch-blob';
import * as constants from './constants';
import * as config from '../config';

export const reactionFetched = (reaction) => ({
  type: constants.ACTION_REACTION_FETCHED,
  reaction,
});

export const changeQuery = (query) => ({
  type: constants.ACTION_CHANGE_QUERY,
  query,
});

export const changeState = (state) => ({
  type: constants.ACTION_CHANGE_STATE,
  state,
});

export const fetchReaction = (query) => (dispatch, getState) => {
  dispatch(changeQuery(query));
  if (!query) {
    dispatch(changeState(constants.STATE_EMPTY_QUERY));
    return;
  }

  dispatch(changeState(constants.STATE_SEARCHING));

  setTimeout(async () => {
    if (query !== getState().query) {
      return;
    }

    const response = await fetch(
      `${config.API_URL}${constants.API_REACTION_ENDPOINT}?query=${query}`);
    const [reaction, ..._] = await response.json();

    if (query !== getState().query) {
      return;
    }

    if (!reaction) {
      dispatch(reactionFetched({}));
      dispatch(changeState(constants.STATE_NOT_FOUND));
      return;
    }

    const blob = await RNFetchBlob.fetch('GET', reaction.url);
    const uri = await blob.base64();

    if (query !== getState().query) {
      return;
    }

    dispatch(reactionFetched({
      ...reaction,
      uri: `data:image/gif;base64,${uri}`,
    }));
    dispatch(changeState(constants.STATE_FOUND));
  }, 300);
};

export const startSharingReaction = () => ({
  type: constants.ACTION_START_SHARING_REACTION,
});

export const reactionShared = () => ({
  type: constants.ACTION_REACTION_SHARED,
});

export const shareReaction = ({uri}) => (dispatch) => {
  dispatch(startSharingReaction());
  Share.open({url: uri});
  dispatch(reactionShared());
};
