import RESTAdapter from '@ember-data/adapter/rest';
import Ember from 'ember';

export default class ReservationAdapter extends RESTAdapter {
  host = 'http://localhost:8080/hotelres';

  pathForType() {
    console.log('Adapters...');
    return 'Reservation';
  }
}
