import PropTypes from 'prop-types';
import Link from 'next/link';
import Toolbar from '@material-ui/core/Toolbar';
import Grid from '@material-ui/core/Grid';
import Hidden from '@material-ui/core/Hidden';
import Avatar from '@material-ui/core/Avatar';

import MenuDrop from './MenuDrop';

import { styleToolbar } from '../lib/SharedStyles';

const optionsMenu = [
  {
    text: 'Got questions?',
    href: 'https://github.com/tramm/bas/issues',
  },
  {
    text: 'Log out',
    href: '/logout',
  },
];

function Header({ user }) {
  return (
    <div>
      <Toolbar style={styleToolbar}>
        <Grid container direction="row" justify="space-around" alignItems="center">
          <Grid item sm={10} xs={9} style={{ textAlign: 'left' }}>
            {user ? (
              <Link prefetch href="/">
                <a>
                  <Avatar
                    src="/static/logo.png"
                    alt="Builder Book logo"
                    style={{ margin: '0px auto 0px 20px' }}
                  />
                </a>
              </Link>
            ):null}
          </Grid>
          <Grid item sm={1} xs={3} style={{ textAlign: 'right' }}>
            {user ? (
              <div style={{ whiteSpace: ' nowrap' }}>
                  <MenuDrop options={optionsMenu} src="/static/avatar.jpg" alt="Book a Service" />
              </div>
            ) : (
              <Link prefetch href="/userLogin">
                <a style={{ margin: '0px 20px 0px auto' }}>Log in</a>
              </Link>
            )}
          </Grid>
        </Grid>
      </Toolbar>
    </div>
  );
}

Header.propTypes = {
  user: PropTypes.shape({
    avatarUrl: PropTypes.string,
    email: PropTypes.string.isRequired,
  }),
};

Header.defaultProps = {
  user: null,
};

export default Header;
