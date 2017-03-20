import React, { Component } from 'react';
import {
  View,
  TextInput,
  Image,
  Dimensions,
  Text,
  TouchableNativeFeedback,
  StatusBar,
} from 'react-native';
import * as constants from '../../constants';
import { shareReaction } from "../../actions";
import navigationOptions from '../../navigator/navigationOptions';
import HeaderButton from '../header-button';
import styles from './styles';

type Props = {
  query: string,
  reaction: {url: string, name: string, title: string, uri: string},
  fetchReaction: (query: string) => void,
  sharing: boolean,
  state: string,
};

class FindReaction extends Component {
  props: Props;

  static navigationOptions = {
    title: 'MRW',
    header: ({navigate, state, dispatch}) => {
      const getRight = () => {
        if (state.params.sharing === constants.SHARING_AVAILABLE) {
          return (
            <HeaderButton
              onPress={() => dispatch(shareReaction())}
              icon="share"
            />
          );
        }
      };

      return {
        left: (
          <HeaderButton
            onPress={() => navigate(constants.ROUTE_ABOUT)}
            icon="info"
          />
        ),
        right: getRight(),
        ...navigationOptions.header(),
      }
    },
  };

  constructor() {
    super();
    this._changeQuery = this._changeQuery.bind(this);
    this.state = {
      width: 0,
      height: 0,
    };
  }

  _changeQuery(value: string) {
    this._resetImage();
    this.props.fetchReaction(value);
  }

  _calculateHeight(uri) {
    Image.getSize(uri, (imageWidth, imageHeight) => {
      const width = Dimensions.get('window').width - 10;
      this.setState({
        width,
        height: (width / imageWidth) * imageHeight,
      })
    });
  }

  _resetImage() {
    this.setState({
      width: 0,
      height: 0,
    });
  }

  componentWillReceiveProps({reaction}) {
    if (reaction && reaction.uri) {
      this._calculateHeight(reaction.uri);
    }
  }

  componentWillMount() {
    if (this.props.reaction && this.props.reaction.uri) {
      this._calculateHeight(this.props.reaction.uri);
    }
  }

  _renderReaction() {
    switch (this.props.state) {
      case constants.STATE_EMPTY_QUERY:
        return (<Text>Start searching reactions!</Text>);
      case constants.STATE_SEARCHING:
        return (<Text>Searching...</Text>);
      case constants.STATE_NOT_FOUND:
        return (<Text>Nothing found for "{this.props.query}"</Text>);
      case constants.STATE_FOUND:
        return (
          <Image source={{uri: this.props.reaction.uri}}
                 style={{
                    width: this.state.width,
                    height: this.state.height,
                 }}
          />
        );
      default:
        console.warn('Unexpected state', this.props.state);
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <StatusBar backgroundColor={constants.HEADER_COLOR}/>
        <View>
          <TextInput defaultValue={this.props.query}
                     onChangeText={this._changeQuery}
                     placeholder="My reaction when..."
                     underlineColorAndroid="#4078c0"
          />
        </View>
        <View style={styles.reaction}>
          {this._renderReaction()}
        </View>
      </View>
    );
  }
}

export default FindReaction;
