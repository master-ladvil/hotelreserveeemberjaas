import Model, { attr } from '@ember-data/model';
import Ember from 'ember';

export default class AdminindexModel extends Model {
  @attr troom;
  @attr tclient;
  @attr tres;
  @attr error;
}
