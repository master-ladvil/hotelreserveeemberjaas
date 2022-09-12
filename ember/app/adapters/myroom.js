import RESTAdapter from '@ember-data/adapter/rest';
import Ember from 'ember';

export default class MyroomAdapter extends RESTAdapter {
  host = 'http://localhost:8085/hotelres';
  pathForType() {
    return 'Myroom';
  }
}
