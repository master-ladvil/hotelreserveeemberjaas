import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';
import Ember from 'ember';
export default class MyroomRoute extends Route {
  @service store;
  async model() {
    console.log('inside the router..');
    return this.store.findAll('Myroom');
  }
}
