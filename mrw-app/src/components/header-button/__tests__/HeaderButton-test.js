jest.mock('react-navigation/lib-rn/views/TouchableItem', () => 'TouchableItem');
jest.mock('react-native-vector-icons/MaterialIcons', () => 'Icon');

import React from 'react';
import ReactTestRenderer from 'react-test-renderer';
import HeaderButton from '../index';
import { query } from '../../../utils';

describe('Header button', () => {
  it('Contains icon', () => {
    const rendered = ReactTestRenderer.create(
      <HeaderButton icon="info" />
    ).toJSON();

    const icon = query(rendered, ({type}) => type === 'Icon')[0];
    expect(icon.props.name).toBe('info');
  });

  it('Can be touched', () => {
    const callback = jest.fn();
    const rendered = ReactTestRenderer.create(
      <HeaderButton onPress={callback} />
    ).toJSON();

    const touchable = query(rendered, ({type}) => type === 'TouchableItem')[0];
    touchable.props.onPress();
    expect(callback.mock.calls.length).toBe(1);
  });
});
