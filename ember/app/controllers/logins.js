import Controller from '@ember/controller';
import { action } from '@ember/object';
import { service } from '@ember/service';
import { tracked } from '@glimmer/tracking';
//import EmberResolver from 'ember-resolver';
import Ember from 'ember';
import $ from 'jquery';

export default class LoginsController extends Controller {
  @tracked result = 0;
  @service router;
  //canLogin:true;
  @action
  get() {
    var that = this;

    var uname = document.getElementById('uname').value;
    var mobile = document.getElementById('mobile').value;
    console.log('name ->' + uname);
    console.log('mobile ->' + mobile);
    $.ajax({
      url: 'http://localhost:8080/hotelres/AuthenticationServlet',
      method: 'GET',
      data: { uname: uname, mobile: mobile },
      success: function (response) {
        that.result = response;
        console.log('response-> ' + response);
        if (that.result == 1) {
          alert('sucess');
          that.transitionToRoute('my');
          console.log(that.result);
        } else if (that.result == 2022) {
          alert('Asuccess');
          that.transitionToRoute('admin');
        } else {
          alert('Login failed');
        }
      },
      error: function (xhr, status, error) {
        var errorMessage = xhr.status + ':' + xhr.statusText;
        alert('error' + errorMessage);
      },
    });
  }
}
