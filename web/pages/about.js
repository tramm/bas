import React from 'react';
import { Grid } from '@material-ui/core';
import MUIDataTable from "mui-datatables";
import PropTypes from 'prop-types';
//import Partners from '../lib/api/about';
import { getPartners } from '../lib/api/admin';
import { getTodayBookings } from '../lib/api/admin';
//import PageTitle from '../../components/PageTitle';
//import Widget from '../../components/Widget';
//import Table from '../dashboard/components/Table/Table';
//import mock from '../dashboard/mock'; 

class Tables extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      partners: [],
      bookings: []
    };
  }

  async componentDidMount() {
    try {
      const resp = await getPartners();
      console.log("The data from admin js", resp.partners);
      const partnerArray = resp.partners.map((partner) => {
        console.log("Partner NAme", partner.name);
        return [partner.name, partner.mobile, partner.email, partner.tag]
      });
      console.log("The data -partner", partnerArray);
      this.setState({ partners: partnerArray }); // eslint-disable-line
    } catch (err) {
      console.log(err); // eslint-disable-line
    }
    try {
      const resp = await getTodayBookings();
      console.log("The data from admin js", resp.bookings);
      const bookingsArray = resp.bookings.map((booking) => {
        console.log("customer Name", booking.partner.name);
        return [booking.partner.name, booking.vehicle[0].manufacturer.name, booking.vehicle[0].registration_Number, booking.offer.serviceCenter_Name, booking.dateOfService]
      });
      console.log("The bookings data is ", bookingsArray);
      this.setState({ bookings: bookingsArray }); // eslint-disable-line
    } catch (err) {
      console.log(err); // eslint-disable-line
    }
  }

  render() {
    return (
      <React.Fragment>
        {/* <PageTitle title="Tables" /> */}
        <Grid container spacing={32}>
          <Grid item xs={12}>
            <MUIDataTable
              title="Booking Requests"
              data={this.state.bookings}
              columns={["Customer Name", "Vehicle", "Reg Number", "service center", "Request Date"]}
              options={{
                filterType: 'checkbox',
                search: false,
                print: false,
                download: true,
                selectableRows: false,
                rowsPerPage: 10,
                filter: false,
                filterType: 'dropdown',
                responsive: 'stacked',
                page: 0,
                onColumnSortChange: (changedColumn, direction) => console.log('changedColumn: ', changedColumn, 'direction: ', direction),
                onChangeRowsPerPage: numberOfRows => console.log('numberOfRows: ', numberOfRows),
                onChangePage: currentPage => console.log('currentPage: ', currentPage)
              }}
            />
          </Grid>
          {/* <Grid item xs={12}>
          <Widget title="Material-UI Table" upperTitle noBodyPadding>
            <Table data={mock.table} />
          </Widget>
        </Grid> */}
        </Grid>
      </React.Fragment>
    )
  }
};

export default Tables;