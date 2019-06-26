import React from 'react';
import { Grid } from '@material-ui/core';
import MUIDataTable from "mui-datatables";
import PropTypes from 'prop-types';
//import Partners from '../lib/api/about';
import { getPartners } from '../lib/api/admin';
//import PageTitle from '../../components/PageTitle';
//import Widget from '../../components/Widget';
//import Table from '../dashboard/components/Table/Table';
//import mock from '../dashboard/mock'; 

const datatableData = [
  ["Joe James", "Example Inc.", "Yonkers", "NY"],
  ["John Walsh", "Example Inc.", "Hartford", "CT"],
  ["Bob Herm", "Example Inc.", "Tampa", "FL"],
  ["James Houston", "Example Inc.", "Dallas", "TX"],
  ["Prabhakar Linwood", "Example Inc.", "Hartford", "CT"],
  ["Kaui Ignace", "Example Inc.", "Yonkers", "NY"],
  ["Esperanza Susanne", "Example Inc.", "Hartford", "CT"],
  ["Christian Birgitte", "Example Inc.", "Tampa", "FL"],
  ["Meral Elias", "Example Inc.", "Hartford", "CT"],
  ["Deep Pau", "Example Inc.", "Yonkers", "NY"],
  ["Sebastiana Hani", "Example Inc.", "Dallas", "TX"],
  ["Marciano Oihana", "Example Inc.", "Yonkers", "NY"],
  ["Brigid Ankur", "Example Inc.", "Dallas", "TX"],
  ["Anna Siranush", "Example Inc.", "Yonkers", "NY"],
  ["Avram Sylva", "Example Inc.", "Hartford", "CT"],
  ["Serafima Babatunde", "Example Inc.", "Tampa", "FL"],
  ["Gaston Festus", "Example Inc.", "Tampa", "FL"],
];

class Tables extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      partners: []
    };
  }

  async componentDidMount() {
    try {
      const resp = await getPartners();
      console.log("The data from admin js", resp.partners);
      const partnerArray  = resp.partners.map((partner) => {
        console.log("Partner NAme",partner.name);
       return [partner.name,partner.mobile,partner.email,partner.tag]
      });
      console.log("The data -partner", partnerArray);
      this.setState({ partners: partnerArray }); // eslint-disable-line
    } catch (err) {
      console.log(err); // eslint-disable-line
    }
    // const url = "http://localhost:8000/api/v1/user/partners";
    // fetch(url,{
    //     method: 'GET',
    //     withCredentials: true,
    //     credentials: 'include',
    //     headers: {
    //         'Authorization': 'JWT eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjZGQxZmZmNTE1ZWY3MzZmYzYxZjllYiIsImlhdCI6MTU1Nzk5NTUxOX0.9EBSn_O6Thy6FlExMMXBGf4sfnMKKntTY-VYc1okYBQ',
    //         'X-FP-API-KEY': 'iphone', //it can be iPhone or your any other attribute
    //         'Content-Type': 'application/json'
    //     }
    // }).then(res => res.json())
    //   .then((data) => {
    //     console.log("The result is ",data);
    //     this.setState({ partners: data.partners })
    //   })
  }

  render() {
    return (
      <React.Fragment>
        {/* <PageTitle title="Tables" /> */}
        <Grid container spacing={32}>
          <Grid item xs={12}>
            <MUIDataTable
              title="Partner List"
              data=  {this.state.partners}
              columns={["Name", "Mobile", "Email", "Tag"]}
              options={{
                filterType: 'checkbox',
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