import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';

export default class PostsRoute extends Route {
  @service store;
  model() {
    console.log(this.store.findAll('My'));
    return this.store.findAll('post');
  }
}
