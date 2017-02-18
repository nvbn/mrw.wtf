import React from 'react';
import ReactTestRenderer from 'react-test-renderer';
import { Image } from 'react-native';
import * as constants from '../../../constants';
import FindReaction from '../';

describe('FindReaction component', () => {
  it('When query is empty', () => {
    const rendered = ReactTestRenderer.create(
      <FindReaction
        query=""
        reaction={{}}
        fetchReaction={jest.fn()}
        sharing={false}
        state={constants.STATE_EMPTY_QUERY}
      />
    ).toJSON();

    const text = rendered.children[1].children[0];
    expect(text.type).toBe('Text');
    expect(text.children).toEqual(["Start searching reactions!"]);
  });

  it('When searching', () => {
    const rendered = ReactTestRenderer.create(
      <FindReaction
        query="I lost my keys"
        reaction={{}}
        fetchReaction={jest.fn()}
        sharing={false}
        state={constants.STATE_SEARCHING}
      />
    ).toJSON();

    const text = rendered.children[1].children[0];
    expect(text.type).toBe('Text');
    expect(text.children).toEqual(["Searching..."]);
  });

  it('When nothing found', () => {
    const rendered = ReactTestRenderer.create(
      <FindReaction
        query="I lost my keys"
        reaction={{}}
        fetchReaction={jest.fn()}
        sharing={false}
        state={constants.STATE_NOT_FOUND}
      />
    ).toJSON();

    const text = rendered.children[1].children[0];
    expect(text.type).toBe('Text');
    expect(text.children.join(''))
      .toBe('Nothing found for "I lost my keys"');
  });

  it('When reaction found', () => {
    Image.getSize = (uri, callback) => callback(800, 600);

    const rendered = ReactTestRenderer.create(
      <FindReaction
        query="I lost my keys"
        reaction={{uri: "data:image/gif;base64,image-in-base-64"}}
        fetchReaction={jest.fn()}
        sharing={false}
        state={constants.STATE_FOUND}
      />
    ).toJSON();

    const image = rendered.children[1].children[0];
    expect(image.type).toBe('Image');
    expect(image.props.source.uri).toBe("data:image/gif;base64,image-in-base-64");
    expect(image.props.style).toEqual({
      width: 740,
      height: 555,
    });
  });

  it('Fetch reaction when query text changed', () => {
    const fetchReaction = jest.fn();
    const rendered = ReactTestRenderer.create(
      <FindReaction
        query=""
        reaction={{}}
        fetchReaction={fetchReaction}
        sharing={false}
        state={constants.STATE_EMPTY_QUERY}
      />
    ).toJSON();

    rendered.children[0].children[0].props.onChangeText("I can't find my car");

    expect(fetchReaction.mock.calls).toEqual([
      ["I can't find my car"],
    ]);
  });
});
