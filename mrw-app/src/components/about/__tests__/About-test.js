import React from 'react';
import ReactTestRenderer from 'react-test-renderer';
import About from '../';

describe('About component', () => {
  it('Render information about app', () => {
    const rendered = ReactTestRenderer.create(<About />).toJSON();

    expect(rendered.children[0].children[0].type).toBe('Text');
  });
});
