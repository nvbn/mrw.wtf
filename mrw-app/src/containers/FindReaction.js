import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import { fetchReaction } from '../actions';
import FindReaction from '../components/find-reaction';

const mapStateToProps = ({query, reaction, sharing, state}) => ({
  query, reaction, sharing, state,
});

const mapDispatchToProps = (dispatch) => bindActionCreators({
  fetchReaction,
}, dispatch);

export default connect(mapStateToProps, mapDispatchToProps)(FindReaction);
