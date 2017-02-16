import { Image } from 'react-native';
import Share from 'react-native-share';
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

export const fetchReaction = (query) => (dispatch) => {
  dispatch(changeQuery(query));

  fetch(`${config.API_URL}${constants.API_REACTION_ENDPOINT}?query=${query}`)
    .then((response) => response.json())
    .then((reactions) => dispatch(reactionFetched(reactions[0] || {})));
};

export const startSharingReaction = () => ({
  type: constants.ACTION_START_SHARING_REACTION,
});

export const reactionShared = () => ({
  type: constants.ACTION_REACTION_SHARED,
});

export const shareReaction = ({url}) => (dispatch) => {
  dispatch(startSharingReaction());

  const xhr = new XMLHttpRequest();
  xhr.onload = () => {
    const reader = new FileReader();
    reader.onloadend = () => {
      Share.open({url: reader.result});
      dispatch(reactionShared());
    };
    reader.readAsDataURL(xhr.response);
  };
  xhr.open('GET', url);
  xhr.responseType = 'blob';
  xhr.send();
};
