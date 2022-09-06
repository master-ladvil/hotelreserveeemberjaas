import Controller from '@ember/controller';
import { action } from '@ember/object';
import { service } from '@ember/service';
import $ from 'jquery';
import Ember from 'ember';

export default class AdminlogouteController extends Controller {
  @service router;
  @action
  get() {
    var dis = this;
    $.ajax({
      url: 'http://localhost:8080/hotelres/TokenExchange',
      method: 'GET',
      success: function (response) {
        if (response == 1) {
          alert('Logout successfull');
          dis.transitionToRoute('logins');
        } else {
          alert('Coulnt logout');
        }
      },
      error: function (xhr, status, error) {
        var errorMessage = xhr.status + ': ' + xhr.statusText;
        alert('error' + errorMessage);
      },
    });
  }
}
