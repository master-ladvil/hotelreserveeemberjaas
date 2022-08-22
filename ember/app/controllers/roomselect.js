import Controller from '@ember/controller';
import { use, resource } from 'ember-resources';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';
import { service } from '@ember/service';

class RequestState {
  @tracked value;
  @tracked error;

  get isPending() {
    return !this.error && !this.value;
  }
}

export default class RoomselectController extends Controller {
  @service router;

  @use request = resource(({ on }) => {
    const state = new RequestState();
    var dis = this;
    $.ajax({
      url: 'http://localhost:8080/hotelres/My',
      method: 'GET',
      dataType: 'json',
      success: function(response){ 
        console.log(response)
        if(response == 0){
          dis.transitionToRoute("error");
        }else{
          state.value = response
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
    var roomno = document.getElementById('roomno').value;
    var sdate = document.getElementById('sdate').value;
    var edate = document.getElementById('edate').value;
    console.log(
      'room no -> ' + roomno + 'sdate --> ' + sdate + 'edate -> ' + edate
    );
    $.ajax({
      url: 'http://localhost:8080/hotelres/My',
      method: 'POST',
      data: { roomno: roomno, sdate: sdate, edate: edate },
      success: function (response) {
        console.log('reserve response -> ' + response);
        if (response == 1) {
          alert('room reserved');
          dis.transitionToRoute('roomreserved');
        } else if(response == 5){
          dis.transitionToRoute('error')
        } 
        else {
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
