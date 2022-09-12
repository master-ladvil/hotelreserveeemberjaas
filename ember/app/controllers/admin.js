import Controller from '@ember/controller';
import { use, resource } from 'ember-resources';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';
import { service } from '@ember/service';
import Ember from 'ember';

class RequestState {
  @tracked value;
  @tracked error;
  get isPending() {
    return !this.error && !this.value;
  }
}
export default class AdminController extends Controller {
  @service router;
  @use request = resource(({ on }) => {
    var dis = this;
    const state = new RequestState();
    $.ajax({
      url: 'http://localhost:8085/hotelres/Admin',
      method: 'GET',

      success: function (response) {
        console.log(response);
        if (response == 4 || response == 5) {
          dis.transitionToRoute('error');
        } else {
          state.value = response;
        }
      },
      error: (xhr, status, error) =>
        (state.error = `${status}: ${xhr.statusText}`),
    });
    return state;
  });
  get result() {
    return this.request.value || [];
  }
  @action
  get() {
    var dis = this;
    var capacity = document.getElementById('capacity').value;
    var rtype = document.getElementById('rtype').value;
    var price = document.getElementById('price').value;
    console.log(
      'capacity -> ' + capacity + 'rtype --> ' + rtype + 'price -> ' + price
    );
    $.ajax({
      url: 'http://localhost:8085/hotelres/AdminFunctions',
      method: 'GET',
      data: { capacity: capacity, rtype: rtype, price: price },
      success: function (response) {
        console.log('reserve response -> ' + response);
        if (response == 1) {
          alert('room added');
        } else {
          alert('room addition failed');
        }
      },
      error: function (xhr, status, error) {
        var errorMessage = xhr.status + ': ' + xhr.statusText;
        alert('error' + errorMessage);
      },
    });
  }
}
