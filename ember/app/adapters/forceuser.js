import RESTAdapter from '@ember-data/adapter/rest';
import Ember from 'ember';

export default class ForceuserAdapter extends RESTAdapter {
  host = 'http://localhost:8080/lorduoauth';
  pathForType() {
    console.log('url -> /ConnectSalesForce');
    return 'ConnectSalesForce';
  }
}
