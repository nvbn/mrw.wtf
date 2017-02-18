import React from 'react';
import {  Navigator } from 'react-native';
import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { shareReaction } from '../actions';
import getNavigationBar, { LeftButton, RightButton, Title } from '../components/navigation-bar';

const WrappedRightButton = connect(
  ({reaction, state, sharing}, ownProps) => ({reaction, sharing, state, ...ownProps}),
  (dispatch) => bindActionCreators({shareReaction}, dispatch),
)(RightButton);

const routeMapper = {
  LeftButton,
  RightButton: ({type}, navigator) => (<WrappedRightButton type={type} navigator={navigator} />),
  Title,
};

export default () => getNavigationBar(routeMapper);
