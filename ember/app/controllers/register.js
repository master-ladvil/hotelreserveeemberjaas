import Controller from '@ember/controller';
import { action } from '@ember/object';
import { service } from '@ember/service';
import EmberResolver from 'ember-resolver';
import Ember from 'ember';
import $ from 'jquery';

export default class RegisterController extends Controller {
  @service router;
  @action
  get() {
    var that = this;
    var uname = document.getElementById('uname').value;
    var mobile = document.getElementById('mobile').value;
    console.log('uname ->' + uname);
    console.log('mobile -> ' + mobile);
    $.ajax({
      url: 'http://localhost:8080/hotelres/Register',
      method: 'POST',
      data: { uname: uname, mobile: mobile },
      success: function (response) {
        console.log('response ->' + response);
        if (response == 1) {
          alert('reg successfull');
          that.transitionToRoute('succcess');
        } else {
          alert('Reg failed');
          that.transitionToRoute('error');
        }
      },
      error: function (xhr, status, error) {
        var errorMessage = xhr.status + ': ' + xhr.statusText;
        alert('error' + errorMessage);
      },
    });
  }
}
