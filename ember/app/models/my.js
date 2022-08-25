import Model, { attr } from '@ember-data/model';

export default class MyModel extends Model {
  @attr capacity;
  @attr rtype;
  @attr price;
}
