import React from 'react';
import PropTypes from 'prop-types';
import Router from 'next/router';

let globalUser = null;

export default (
  Page,
  { loginRequired = true, logoutRequired = false, adminRequired = false } = {},
) => class BaseComponent extends React.Component {
    static propTypes = {
      user: PropTypes.shape({
        id: PropTypes.string,
        isAdmin: PropTypes.bool,
      }),
      isFromServer: PropTypes.bool.isRequired,
    };

    static defaultProps = {
      user: null,
    };

    componentDidMount() {
      const { user, isFromServer } = this.props;
      console.log("The user in request is :",user);

      if (isFromServer) {
        globalUser = user;
      }

      if (loginRequired && !logoutRequired && !user) {
        console.log("The login req and not user is there  :",user);

        Router.push('/login', '/login');
        return;
      }

      if (logoutRequired && user) {
        console.log("The logout required and user is there  :",user);
        Router.push('/');
      }

      if (adminRequired && (!user || !user.isAdmin)) {
        Router.push('/');
      }
    }

    static async getInitialProps(ctx) {
      console.log("The context in with Auth",ctx);
      const isFromServer = !!ctx.req;
      const user = ctx.req ? ctx.req.user && ctx.req.user.toObject() : globalUser;

      if (isFromServer && user) {
        user._id = user._id.toString();
      }
      
      const props = { user, isFromServer };

      if (Page.getInitialProps) {
        Object.assign(props, (await Page.getInitialProps(ctx)) || {});
      }

      return props;
    }

    render() {
      const { user } = this.props;

      if (loginRequired && !logoutRequired && !user) {
        return null;
      }

      if (logoutRequired && user) {
        return null;
      }

      if (adminRequired && (!user || !user.isAdmin)) {
        return null;
      }

      return <Page {...this.props} />;
    }
};
