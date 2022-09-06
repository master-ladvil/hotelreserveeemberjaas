import Model, { attr } from '@ember-data/model';
import Ember from 'ember';

export default class MyModel extends Model {
  @attr capacity;
  @attr rtype;
  @attr price;
}
