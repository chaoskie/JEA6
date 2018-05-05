import React from 'react';
import { searchKweets } from '../actions/kweets';
import KweetList from './KweetList';

import { connect } from 'react-redux';

const mapStateToProps = (state, ownProps) => {
  return {
    query: ownProps.query,
    kweets: state.kweets
  };
};

class Timeline extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
        loadedKweets: ""
    }
  }

  componentDidMount() { }
  
  static getDerivedStateFromProps(nextProps, prevState) {
    if (!nextProps) { return prevState; }

    if (prevState.loadedKweets !== nextProps.query) {
        nextProps.dispatch(searchKweets(nextProps.query));
        return {...prevState, loadedKweets: nextProps.query};
    }

    return prevState;
  }

  getSearchKweets() {
    let kweets = this.props.kweets.filter(kweet => kweet.message.includes(this.props.query));
    return kweets;
  }

  render() {
    if (this.state.loadedKweets && !this.props.kweets.length) {
        return <h2 className="timeline"><br />No kweets found matching this search query!</h2>
    }
    
    return (
    <div className="timeline">
        <KweetList kweets={this.getSearchKweets()} />
    </div>)
  }

}


export default connect(mapStateToProps, null)(Timeline);