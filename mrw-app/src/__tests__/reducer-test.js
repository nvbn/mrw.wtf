import reducer from '../reducer';
import * as actions from '../actions';
import * as constants from "../constants";

describe('Reducer', () => {
  it('Have initial value', () => {
    const state = reducer(undefined, {type: 'FAKE_ACTION'});

    expect(state).toEqual({
      reaction: {},
      query: '',
      sharing: false,
      state: constants.STATE_EMPTY_QUERY,
    });
  });

  it('Change query', () => {
    const initialState = {
      reaction: {url: 'test'},
      query: 'I fall asleep',
    };
    Object.freeze(initialState);

    const state = reducer(initialState, actions.changeQuery('forgot my keys'));
    expect(state).toEqual({
      reaction: {},
      query: 'forgot my keys',
    });
  });

  it('Reaction fetched', () => {
    const initialState = {
      state: constants.STATE_SEARCHING,
      reaction: {},
      query: 'looking for my keys',
    };
    Object.freeze(initialState);

    const state = reducer(initialState, actions.reactionFetched(
      {url: 'test'}
    ));
    expect(state).toEqual({
      reaction: {url: 'test'},
      query: 'looking for my keys',
      state: constants.STATE_SEARCHING,
    });
  });

  it('Start sharing reaction', () => {
    const initialState = {
      state: constants.STATE_FOUND,
      reaction: {url: 'test'},
      query: 'looking for my keys',
      sharing: false,
    };
    Object.freeze(initialState);

    const state = reducer(initialState, actions.startSharingReaction());
    expect(state).toEqual({
      state: constants.STATE_FOUND,
      reaction: {url: 'test'},
      query: 'looking for my keys',
      sharing: true,
    });
  });

  it('Reaction shared', () => {
    const initialState = {
      state: constants.STATE_FOUND,
      reaction: {url: 'test'},
      query: 'looking for my keys',
      sharing: true,
    };
    Object.freeze(initialState);

    const state = reducer(initialState, actions.reactionShared());
    expect(state).toEqual({
      state: constants.STATE_FOUND,
      reaction: {url: 'test'},
      query: 'looking for my keys',
      sharing: false,
    });
  });

  it('Change state', () => {
    const initialState = {
      state: constants.STATE_EMPTY_QUERY,
    };
    Object.freeze(initialState);

    const state = reducer(initialState, actions.changeState(constants.STATE_SEARCHING));
    expect(state).toEqual({
      state: constants.STATE_SEARCHING,
    });
  });
});
