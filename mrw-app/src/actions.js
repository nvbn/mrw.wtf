import Share from 'react-native-share';
import RNFetchBlob from 'react-native-fetch-blob';
import * as config from '../config';
import * as constants from './constants';
import { timeout } from './utils';

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

export const fetchReaction = (query) => async(dispatch, getState) => {
  dispatch(changeQuery(query));

  if (!query) {
    dispatch(changeState(constants.STATE_EMPTY_QUERY));
    return;
  }

  dispatch(changeState(constants.STATE_SEARCHING));

  await timeout(300);
  if (query !== getState().reactions.query) {
    return;
  }

  const response = await fetch(
    `${config.API_URL}${constants.API_REACTION_ENDPOINT}?query=${query}`);
  const [reaction, ..._] = await response.json();

  if (query !== getState().reactions.query) {
    return;
  }

  if (!reaction) {
    dispatch(reactionFetched({}));
    dispatch(changeState(constants.STATE_NOT_FOUND));
    return;
  }

  const blob = await RNFetchBlob.config({
    path: `${RNFetchBlob.fs.dirs.PictureDir}/mrw_app/${reaction.name}.gif`
  }).fetch('GET', reaction.url);

  await RNFetchBlob.fs.scanFile([{
    path: blob.path(),
    mime: 'image/gif',
  }]);

  if (query !== getState().reactions.query) {
    return;
  }

  dispatch(reactionFetched({
    ...reaction,
    uri: `file://${blob.path()}`,
  }));
  dispatch(changeState(constants.STATE_FOUND));
};

export const startSharingReaction = () => ({
  type: constants.ACTION_START_SHARING_REACTION,
});

export const reactionShared = () => ({
  type: constants.ACTION_REACTION_SHARED,
});

export const shareReaction = () => (dispatch, getState) => {
  dispatch(startSharingReaction());
  Share.open({
    url: getState().reactions.reaction.uri,
    type: 'image/gif',
  });
  dispatch(reactionShared());
};
