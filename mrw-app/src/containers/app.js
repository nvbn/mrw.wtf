import React, { Component } from 'react';
import { Provider } from 'react-redux';
import configureStore from '../store';
import WithNavigation from './WithNavigation';

const store = configureStore();

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <WithNavigation />
      </Provider>
    );
  }
}

export default App;
