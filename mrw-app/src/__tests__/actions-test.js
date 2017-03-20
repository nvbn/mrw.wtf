import Share from 'react-native-share';
import * as actions from '../actions';
import * as constants from '../constants';

describe('reactionFetched', () => {
  it('Create action', () => {
    const action = actions.reactionFetched({
      url: 'test',
    });

    expect(action).toEqual({
      type: constants.ACTION_REACTION_FETCHED,
      reaction: {url: 'test'},
    });
  });
});

describe('changeQuery', () => {
  it('Create action', () => {
    const action = actions.changeQuery("I can't fall");

    expect(action).toEqual({
      type: constants.ACTION_CHANGE_QUERY,
      query: "I can't fall",
    });
  });
});

describe('changeState', () => {
  it('Create action', () => {
    const action = actions.changeState(constants.STATE_SEARCHING);

    expect(action).toEqual({
      type: constants.ACTION_CHANGE_STATE,
      state: constants.STATE_SEARCHING,
    });
  });
});

describe('fetchReaction', () => {
  it('When image found', async() => {
    jest.useFakeTimers();
    const dispatch = jest.fn();

    global.fetch = async(url) => ({
      async json() {
        return [{
          "title": "MRW I install some new software but I can't find it anywhere on my computer [OC]",
          "url": "http://i.imgur.com/cufIziI.gif",
          "name": "t3_2qux51",
          "sentiment": "boredom"
        }];
      }
    });

    const action = actions.fetchReaction("I can't fall")(
      dispatch, () => ({reactions: {query: "I can't fall"}}));
    jest.runAllTimers();
    await action;

    expect(dispatch.mock.calls).toEqual([
      [actions.changeQuery("I can't fall")],
      [actions.changeState(constants.STATE_SEARCHING)],
      [actions.reactionFetched({
        "name": "t3_2qux51",
        "sentiment": "boredom",
        "title": "MRW I install some new software but I can\'t find it anywhere on my computer [OC]",
        "uri": "file:///image.gif",
        "url": "http://i.imgur.com/cufIziI.gif",
      })],
      [actions.changeState(constants.STATE_FOUND)]]);
  });

  it('When image not found', async() => {
    jest.useFakeTimers();
    const dispatch = jest.fn();

    global.fetch = async(url) => ({
      async json() {
        return [];
      }
    });

    const action = actions.fetchReaction("I can't fall")(
      dispatch, () => ({reactions: {query: "I can't fall"}}));
    jest.runAllTimers();
    await action;

    expect(dispatch.mock.calls).toEqual([
      [actions.changeQuery("I can't fall")],
      [actions.changeState(constants.STATE_SEARCHING)],
      [actions.reactionFetched({})],
      [actions.changeState(constants.STATE_NOT_FOUND)]
    ]);
  });

  it('When query changed', async() => {
    jest.useFakeTimers();
    const dispatch = jest.fn();

    global.fetch = async(url) => ({
      async json() {
        return [];
      }
    });

    const action = actions.fetchReaction("I can't fall")(
      dispatch, () => ({reactions: {query: "I'm too old"}}));
    jest.runAllTimers();
    await action;

    expect(dispatch.mock.calls).toEqual([
      [actions.changeQuery("I can't fall")],
      [actions.changeState(constants.STATE_SEARCHING)],
    ]);
  });

  it('When query is empty', async() => {
    jest.useFakeTimers();
    const dispatch = jest.fn();

    global.fetch = async(url) => ({
      async json() {
        return [];
      }
    });

    const action = actions.fetchReaction("")(
      dispatch, () => ({reactions: {query: ""}}));
    jest.runAllTimers();
    await action;

    expect(dispatch.mock.calls).toEqual([
      [actions.changeQuery("")],
      [actions.changeState(constants.STATE_EMPTY_QUERY)],
    ]);
  });
});

describe('startSharingReaction', () => {
  it('Create action', () => {
    const action = actions.startSharingReaction();

    expect(action).toEqual({
      type: constants.ACTION_START_SHARING_REACTION,
    });
  });
});

describe('reactionShared', () => {
  it('Create action', () => {
    const action = actions.reactionShared();

    expect(action).toEqual({
      type: constants.ACTION_REACTION_SHARED,
    });
  });
});

describe('shareReaction', () => {
  it('Share reaction', () => {
    const dispatch = jest.fn();
    actions.shareReaction()(dispatch, () => (
      {reactions: {reaction: {uri: "data:image/gif;base64,image-in-base-64"}}}
    ));

    expect(dispatch.mock.calls).toEqual([
      [actions.startSharingReaction()],
      [actions.reactionShared()],
    ]);

    expect(Share.open.mock.calls).toEqual([
      [{
        url: "data:image/gif;base64,image-in-base-64",
        type: "image/gif",
      }],
    ]);
  });
});
