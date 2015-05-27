var React = require('react');

var Home = React.createClass({
    render: function() {
        return (
            <div>Oh! {this.props.message}</div>
        );
    }
});

module.exports = Home;