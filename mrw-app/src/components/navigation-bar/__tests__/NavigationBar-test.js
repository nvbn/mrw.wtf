import React from 'react';
import ReactTestRenderer from 'react-test-renderer';
import last from 'lodash/last';
import * as routes from "../../../routes";
import * as constants from '../../../constants';
import { Title, LeftButton, RightButton } from '../';

describe('Title component', () => {
  it('Render current route title', () => {
    const rendered = ReactTestRenderer.create(
      <Title title="MRW"/>
    ).toJSON();

    const title = rendered.children[0];
    expect(title.type).toBe('Text');
    expect(title.children).toEqual(["MRW"]);
  });
});

describe('LeftButton component', () => {
  it('When about page opened', () => {
    jest.mock('TouchableHighlight', () => 'TouchableHighlight');
    const navigator = {
      pop: jest.fn(),
    };

    const rendered = ReactTestRenderer.create(
      LeftButton({type: constants.ROUTE_ABOUT}, navigator)
    ).toJSON();

    expect(rendered.children[0].type).toBe('Text');
    expect(
      last(rendered.children[0].props.style).fontFamily
    ).toBe('Material Icons');

    rendered.props.onPress();
    expect(navigator.pop).toBeCalled();
  });

  it('When other page opened', () => {
    jest.mock('TouchableHighlight', () => 'TouchableHighlight');
    const navigator = {
      push: jest.fn(),
    };

    const rendered = ReactTestRenderer.create(
      LeftButton({type: constants.ROUTE_FIND_REACTION}, navigator)
    ).toJSON();

    expect(rendered.children[0].type).toBe('Text');
    expect(
      last(rendered.children[0].props.style).fontFamily
    ).toBe('Material Icons');

    rendered.props.onPress();
    expect(navigator.push).toBeCalledWith(routes.about);
  })
});

describe('RightButton', () => {
  it('When about page opened', () => {
    const rendered = ReactTestRenderer.create(
      <RightButton
        type={constants.ROUTE_ABOUT}
        navigator={jest.fn()}
        reaction={{}}
        sharing={false}
        state={constants.STATE_EMPTY_QUERY}
        shareReaction={jest.fn()}
      />
    ).toJSON();

    expect(rendered).toBe(null);
  });

  it('While sharing', () => {
    const rendered = ReactTestRenderer.create(
      <RightButton
        type={constants.ROUTE_FIND_REACTION}
        navigator={jest.fn()}
        reaction={{}}
        sharing={true}
        state={constants.STATE_FOUND}
        shareReaction={jest.fn()}
      />
    ).toJSON();

    expect(rendered.children[0].type).toBe('Text');
    expect(
      last(rendered.children[0].props.style).fontFamily
    ).toBe('Material Icons');
  });

  it('When reaction found', () => {
    jest.mock('TouchableHighlight', () => 'TouchableHighlight');
    const shareReaction = jest.fn();

    const rendered = ReactTestRenderer.create(
      <RightButton
        type={constants.ROUTE_FIND_REACTION}
        navigator={jest.fn()}
        reaction={{uri: 'data:image/gif;base64,image-in-base-64'}}
        sharing={false}
        state={constants.STATE_FOUND}
        shareReaction={shareReaction}
      />
    ).toJSON();

    expect(rendered.children[0].type).toBe('Text');
    expect(
      last(rendered.children[0].props.style).fontFamily
    ).toBe('Material Icons');

    rendered.props.onPress();
    expect(shareReaction.mock.calls).toEqual([
      [{uri: 'data:image/gif;base64,image-in-base-64'}],
    ]);
  });

  it('When query is empty', () => {
    const rendered = ReactTestRenderer.create(
      <RightButton
        type={constants.ROUTE_FIND_REACTION}
        navigator={jest.fn()}
        reaction={{}}
        sharing={false}
        state={constants.STATE_EMPTY_QUERY}
        shareReaction={jest.fn()}
      />
    ).toJSON();

    expect(rendered).toBe(null);
  });
});
