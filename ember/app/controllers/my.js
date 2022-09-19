import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';
import Ember from 'ember';

export default class MyController extends Controller {
  @tracked count = 0;

  getroomno = (value) => {
    console.log(value.id);
    this.count = value.id;
    console.log(this.count);
  };

  @action
  get() {
    var dis = this;
    var roomno = document.getElementById('roomno').value;
    var sdate = document.getElementById('sdate').value;
    var edate = document.getElementById('edate').value;
    console.log(
      'room no -> ' + roomno + 'sdate --> ' + sdate + 'edate -> ' + edate
    );
    $.ajax({
      url: 'http://localhost:8085/hotelres/My',
      method: 'POST',
      data: { roomno: roomno, sdate: sdate, edate: edate },
      success: function (response) {
        console.log('reserve response -> ' + response);
        if (response == 1) {
          alert('room reserved');
          dis.transitionToRoute('logins');
        } else if (response == 5) {
          dis.transitionToRoute('error');
        } else {
          alert('room reservation failed');
        }
      },
      error: function (xhr, status, error) {
        var errorMessage = xhr.status + ': ' + xhr.statusText;
        alert('error' + errorMessage);
      },
    });
  }
}
