import React, { Component } from 'react';
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
import RaisedButton from 'material-ui/RaisedButton';
import { connect } from 'react-redux';
import { kweetsFetchAll } from '../actions/kweets';
import { Kweet } from './Kweet';


const styles = theme => ({
    root: theme.mixins.gutters({
        paddingTop: 16,
        paddingBottom: 16,
        marginTop: theme.spacing.unit * 3,
    }),
});

const mapStateToProps = (state) => {
    return {
        kweets: state.kweets,
        hasErrored: state.kweetsHasErrored,
        isLoading: state.kweetsIsLoading
    };
};

// const mapDispatchToProps = (dispatch) => {
//     return {
//         fetchData: dispatch(kweetsFetchAll())
//     };
// }


class UserList extends Component {
    componentDidMount() {
        this.props.dispatch(kweetsFetchAll());
    }

    render() {
        if (this.props.hasErrored) {
            return <p>Sorry! There was an error loading the kweets</p>;
        }
        if (this.props.isLoading) {
            return <p>Loading…</p>;
        }
        return (
            <div>
                {this.props.kweets.map((kweet) => (
                    <div key={kweet.id}>
                        <Kweet kweet={kweet} />
                    </div>
                ))}
            </div>
        );
    }
}
export default connect(mapStateToProps, null)(UserList);