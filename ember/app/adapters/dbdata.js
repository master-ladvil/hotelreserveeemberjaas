import RESTAdapter from '@ember-data/adapter/rest';
import Ember from 'ember';

export default class DbdataAdapter extends RESTAdapter {
  host = 'http://localhost:8085/hotelres';
  pathForType() {
    console.log('Adapter...');
    return 'GetDbData';
  }
}
