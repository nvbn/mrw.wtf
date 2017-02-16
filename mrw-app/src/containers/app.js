import React, { Component } from 'react';
import {
  Navigator,
  Text,
} from 'react-native';
import { Provider } from 'react-redux';
import configureStore from '../store';
import * as routes from '../routes';
import * as constants from '../constants';
import About from '../components/about';
import FindReaction from './FindReaction';
import getNavigationBar from './NavigationBar';

const store = configureStore();

class App extends Component {
  constructor() {
    super();
    this._renderScene = this._renderScene.bind(this);
  }

  _renderScene({type}) {
    if (type === constants.ROUTE_FIND_REACTION) {
      return (<FindReaction />);
    } else if (type === constants.ROUTE_ABOUT) {
      return (<About />);
    }
  }

  render() {
    return (
      <Provider store={store}>
        <Navigator initialRoute={routes.findReaction}
                   renderScene={this._renderScene}
                   navigationBar={getNavigationBar()}
        />
      </Provider>
    );
  }
}

export default App;
