import Model, { attr } from '@ember-data/model';
import Ember from 'ember';

export default class ReservationModel extends Model {
  @attr fullname;
  @attr sdate;
  @attr edate;
  @attr rid;
}
