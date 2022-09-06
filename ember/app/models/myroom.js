import Model, { attr } from '@ember-data/model';
import Ember from 'ember';

export default class MyroomModel extends Model {
  @attr sdate;
  @attr edate;
  @attr rid;
}
