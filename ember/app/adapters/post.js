import DS from 'ember-data';
import Ember from 'ember';

export default DS.RESTAdapter.extend({
  host: 'http://localhost:8080/hotelres',

  pathForType() {
    return 'My';
  },
});
