import Torii from 'ember-simple-auth/authenticators/torii';
import Ember from 'ember';
import { inject as service } from '@ember/service';
export default Torii.extend({
  torii: service('torii'),
  authenticate(provider, options) {
    return this.torii.open(provider, options);
  },
});
