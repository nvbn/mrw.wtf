import React, { Component } from 'react';
import {
  View,
  TextInput,
  Image,
  Dimensions,
  Text,
  TouchableHighlight,
} from 'react-native';
import styles from './styles';

class FindReaction extends Component {
  propTypes: {
    query: string,
    reaction: {url: string, name: string, title: string},
    fetchReaction: (query: string) => void,
    sharing: boolean,
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

  _calculateHeight(url) {
    Image.prefetch(url).then(() =>
      Image.getSize(url, (imageWidth, imageHeight) => {
        const width = Dimensions.get('window').width - 10;
        this.setState({
          width,
          height: (width / imageWidth) * imageHeight,
        })
      })
    );
  }

  _resetImage() {
    this.setState({
      width: 0,
      height: 0,
    });
  }

  componentWillReceiveProps({reaction}) {
    if (reaction && reaction.url) {
      this._calculateHeight(reaction.url);
    }
  }

  componentWillMount() {
    if (this.props.reaction && this.props.reaction.url) {
      this._calculateHeight(this.props.reaction.url);
    }
  }

  _renderReaction() {
    if (!this.props.reaction) {
      return (<View />);
    } else if (this.props.reaction && !this.state.height) {
      return (<Text>Loading...</Text>);
    } else {
      return (
        <View>
          <Image source={{uri: this.props.reaction.url}}
                 style={{
                    width: this.state.width,
                    height: this.state.height,
               }}/>
        </View>
      );
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <View>
          <TextInput defaultValue={this.props.query}
                     onChangeText={this._changeQuery}
                     placeholder="My reaction when..."
                     underlineColorAndroid="#00bcd4"
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
