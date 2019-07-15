/* eslint react/prefer-stateless-function: 0 */

import React from 'react';
import PropTypes from 'prop-types';
import Head from 'next/head';

import withAuth from '../lib/withAuth';
import withLayout from '../lib/withLayout';
import dynamic from 'next/dynamic'
import Link from 'next/link'
import Table from '../components/table'
const Dashboard = dynamic(import('../components/dashboard'), { ssr: false })
import { connect } from 'react-redux';

//import Dashboard from '../components/dashboard';

class Index extends React.Component {
  static propTypes = {
    user: PropTypes.shape({
      email: PropTypes.string.isRequired,
    }),
  };

  static defaultProps = {
    user: null,
  };

  render() {
    const { user } = this.props;
    return (
      <div style={{ padding: '10px 45px' }}>
        <Head>
          <title>Dashboard</title>
          <meta name="description" content="description for indexing bots" />
        </Head>
        <p> Bookings </p>
        <Table {...this.props} />
      </div>
    );
  }
}
const mapStateToProps = state =>  {
  //const {app_state} = state;     
  console.log("state in mapping",state);
  return {user:state.user};
}
export default connect(
mapStateToProps,
null,
)(withAuth(withLayout(Index)));

