import Model from '@ember-data/model';
import { attr } from 'ember-data/model';
export default class EmbdtstModel extends Model {
  @attr capacity;
  @attr price;
  @attr rtype;
}
