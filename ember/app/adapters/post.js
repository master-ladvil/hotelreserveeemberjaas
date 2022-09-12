import DS from 'ember-data';
import Ember from 'ember';

export default DS.RESTAdapter.extend({
  host: 'http://localhost:8085/hotelres',

  pathForType() {
    return 'My';
  },
});
