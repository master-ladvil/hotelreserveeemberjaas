import RESTAdapter from '@ember-data/adapter/rest';
import Ember from 'ember';

export default class AdminIndexAdapter extends RESTAdapter {
  host = 'http://localhost:8080/hotelres';
  pathForType() {
    return 'My';
  }
}
